class Prime{
  private long number;
  boolean prime;
  Prime(long numnber){
    this.number = number;
    prime=isPrime();
  }
  boolean isPrime(){
     int i=2;
  int cnt=0;

  while(i<number){
    if(number%i == 0){
    cnt++;
    }
    i++;
  }
  System.out.println("counter: "+cnt);
    System.out.println("number: "+number);
  if(cnt==0){
  return true;
  }
  else{
    return false;
  }
  }
}
