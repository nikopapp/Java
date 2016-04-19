package API;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

class ButtonStyle extends BasicButtonUI {
  @Override
  public void installUI(JComponent c){
    super.installUI(c);
    AbstractButton button = (AbstractButton) c;
    button.setOpaque(false);
    button.setBorder(new EmptyBorder(5,15,5,15));
  }
  @Override
  public void paint(Graphics g, JComponent c){
    AbstractButton b = (AbstractButton) c;
    paintBackground(g,c, b.getModel().isPressed() ? 2 : 0);
    super.paint(g,c);
  }
  private void paintBackground(Graphics g, JComponent c, int yOffset){
    Dimension size = c.getSize();
    Graphics2D g2 = (Graphics2D) g;
    RenderingHints rh = new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHints(rh);
    g.setColor(c.getBackground().darker());
    g.fillRoundRect(0, yOffset ,size.width,size.height -yOffset,8,8);
    if(yOffset==0) g.setColor(c.getBackground());
    else g.setColor(c.getBackground().darker());
    g.fillRoundRect(0, yOffset ,size.width,size.height -yOffset-4,8,8);

  }

}
