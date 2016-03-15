import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;


class GUI extends JPanel {
  private static final long serialVersionUID = 1L;
  GUI(){
    setPreferredSize(new Dimension(400,300));
  }
  public Box frame=mainFrame();
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g.fillRect(50,100,300,25);
    g.fillOval(175,125,50,50);
  }
  public Box mainFrame(){
    Border border = BorderFactory.createEmptyBorder(10,10,10,10);
    Box box = Box.createHorizontalBox();
    JLabel label = new JLabel("DB.db");
    box.add(label);
    box.add(mainButtons());
    box.setBorder(border);
    return box;
  }
  public Box recordFrame(){
    Border border = BorderFactory.createEmptyBorder(10,10,10,10);
    Box box = Box.createHorizontalBox();
    JLabel label = new JLabel("DB.db");
    box.add(label);
    box.add(mainButtons());
    box.setBorder(border);
    return box;
  }
  JPanel mainButtons(){
    GridLayout grid = new GridLayout(2,4,10,10);
    JPanel box = new JPanel();
    ButtonStyle bStyle = new ButtonStyle();
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    buttons.add(new JButton("Records"));
    buttons.add(new JButton("Tables"));
    buttons.add(new JButton("Print"));
    buttons.add(new JButton("Save"));
    box.setLayout(grid);
    for (JButton but:buttons){
      but.setForeground(Color.white);
      but.setBackground(new Color(0xAABBAA));
      but.setUI(bStyle);
      box.add(but);
    }
    return box;
  }
  JPanel recordButtons(){
    GridLayout grid = new GridLayout(2,4,10,10);
    JPanel box = new JPanel();
    ButtonStyle bStyle = new ButtonStyle();
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    buttons.add(new JButton("add record"));
    buttons.add(new JButton("remove record"));
    buttons.add(new JButton("Print"));
    buttons.add(new JButton("Save"));
    box.setLayout(grid);
    for (JButton but:buttons){
      but.setForeground(Color.white);
      but.setBackground(new Color(0xAABBAA));
      but.setUI(bStyle);
      box.add(but);
    }
    return box;
  }
  public Color paneBack(int RGB){
    return new Color(RGB);
  }
  public void setRecordButtons(){
    frame=recordFrame();
    repaint();
  }
}
