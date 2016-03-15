class Position {
  int x;
  int y;
  char player ='0';
  void setPos(String posit){
    this.x=posit.charAt(1)-'1';
    this.y=posit.charAt(0)-'A';
  }
  char swapPlayer(char player){
    if(player=='0') this.player='X';
    else this.player='0';
    return this.player;
  }
}
