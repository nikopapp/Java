import java.util.Arrays;

class Board {
  char[][] board = new char[3][3];
  boolean gameWin = false;
  boolean gameDraw =false;
  Board(){
    this.initialize();
  }
  Board(char c00, char c01, char c02, char c10, char c11, char c12, char c20, char c21, char c22 ){
  char[][] btemp ={
    {c00,c01,c02},
    {c10,c11,c12},
    {c20,c21,c22}
  };
    board = btemp;
  }
  private void initialize(){
    for (char[] row: board)
      Arrays.fill(row, '-');
  }
  boolean addPond (int y, int x, char player){
    if (x<0 || x>=3 || y<0 || y>=3){
      System.out.println("Try inside the board, please ");
      return false;
    }
    else if(board[y][x]!='-'){
      System.out.println("Try a position that is free, please ");
      return false;
    }
    else {
      board[y][x]=player;
      checkFinished(y, x, player);
      return true;
    }
  }
  private void checkFinished(int y, int x, char player){
    gameWin = win(y,x,player);
    gameDraw = draw();
  }
  private boolean draw(){
    int counter=0;
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        if(board[i][j]=='-') counter++;
      }
    }
    if(counter == 0) return true;
    else return false;
  }
  boolean win(int y, int x, char player){
    if(checkRow(y, x, player))        return true;
    else if(checkCol(y, x, player))   return true;
    else if(checkDiag1(y, x, player)) return true;
    else if(checkDiag2(y, x, player)) return true;
    else return false;
  }
  private boolean checkRow(int y, int x, char player){
    int counter=0;
    for(int i=0;i<3;i++){
      if(board[y][i] == player) counter++;
    }
    if(counter==3){
      System.out.println("you win " + player);
      return true;
    }
    else return false;
  }
  private boolean checkCol(int y, int x, char player){
    int counter=0;
    for(int i=0;i<3;i++){
      if(board[i][x]==player) counter++;
    }
    if(counter==3){
      System.out.println("you win "+player);
      return true;
    }
    else return false;
  }
  private boolean checkDiag1(int y, int x, char player){
    if(x==y){
    int counter=0;
    for(int i=0;i<3;i++){
      if(board[i][i]==player) counter++;
    }
    if(counter==3){
      System.out.println("you win "+player);
      return true;
    }
    else return false;
    }
    else return false;
  }
  private boolean checkDiag2(int y, int x, char player){
    if((x+y)==2){
    int counter=0;
    for(int i=0;i<3;i++){
      if(board[i][2-i]==player) counter++;
    }
    if(counter==3){
      System.out.println("you win "+player);
      return true;
    }
    else return false;
    }
    else return false;
  }
}
