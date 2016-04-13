import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

class Story extends JPanel{
  private Rectangle2D.Double rect = new Rectangle2D.Double(100,75,200,160);
  private AffineTransform at = new AffineTransform();
  private static final long serialVersionUID = 1L;
  public int windowHeight = 650;
  public int windowWidth  = 800;
  private Polygon flashlight;
  private Line2D line;
  private int X1=20;
  private int X2=20;
  private int Y1= 0;
  private int Y2= 0;
  private int rectX1 = 50;
  private Color grayBg = new Color(50,50,50);
  private Color yellow = new Color(150,150,100);
  private RenderingHints rh=ToolKit.configRHints();
  // private JPanel flshlght;
  Story(){
    super();
    setBackground( grayBg );
    setPreferredSize(new Dimension(windowWidth , windowHeight));
    addMouseListener(rotator);
    addMouseMotionListener(rotator);
    line = new Line2D.Double(X1,Y1,X1,Y2);
    flashlight = loadSvg("resources/vectors/flashlight.fx",windowWidth/2,200);
    // flshlght = new JPanel(new BoxLayout(flshlght,BoxLayout.PAGE_AXIS));
  }
  public static void main(String[] args){
    Story test = new Story();
    test.addMouseListener(test.rotator);
    test.addMouseMotionListener(test.rotator);
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(test);
    f.setSize(400,400);
    f.setLocation(200,200);
    f.setVisible(true);
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(rh);
    draw(g);
  }
  private void draw(Graphics2D g){
    // add(flshlght);
    g.setColor(new Color(5, 5,20,50));
    g.fillRect(0,0,400,300);
    g.setColor(new Color(5, 5,20,50));
    g.fillRect(0,0,300,400);
    //  g.rotate(60, 200, 150);
    g.setColor(yellow);
    drawSvgShapes(g,flashlight,300,300);
    g.setColor(new Color(100,100,100));
    g.drawLine(X1, Y1, X2, Y2);
    g.fillRect(rectX1, 100, 300, 25);
    g.setColor(yellow);
    drawText("resources/conv.txt",g);
    g.drawString("Convolution is a method that is cool to understand",50,50);
    g.draw(at.createTransformedShape(rect));
  }
  private void drawSvgShapes(Graphics2D g,Polygon shape,int x, int y){
    // if(!shape.contains(x+1,y))
    // shape.translate(x,y);
    g.drawPolygon(shape);
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
  private Polygon loadSvg(String filePath, int x , int y){
    Polygon nGon = new Polygon();
    BufferedReader b;
    String temp;
    ArrayList<Integer> dimensionsX = new ArrayList<Integer>();
    ArrayList<Integer> dimensionsY = new ArrayList<Integer>();
    try{
      b = new BufferedReader(new FileReader(filePath));
      while((temp = b.readLine()) != null){
        temp=temp.trim();
        getPoint("x:",temp,dimensionsX);
        getPoint("y:",temp,dimensionsY);
      }
      for(int i=0; i<dimensionsY.size(); i++){
        nGon.addPoint(2*Double.valueOf(dimensionsX.get(i)).intValue(),
                      2*Double.valueOf(dimensionsY.get(i)).intValue());
      }
    }catch(Exception e){}
    nGon.translate(x,y);
    return nGon;
  }
  private void getPoint(String ident, String line, ArrayList<Integer> l){
    if(line.startsWith(ident)){
      line = line.replace(ident,"");
      l.add(Double.valueOf(line).intValue());
    }
  }
  private MouseInputAdapter rotator  = new MouseInputAdapter() {
    Point2D.Double center = new Point2D.Double();
    double thetaStart = 0;
    double thetaEnd = 0;
    boolean rotating = false;

      public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Shape shape = at.createTransformedShape(rect);
        if(shape.contains(p)) {
          Rectangle r = shape.getBounds();
          center.x = r.getCenterX();
          center.y = r.getCenterY();
          double dy = p.y - center.y;
          double dx = p.x - center.x;
          thetaStart = Math.atan2(dy, dx) - thetaEnd;
          //System.out.printf("press thetaStart = %.1f%n",
          //                   Math.toDegrees(thetaStart));
          rotating = true;
        }
      }

      public void mouseReleased(MouseEvent e) {
        rotating = false;
        double dy = e.getY() - center.y;
        double dx = e.getX() - center.x;
        thetaEnd = Math.atan2(dy, dx) - thetaStart;
        // System.out.printf("release thetaEnd = %.1f%n",
        //                   Math.toDegrees(thetaEnd));
      }

      public void mouseDragged(MouseEvent e) {
        if(rotating) {
          double dy = e.getY() - center.y;
          double dx = e.getX() - center.x;
          double theta = Math.atan2(dy, dx);
          at.setToRotation(theta - thetaStart, center.x, center.y);
          repaint();
        }
      }
    };
    public void setWindowSize(Rectangle size){
      this.windowHeight = size.height;
      this.windowWidth  = size.width;
    }
    public void reset(){
      Y2=0;
    }

        // private static BufferedImage loadImage(String url){
        //   BufferedImage img = null;
        //   try {
        //     img = ImageIO.read(new File(url));
        //   } catch (IOException e) {}
        //   return img;
        // }


}
