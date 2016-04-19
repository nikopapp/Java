package API;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class ChannelCombination extends Slide{
  private static final long serialVersionUID = 3L;
  public ChannelCombination(){
    createChannelArrays();
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
    g.drawRect(windowWidth/7,windowHeight/7,windowWidth/7,windowHeight/7);
    g.drawRect(3*windowWidth/7,windowHeight/7,windowWidth/7,windowHeight/7);
    g.drawRect(5*windowWidth/7,windowHeight/7,windowWidth/7,windowHeight/7);
    g.drawRect(300,300,300,300);
  }
  private void createChannelArrays(){
    ArrayList<JPanel> boxes = new ArrayList<>();
    for(int i = 0; i<3; i++){
      boxes.add(new JPanel());
      boxes.get(i).setLayout(new GridLayout(3,3));
    }
    for(JPanel b: boxes)
      for(int j=0;j<9;j++)
        b.add(new JComboBox());
    for(JPanel b: boxes)
      add(b);
  }
}
