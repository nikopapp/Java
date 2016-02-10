class Display{
  void Display(char[][] board){
    char indexY='A';

    System.out.println("  123");
    for(int i=0;i<3;i++){
      System.out.print(indexY++ +" ");
      for(int j=0;j<3;j++){
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }
}
