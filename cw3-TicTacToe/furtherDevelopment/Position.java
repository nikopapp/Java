class Position {
  public int x;
  public int y;
  public char player;
  Position(){
    this.x = -1;
    this.y = -1;
    this.player='0';
  }
  void setPos(String posit){
    this.x=posit.charAt(0)-'0';
    this.y=posit.charAt(1)-'0';
  }
  char swapPlayer(char player){
    this.x=-1;
    if(player=='0') this.player='X';
    else this.player='0';
    return this.player;
  }
}
