package API;

import java.awt.*;

class Intro extends Slide{
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(ToolKit.configRHints());
    drawText("resources/intro.txt",g,guideX,100);
    // gray2trans = new GradientPaint(0,windowHeight,getColor("grayBg"),0, windowHeight-100,new Color(50,50,50,0));
    // g.setPaint(gray2trans);
    // g.fillRect(0,300,windowWidth,windowHeight-300);
  }
}
