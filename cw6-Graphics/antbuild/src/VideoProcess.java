package API;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.net.*;
import javax.naming.Context;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfRect;

public class VideoProcess{
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private processor my_processor = new processor();
    static Mat imag = null;
    static VideoCapture camera = new VideoCapture(0);
    static Size sz = new Size(640, 480);
    static JLabel vidpanel = new JLabel();
    static Mat frame = new Mat();
    static Mat outerBox = new Mat();
    static Mat diff_frame = null;
    static Mat tempon_frame = null;
    static private int filter = 0;
    public VideoProcess(int filter){
      this.filter=filter;
    }
    public VideoProcess(){
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
      JFrame jframe = new JFrame("HUMAN MOTION DETECTOR FPS");
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setContentPane(vidpanel);
      jframe.setSize(640, 480);
      jframe.setVisible(true);
      JavaCVPrjt03 prog = new JavaCVPrjt03();
      // SwingUtilities.invokeLater(prog::run);
      int i = 0;
      while (true) {
        switch(filter){
          case 0:
          vidpanel.setIcon(prog.runSimpleGS());
          break;
          case 1:
          vidpanel.setIcon(prog.run(i));
          break;
        }
      }
    }

    /*
    *   This code was taken from https://ratiler.wordpress.com/2014/09/08/detection-de-mouvement-avec-javacv/
    *   and has been refactored to match the needs of this assignement
    */
    public ImageIcon run(int i){

      if (camera.read(frame)) {
        readFrame();
        Imgproc.GaussianBlur(outerBox, outerBox, new Size(7, 7), 0);

        if (i == 0) {
          diff_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
          tempon_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
          diff_frame = outerBox.clone();
        }

        if (i == 1) {
          Core.subtract(outerBox, tempon_frame, diff_frame);
          Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
             Imgproc.ADAPTIVE_THRESH_MEAN_C,
             Imgproc.THRESH_BINARY_INV, 5, 2);
        }

        i = 1;
        ImageIcon image = new ImageIcon(Mat2bufferedImage(diff_frame));
        tempon_frame = outerBox.clone();
        return image;
      }

      return null;

    }

    public ImageIcon runSimpleED(){

      if (camera.read(frame)) {
          readFrame();
          Imgproc.GaussianBlur(outerBox, outerBox, new Size(7, 7), 0);


          diff_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
          diff_frame = outerBox.clone();

          Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
          Imgproc.ADAPTIVE_THRESH_MEAN_C,
          Imgproc.THRESH_BINARY_INV, 5, 2);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(diff_frame));
        return image;
      }

      return null;

    }


    public ImageIcon runSimpleGS(){

      if (camera.read(frame)) {
        readFrame();
        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }

    public ImageIcon runHand(){

      if (camera.read(frame)) {

            readFrame();
            outerBox = my_processor.detect(frame);
        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }
    public int runFaceCoordX(){
      if (camera.read(frame)) {
        readFrame();
        int  p = my_processor.getFaceCoords(frame);
        return p;
        }
      return -1;
    }

    public ImageIcon runSimpleGSBlur(){

      if (camera.read(frame)) {
        readFrame();

        Imgproc.GaussianBlur(outerBox, outerBox, new Size(7, 7), 3);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }
    public ImageIcon runSimpleGSLaplacian(){

      if (camera.read(frame)) {
        readFrame();

        Imgproc.Laplacian(outerBox, outerBox, 0);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }
    public ImageIcon runLaplacian(){

      if (camera.read(frame)) {
        readFrame();

        Imgproc.Laplacian(outerBox, outerBox, 0,3,1,0,0);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }


    private void readFrame(){
      Imgproc.resize(frame, frame, sz);
      imag = frame.clone();
      outerBox = new Mat(frame.size(), CvType.CV_8UC1);
      Imgproc.cvtColor(frame, outerBox, Imgproc.COLOR_BGR2GRAY);
    }

    public BufferedImage Mat2bufferedImage(Mat image) {
        MatOfByte bytemat = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage img = null;
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    /*
     * The processor class was inspired and at many points copied from the bloger below:
     * http://cell0907.blogspot.co.uk/2013/07/detecting-faces-in-your-webcam-stream.html
     */
    class processor {
      private CascadeClassifier face_cascade;
      // Create a constructor method
      public processor(){
        face_cascade=new CascadeClassifier("/home/np15685/Downloads/Software/opencv-3.1.0/data/haarcascades/haarcascade_frontalface_default.xml");
        if(face_cascade.empty()){
          System.out.println("--(!)Error loading A\n");
          return;
        }
        else{
          System.out.println("Face classifier loooaaaaaded up");
        }
      }
      public Mat detect(Mat inputframe){
        Mat mRgba=new Mat();
        Mat mGrey=new Mat();
        MatOfRect faces = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(mGrey, mGrey, new Size(5,5), 3);
        Imgproc.equalizeHist( mGrey, mGrey );
        face_cascade.detectMultiScale(mGrey, faces);
        System.out.println(String.format("Detected %s faces", faces.toArray().length));
        for(Rect rect:faces.toArray()){
          Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );
          Imgproc.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar(255,0,255) , 4, 8, 0 );
        }
        return mRgba;
      }
      public int getFaceCoords(Mat inputframe){
        Mat mRgba=new Mat();
        Mat mGrey=new Mat();
        MatOfRect faces = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(mGrey, mGrey, new Size(5,5), 3);
        Imgproc.equalizeHist( mGrey, mGrey );
        face_cascade.detectMultiScale(mGrey, faces);
        Rect[] rect=faces.toArray();
        if(rect.length > 0){
          int centerX = (int)rect[0].x;
          return centerX;
        }
        else {
          return -1;
        }
      }
    }

}
