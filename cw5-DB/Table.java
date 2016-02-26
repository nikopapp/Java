import java.util.*;

class Table extends Token{
  int numOfFields;
  List<String> values=new ArrayList<String>();
  List<Record> entries= new ArrayList<Record>();
  Table(int numOfFields, String fieldsIn){
    super(numOfFields,fieldsIn);
    this.numOfFields=numOfFields;
    this.values=tokens;
  }
  public void addEntry(Record r){
    if(r.values!=null) entries.add(r);
  }
  public void removeEntry(int e){
    System.out.println(entries.get(e).values+" has been removed");
    entries.remove(e);
  }
  public void printTable(){
    System.out.println(values);
    for( Record str: entries){
      System.out.println(str.values);
    }
  }
  public String saveTable(){
    String table=new String();
    for( Record str: entries){
      table = table+str.values+'\n';
    }
    return table;
  }
}
