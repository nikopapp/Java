import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

class GUI extends JPanel {
  private static final long serialVersionUID = 1L;
  private DB db=new DB();
  GUI(){
    setPreferredSize(new Dimension(400,300));
  }
  public static void main(String args[]){
    GUI gui = new GUI();
    SwingUtilities.invokeLater(gui::run);
  }
  private void run(){
    JFrame w = new JFrame();
    boolean finished = false;
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    w.setTitle("Simple Database");
    w.add(mainFrame());
    w.pack();
    w.setLocationByPlatform(true);
    w.setVisible(true);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
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
  JPanel mainButtons(){
    GridLayout grid = new GridLayout(3,4,10,10);
    JPanel box = new JPanel();
    ButtonStyle bStyle = new ButtonStyle();
    JButton[] buttons =  new JButton[30];
    buttons[DB.R_NEW] =  new JButton("Add Record");
    buttons[DB.R_RMV] =  new JButton("Remove Record");
    buttons[DB.R_RET_INDEX] = new JButton("By Index");
    buttons[DB.R_RET_VALUE] = new JButton("By Value");
    buttons[DB.T_NEW] =  new JButton("Add Table");
    buttons[DB.T_RMV] =  new JButton("Remove Table");
    buttons[DB.T_TEST] = new JButton("Test Table");
    buttons[DB.PRINT] =  new JButton("Print");
    buttons[DB.SAVE] =   new JButton("Save");
    buttons[DB.LOAD] =   new JButton("Load");
    buttons[DB.T_RENAME] = new JButton("Rename Table");
    buttonAddAction(buttons);
    box.setLayout(grid);
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

    buttons[DB.R_NEW].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.R_NEW);
        }
      });
    buttons[DB.R_RMV].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.R_RMV);
        }
      });
    buttons[DB.R_RET_INDEX].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.R_RET_INDEX);
        }
      });
    buttons[DB.R_RET_VALUE].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.R_RET_VALUE);
        }
      });
    buttons[DB.T_NEW].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.T_NEW);
        }
      });
    buttons[DB.T_RMV].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.T_RMV);
        }
      });
      buttons[DB.T_RENAME].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.T_RENAME);
        }
      });
    buttons[DB.T_TEST].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.T_TEST);
        }
      });
    buttons[DB.LOAD].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.LOAD);
        }
      });
    buttons[DB.PRINT].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.PRINT);
        }
      });
    buttons[DB.SAVE].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
        {
          db.GUI_control(DB.SAVE);
        }
      });
  }
  private Color paneBack(int RGB){
    return new Color(RGB);
  }
  // public void setRecordButtons(){
  //   frame=recordFrame();
  //   repaint();
  // }
}
