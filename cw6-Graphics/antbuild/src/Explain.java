package API;

import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.*;


public class Explain extends JFrame {

  private Color grayBg = new Color(50,50,50);
  ArrayList<Slide> storyline = new ArrayList<Slide>();
  Box mainFrame;
  int sceneCnt = 0;
  Explain(){
    storyline.add(new Intro());
    storyline.add(new Story());
    storyline.add(new ColorCombination());
    storyline.add(new ChannelCombination());
    storyline.add(new TextScrolling());
    storyline.add(new MotionDet());
    storyline.add(new Pong());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Introduction to Image Proccessing");
    addComponentListener(new resizeListener());
  }
  class resizeListener extends ComponentAdapter {
    public void componentResized(ComponentEvent e) {
      adaptSlides(getSize());
    }
  }
  public static void main(String[] args){
    Explain program = new Explain();
    SwingUtilities.invokeLater(program.new Scene());
    // ------------------------------------
    // frame.getContentPane().add(st);
    // // BufferedImage img = loadImage("resources/slime.png");
    // // frame.getContentPane().setLayout(new FlowLayout());
    // // frame.getContentPane().add(new JLabel(new ImageIcon(img)));
    // frame.pack();
    // frame.setVisible(true);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  class Scene implements Runnable {
    public void run() {
      getContentPane().setBackground(grayBg);
      add(mainFrame);
      pack();
      setVisible(true);
      Timer t = new Timer(25, new Animation());
      t.setInitialDelay(1000);
      t.start();
      // File f=new File("../../../../resources/zap.mp3");


      // tx.setWindowSize(getBounds());
      // st.setWindowSize(getBounds());
    }
    class Animation implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        switch (sceneCnt){
          case 0:
            slide1();
            break;
          case 1:
            break;
          case 4:
            slide4();
            break;
          case 5:
            break;
          case 6:
            slide6();
            break;
        }
      }
    }
    }
    public void slide1(){
      if(storyline.get(1).tick()==false) sceneCnt++;
    }
    public void slide4(){
      storyline.get(4).start();
      storyline.get(4).tick();
    }

    public void slide5(){
      storyline.get(5).tick();
    }
    public void slide6(){
      storyline.get(6).tick();
    }
  private void adaptSlides(Dimension d){
    for(Slide s:storyline){
      s.windowHeight = d.height;
      s.windowWidth =  d.width;
    }
  }

}
  //------------------------------------------------------
