class Oxo {
    public static void main(String[] args) {
      int n = args.length;
      // if run with arguments it runs tests
      if(n != 0){
        Tester t = new Tester();
        t.run();
        return;
      }
      Board    b = new Board();
//    Display (int windowHeight, int windowWidth) -- Game is scalable
      Display  d = new Display(500,800);
      Position p = new Position();
      d.initializeGraph();
//---------------------Game Loop start
      while(b.gameWin==false && b.gameDraw == false){
        boolean nextTurn;
        d.drawGraph(b.board,p.player);
        d.Display(b.board);
        while(d.action == false){
        // d.getInput(p.player);
        if(p.x!= -1) p.setPos(d.position);
       }
        nextTurn = b.addPond(p.y, p.x, p.player);
//---------------------PLAYER change
        if (nextTurn==true) {
          p.swapPlayer(p.player);
        }
      }
      d.drawEnd(b.gameWin);
      d.closeGraph();
//---------------------Game Loop end
  }
}
