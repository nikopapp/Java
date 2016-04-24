package API;

import java.awt.*;
public class ToolKit{
  public ToolKit tools = new ToolKit();
  public static RenderingHints configRHints(){
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
}
