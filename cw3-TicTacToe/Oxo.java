import java.util.*;

class Oxo {
    public static void main(String[] args) {
      int n = args.length;
      if(n != 0){
        Tester t = new Tester();
        t.run();
        return;
      }
      Board    b = new Board();
      Display  d = new Display();
      Position p = new Position();
      d.Display(b.board);
      boolean win=false;
//---------------------Game Loop start
      while(b.finished==false){
        d.getInput(p.player);
        p.setPos(d.posit.charAt(0),d.posit.charAt(1));
        boolean nextTurn = b.addPond(p.y, p.x, p.player);
        d.Display(b.board);
//---------------------PLAYER change
        if (nextTurn==true) p.swapPlayer(p.player);
      }
//---------------------Game Loop end
  }
}
