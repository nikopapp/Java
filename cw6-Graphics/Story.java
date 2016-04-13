import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

class Story extends JPanel{
  private static final long serialVersionUID = 1L;
  public int windowHeight = 500;
  public int windowWidth  = 800;
  private Line2D line;
  private int X1=20;
  private int X2=20;
  private int Y1= 0;
  private int Y2= 0;
  private int rectX1 = 50;
  private Color grayBg = new Color(50,50,50);

  Story(){
    super();
    setBackground( grayBg );
    // setPreferredSize(new Dimension(windowWidth , windowHeight));
    line = new Line2D.Double(X1,Y1,X1,Y2);
  }
  public static void main(String[] args){

  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
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
    g.setRenderingHints(rh);
    // g.setColor(new Color(50,50,50));
    // g.fillRect(0,0,windowWidth,windowHeight);
    g.setColor(new Color(5, 5,20,50));
    g.fillRect(0,0,400,300);
    g.setColor(new Color(5, 5,20,50));
    g.fillRect(0,0,300,400);
    // g.rotate(60, 200, 150);
    g.setColor(new Color(100,100,100));
    g.drawLine(X1, Y1, X2, Y2);
    g.fillRect(rectX1, 100, 300, 25);
    g.setColor(new Color(150,150,100));
    drawText("resources/conv.txt",g);
    g.drawString("Convolution is a method that is cool to understand",50,50);
   }

  private void drawText(String filePath, Graphics g){
      BufferedReader b;
      String temp;
      try{
        b = new BufferedReader(new FileReader(filePath));
        temp=b.readLine();
        g.drawString(temp,150,150);
      }catch(Exception e){}
  }
  public boolean lineGrowing(){
    if(Y2<300)
    Y2++;
    else
    return false;
    repaint();
    return true;
  }
  public boolean moveRect(){
    if(rectX1<200) rectX1++;
    else return false;
    repaint();
    return true;
  }

}
