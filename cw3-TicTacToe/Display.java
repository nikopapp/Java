import java.util.*;

class Display{
  String posit;
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
  void getInput(char player){
    Scanner in = new Scanner(System.in);
    System.out.print("player "+player+"'s move: ");
    posit = in.next();
    this.posit = posit.toUpperCase();
    System.out.println(posit);
    if(posit.charAt(0)!='A' && posit.charAt(0)!='B'&& posit.charAt(0)!='C'){
      System.out.println("try inside the board next time");
    }
  }
}
