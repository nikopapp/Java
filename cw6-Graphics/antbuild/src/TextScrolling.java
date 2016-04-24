package API;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.net.*;
class TextScrolling extends Slide{
  private static final long serialVersionUID = 3L;
  private static final int lineSize = 30;
  private boolean running = false;
  private double textY0 = windowHeight;
  private GradientPaint gray2trans;
  private Font fontText = new Font("Dialog",Font.PLAIN,18);
  TextScrolling(){
    this.running=false;
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(ToolKit.configRHints());
    drawText("resources/conv.txt",g);
    gray2trans = new GradientPaint(0,windowHeight,getColor("grayBg"),0, windowHeight-100,new Color(50,50,50,0));
    g.setPaint(gray2trans);
    g.fillRect(0,300,windowWidth,windowHeight-300);
  }

  private void drawText(String filePath, Graphics g){
      BufferedReader b;
      String temp;
      int i=0;
      g.setColor(getColor("yellow"));
      g.setFont(fontText);
      try{
        b = new BufferedReader(new FileReader(filePath));
        while((temp = b.readLine()) != null){
          i++;
          g.drawString(temp,guideX,(int)textY0+lineSize*i);
        }

      }catch(Exception e){}
  }
  public void start(){
    this.running = true;
  }
  public void stop(){
    this.running = false;
  }
  public boolean isRunning(){
    return running ? true : false;
  }
  @Override
  public boolean tick(){
    if(textY0>=40 && running){
      textY0-=.6;
      repaint();
      return true;
    }else return false;
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
