import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.net.*;

import java.awt.event.*;

class GUI extends JPanel {
  private static final long serialVersionUID = 100L;
  private static Explain storyline = new Explain();
  private Timer t = new Timer(25, new Animation());
  private Box mainFrame;
  GUI(){

  }
  public static void main(String args[]){
    GUI gui = new GUI();
    SwingUtilities.invokeLater(gui::run);
  }
  private void run(){
    mainFrame=navFrame();
    storyline.add(mainFrame);
    mainFrame.add(storyline.storyline.get(0));
    mainFrame.setBackground(Color.black);
    // storyline.add()
    storyline.pack();
    storyline.setLocationByPlatform(false);
    storyline.setVisible(true);
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
    box.setBackground(new Color(0x111111));
    return box;
  }

  private JPanel navButtons(){
    JPanel box = new JPanel();
    BoxLayout layout = new BoxLayout(box,BoxLayout.PAGE_AXIS);
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
    buttons[3].addActionListener(this::gotoSlide4);
    buttons[4].addActionListener(this::gotoSlide1);
    buttons[5].addActionListener(this::gotoSlide3);
    buttons[6].addActionListener(this::gotoSlide1);
    buttons[7].addActionListener(this::gotoSlide2);
    buttons[8].addActionListener(this::gotoSlide3);
    buttons[9].addActionListener(this::gotoSlide1);
  }
  private void gotoSlide1(ActionEvent e){
    System.out.println("gotoSlide1");
    storyline.sceneCnt=0;
    changeScene();
    // storyline.st.reset();
  }
  private void gotoSlide2(ActionEvent e){
    System.out.println("gotoSlide2");
    storyline.sceneCnt=1;
    changeScene();
    // mainFrame.remove(1);
    // mainFrame.add(storyline.storyline.get(1),1);
    // storyline.revalidate();
    // storyline.pack();
  }
  private void gotoSlide3(ActionEvent e){
    System.out.println("gotoSlide3");
    storyline.sceneCnt=2;
    changeScene();
    // mainFrame.remove(1);
    // mainFrame.add(storyline.storyline.get(2),1);
    // storyline.revalidate();
    // storyline.pack();
  }
  private void gotoSlide4(ActionEvent e){
    System.out.println("gotoSlide4");
    storyline.sceneCnt=3;
    changeScene();
    // mainFrame.remove(1);
    // mainFrame.add(storyline.storyline.get(3),1);
    // storyline.storyline.get(3).start();
    // storyline.revalidate();
    // storyline.pack();
  }
  private void changeScene(){
    System.out.println();
    mainFrame.remove(1);
    mainFrame.add(storyline.storyline.get(storyline.sceneCnt),1);
    storyline.revalidate();
    storyline.pack();
    storyline.repaint();
  }
  class Animation implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      switch (storyline.sceneCnt){
        case 0:
          for(int i=0;i<6;i++)
            storyline.slide1();
          break;
        case 1:
        case 2:
          break;
        case 3:
            storyline.slide4();
          break;
      }
    }
  }
}
