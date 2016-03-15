class newTest{
  // private final long divident=600851475143;
  public static void main(String[] args){
  for(long i=0;i<100;i++){
    Prime p = new Prime(i);
    System.out.println(i+" "+p.prime);
  }
  }
}
