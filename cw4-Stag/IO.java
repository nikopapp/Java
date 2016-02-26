import java.util.*;
class IO{
    Scanner inp = new Scanner(System.in);
  public List<String> getInput(){
    List<String> list = new ArrayList<String>();
    System.out.println("Tell me:");
    list = tokenise(inp.nextLine());
    List<Boolean> verb = new ArrayList<Boolean>();
    for(int i=0;i<list.size();i++){
      verb.add(isVerb(list.get(i)));
    }
    System.out.println(verb);
    return list;
  }
  private List<String> tokenise(String str){
    List<String> list = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer( str , " ");
    while (st.hasMoreTokens()){
      list.add(st.nextToken());
    }
    return list;
  }
  private boolean isVerb(String str){
   if (str.equalsIgnoreCase("run")||
       str.equalsIgnoreCase("go") ||
       str.equalsIgnoreCase("take")){
     return true;
   }
   else return false;
  }

}
