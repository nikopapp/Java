import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Explain{
  private Color grayBg = new Color(50,50,50);
  Story st;
  ColorCombination cc;
  TextScrolling tx;
  Box mainFrame;
  JFrame frame = new JFrame("Learn about Artificial Neural Netwroks");
  int sceneCnt = 0;
  Explain(){
    st = new Story();
    cc = new ColorCombination();
    tx = new TextScrolling();
  }
  public static void main(String[] args){
    // Explain program = new Explain();
    // SwingUtilities.invokeLater(program.new Scene());
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
    private boolean slide1=false;
    public void run() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setBackground(grayBg);
      // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      // frame.setSize(500,600);
      frame.setLocationByPlatform(true);
      // frame.add(st);
      // mainFrame=navFrame(st);
      frame.add(mainFrame);
      frame.pack();
      // frame.setUndecorated(true);
      frame.setVisible(true);
      Timer t = new Timer(25, new Animation());
      // t.setInitialDelay(2000);
      t.start();
      tx.setWindowSize(frame.getBounds());
      st.setWindowSize(frame.getBounds());
    }

    class Animation implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        switch (sceneCnt){
          case 0:
            slide1();
            break;
          case 1:
            slide2();
            break;
          case 2:

            break;
        }
      }
    }
    }
    public void slide1(){
      if(st.lineGrowing()==false) sceneCnt++;
    }
    public void slide2(){
      tx.scroll();
    }
    public void changeScene(){
      mainFrame.remove(st);
      mainFrame.add(tx);
      frame.pack();
  }
}
  //------------------------------------------------------
