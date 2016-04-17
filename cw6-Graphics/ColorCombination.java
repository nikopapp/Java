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
    SliderStyle rSli = new SliderStyle(JSlider.VERTICAL, 0, 255,0);
    SliderStyle gSli = new SliderStyle(JSlider.VERTICAL, 0, 255,0);
    SliderStyle bSli = new SliderStyle(JSlider.VERTICAL, 0, 255,0);
    rSli.setPaintLabels(true);
    setBackground( grayBg );
    setPreferredSize(new Dimension(windowWidth , windowHeight));
    // SliderStyle sstyle = new SliderStyle(rSli);
    // rSli.setUI(sstyle);
    add(rSli);
    add(gSli);
    add(bSli);
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
