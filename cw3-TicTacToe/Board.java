class Board {
  char[][] board = new char[3][3];
  void initialize(){
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        board[i][j]='*';
      }
    }
  }

  void addPond(int y, int x, char player){
    board[y-'A'][x-'1']=player;
    System.out.println("x: "+(x-'1')+", y: "+(y-'A') + " -- " +player);
  }
  void win(int x, int y, char player){

  }
  void goLeft(int x, int y, char player){
  
  }
}
