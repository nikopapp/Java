import java.awt.*;
import javax.swing.*;

public class ColorCombination extends JPanel{
  private static final long serialVersionUID = 2L;
  public int windowHeight = 650;
  public int windowWidth  = 800;
  private int red = 0;
  private int green = 0;
  private int blue = 0;
  private Color grayBg = new Color(50,50,50);

  public ColorCombination(){
    JSlider rSli = new JSlider(JSlider.VERTICAL, 0, 255,0);
    JSlider gSli = new JSlider(JSlider.VERTICAL, 0, 255,0);
    JSlider bSli = new JSlider(JSlider.VERTICAL, 0, 255,0);
    setBackground( grayBg );
    setPreferredSize(new Dimension(windowWidth , windowHeight));
  }
  public static void main(String[] args){
    ColorCombination cc=  new ColorCombination();
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(ToolKit.configRHints());
    g.setColor(new Color(red,green,blue));
  }
}
