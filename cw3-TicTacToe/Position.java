class Position {
  int x;
  int y;
  char player ='0';
  void setPos(int y, int x){
    this.x=x-'1';
    this.y=y-'A';
  }
  char swapPlayer(char player){
    if(player=='0') this.player='X';
    else this.player='0';
    return this.player;
  }
}
