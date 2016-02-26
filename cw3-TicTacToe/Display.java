import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Display extends JFrame {
  private int windowHeight = 400;
  private int windowWidth  = 540;
  private final int cellSize   = 60;
  private final int padding    = 10;
  private final int pondSize   = 40;
  private final int boardSize  = 3;
  private final int boardWidth = boardSize*cellSize+4;
  private final int titleY     = 60;
  private int commentYo  = 4*windowHeight/5;
  private final float titleFontSize = 26f;
  private final float endFontSize   = 40f;
  private final int commentFontSize = 15;
  private int boardXo    = (windowWidth-boardWidth)/2;
  private int boardYo    = (windowHeight-boardWidth)/2;
  private JPanel panel = new JPanel();
  private Graphics myGraphics = null;
  private Graphics2D myGraphics2 = null;

  //----backup titleFont
  Font font = new Font("Arial", Font.BOLD, (int) titleFontSize);
  Font commentFont = new Font("Arial", Font.PLAIN, commentFontSize);
  String position;

  public Display (int windowHeight, int windowWidth){
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    this.boardXo =(windowWidth-boardWidth)/2;
    this.boardYo = (windowHeight-boardWidth)/2;
    this.commentYo = 4*windowHeight/5;
  }
  public Display (){
  }
  // output to terminal
  public void Display(char[][] board){
    char indexY='A';
    System.out.println("  123");
    for(int i=0;i<boardSize;i++){
      System.out.print((indexY++) +" ");
      for(int j=0;j<boardSize;j++){
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }
  private  Font loadFont(String str, float fontSize){

    try {
      Font font = Font.createFont(Font.TRUETYPE_FONT, new File(str));
      //Derive and return a "fontSize" pt version:
      return font.deriveFont(fontSize);
    }
    catch (IOException|FontFormatException e) {
      return font;
    }
  }
  public void initializeGraph(){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    setSize(windowWidth, windowHeight);
  }
  public void closeGraph(){
    setVisible(false);
    dispose();
  }
  //output to JFrame
  public void drawGraph(char[][] board, char player){
    revalidate();
    repaint();
    drawTitle("OX  TIC TAC TOE  XO");
    drawPond(board);
    drawComment("player "+player+"'s move");
    drawBoardLines();
  }
  private void drawBoardLines(){
    Graphics g = this.getGraphics();
    //horizontal
    g.drawLine(boardXo, boardYo + boardWidth/3,
     boardXo+boardWidth, boardYo + boardWidth/3);
    g.drawLine(boardXo, boardYo + 2*boardWidth/3,boardXo+boardWidth,
     boardYo + 2*boardWidth/3);
    //vertical
    g.drawLine(boardXo + boardWidth/3,boardYo,
     boardXo + boardWidth/3,boardYo+boardWidth);
    g.drawLine(boardXo + 2*boardWidth/3, boardYo,
     boardXo + 2*boardWidth/3, boardYo+boardWidth);
  }
  private void drawTitle(String str){
    Graphics g = this.getGraphics();
    Font titleFont = loadFont("Belgika-8th.otf", titleFontSize);
    g.setFont(titleFont);
    g.drawString(str , boardXo -cellSize, titleY);
  }
  private void drawX(int i, int j){
    Graphics g = this.getGraphics();
    Graphics2D g2 =(Graphics2D)g;
    int Xo = boardXo + j*(cellSize+1) + padding;
    int Yo = boardYo + i*(cellSize+1) + padding;
    g.setColor (Color.red);
    g2.setStroke(new BasicStroke(4));
    g2.drawLine( Xo ,Yo, Xo+pondSize , Yo+pondSize);
    g2.drawLine( Xo+pondSize, Yo, Xo, Yo+pondSize);
  }
  private void draw0(int i, int j){
    Graphics g = this.getGraphics();
    Graphics2D g2 =(Graphics2D)g;
    int Xo = boardXo + j*(cellSize+1) + padding;
    int Yo = boardYo + i*(cellSize+1) + padding;
    g.setColor (Color.blue);
    g2.setStroke(new BasicStroke(4));
    g.drawOval(Xo, Yo, pondSize, pondSize);
  }
  private void drawPond(char[][] board){
    for(int i=0; i<boardSize;i++){
      for (int j=0; j<boardSize;j++){
        switch(board[i][j]){
          case '0':
            draw0(i,j);
            break;
          case 'X':
            drawX(i,j);
            break;
          default:
            break;
        }
      }
    }
  }
  public void drawComment(String str){
    Graphics g = this.getGraphics();
    g.setFont(commentFont);
    g.drawString(str,boardXo+35,commentYo);
  }
  public void getInput(char player){
    Scanner in = new Scanner(System.in);
    System.out.print("player "+player+"'s move: ");
    position = in.next();
    this.position = position.toUpperCase();
  }
  private void sleep(int Milisecs){
    try
        { Thread.sleep(Milisecs); }
      catch(InterruptedException ex)
        { Thread.currentThread().interrupt(); }
  }
  public void drawEnd(boolean gameWin){
    Graphics g = this.getGraphics();
    revalidate();
    repaint();
    getContentPane().setBackground(Color.black);
    Font endFont = loadFont("Belgika-5th.otf", endFontSize);
    g.setFont(endFont);
    g.setColor(Color.white);
    if(gameWin == true){
      g.drawString("you win !",boardXo-25,boardYo+100);
    }
    else {
      g.drawString("it's a draw",boardXo-40,boardYo+100);
    }
    sleep(2500);
  }
}
