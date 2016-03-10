import java.util.*;

class Table extends Token{
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
