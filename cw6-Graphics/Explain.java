import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Explain{
  JFrame frame = new JFrame("Learn about Artificial Neural Netwroks");
  private Story st;
  private TextScrolling tx;
  Explain(){
    st = new Story();
    tx = new TextScrolling();
  }
  public static void main(String[] args){
    Explain program = new Explain();
    SwingUtilities.invokeLater(program.new Scene());
    // SwingUtilities.invokeLater(program.new Scene2());
    // frame.getContentPane().add(st);
    // // BufferedImage img = loadImage("resources/slime.png");
    // // frame.getContentPane().setLayout(new FlowLayout());
    // // frame.getContentPane().add(new JLabel(new ImageIcon(img)));
    // frame.pack();
    // frame.setSize(500,600);
    // frame.setVisible(true);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  class Scene implements Runnable {
    private boolean slide1=false;
    private int sceneCnt = 0;
    public void run() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(st);
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setLocationByPlatform(true);
      frame.pack();
      // frame.setUndecorated(true);
      frame.setVisible(true);
      Timer t = new Timer(25, new Animation1());
      t.setInitialDelay(2000);
      t.start();
      tx.setWindowSize(frame.getBounds());
    }
    class Animation1 implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        switch (sceneCnt){
          case 0:
            if(st.lineGrowing()==false){
              sceneCnt++;
            }
            break;
          case 1:
            changeScene();
            sceneCnt++;
            break;
          case 2:
            tx.scroll();
            break;
        }
      }
      private void slide2(){
        if(!tx.isRunning()){
          tx.setRunning();
          changeScene();
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
  // class Scene2 implements Runnable {
  //   Timer t = new Timer(25, new Animation2());
  //   public void run() {
  //     t.start();
  //   }
  //   class Animation2 implements ActionListener {
  //     public void actionPerformed(ActionEvent e) {
  //       if(!tx.isRunning()) {
  //         // changeScene();
  //         tx.setRunning();
  //       }
  //       else tx.scroll();
  //     }
  //   }
  // }
//------------------------------------------------------

  private static BufferedImage loadImage(String url){
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(url));
    } catch (IOException e) {}
    return img;
  }
}
