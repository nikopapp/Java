import java.util.*;

class Record extends Token{
  int numOfFields;
  String valuesIn;
  List<String> values = new ArrayList<String>();
  Record(int numOfFields, String valuesIn){
    super(numOfFields,valuesIn);
    this.numOfFields=numOfFields;
    this.valuesIn=valuesIn;
    this.values=tokens;
  }
  public int returnEntry(String name){
    return values.indexOf(name);
  }
  //-------------- Testing --------------
  public static void main(String args[]){
    Tester t = new Tester();
    Record r = new Record(2,"nikos,cat");
    t.is(r.values.size(),2);
    t.is(r.values.get(0),"nikos");
    t.is(r.values.get(1),"cat");
    t.is(r.numOfFields,2);
    t.result();
  }
}
