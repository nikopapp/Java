import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Explain1{
  private Story st;
  private TextScrolling tx;
  private int scenecntr = 0;
  private int framerate = 25;
  private Rectangle windowSize;
  public static void main(String[] args){
    Explain1 program = new Explain1();
    SwingUtilities.invokeLater(program.new Scene1());
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
    JFrame frame = new JFrame("Learn about Artificial Neural Netwroks");
    public void run() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(st);
      frame.pack();
      // frame.setUndecorated(true);
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      // frame.setLocationByPlatform(true);
      frame.setVisible(true);
      // t.setInitialDelay(2000);
      scene1();
      windowSize = frame.getBounds();
    }
    private void scene1(){
      Story st = new Story();
      Timer t = new Timer(framerate, new Animation());
      t.start();
    }
    private void scene2(){
      TextScrolling tx = new TextScrolling();
      Timer t = new Timer(framerate, new Animation());
      tx.setWindowSize(frame.getBounds());
      t.start();
    }
  }
  class Animation implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      switch (scenecntr){
        case 0:
          if(st.lineGrowing()==false)
            scenecntr++;
            break;
            // Scene1.frame.remove(st);
            // frame.add(tx);
            // frame.pack();
        case 1:
          scenecntr++;
          break;
        case 2:
          tx.scroll();
          break;
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
