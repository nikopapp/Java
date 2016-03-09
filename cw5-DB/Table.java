import java.util.*;

class Table extends Token{
  int numOfFields;
  private List<String> columns = new ArrayList<String>();
  private List<Record> entries = new ArrayList<Record>();
  Table(int numOfFields, String fieldsIn){
    super(numOfFields,fieldsIn);
    this.numOfFields=numOfFields;
    this.columns=tokens;
  }
  public void addEntry(Record r){
    if(r.values!=null) entries.add(r);
  }
  public void removeEntry(int e){
    System.out.println(entries.get(e).values+" has been removed");
    entries.remove(e);
  }
  public void printTable(){
    System.out.println(columns);
    System.out.println("---------------------------");
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
  public int returnTableColumns(){
    return columns.size();
  }
  public int returnTableSize(){
    return entries.size();
  }
  public Record returnEntry(int i){
    return entries.get(i);
  }
  public void t_addEntry(){
    addEntry(new Record(3,"Nikos,Databases,95") );
  }
}
