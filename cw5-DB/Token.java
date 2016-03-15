import java.util.*;

public class Token{
  List<String> tokens = new ArrayList<String>();
  public Token(int numOfFields,String input){
    this.tokens=tokenise(numOfFields,input);
  }
  public Token(String input){
    int numOfFields=listSize(input);
    this.tokens=tokenise(numOfFields,input);
  }
  public Token(){}
  private int listSize(String str){
    StringTokenizer st = new StringTokenizer( str , ",");
    return st.countTokens();
  }
  private List<String> tokenise(int numOfFields, String str){
    List<String> list = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer( str , ",");
    while (st.hasMoreTokens()){
      list.add(st.nextToken());
    }
    if(isFieldEqualValues(numOfFields,list.size())) return list;
    else return null;
  }
  private boolean isFieldEqualValues(int numOfFields, int numOfValues){
    if(numOfFields!=numOfValues){
      System.out.println("Error: Number of values is different of that of fields");
      return false;
    }
  return true;
  }
}
