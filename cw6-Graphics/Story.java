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
  private PolygonExt flashlight;
  private boolean lit = false;
  private PolygonExt prism;
  private static int prismCenterX;
  private static int prismCenteY;
  private int guideX = 40;
  private int X1 = guideX;
  private int X2 = guideX;
  private int X3 = guideX;
  private int X4 = guideX;
  private int X5 = guideX;
  private int X6 = guideX;
  private int Y1 = 0;
  private int Y2 = 0;
  private int Y3 = 0;
  private int Y4;
  private int Y5;
  private int Y6;
  private int flashlightY=74;
  private int rectX1 = guideX;
  private Point2D rotCenter;
  private Color grayBg = new Color(50,50,50);
  private Color blue = new Color(120,150,150);
  private Color yellow = new Color(150,150,100);
  private Color transparent = new Color(0,0,0,0);
  private GradientPaint light = new GradientPaint(0,200,new Color(190,200,170,110),0,400, transparent);
  private ArrayList<GradientPaint> prismLightStyleR = new ArrayList<GradientPaint>();
  private ArrayList<GradientPaint> prismLightStyleL = new ArrayList<GradientPaint>();
  private RenderingHints rh=ToolKit.configRHints();
  private Font fontText = new Font("Dialog",Font.PLAIN,18);
  Story(){
    super();
    setBackground( new Color(0,0,0) );
    setPreferredSize(new Dimension(windowWidth , windowHeight));
    addMouseListener(rotator);
    addMouseMotionListener(rotator);
    // line = new Line2D.Double(X1,Y1,X1,Y2);
    flashlight = SvgShapes.loadSvg("resources/vectors/flashlight.fx",windowWidth/2,200);
    prism = SvgShapes.loadSvg("resources/vectors/prism.fx",windowWidth/2,400);
    prismLightStyleR.add(new GradientPaint(windowWidth/2,200,new Color(160,40,0,100),windowWidth,400,transparent));
    prismLightStyleR.add(new GradientPaint(windowWidth/2,200,new Color(40,160,0,100),windowWidth,400,transparent));
    prismLightStyleR.add(new GradientPaint(windowWidth/2,200,new Color(0,160,40,100),windowWidth,400,transparent));
    prismLightStyleR.add(new GradientPaint(windowWidth/2,200,new Color(0,40,160,100),windowWidth,400,transparent));
    prismLightStyleL.add(new GradientPaint(windowWidth/2,200,new Color(160,40,0,100),0,400,transparent));
    prismLightStyleL.add(new GradientPaint(windowWidth/2,200,new Color(40,160,0,100),0,400,transparent));
    prismLightStyleL.add(new GradientPaint(windowWidth/2,200,new Color(0,160,40,100),0,400,transparent));
    prismLightStyleL.add(new GradientPaint(windowWidth/2,200,new Color(0,40,160,100),0,400,transparent));

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
    // prismCenterX = prism.getCenterX();
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(rh);
    draw(g);
  }
  private void draw(Graphics2D g){
    g.setColor(yellow);
    g.drawPolygon(flashlight);
    drawCable(g);
    if(lit==true) drawScene(g);
    g.setColor(yellow);
    g.setFont(fontText);
    g.drawString("It all begins with light", 2*guideX,50);
      // System.out.println("-degrees: " + flatMatrix[2]);
  }
  private void drawScene(Graphics2D g){
    g.setColor(new Color(5, 5,20,50));
    g.fillRect(0,0,400,300);
    g.fillRect(0,0,300,400);
    g.fillRect(guideX,0,600,flashlightY);
    g.setColor(new Color (100,100,100));
    g.fillRect(rectX1, 100, 300, 25);
    drawLight(g);
    g.setColor(yellow);
    g.fill(at.createTransformedShape(prism));
    g.drawString("Interact with me", 550,300);
    g.drawLine(550,305,470,355);
    g.drawLine(550,305,650,305);
    double[] flatMatrix = new double[6];
    at.getMatrix(flatMatrix);
    if((flatMatrix[2]>0.93&&flatMatrix[2]<1.0)||
       (flatMatrix[2]>-0.98&&flatMatrix[2]<-0.86)){
      g.drawString("Light can be separated into its different frequencies, thus different colors",60,600);
      drawSpectrum(g,flatMatrix[2]);
    }
  }
  private void drawLight(Graphics2D g){
    ArrayList<Point> list = new ArrayList<Point>();
    list.add(new Point(430,200));
    list.add(new Point(490,200));
    list.add(new Point(510,600));
    list.add(new Point(410,600));
    g.setPaint(light);
    g.fillPolygon(new PolygonExt(list));
    // g.fillRect(430,200,60,200);
  }
  private void drawSpectrum(Graphics2D g, double side){
    int i = 1;
    ArrayList<GradientPaint> list;
    if(side >=0 ) list = prismLightStyleR;
    else list = prismLightStyleL;
    for(GradientPaint gpaint: list  ){
      g.setPaint(gpaint);
      g.fill(at.createTransformedShape(new Rectangle(425+i*10,379,20,500)));
      i++;
    }
  }
  private void drawCable(Graphics2D g){
    g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
             BasicStroke.JOIN_ROUND));
    g.setColor(blue);
    g.drawLine(X1, Y1, X2, Y2);
    g.drawLine(X2, Y2, X3, Y3);
    g.drawLine(X3, Y3, X4, Y4);
    g.drawLine(X4, Y4, X5, Y5);
    if(Y6 != 0)
    g.drawLine(X5, Y5, X6, Y6);
    // System.out.println(""+X1+","+Y1+","+X2+","+Y2+","+X3+","+Y3+","+X4+","+Y4+","+X5+","+Y5);
  }
  public boolean lineGrowing(){
    if(Y2<300)      Y2+=3;
    else if(X3<400){ X3+=3; Y3=Y2; Y5=Y4=Y3; X5=X4=X3;}
    else if(Y4>50) { X4=X3;Y4-=3;X5=X3;}
    else if(X5<458){ X6=X5+=3; Y6=Y5=Y4;}
    else if(Y6<flashlightY) Y6+=3;
    else {
      lit = true;
      setBackground(grayBg);
      repaint();
      return false;}
    repaint();
    return true;
  }
  public boolean moveRect(){
    if(rectX1<200) rectX1++;
    else return false;
    repaint();
    return true;
  }
  private MouseInputAdapter rotator  = new MouseInputAdapter() {
    Point2D.Double center = new Point2D.Double();
    double thetaStart = 0;
    double thetaEnd = 0;
    boolean rotating = false;

      public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Shape shape = at.createTransformedShape(prism);
        Rectangle r = shape.getBounds();
        // center.x = r.getCenterX();
        // center.y = r.getCenterY();
        center.x = prism.getCenterX();
        center.y = prism.getCenterY();
        System.out.println(center.x+" "+center.y);
        if(shape.contains(p)) {
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
      lit=false;
    }

        // private static BufferedImage loadImage(String url){
        //   BufferedImage img = null;
        //   try {
        //     img = ImageIO.read(new File(url));
        //   } catch (IOException e) {}
        //   return img;
        // }


}
