import java.util.*;

class Tester{
  private int tests;
  void run(){
    test_Board();
    test_Position();
    test_Display();
    System.out.println("Tests passed: " + tests);
  }
  void test_Board(){
    Board_addPond();
    Board_win();
  }
  void test_Position(){
    Position_swapPlayer();
  }
  void test_Display(){
   Display d=new Display();
   Board b = new Board();
   for (char[] row: b.board)
     Arrays.fill(row, '2');
   d.Display(b.board);

  }
  //Tests 1 to 9
  void Board_addPond(){
    Board b = new Board();
    for(int i=-1;i<4;i++){
      for(int j=-1;j<4;j++){
        boolean pondAdded = b.addPond(i,j,'0');
          if(pondAdded==true) is(b.board[i][j],'0');
          else tests++;
      }
    }
  }
  //Tests 10 to 12
  void Board_win(){
    Board b = new Board(
     '0', '0', '0',
     '-', '-', '-',
     '-', '-', '-'
    );
   is(b.win(0,0,'0'),true);  //
   is(b.win(0,0,'X'),false); //
   is(b.win(2,2,'0'),false); //
  }
  //Tests 13, 14
  void Position_swapPlayer(){
    Position pos = new Position();
    is(pos.swapPlayer('0'),'X');
    is(pos.swapPlayer('X'),'0');
  }
  // Test whether two objects or primitive values such as ints are equal.
  // Throw an error if not, to cause a backtrace with line numbers.
  void is(Object x, Object y) {
      tests++;
      if (x == y) return;
      if (x != null && x.equals(y)) return;
      throw new Error("Test " + tests + " failed: " + x + ", " + y);
  }
}
