import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Explain{
  private Color grayBg = new Color(50,50,50);
  private Story st;
  private TextScrolling tx;
  JFrame frame = new JFrame("Learn about Artificial Neural Netwroks");
  Explain(){
    st = new Story();
    tx = new TextScrolling();
  }
  public static void main(String[] args){
    Explain program = new Explain();
    SwingUtilities.invokeLater(program.new Scene());
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
    private int sceneCnt = 0;
    public void run() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setBackground(grayBg);
      // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setSize(500,600);
      frame.setLocationByPlatform(true);
      frame.add(st);
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
      private void slide1(){
        if(st.lineGrowing()==false) sceneCnt++;
      }
      private void slide2(){
        if(!tx.isRunning()){
          tx.setRunning();
          changeScene();
          tx.repaint();
        }
        else tx.scroll();
      }
      private void changeScene(){
        frame.remove(st);
        frame.add(tx);
        frame.pack();
      }
    }
  }
}
  //------------------------------------------------------
