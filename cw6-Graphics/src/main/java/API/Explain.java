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
  // Story st = new Story();
  // ColorCombination cc;
  // ChannelCombination chc;
  // TextScrolling tx;
  Box mainFrame;
  // JFrame frame = new JFrame("Learn about Artificial Neural Netwroks");
  int sceneCnt = 0;
  Explain(){
    storyline.add(new Story());
    storyline.add(new ColorCombination());
    storyline.add(new ChannelCombination());
    storyline.add(new TextScrolling());
    // st  = new Story();
    // cc  = new ColorCombination();
    // chc = new ChannelCombination();
    // tx  = new TextScrolling();
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
      //    Player p=Manager.createRealizedPlayer(f.toURI().toURL());
      //    p.start();

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
        }
      }
    }
    }
    public void slide1(){
      if(storyline.get(0).tick()==false) sceneCnt++;
    }
    public void slide4(){
      storyline.get(3).start();
      storyline.get(3).tick();
    }
  private void adaptSlides(Dimension d){
    for(Slide s:storyline){
      s.windowHeight = d.height;
      s.windowWidth =  d.width;
    }
  }

}
  //------------------------------------------------------
