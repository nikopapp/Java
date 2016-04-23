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
  public int windowHeight = 630;
  public int windowWidth  = 840;
  public int guideX = 40;
  private Color grayBg = new Color(50,50,50);
  private Color blue = new Color(120,150,150);
  private Color yellow = new Color(150,150,100);
  private Color transparent = new Color(0,0,0,0);
  private RenderingHints rh=ToolKit.configRHints();
  private Font fontText = new Font("Dialog",Font.PLAIN,18);
  private long frameCnt = 0;
  private BasicStroke defStroke = new BasicStroke(5);
  private GradientPaint light = new GradientPaint(0,200,new Color(190,200,170,110),0,400, getColor("transparent"));

  public Slide(){
    setBackground(grayBg);
    setPreferredSize(new Dimension(windowWidth , windowHeight));
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
  public Font getFont(){
    return fontText;
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
  public void start(){}
  public void stop(){}
}
