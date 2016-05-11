package API;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.net.*;

import java.awt.event.*;

class GUI extends JPanel {
  private static final long serialVersionUID = 100L;
  private static Explain storyline = new Explain();
  private static final int refreshRate = 25;
  private Timer t = new Timer(refreshRate, new Animation());
  private Box mainFrame;
  private Color navigMenu = new Color(25,25,25);
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
    storyline.pack();
    storyline.setLocationByPlatform(false);
    storyline.setVisible(true);
    t.start();
  }
  private Box navFrame(){
    Box box = Box.createHorizontalBox();
    //necessary for the image to be loaded directly from the jar file
    box.setOpaque(true);
    box.setBackground(navigMenu);
    box.add(navButtons());
    // box.setBorder(border);
    return box;
  }

  private Box navButtons(){
    String[] butNames = {"Introduction", "Light", "Color", "Pixel",
                         "Convolution", "Video Processing", "Pong"};
    Border border = BorderFactory.createEmptyBorder(10,10,10,10);
    Box box = Box.createVerticalBox();
    JPanel butPanel = new JPanel(new GridLayout(10,1));
    butPanel.setBackground(navigMenu);
    box.setBorder(border);
    // URL resource = getClass().getResource("vectors/logo.png");
    JLabel logo = new JLabel(new ImageIcon("resources/vectors/logo.png"));
    box.add(logo);
    ButtonStyle bStyle = new ButtonStyle();
    JButton[] buttons =  new JButton[30];
    for (int i=0;i<10;i++){
      if(i < butNames.length) buttons[i] = new JButton(butNames[i]);
      else buttons[i] = new JButton("Scene " +i);
    }
    buttonAddAction(buttons);
    for (JButton but:buttons){
      if(but!=null){
        but.setUI(bStyle);
        butPanel.add(but);
      }
    }
    box.add(butPanel);
    return box;
  }
  private void buttonAddAction(JButton[] buttons){
    buttons[0].addActionListener(this::gotoSlide1);
    buttons[1].addActionListener(this::gotoSlide2);
    buttons[2].addActionListener(this::gotoSlide3);
    buttons[3].addActionListener(this::gotoSlide4);
    buttons[4].addActionListener(this::gotoSlide5);
    buttons[5].addActionListener(this::gotoSlide6);
    buttons[6].addActionListener(this::gotoSlide7);
  }
  private void gotoSlide1(ActionEvent e){
    storyline.sceneCnt=0;
    changeScene();
  }
  private void gotoSlide2(ActionEvent e){
    storyline.sceneCnt=1;
    changeScene();
  }
  private void gotoSlide3(ActionEvent e){
    storyline.sceneCnt=2;
    changeScene();
  }
  private void gotoSlide4(ActionEvent e){
    storyline.sceneCnt=3;
    changeScene();
  }

  private void gotoSlide5(ActionEvent e){
    storyline.sceneCnt=4;
    changeScene();
  }
  private void gotoSlide6(ActionEvent e){
    storyline.sceneCnt=5;
    changeScene();
  }
  private void gotoSlide7(ActionEvent e){
    storyline.sceneCnt=6;
    changeScene();
  }

  private void changeScene(){
    mainFrame.remove(1);
    mainFrame.add(storyline.storyline.get(storyline.sceneCnt),1);
    storyline.revalidate();
    storyline.pack();
    storyline.repaint();
  }
  public int getSceneCnt(){
    return storyline.sceneCnt;
  }
  class Animation implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      switch (storyline.sceneCnt){
        case 0:
          break;
        case 1:
          for(int i=0;i<6;i++)
            storyline.slide1();
        case 2:
          break;
        case 3:
          break;
        case 4:
          storyline.slide4();
          break;
        case 5:
          storyline.slide5();
          break;
        case 6:
          storyline.slide6();
          break;
      }
    }
  }
}
