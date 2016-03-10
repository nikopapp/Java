import java.util.*;

class DB{
  public static final int decimal = 10;
  public static final int entire = -1;
  private List<Table> dbase = new ArrayList<Table>();
  private IOF file = new IOF("data/example.db");
  private Defin def;
    public static void main(String args[]){
    DB db=new DB();
    Scanner in = new Scanner(System.in);
    boolean cntrl=true;
    while(cntrl==true){
      printInstructions();
      int cases=-1;
      cases=parseIn(in);
      int tableNum;
      int numOfFields;
      cntrl=db.control(in,cases-1);
    }
  }
  private static int parseIn(Scanner in){
    int cases=10;
    try{
      cases=Integer.parseInt(in.next(),decimal);
    } catch(Exception e){System.out.println("please stop messing around");}
    return cases;
  }
  private boolean control(Scanner in, int cases){
      switch (cases) {
      case 0:
        controlRecord(in);
        break;
      case 1:
        controlTable(in);
        break;
      case 2:
        for(Table tab: dbase){
          tab.printTable(entire);
        }
        break;
      case 3:
        System.out.println("saving...");
        for(Table tab: dbase){
          file.write(0,tab.saveTable());
        }
        break;
      case 4:
        testing();
        break;
      case 8:
        return false;
      default:
        break;
    }
    return true;
  }
  private boolean controlRecord(Scanner in){
   boolean cntrl=true;
    while(cntrl==true){
      printInstructionsRecord();
      switch (parseIn(in)-1) {
        case 0:
          newRecord(in);
          break;
        case 1:
          removeRecord(in);
          break;
        case 2:
          dbase.get(0).printTable(entire);
          break;
        case 3:
          retrieveIndex(in);
          break;
        case 4:
          retrieveValue(in);
          break;
        case 8:
          cntrl=false;
          break;
        default:
          break;
      }
    }
    return true;
  }
  private boolean controlTable(Scanner in){
   boolean cntrl=true;
    while(cntrl==true){
      printInstructionsTable();
      switch (parseIn(in)-1) {
        case 0:
          newTable(in);
          break;
        case 1:
          removeTable(in);
          break;
        case 2:
          System.out.println("Chose the name of table to be printed");
          getTableNames("Chose from:");
          dbase.get(returnTableByName(in.next())).printTable(entire);
          break;
        case 3:
          addTestTable();
          break;
        case 8:
          cntrl=false;
          break;
        default:
          break;
      }
    }
    return true;
  }
  private void newRecord(Scanner in){
    printInstructionsNewRecord();
    System.out.println("this is the size "+dbase.size());
    int tableNum =  returnTableByName(in.next());
    int numOfFields=dbase.get(tableNum).numOfFields;
    dbase.get(tableNum).addEntry(new Record(numOfFields,in.next()));
  }
  private void removeRecord(Scanner in){
    printInstructionsRmvRecord();
    getTableNames("the tables are ");
    int tableNum =  returnTableByName(in.next());
    int entryNum = Integer.parseInt(in.next())-1;
    dbase.get(tableNum).removeEntry(entryNum);
  }
  private int returnTableByName(String name){
    int i=0;
    for (Table tab: dbase){
      if(tab.getName().equals(name)) return i;
      i++;
    }
    return -1;
  }
  private void newTable(Scanner in){
    printInstructionsNewTable();
    String name = in.next();
    int numOfFields = Integer.parseInt(in.next());
    Table temp = new Table(name ,numOfFields,in.next());
    if (temp.valid==true) dbase.add(temp);
    else System.out.println("This table has duplicate columns and has not been stored");
  }
  private void removeTable(Scanner in){
    printInstructionsRmvTable();
    int tableNum = returnTableByName(in.next());
    dbase.remove(tableNum);
  }
  private void retrieveIndex(Scanner in){
    printInstructionsRetrieve();
    int tableNum = returnTableByName(in.next());
    int entryNum = Integer.parseInt(in.next())-1;
    dbase.get(tableNum).printTable(entryNum);

    // System.out.println(dbase.get(tableNum).returnEntry(entryNum).values);
  }
  private void retrieveValue(Scanner in){
    printInstructionsRetrieve();
    int tableNum = returnTableByName(in.next());
    List<Integer> entryNum = dbase.get(tableNum).indexOfTable(in.next());
    System.out.println("entry: " + entryNum +" Tablenum: " + tableNum);
    for(int i:entryNum){
      dbase.get(tableNum).printTable(i);
    }
    // System.out.println(dbase.get(tableNum).returnEntry(entryNum).values);
  }

  private static void printInstructions(){
    System.out.println("Usage:");
    System.out.println("1: Records, 2: Tables, 3: print all, 4: save, 5:test 9: exit");
  }
  private static void printInstructionsRecord(){
    System.out.println("1: Add record, 2: Remove record, 3: print all, 4: retrieve value by index, 5: retrieve value by query, 9: back");
  }
  private static void printInstructionsTable(){
    System.out.println("1: Add table, 2: Remove table, 3: print, 4:add example Table, 9: back");
  }

  private static void printInstructionsRetrieve(){
    System.out.println("First declare the name of the table");
    System.out.println("Then the number of the entry");
    System.out.println("eg. 3 10");
  }

  private static void printInstructionsNewTable(){
    System.out.println("First declare the name and number of fields");
    System.out.println("Then the name of each column separeted by commas");
    System.out.println("eg. Cohort 3 \nstudent,unit,grade");
  }
  private static void printInstructionsRmvTable(){
    System.out.println("Input the name of the table you want to remove");
  }

  private static void printInstructionsNewRecord(){
    System.out.println("Select table name");
    System.out.println("Then insert the exact number of \nvalues as the columns in the table");
    System.out.println("eg. Student \nNikos,Databases,95");
  }
  private static void printInstructionsRmvRecord(){
    System.out.println("Select table name");
    System.out.println("Then insert the number of the record you want to remove");
    System.out.println("eg. Student 3");
  }
  public int getDBsize(){
    return dbase.size();
  }
  private void getTableNames(String str){
    System.out.println(str);
    for(Table tab: dbase){
      System.out.println(tab.getName());
    }

  }
  private void addTestTable(){
    System.out.println("creating table: \n animals(String name, String type)");
    dbase.add(new Table("animals",2,"name,type"));
    dbase.get(0).addEntry(new Record(2,"Wanda,fish"));
    dbase.get(0).addEntry(new Record(2,"Lassy,dog"));
  }
// -----------Testing------------------
  private void testing(){
    Tester t=new Tester();
    t.run();
  }

}
