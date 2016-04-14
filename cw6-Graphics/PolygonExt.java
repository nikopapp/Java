import java.awt.*;

public class PolygonExt extends Polygon{
  private static final long serialVersionUID = 11L;
  PolygonExt(){
    super();
  }
  public int getCenterX(){
    int centerX;
    int sumX=0;
    for(int x: xpoints){
      sumX+=x;
    }
    centerX= sumX/npoints;
    return centerX;
  }
  public int getCenterY(){
    int centerY;
    int sumY=0;
    for(int y: ypoints){
      sumY+=y;
    }
    centerY= sumY/npoints;
    return centerY;
  }
}
