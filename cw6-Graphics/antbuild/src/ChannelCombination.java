package API;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ChannelCombination extends Slide implements ActionListener{
  private static final long serialVersionUID = 3L;
  private static final Integer[] valueList = {
                            0,20,50,100,150,200,255
                          };
  private  ArrayList<JComboBox> cbList = new ArrayList<JComboBox>();
  private Integer[][] redChannel   = new Integer[3][3];
  private Integer[][] greenChannel = new Integer[3][3];
  private Integer[][] blueChannel  = new Integer[3][3];
  private BasicStroke old = new BasicStroke(1f);

  public ChannelCombination(){
    createChannelInputArrays();
    initPixelArrays();
  }
  public static void main (String[] args){
    ChannelCombination chc = new ChannelCombination();
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(chc);
    f.pack();
    f.setVisible(true);
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(ToolKit.configRHints());
    g.setColor(getColor("yellow"));
    g.drawRect(windowWidth/8,windowHeight/8,windowWidth/8,windowHeight/8);
    g.drawRect(3*windowWidth/8,windowHeight/8,windowWidth/8,windowHeight/8);
    g.drawRect(5*windowWidth/8,windowHeight/8,windowWidth/8,windowHeight/8);

    g.setStroke(new BasicStroke(5f));
    g.drawRect(windowWidth/2-150,300,300,300);
    fillPixelArray(g);
    drawOperators(g);
  }
  private void createChannelInputArrays(){
    ArrayList<JPanel> boxes = new ArrayList<>();
    setupComboboxes();
    for(int i = 0; i<3; i++){
      boxes.add(new JPanel());
      boxes.get(i).setBackground(getColor("grayBg"));
      boxes.get(i).setLayout(new GridLayout(3,3));
      boxes.get(i).setBorder(BorderFactory.createEmptyBorder(0,100,0,20));
    }
    for(int i=0;i<3;i++){
      for(int j=0;j<9;j++){
        boxes.get(i).add(cbList.get(i*9+j));
      }
    }
    for(JPanel b: boxes)
      add(b);
  }
  private void initPixelArrays(){
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        redChannel[i][j]=0;
        greenChannel[i][j]=0;
        blueChannel[i][j]=0;
      }
    }
  }
  private void fillPixelArray(Graphics2D g){
    for(int i = 0; i<3;i++){
      for(int j = 0; j<3;j++){
        g.setStroke(new BasicStroke(3f));
        g.setColor(new Color(redChannel[i][j],greenChannel[i][j],blueChannel[i][j]));
        g.fillRect(windowWidth/2-150+j*100,300+i*100,100,100);
        g.setColor(new Color(redChannel[i][j],0,0));
        g.fillRect(windowWidth/7+j*windowWidth/7/3,windowHeight/7+i*windowHeight/7/3,windowWidth/7/3,windowHeight/7/3);
        g.setColor(new Color(0,greenChannel[i][j],0));
        g.fillRect(3*windowWidth/7+j*windowWidth/7/3,windowHeight/7+i*windowHeight/7/3,windowWidth/7/3,windowHeight/7/3);
        g.setColor(new Color(0,0,blueChannel[i][j]));
        g.fillRect(5*windowWidth/7+j*windowWidth/7/3,windowHeight/7+i*windowHeight/7/3,windowWidth/7/3,windowHeight/7/3);
        g.setColor(getColor("yellow"));
        g.drawRect(windowWidth/7+j*windowWidth/7/3,windowHeight/7+i*windowHeight/7/3,windowWidth/7/3,windowHeight/7/3);
        g.drawRect(3*windowWidth/7+j*windowWidth/7/3,windowHeight/7+i*windowHeight/7/3,windowWidth/7/3,windowHeight/7/3);
        g.drawRect(5*windowWidth/7+j*windowWidth/7/3,windowHeight/7+i*windowHeight/7/3,windowWidth/7/3,windowHeight/7/3);
        g.setStroke(old);

      }
    }
  }
  private void drawOperators(Graphics2D g){
    g.setStroke(new BasicStroke(5f));
    g.drawLine(2*windowWidth/7+windowWidth/14,windowHeight/7+20,2*windowWidth/7+windowWidth/14,2*windowHeight/7-20);
    g.drawLine(4*windowWidth/7+windowWidth/14,windowHeight/7+20,4*windowWidth/7+windowWidth/14,2*windowHeight/7-20);

    g.drawLine(2*windowWidth/7+windowWidth/14-30,windowHeight/7+windowHeight/14,2*windowWidth/7+windowWidth/14+30,windowHeight/7+windowHeight/14);
    g.drawLine(4*windowWidth/7+windowWidth/14-30,windowHeight/7+windowHeight/14,4*windowWidth/7+windowWidth/14+30,windowHeight/7+windowHeight/14);

    g.drawLine(windowWidth/2-30,2*windowHeight/7+windowHeight/14,windowWidth/2+30,2*windowHeight/7+windowHeight/14);
    g.drawLine(windowWidth/2-30,2*windowHeight/7+windowHeight/14+30,windowWidth/2+30,2*windowHeight/7+windowHeight/14+30);
    g.setStroke(old);
  }
  private void setupComboboxes(){
    for(int j=0;j<27;j++){
      JComboBox cb = new JComboBox(valueList);
      cb.addActionListener(this);
      cb.setUI(new JBoxStyle());
      cb.setForeground(getColor("yellow"));
      cb.setBackground(getColor("grayBg"));
      cb.setFont(getFont("small"));
      cbList.add(cb);
    }
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    for(int i = 0;i< 3;i++){
      for(int j = 0;j<3;j++){
        for (int k = 0; k<3;k++){
          switch (i){
            case 0:
              redChannel[j][k]=Integer.parseInt(cbList.get(i*9+j*3+k).getSelectedItem().toString());
              break;
            case 1:
              greenChannel[j][k]=Integer.parseInt(cbList.get(i*9+j*3+k).getSelectedItem().toString());
              break;
            case 2:
              blueChannel[j][k]=Integer.parseInt(cbList.get(i*9+j*3+k).getSelectedItem().toString());
              break;
          }
          repaint();
        }
      }
    }
  }
}
