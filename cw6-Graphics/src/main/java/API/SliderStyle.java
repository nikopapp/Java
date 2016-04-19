package API;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

class SliderStyle extends BasicSliderUI {
  private int offset = 20;
  private BasicStroke stroke = new BasicStroke(3f);
  private String colorSelector;
  private RenderingHints rh=ToolKit.configRHints();
  public SliderStyle(JSlider b, String colorSelector) {
        super(b);
        this.colorSelector=colorSelector;
  }
  @Override
  protected Dimension getThumbSize() {
    return new Dimension(40, 40);
  }

  @Override
  public void paintThumb(Graphics g0) {
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(rh);
    updateFillColor(g,colorSelector);
    g.fillOval(thumbRect.x+offset/2, thumbRect.y+offset/2, thumbRect.width-offset, thumbRect.height-offset);
    Stroke old = g.getStroke();
    g.setStroke(stroke);
    selectColor(g,colorSelector);
    // g.setColor(new Color(240,240,240));
    // g.setPaint(new Color(255, 200, 211));
    g.drawOval(thumbRect.x+offset/2, thumbRect.y+offset/2, thumbRect.width-offset, thumbRect.height-offset);
    g.setStroke(old);
    }
    @Override
    public void paintTrack(Graphics g0) {
      Graphics2D g = (Graphics2D) g0;
      g.setRenderingHints(rh);
      Stroke old = g.getStroke();
      g.setStroke(stroke);
      g.setPaint(Color.BLACK);
      if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
        g.drawLine(trackRect.x, trackRect.y + trackRect.height / 2,
        trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
      } else {
        g.drawLine(trackRect.x + trackRect.width / 2, trackRect.y,
        trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
      }
      g.setStroke(old);
    }
    @Override
     protected Color getFocusColor(){
         return new Color(255,255,255,0);
     }
    private void selectColor(Graphics2D g ,String colorSelector){
      switch(colorSelector){
        case "red":
          g.setColor(Color.red);
          break;
        case "green":
          g.setColor(Color.green);
          break;
        case "blue":
          g.setColor(Color.blue);
          break;
      }
    }
    private void updateFillColor(Graphics2D g, String colorSelector){
      int colourlevel = 255*thumbRect.x/360;
      if(colourlevel>255) colourlevel = 255;

      switch(colorSelector){
        case "red":
          g.setColor(new Color(colourlevel,0,0));
          break;
        case "green":
          g.setColor(new Color(0,colourlevel,0));
          break;
        case "blue":
          g.setColor(new Color(0,0,colourlevel));
          break;
      }
  }
  // @Override
  // public void paint(Graphics2D g, JComponent c){
  //   JSlider b = (JSlider) c;
  //   super.paint(g,c);
  // }
  // private void paintBackground(Graphics g, JComponent c, int yOffset){
  //   Dimension size = c.getSize();
  //   Graphics2D g2 = (Graphics2D) g;
  //   RenderingHints rh = new RenderingHints(
  //     RenderingHints.KEY_ANTIALIASING,
  //     RenderingHints.VALUE_ANTIALIAS_ON);
  //   g2.setRenderingHints(rh);
  //   g.setColor(c.getBackground().darker());
  //   g.fillRoundRect(0, yOffset ,size.width,size.height -yOffset,8,8);
  //   if(yOffset==0) g.setColor(c.getBackground());
  //   else g.setColor(c.getBackground().darker());
  //   g.fillRoundRect(0, yOffset ,size.width,size.height -yOffset-4,8,8);
  //
  // }

}
