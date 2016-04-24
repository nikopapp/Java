package API;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

class JBoxStyle extends BasicComboBoxUI {

  // @Override
  // public void paint(final Graphics g, final JComponent c){
  //   g.setColor(Color.black);
  //   final Rectangle r = this.rectangleForCurrentValue();
  //   this.paintCurrentValueBackground(g, r, true);
  //   this.paintCurrentValue(g, r, true);
  // }
  //
  // @Override
  // public void paintCurrentValueBackground(final Graphics g, final Rectangle bounds, final boolean hasFocus){
  //   final Color old = g.getColor();
  //   if (this.comboBox.isEnabled())
  //     g.setColor(Color.GREEN);
  //   else
  //     g.setColor(Color.BLACK);
  //
  //   g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
  //   g.setColor(old);
  // }

  @Override
  protected JButton createArrowButton(){
    return new JButton(){
      @Override
      public int getWidth(){
        return 0;
      }
    };
  }

}
