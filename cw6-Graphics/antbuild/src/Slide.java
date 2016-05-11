package API;

import java.awt.*;
import java.awt.GradientPaint;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class Slide extends JPanel{

  private static final long serialVersionUID = 10L;
  private RenderingHints rh = configRHints();
  public int windowHeight = 630;
  public int windowWidth  = 840;
  public int guideX = 40;
  public static final int lineSize = 25;
  private Color grayBg = new Color(50,50,50);
  private Color blue = new Color(120,150,150);
  private Color yellow = new Color(150,150,100);
  private Color transparent = new Color(0,0,0,0);
  private Font fontTextBig = new Font("Dialog",Font.PLAIN,18);
  private Font fontTextNorm = new Font("Dialog",Font.PLAIN,15);
  private Font fontTextSmall = new Font("Dialog",Font.BOLD,13);
  private long frameCnt = 0;
  private BasicStroke defStroke = new BasicStroke(5);
  private GradientPaint light = new GradientPaint(0,200,new Color(190,200,170,110),0,400, getColor("transparent"));

  public Slide(){
    setBackground(grayBg);
    setPreferredSize(new Dimension(windowWidth , windowHeight));
  }
  public void drawText(String filePath, Graphics g, int x, int y){
      BufferedReader b;
      String temp;
      int i=0;
      g.setColor(yellow);
      g.setFont(fontTextBig);
      try{
        b = new BufferedReader(new FileReader(filePath));
        while((temp = b.readLine()) != null){
          i++;
          g.drawString(temp, x, y+lineSize*i);
        }

      }catch(Exception e){}
  }
  public Color getColor(String color){
    if(color.equals("grayBg")) return grayBg;
    if(color.equals("yellow")) return yellow;
    if(color.equals("blue")) return blue;
    if(color.equals("transparent")) return transparent;
    return Color.black;
  }
  public Color getColor(int r, int g, int b){
    return new Color(r,g,b);
  }
  public Font getFont(String type){
    if(type.equals("small")) return fontTextSmall;
    if(type.equals("normal")) return fontTextNorm;
    if(type.equals("big"))  return fontTextBig;
    return fontTextNorm;
  }

  public Font getFont(){
    return fontTextNorm;
  }
  public RenderingHints getRenderingHints(){
    return rh;
  }
  public void setWindowSize(Rectangle size){
    this.windowHeight = size.height;
    this.windowWidth  = size.width;
  }
  public BasicStroke getDefStroke(){
    return defStroke;
  }
  public GradientPaint getPaintLight(){
    return light;
  }
  public boolean tick(){
    return false;
  }
  public RenderingHints configRHints(){
    //---------------- Anti aliasing --------------
    RenderingHints rh = new RenderingHints(
    RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
    rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
    RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    rh.put(RenderingHints.KEY_COLOR_RENDERING,
    RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    rh.put(RenderingHints.KEY_DITHERING,
    RenderingHints.VALUE_DITHER_ENABLE);
    rh.put(RenderingHints.KEY_FRACTIONALMETRICS,
    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    rh.put(RenderingHints.KEY_INTERPOLATION,
    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    rh.put(RenderingHints.KEY_RENDERING,
    RenderingHints.VALUE_RENDER_QUALITY);
    rh.put(RenderingHints.KEY_STROKE_CONTROL,
    RenderingHints.VALUE_STROKE_PURE);
    return rh;
  }
  public void start(){}
  public void stop(){}
  public void reset(){}
}
