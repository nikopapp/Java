package API;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WebCamSlide extends Slide{
  static JLabel label = new JLabel();
  public static int i = 0;
  private static int filter = 0;
  static JavaCVPrjt03 cc =  new JavaCVPrjt03();

  WebCamSlide(){
    add(FrameAndButtons());
  }
  public static void main(String[] args){
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(label);
    f.setVisible(true);
    int i = 0;
    while(true){
      label.setIcon(cc.run(i));
      label.repaint();
    }
  }
  private Box FrameAndButtons(){
    JPanel buttonPanel = new JPanel();
    ArrayList<JButton> blist = new ArrayList<JButton>();
    buttonPanel.setBackground(new Color(25,25,25));
    buttonPanel.setLayout(new GridLayout(7,1));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(50,10,50,10));
    Box hbox = Box.createHorizontalBox();

    blist.add(new JButton("Simple Grayscale"));
    blist.add(new JButton("Blur"));
    blist.add(new JButton("Simple Laplacian"));
    blist.add(new JButton("Laplacian"));
    blist.add(new JButton("Edge Detection"));
    blist.add(new JButton("Motion Detection"));
    blist.add(new JButton("Hand Detection"));

    ButtonStyle general = new ButtonStyle();
    buttonAddAction(blist);

    for(JButton b:blist){
      b.setUI(general);
      buttonPanel.add(b);
    }
    Box box = Box.createVerticalBox();
    hbox.setBorder(BorderFactory.createEmptyBorder(60,20,20,20));
    hbox.add(label);
    hbox.add(buttonPanel);

    box.add(hbox);
    return box;
  }
  private void buttonAddAction(ArrayList<JButton> buttons){
    buttons.get(0).addActionListener(this::setFilter0);
    buttons.get(1).addActionListener(this::setFilter1);
    buttons.get(2).addActionListener(this::setFilter2);
    buttons.get(3).addActionListener(this::setFilter3);
    buttons.get(4).addActionListener(this::setFilter4);
    buttons.get(5).addActionListener(this::setFilter5);
    buttons.get(6).addActionListener(this::setFilter6);
  }


  //Button Actions
  private void setFilter0(ActionEvent e){
    filter=0;
  }
  private void setFilter1(ActionEvent e){
    filter=1;
  }
  private void setFilter2(ActionEvent e){
    filter=2;
  }
  private void setFilter3(ActionEvent e){
    filter=3;
  }
  private void setFilter4(ActionEvent e){
    filter=4;
  }
  private void setFilter5(ActionEvent e){
    this.i=0;
    filter=5;
  }
  private void setFilter6(ActionEvent e){
    filter=6;
  }

  @Override
  public boolean tick(){
    switch(filter){
      case 0:
        label.setIcon(cc.runSimpleGS());
        break;
      case 1:
        label.setIcon(cc.runSimpleGSBlur());
        break;
      case 2:
        label.setIcon(cc.runSimpleGSLaplacian());
        break;
      case 3:
        label.setIcon(cc.runLaplacian());
        break;
      case 4:
        label.setIcon(cc.runSimpleED());
        break;
      case 5:
        label.setIcon(cc.run(this.i));
        break;
      case 6:
        label.setIcon(cc.runHand());
        break;
    }
    label.repaint();
    this.i=1;
    return true;
  }

}
