import java.util.*;
class Table extends Token{

  private static final int TITLE = 0;
  private static final int COLUMNS = 1;
  int numOfFields;
  boolean valid;
  private String name;
  private List<String> columns = new ArrayList<String>();
  private List<Record> entries = new ArrayList<Record>();
  Table(String name, int numOfFields, String fieldsIn){
    super(numOfFields,fieldsIn);
    this.name=name;
    this.numOfFields=numOfFields;
    this.columns=tokens;
    this.valid=checkforDuplicates(tokens);
  }
  Table(String name){
    this.name=name;
  }
  private boolean checkforDuplicates(List<String> tokens){
    int cnt=0;
    for(String str1: columns ){
      for(String str2: columns ){
        if(str1.equals(str2)==true) cnt++;
      }
    }
    if(cnt==this.numOfFields) return true;
    else return false;
  }
  public void addEntry(Record r){
    if(r.values!=null) entries.add(r);
  }
  public void removeEntry(int e){
    System.out.println(entries.get(e).values+" has been removed");
    entries.remove(e);
  }
  public void printTable(int index){
    System.out.println(name);
    System.out.println(columns);
    System.out.println("---------------------------");
    if(index==-1){
      for( Record str: entries){
        System.out.println(str.values);
      }
    }
    else {
      System.out.println(entries.get(index).values);
    }
    System.out.println();
  }
  public void saveTable(){
    IOF file = new IOF(name+".txt");
    String table=new String(name+"\n");
    System.out.println(table);
    for(int i=0;i<columns.size();i++){
      table = table+columns.get(i);
      if(i<columns.size()-1) table=table+",";
    }
    System.out.println(table);
    table=table+"\n";
    System.out.println(table);
    for(Record r: entries){
      for(int i=0;i<r.values.size();i++){
        table = table+r.values.get(i);
        if(i<r.values.size()-1) table=table+",";
      }
      table=table+"\n";
    }
    System.out.println(table);
    file.write(0,table);
  }

  public Table loadTable(String name){
    IOF file = new IOF(name+".txt");
    String tableIn=new String();
    tableIn = file.read(0);
    String[] tokens=tableIn.split("\\\n");
    Table table = new Table(tokens[TITLE]);
    Token t = new Token(tokens[COLUMNS]);
    table.columns=t.tokens;
    for (int i=2;i<tokens.length;i++){
      System.out.println(tokens[i]);
      table.addEntry(new Record(table.columns.size() , tokens[i]));
    }
    return table;
  }
  public int returnTableColumns(){
    return columns.size();
  }
  public int returnTableSize(){
    return entries.size();
  }
  public Record returnEntryIndex(int i){
    return entries.get(i);
  }
  // public Record returnEntryValue(String str){
  //   return entries.indexOf(values.str);
  // }
  public String getName(){
    return this.name;
  }
  public boolean isName(String name){
    return this.name.equals(name);
  }
  public void rename(String newName){
    this.name=newName;
  }
  public List<Integer> indexOfTable(String name){
    int i=0;
    List<Integer> list = new ArrayList<Integer>();
    for(Record r:entries){
      int temp=r.returnEntry(name);
      if (temp!=-1) list.add(i);
      i++;
    }
    return list;
  }


// ----------- Testing ----------------
  public static void main(String args[]){
    Tester t = new Tester();
    Table tab = new Table("temp",3,"Student,unit,grade");
    t.is(tab.returnTableColumns(),3);
    t.is(tab.returnTableSize(),0);
    tab.t_addEntry();
    t.is(tab.returnEntryIndex(0).values.get(0),"Nikos");
    t.is(tab.returnEntryIndex(0).values.get(1),"Databases");
    t.is(tab.returnEntryIndex(0).values.get(2),"95");
    //Testing duplicate columns
    Table tab2 = new Table("temp",0,"");
    List<String> list= tab2.t_duplicateList();
    t.is(tab2.checkforDuplicates(list),false);
    Table tab3 = new Table("temp",3,"student,student,grade");
    t.is(tab3.valid,false);
    t.result();
  }
  private List<String> t_duplicateList(){
    List<String> list= new ArrayList<String>();
    numOfFields=4;
    list.add("student");
    list.add("student");
    list.add("unit");
    list.add("grade");
    return list;
  }
  public void t_addEntry(){
    addEntry(new Record(3,"Nikos,Databases,95") );
  }
}
