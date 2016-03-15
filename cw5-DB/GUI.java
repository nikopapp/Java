import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

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
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    w.setTitle("JDataB");
    w.add(mainFrame());
    w.pack();
    w.setLocationByPlatform(false);
    w.setVisible(true);
  }
  private Box mainFrame(){
    Border border = BorderFactory.createEmptyBorder(10,10,10,10);
    Box box = Box.createHorizontalBox();
    JLabel logo = new JLabel(new ImageIcon("logo/logo.png"));
    box.add(logo);
    box.add(mainButtons());
    box.setBorder(border);
    return box;
  }
  private JPanel mainButtons(){
    GridLayout grid = new GridLayout(3,4,10,10);
    JPanel box = new JPanel();
    box.setBackground(new Color(0xFDFADF));
    ButtonStyle bStyle = new ButtonStyle();
    JButton[] buttons =  new JButton[30];
    buttons[DB.R_NEW] =  new JButton("Add Record");
    buttons[DB.R_RMV] =  new JButton("Remove Record");
    buttons[DB.R_RET_INDEX] = new JButton("By Index");
    buttons[DB.R_RET_VALUE] = new JButton("By Value");
    buttons[DB.T_NEW] =  new JButton("Add Table");
    buttons[DB.T_RMV] =  new JButton("Remove Table");
    buttons[DB.T_TEST] = new JButton("Test Table");
    buttons[DB.PRINT_ALL] =  new JButton("Print All");
    buttons[DB.PRINT] =  new JButton("Print One");
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
    buttons[DB.R_NEW].addActionListener(this::newRecord);
    buttons[DB.R_RMV].addActionListener(this::removeRecord);
    buttons[DB.R_RET_INDEX].addActionListener(this::retrieveByIndex);
    buttons[DB.R_RET_VALUE].addActionListener(this::retrieveByValue);
    buttons[DB.T_NEW].addActionListener(this::newTable);
    buttons[DB.T_RMV].addActionListener(this::removeTable);
    buttons[DB.T_RENAME].addActionListener(this::renameTable);
    buttons[DB.T_TEST].addActionListener(this::testTable);
    buttons[DB.LOAD].addActionListener(this::load);
    buttons[DB.PRINT_ALL].addActionListener(this::print);
    buttons[DB.PRINT].addActionListener(this::printOne);
    buttons[DB.SAVE].addActionListener(this::save);
  }
  private void newRecord(ActionEvent e){
    db.GUI_control(DB.R_NEW);
  }
  private void removeRecord(ActionEvent e){
    db.GUI_control(DB.R_RMV);
  }
  private void retrieveByIndex(ActionEvent e){
    db.GUI_control(DB.R_RET_INDEX);
  }
  private void retrieveByValue(ActionEvent e){
    db.GUI_control(DB.R_RET_VALUE);
  }
  private void newTable(ActionEvent e){
    db.GUI_control(DB.T_NEW);
  }
  private void removeTable(ActionEvent e){
    db.GUI_control(DB.T_RMV);
  }
  private void renameTable(ActionEvent e){
    db.GUI_control(DB.T_RENAME);
  }
  private void testTable(ActionEvent e){
    db.GUI_control(DB.T_TEST);
  }
  private void load(ActionEvent e){
    db.GUI_control(DB.LOAD);
  }
  private void print(ActionEvent e){
    db.GUI_control(DB.PRINT_ALL);
  }
  private void printOne(ActionEvent e){
    db.GUI_control(DB.PRINT);
  }

  private void save(ActionEvent e){
    db.GUI_control(DB.SAVE);
  }
}
