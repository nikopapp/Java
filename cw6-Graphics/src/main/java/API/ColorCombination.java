package API;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.ChangeEvent;

public class ColorCombination extends Slide implements ChangeListener{
  private static final long serialVersionUID = 2L;
  private int initValue = 35;
  private int red = initValue;
  private int green = initValue;
  private int blue = initValue;
  private Color grayBg = new Color(50,50,50);
  private String redValues =   "red   : " + red;
  private String greenValues = "green : " + green;
  private String blueValues =  "blue  : " + blue;
  private String hexValue =    "Hex   : 000000";
  private int guideXCC = 150;


  public ColorCombination(){
    JSlider rSli = new JSlider(JSlider.HORIZONTAL, 0, 255,initValue);
    JSlider gSli = new JSlider(JSlider.HORIZONTAL, 0, 255,initValue);
    JSlider bSli = new JSlider(JSlider.HORIZONTAL, 0, 255,initValue);
    rSli.setPreferredSize(new Dimension(400,400));
    rSli.setUI(new SliderStyle(rSli,"red"));
    gSli.setUI(new SliderStyle(gSli,"green"));
    bSli.setUI(new SliderStyle(bSli,"blue"));

    JLabel redLabel = new JLabel("red");
    JLabel greenLabel = new JLabel("green");
    JLabel blueLabel = new JLabel("blue");

    redLabel.setForeground(Color.red);
    redLabel.add(rSli);
    greenLabel.setForeground(Color.green);
    blueLabel.setForeground(Color.blue);

    rSli.setBackground(getColor("grayBg"));
    gSli.setBackground(getColor("grayBg"));
    bSli.setBackground(getColor("grayBg"));
    rSli.setForeground(Color.red);

    rSli.setName("red");
    gSli.setName("green");
    bSli.setName("blue");

    rSli.addChangeListener(this);
    gSli.addChangeListener(this);
    bSli.addChangeListener(this);

    Box sliders = Box.createVerticalBox();
    sliders.add(rSli);
    sliders.add(gSli);
    sliders.add(bSli);

    add(sliders);
  }
  public static void main(String[] args){
    ColorCombination cc =  new ColorCombination();
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(cc);
    f.pack();
    f.setVisible(true);
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(ToolKit.configRHints());
    drawColorBox(g);
    valueStringUpdate();
    drawValues(g);
    
    g.drawString("So all a colour is, is our perception",guideXCC,540);
    g.drawString("of a combination of different frequencies of light",guideXCC, 570);
  }
  private void valueStringUpdate(){
    redValues =   "red   : " + red;
    greenValues = "green : " + green;
    blueValues =  "blue  : " + blue;
    hexValue =    "Hex   : " +
               String.format("%02X",red) +
               String.format("%02X",green) +
               String.format("%02X",blue);
  }
  private void drawColorBox(Graphics2D g){
    g.setStroke(getDefStroke());
    g.setColor(new Color(red,green,blue));
    g.fillRect(guideXCC,250,200,200);
    g.setColor(Color.black);
    g.drawRect(guideXCC,250,200,200);
  }
  private void drawValues(Graphics2D g){
    g.setColor(Color.black);
    g.fillRect(460,250,200,200);
    g.setStroke(getDefStroke());
    g.setColor(new Color(red,green,blue));
    g.drawRect(460,250,200,200);

    int x = 500;
    int y = 300;
    g.setColor(getColor("yellow"));
    g.drawString(redValues,x,y);
    g.drawString(greenValues,x,y+30);
    g.drawString(blueValues,x,y+60);
    g.drawString(hexValue,x,y+90);
  }
  @Override
  public void stateChanged(ChangeEvent e){
    JSlider source = (JSlider)e.getSource();
    int level = source.getValue();
    String name = source.getName();
    switch (name){
      case "red":
        red = level;
        break;
      case "green":
        green = level;
        break;
      case "blue":
        blue = level;
        break;
    }
    repaint();
  }
}
