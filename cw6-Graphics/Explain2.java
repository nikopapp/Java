import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Explain2{
  JFrame frame = new JFrame("Learn about Artificial Neural Netwroks");
  private Story st;
  private TextScrolling tx;
  private int scenecntr = 0;
  Explain2(){
    st = new Story();
    tx = new TextScrolling();
  }
  public static void main(String[] args){
    Explain program = new Explain();
    // SwingUtilities.invokeLater(program.new Scene1());
    // SwingUtilities.invokeLater(program.new Scene2());
    // ----------------------------
    // frame.getContentPane().add(st);
    // // BufferedImage img = loadImage("resources/slime.png");
    // // frame.getContentPane().setLayout(new FlowLayout());
    // // frame.getContentPane().add(new JLabel(new ImageIcon(img)));
    // frame.pack();
    // frame.setSize(500,600);
    // frame.setVisible(true);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  class Scene1 implements Runnable {

    private Timer t = new Timer(25, new Animation1());
    public void run() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(st);
      frame.pack();
      // frame.setUndecorated(true);
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      // frame.setLocationByPlatform(true);
      frame.setVisible(true);
      t.setInitialDelay(2000);
      t.start();
      tx.setWindowSize(frame.getBounds());
    }
    class Animation1 implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        System.out.println(st.lineGrowing());
        if(st.lineGrowing()==false){
          t.stop();
        }
      }
    }
  }
  class Scene2 implements Runnable {
    Timer t = new Timer(25, new Animation2());
    public void run() {
      tx.setWindowSize(frame.getBounds());
      t.start();
    }
    class Animation2 implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        if(!tx.isRunning()) {
          changeScene();
          tx.start();
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
//------------------------------------------------------

  private static BufferedImage loadImage(String url){
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(url));
    } catch (IOException e) {}
    return img;
  }
}
