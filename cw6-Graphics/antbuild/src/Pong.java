package API;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
public class Pong extends Slide{
  private int ballX = 200;
  private int ballY = 200;
  private double ballVelX = 1;
  private double ballVelY = 1;
  private static final int ballDiam = 20;

  private int paddleX = 200;
  private static final int paddleY = 500;
  private static final int paddleWidth = 80;
  private static final int paddleHeight = 15;

  private int limitX  = 600;
  private int limitY  = 400;

  private Rectangle paddle = new Rectangle(paddleX,paddleY,paddleWidth,paddleHeight);
  private Ellipse2D.Double ball = new Ellipse2D.Double(ballX,ballY,ballDiam,ballDiam);
  private static final Color ballColor = new Color(60,10,30);
  private Color paddleColor = getColor("yellow");
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
    JFrame frame = new JFrame("Pong Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(p);
    frame.setSize(p.windowWidth, p.windowHeight);
    frame.setVisible(true);
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
    g.setColor(ballColor);
    g.fill(ball);
  }
  private void drawPadle(Graphics2D g){
    g.setColor(paddleColor);
    g.fill(paddle);
  }
  private void updateBall(){
    if(ball.y < 0 || paddle.contains(ball.x+ballDiam, ball.y+ballDiam)){
      ballVelY = - ballVelY;
    }
    if((ball.x+ballDiam) > limitX || ballX < 200 ){
      ballVelX = - ballVelX;
    }

    ball.x += ballVelX;
    ball.y += ballVelY;
  }
  private void updatePaddle(){
    faceCoordX = faceDetection.runFaceCoordX();
    if(faceCoordX == -1) return;
    if(faceCoordX > 300) paddle.x -= 2;
    if(faceCoordX < 200) paddle.x += 2;
  }
  @Override
  public boolean tick(){
    updateBall();
    updatePaddle();
    repaint();
    return true;
  }
  class Animation implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      tick();
    }
  }
}
