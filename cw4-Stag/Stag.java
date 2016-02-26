import java.util.*;
class Stag{

  public static void main (String args[])
  {
    IO token = new IO();
    List<String> buf = new ArrayList<String>();
    buf = token.getInput();
    for(String str: buf){
      System.out.println(str);
    }
    System.out.println(buf);
  }
}
