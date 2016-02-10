import java.util.*;

class Oxo {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
      Board    b = new Board();
      Display  d = new Display();
      Position p = new Position();
      b.initialize();
      while(true){
        System.out.println("player 0");
        String posit = in.next();
        p.setPos(posit.charAt(0),posit.charAt(1), '0');
        b.addPond(p.x, p.y, p.player);
      d.Display(b.board);
        System.out.println("player X");
        posit = in.next();
        p.setPos(posit.charAt(0),posit.charAt(1), 'X');
        b.addPond(p.x, p.y,p.player);
        d.Display(b.board);
      }


    }
}
