package API;
/*
*   This code was taken from https://ratiler.wordpress.com/2014/09/08/detection-de-mouvement-avec-javacv/
*   and has been refactored to match the needs of this assignement
*/

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class JavaCVPrjt03{
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static Mat imag = null;
    static VideoCapture camera = new VideoCapture(0);
    static Size sz = new Size(640, 480);
    static JLabel vidpanel = new JLabel();
    static Mat frame = new Mat();
    static Mat outerBox = new Mat();
    static Mat diff_frame = null;
    static Mat tempon_frame = null;
    static private int filter = 0;
    public JavaCVPrjt03(int filter){
      this.filter=filter;
    }
    public JavaCVPrjt03(){
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
      JFrame jframe = new JFrame("HUMAN MOTION DETECTOR FPS");
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setContentPane(vidpanel);
      jframe.setSize(640, 480);
      jframe.setVisible(true);
      // run();
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

    public static ImageIcon run(int i){

      if (camera.read(frame)) {
        readFrame();
        Imgproc.GaussianBlur(outerBox, outerBox, new Size(3, 3), 0);

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

    public static ImageIcon runSimpleED(){

      if (camera.read(frame)) {
          readFrame();
          Imgproc.GaussianBlur(outerBox, outerBox, new Size(3, 3), 0);


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


    public static ImageIcon runSimpleGS(){

      if (camera.read(frame)) {
        readFrame();
        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }
    public static ImageIcon runSimpleGSBlur(){

      if (camera.read(frame)) {
        readFrame();

        Imgproc.GaussianBlur(outerBox, outerBox, new Size(7, 7), 3);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }
    public static ImageIcon runSimpleGSLaplacian(){

      if (camera.read(frame)) {
        readFrame();

        Imgproc.Laplacian(outerBox, outerBox, 0);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }
    public static ImageIcon runLaplacian(){

      if (camera.read(frame)) {
        readFrame();

        Imgproc.Laplacian(outerBox, outerBox, 0,3,1,0,0);

        ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        return image;
        }

      return null;

    }


    private static void readFrame(){
      Imgproc.resize(frame, frame, sz);
      imag = frame.clone();
      outerBox = new Mat(frame.size(), CvType.CV_8UC1);
      Imgproc.cvtColor(frame, outerBox, Imgproc.COLOR_BGR2GRAY);
    }

    public static BufferedImage Mat2bufferedImage(Mat image) {
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

}
