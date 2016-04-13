import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.net.*;
class TextScrolling extends JPanel{
  private static final long serialVersionUID = 2L;
  private static final int lineSize = 30;
  private boolean running = false;
  public int windowHeight = 500;
  public int windowWidth  = 800;
  private int textX0 = 40;
  private double textY0 = windowHeight;
  private GradientPaint gray2trans;
  private Color grayBg = new Color(50,50,50);
  private Color trans = new Color(0,0,0,0);
  private Color yelltext = new Color(150,150,100);
  private Font fontText = new Font("Dialog",Font.PLAIN,20);
  TextScrolling(){
    super();
    this.running=false;
    setBackground( grayBg );
    // setPreferredSize(new Dimension(windowWidth , windowHeight));
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
    // g.setColor(grayBg);
    // g.fillRect(0,0,windowWidth,windowHeight);
    drawText("resources/conv.txt",g);
    gray2trans = new GradientPaint(0,windowHeight,new Color(50,50,50,255),0, windowHeight-100,new Color(50,50,50,0));
    g.setPaint(gray2trans);
    g.fillRect(0,300,windowWidth,windowHeight-300);
  }

  private void drawText(String filePath, Graphics g){
      BufferedReader b;
      String temp;
      int i=0;
      g.setColor(yelltext);
      g.setFont(fontText);
      try{
        b = new BufferedReader(new FileReader(filePath));
        while((temp = b.readLine()) != null){
          i++;
          g.drawString(temp,textX0,(int)textY0+lineSize*i);
        }

      }catch(Exception e){}
  }
  public void setRunning(){
    this.running=true;
  }
  public boolean isRunning(){
    return running ? true : false;
  }
  public void scroll(){
    System.out.print("\r0"+textY0+" "+windowWidth+" "+windowHeight);
    if(textY0>=40){
      textY0-=.3;
      repaint();
    }
  }
  private static void open(URI uri) {
   if (Desktop.isDesktopSupported()) {
     try {
       Desktop.getDesktop().browse(uri);
     } catch (IOException e) { /* TODO: error handling */ }
   } else { /* TODO: error handling */ }
 }
  public void setWindowSize(Rectangle size){
    this.windowHeight = size.height;
    this.windowWidth  = size.width;
    this.textY0       = size.height;
  }

}
