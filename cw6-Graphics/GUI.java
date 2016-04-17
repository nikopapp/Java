import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.net.*;

import java.awt.event.*;

class GUI extends JPanel {
  private static final long serialVersionUID = 1L;
  private static Explain storyline = new Explain();
  private Timer t = new Timer(25, new Animation());
  private Box mainFrame;
  GUI(){
    setPreferredSize(new Dimension(storyline.st.windowWidth,storyline.st.windowHeight));
  }
  public static void main(String args[]){
    GUI gui = new GUI();
    SwingUtilities.invokeLater(gui::run);
    // SwingUtilities.invokeLater(storyline::run);
  }
  private void run(){
    // JFrame w = new JFrame()
    storyline.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    storyline.frame.setTitle("Introduction to Image Proccessing");
    mainFrame=navFrame();
    storyline.frame.add(mainFrame);
    mainFrame.add(storyline.st);
    // storyline.frame.add()
    storyline.frame.pack();
    storyline.frame.setLocationByPlatform(false);
    storyline.frame.setVisible(true);
    t.start();
  }
  private Box navFrame(){
    Border border = BorderFactory.createEmptyBorder(10,10,10,10);
    Box box = Box.createHorizontalBox();
    //necessary for the image to be loaded directly from the jar file
    // URL resource = getClass().getClassLoader().getResource("logo/logo.png");
    // JLabel logo = new JLabel(new ImageIcon(resource));
    // box.add(logo);
    box.add(navButtons());
    // box.add(something);
    box.setBorder(border);
    return box;
  }

  private JPanel navButtons(){
    JPanel box = new JPanel();
    BoxLayout layout = new BoxLayout(box,BoxLayout.PAGE_AXIS);
    box.setBackground(new Color(0xFDFADF));
    ButtonStyle bStyle = new ButtonStyle();
    JButton[] buttons =  new JButton[30];
    for (int i=0;i<10;i++){
      buttons[i] = new JButton("Scene " +i);
    }
    buttonAddAction(buttons);
    box.setLayout(layout);
    for (JButton but:buttons){
      if(but!=null){
        but.setForeground(Color.white);
        but.setBackground(new Color(0xAABBAA));
        but.setUI(bStyle);
        box.add(but);
      }
    }
    return box;
  }
  private void buttonAddAction(JButton[] buttons){
    buttons[0].addActionListener(this::gotoSlide1);
    buttons[1].addActionListener(this::gotoSlide2);
    buttons[2].addActionListener(this::gotoSlide3);
    buttons[3].addActionListener(this::gotoSlide1);
    buttons[4].addActionListener(this::gotoSlide2);
    buttons[5].addActionListener(this::gotoSlide3);
    buttons[6].addActionListener(this::gotoSlide1);
    buttons[7].addActionListener(this::gotoSlide2);
    buttons[8].addActionListener(this::gotoSlide3);
    buttons[9].addActionListener(this::gotoSlide1);
  }
  private void gotoSlide1(ActionEvent e){
    System.out.println("gotoSlide1");
    storyline.sceneCnt=0;
    // storyline.st.reset();
    mainFrame.remove(1);
    mainFrame.add(storyline.st,1);
    storyline.frame.revalidate();
    storyline.frame.pack();
  }
  private void gotoSlide2(ActionEvent e){
    System.out.println("gotoSlide2");
    storyline.sceneCnt=1;
    mainFrame.remove(1);
    mainFrame.add(storyline.tx,1);
    storyline.tx.start();
    storyline.frame.revalidate();
    storyline.frame.pack();
  }
  private void gotoSlide3(ActionEvent e){
    System.out.println("gotoSlide3");
    storyline.sceneCnt=2;
    mainFrame.remove(1);
    mainFrame.add(storyline.cc,1);
    storyline.frame.revalidate();
    storyline.frame.pack();

  }
  class Animation implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      switch (storyline.sceneCnt){
        case 0:
          storyline.slide1();
          break;
        case 1:
          storyline.slide2();
          break;
        case 2:
          break;
      }
    }
    private void changeScene(){
      storyline.mainFrame.remove(storyline.st);
      storyline.mainFrame.add(storyline.tx);
      storyline.frame.add(storyline.mainFrame);
      storyline.frame.pack();
    }
  }
}
