package API;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.sound.sampled.*;


// write a tick method on slide then override it in every scene

class Story extends Slide{
  AudioFormat PLAYBACK_FORMAT = new AudioFormat(44100,16, 1, true, false);
  Sound bzz,reveal;
  SoundManager soundManager;
  private static final int X_ANGLE = 2;
  private AffineTransform at = new AffineTransform();
  private static final long serialVersionUID = 1L;
  private PolygonExt flashlight;
  private int flashlightCentX;
  private boolean lit = false;
  private PolygonExt prism;
  private double[] prismTransfrm = new double[6];
  private boolean solved = false;
  private boolean firstTime = true;
  private int prismCenterX;
  private int prismCenterY;
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
  private GradientPaint light = new GradientPaint(0,200,new Color(190,200,170,110),0,400, getColor("transparent"));
  private ArrayList<GradientPaint> prismLightStyleR = new ArrayList<GradientPaint>();
  private ArrayList<GradientPaint> prismLightStyleL = new ArrayList<GradientPaint>();
  private SvgShapes svg = new SvgShapes();
  Story(){
    super();
    setBackground( Color.black );
    addMouseListener(rotator);
    addMouseMotionListener(rotator);
    URL resourceFlashlight = getClass().getClassLoader().getResource("vectors/flashlight.fx");
    URL resourcePrism = getClass().getClassLoader().getResource("vectors/prism.fx");

    // flashlight = svg.loadSvg("resources/vectors/flashlight.fx",windowWidth/2+95,200);
    // prism = svg.loadSvg("resources/vectors/prism.fx",windowWidth/2+95,400);

        flashlight = svg.loadSvg(resourceFlashlight,windowWidth/2+95,200);
        prism = svg.loadSvg(resourcePrism,windowWidth/2+95,400);

    prismCenterX = prism.getCenterX();
    prismCenterY = prism.getCenterY();

    createDynamicGradientPaints();
    initSounds();
  }
  public static void main(String[] args){
    Story test = new Story();
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(test);
    f.setVisible(true);
    f.pack();
    // prismCenterX = prism.getCenterX();
  }
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(getRenderingHints());
    draw(g);
  }
  private void draw(Graphics2D g){
    g.setColor(getColor("yellow"));
    g.drawPolygon(flashlight);
    if(lit) drawScene(g);
    drawCable(g);
    drawPunchLines(g);
    if(!lit) drawPunchCovers(g);
    at.getMatrix(prismTransfrm);
    drawSpectrum(g);
  }
  private void drawScene(Graphics2D g){
    g.setColor(new Color(5, 5,20,50));
    g.fillRect(0,0,450,300);
    g.fillRect(0,0,300,400);
    g.fillRect(guideX,0,600,flashlightY);
    g.setColor(new Color (100,100,100));
    g.fillRect(rectX1, 100, 300, 25);
    drawLight(g);
    g.setColor(getColor("yellow"));
    drawPrism(g);
    g.setFont(getFont("big"));
    g.drawString("Interact with me", 630,250);
    g.drawLine(630,255,570,355);
    g.drawLine(630,255,780,255);
  }
  private void createDynamicGradientPaints(){
    prismLightStyleR.add(new GradientPaint(windowWidth/2+115,300,new Color(160,40,0,100),windowWidth,600,getColor("transparent")));
    prismLightStyleR.add(new GradientPaint(windowWidth/2+115,300,new Color(40,160,0,100),windowWidth,600,getColor("transparent")));
    prismLightStyleR.add(new GradientPaint(windowWidth/2+115,300,new Color(0,160,40,100),windowWidth,600,getColor("transparent")));
    prismLightStyleR.add(new GradientPaint(windowWidth/2+115,300,new Color(0,40,160,100),windowWidth,600,getColor("transparent")));
    prismLightStyleL.add(new GradientPaint(windowWidth/2+115,300,new Color(160,40,0,100),0,600,getColor("transparent")));
    prismLightStyleL.add(new GradientPaint(windowWidth/2+115,300,new Color(40,160,0,100),0,600,getColor("transparent")));
    prismLightStyleL.add(new GradientPaint(windowWidth/2+115,300,new Color(0,160,40,100),0,600,getColor("transparent")));
    prismLightStyleL.add(new GradientPaint(windowWidth/2+115,300,new Color(0,40,160,100),0,600,getColor("transparent")));
  }
  private void playSoundReveal(){
    firstTime = false;
    soundManager.play(reveal);
  }
  private void drawPunchLines(Graphics2D g){
    g.setColor(getColor("yellow"));
    g.setFont(getFont("big"));
    g.drawString("It all begins with electricity", 2*guideX,50);
    String s = "Which is transformed into ";
    if(lit) s = s+"Light";
    g.drawString(s,3*guideX,250);
  }
  private void drawPrism(Graphics2D g){
    Color oldTemp = g.getColor();
    g.fill(at.createTransformedShape(prism));
    g.setColor(Color.black);
    g.fillOval(prismCenterX,prismCenterY,4,4);
    g.setColor(oldTemp);
  }
  private void drawPunchCovers(Graphics2D g){
    g.setColor(Color.black);
    g.fillRect(guideX+2,Y2+2,400,100);
    g.fillRect(X3+2,230,400,30);
  }
  private void drawLight(Graphics2D g){
    ArrayList<Point> lightGon = new ArrayList<Point>();
    lightGon.add(new Point(430,200));
    lightGon.add(new Point(490,200));
    lightGon.add(new Point(510,600));
    lightGon.add(new Point(410,600));
    g.setPaint(light);
    PolygonExt pol = new PolygonExt(lightGon);
    pol.translate(115,0);
    g.fillPolygon(pol);
  }
  private void drawSpectrum(Graphics2D g){
    double angle = prismTransfrm[X_ANGLE];
    if(( angle > 0.93 && angle < 1.0) ||
       (angle > -1.0 && angle < -0.93)){
      if(!solved && firstTime) playSoundReveal();
      int i = 1;
      ArrayList<GradientPaint> list;
      if(angle >=0 ) list = prismLightStyleR;
      else list = prismLightStyleL;
      g.drawString("Light can be separated into its different frequencies, thus different colors",60,600);
      for(GradientPaint gpaint : list){
        g.setPaint(gpaint);
        g.fill(at.createTransformedShape(new Rectangle(540+i*10,379,20,700)));
        i++;
      }
    } else{
      firstTime = true;
      solved = false;
    }
  }
  private void drawCable(Graphics2D g){
    g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
    g.setColor(getColor("blue"));
    g.drawLine(X1, Y1, X2, Y2);
    g.drawLine(X2, Y2, X3, Y3);
    g.drawLine(X3, Y3, X4, Y4);
    g.drawLine(X4, Y4, X5, Y5);
    if(Y6 != 0)
      g.drawLine(X5, Y5, X6, Y6);
    // System.out.println(""+X1+","+Y1+","+X2+","+Y2+","+X3+","+Y3+","+X4+","+Y4+","+X5+","+Y5);
  }
  @Override
  public boolean tick(){
    if(Y2<300)      Y2++;
    else if(X3<450){X3++; Y3=Y2; Y5=Y4=Y3; X5=X4=X3;}
    else if(Y4>40) {X4=X3;Y4--;X5=X3;}
    else if(X5<(prism.getCenterX())){X6=X5++; Y6=Y5=Y4;}
    else if(Y6<flashlightY) Y6++;
    else {
      if(!lit) soundManager.play(bzz);
      lit = true;
      setBackground(getColor("grayBg"));
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
    private void initSounds() {
      soundManager = new SoundManager(PLAYBACK_FORMAT);
      bzz = soundManager.getSound("resources/shorterZap.wav");
      reveal = soundManager.getSound("resources/reveal.wav");

    }


        // private static BufferedImage loadImage(String url){
        //   BufferedImage img = null;
        //   try {
        //     img = ImageIO.read(new File(url));
        //   } catch (IOException e) {}
        //   return img;
        // }
}
