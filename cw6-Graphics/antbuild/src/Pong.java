package API;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

public class Pong extends Slide{

  private Ball ball = new Ball();
  private Paddle paddle = new Paddle();
  private int limitX  = 600;
  private int limitY  = 400;

  private Font instructionFont = getFont("big");

  private int faceCoordX = 250;
  private JavaCVPrjt03 faceDetection;
  private Timer t = new Timer(5, new Animation());


  Pong(){
    faceDetection = new JavaCVPrjt03();
    this.limitX = windowWidth;
    this.limitY = windowHeight;
  }
  public static void main(String[] args){
    Pong p = new Pong();
    JFrame frame = initFrame("Pong Game", p);
    SwingUtilities.invokeLater(p::run);
  }
  private void run(){
    t.start();
  }

  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
    g.setRenderingHints(getRenderingHints());
    drawInstructions(g);
    drawBall(g);
    drawPadle(g);
    g.drawString("Face :"+ faceCoordX, windowWidth/2,windowHeight-40);
  }
  private void drawInstructions(Graphics2D g){
    g.setColor(paddleColor);
    g.setFont(instructionFont);
    g.drawString("Notice that the paddles moves along with your face", 50, 50);
  }
  private void drawBall(Graphics2D g){
    g.setColor(ball.getColor());
    g.fill(ball.shape);
  }
  private void drawPadle(Graphics2D g){
    g.setColor(paddleColor);
    g.fill(paddle);
  }
  private void updateBall(){
    if(ball.getCenterY() < 200 || paddle.contains(ball.getCenterX(), ball.getCenterY())){
      ball.bounceY();
    }
    if((ball.getCenterX()) > limitX || ball.getCenterX() < 200 ){
      ball.bounceX();
    }
    ball.move();
  }
  private void updatePaddle(){
    faceCoordX = faceDetection.runFaceCoordX();
    if(faceCoordX == -1) return;
    if(faceCoordX > 300) paddle.x -= paddleVel;
    if(faceCoordX < 200) paddle.x += paddleVel;
  }
  private class Ball{
    private int x = 200;
    private int y = 200;
    private int velX = 2;
    private int velY = 4;
    private static final int diam = 20;
    private Ellipse2D.Double shape = new Ellipse2D.Double(x, y, diam, diam);
    private Color col = new Color(60,10,30);

    public void move(){
      if(goingLeft){
        shape.x -= velX;
      }
      else{
        shape.x += velX;
      }
      if(goingUp){
        shape.y -= velY;
      }
      else{
        shape.y += velY;
      }
      if(shape.y>windowHeight) shape.y = 200;
    }
    private void bounceY(){
      velY = - velY;
      // if(goingUp) goingUp  = false;
      // if(!goingUp) goingUp = true;
    }
    private void bounceX(){
      velX = - velX;
      // if(goingLeft) goingLeft  = false;
      // if(goingLeft) goingLeft = true;
    }
    public Color getColor(){
      return col;
    }
    public int getCenterX(){
      return (int) shape.x + diam;
    }
    public int getCenterY(){
      return (int) shape.y + diam;
    }
  }
  private class Paddle{
    private int paddleX = 200;
    private static final int paddleY      = 500;
    private static final int paddleWidth  = 80;
    private static final int paddleHeight = 15;
    private static final int paddleVel    = 4;
    private Rectangle paddle = new Rectangle(paddleX,paddleY,paddleWidth,paddleHeight);
    private Color paddleColor = getColor("yellow");

  }
  @Override
  public boolean tick(){
    updateBall();
    updatePaddle();
    repaint();
    Toolkit.getDefaultToolkit().sync();
    return true;
  }
  class Animation implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      tick();
    }
  }
}
