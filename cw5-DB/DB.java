//at the moment allows duplicate table name do smthng about it
//id Records

import java.util.*;

class DB{
  public static final int NOT_EXIST  = -1;
  public static final int ENTIRE  = -1;
  public static final int DECIMAL = 10;
  //---Base Menu Switch
  public static final int RECORD  = 1;
  public static final int TABLE   = 2;
  public static final int PRINT_ALL = 3;
  public static final int PRINT   = 4;
  public static final int SAVE    = 5;
  public static final int LOAD    = 6;
  public static final int TEST    = 7;
  public static final int QUIT    = 0;
  //---Record Menu Switch
  public static final int R_MENU  = 10;
  public static final int R_NEW   = 11;
  public static final int R_RMV   = 12;
  public static final int R_RET_INDEX = 14;
  public static final int R_RET_VALUE = 15;
  //---Table Menu Switch
  public static final int T_MENU  = 20;
  public static final int T_NEW    = 21;
  public static final int T_RMV    = 22;
  public static final int T_RENAME = 24;
  public static final int T_TEST   = 25;

  private static Scanner in = new Scanner(System.in);
  private List<Table> dbase = new ArrayList<Table>();

  public static void main(String args[]){
    DB db=new DB();
    Scanner in = DB.in;
    boolean cntrl=true;
    while(cntrl==true){
      int cases=-1;
      int tableNum;
      int numOfFields;
      Inst.printInstructions();
      cases=parseIn(in);
      cntrl=db.control(cases);
    }
  }
  private static int parseIn(Scanner in){
    int cases=10;
    try{
      cases=Integer.parseInt(in.next(), DECIMAL);
    } catch(Exception e){System.out.println("please stop messing around");}
    return cases;
  }
  private boolean control(int cases){
      switch (cases) {
      case RECORD:
        controlRecord();
        break;
      case TABLE:
        controlTable();
        break;
      case PRINT:
        controlPrint();
        break;
      case SAVE:
        controlSave();
        break;
      case LOAD:
        controlLoad();
        break;
      case TEST:
        testing();
        break;
      case QUIT:
        return false;
      default:
        break;
    }
    return true;
  }
  public void GUI_control(int cases){
      switch (cases) {
      case R_NEW:
        newRecord(in);
        break;
      case R_RMV:
        removeRecord(in);
        break;
      case R_RET_INDEX:
        retrieveIndex(in);
        break;
      case R_RET_VALUE:
        retrieveValue(in);
        break;
      case T_NEW:
        newTable(in);
        break;
      case T_RMV:
        removeTable(in);
        break;
      case T_TEST:
        addTestTable();
        break;
      case PRINT_ALL:
        controlPrint();
        break;
      case PRINT:
        controlPrintOne();
        break;
      case SAVE:
        controlSave();
        break;
      case LOAD:
        controlLoad();
        break;
      case TEST:
        testing();
        break;
      case T_RENAME:
        renameTable(in);
        break;
      default:
        break;
    }
  }
  private boolean controlRecord(){
   boolean cntrl=true;
    while(cntrl==true){
      Inst.printInstructionsRecord();
      switch (parseIn(in)-R_MENU) {
        case R_NEW:
          newRecord(in);
          break;
        case R_RMV:
          removeRecord(in);
          break;
        case R_RET_INDEX:
          retrieveIndex(in);
          break;
        case R_RET_VALUE:
          retrieveValue(in);
          break;
        case QUIT:
          cntrl=false;
          break;
        default:
          break;
      }
    }
    return true;
  }
  private void newRecord(Scanner in){
    Inst.printInstructionsNewRecord();
    getTableNames("the tables are ");
    int tableNum =  returnTableByName(in.next());
    int numOfFields=dbase.get(tableNum).returnTableColumns();
    dbase.get(tableNum).addEntry(new Record(numOfFields,in.next()));
  }
  private void removeRecord(Scanner in){
    Inst.printInstructionsRmvRecord();
    getTableNames("the tables are: ");
    int tableNum =  returnTableByName(in.next());
    int entryNum = parseIn(in)-1;
    dbase.get(tableNum).removeEntry(entryNum);
  }
  private void retrieveIndex(Scanner in){
    Inst.printInstructionsRetrieveIndex();
    int tableNum = returnTableByName(in.next());
    int entryNum = parseIn(in)-1;
    dbase.get(tableNum).printTable(entryNum);
  }
  private void retrieveValue(Scanner in){
    Inst.printInstructionsRetrieveValue();
    int tableNum = returnTableByName(in.next());
    List<Integer> entryNum = dbase.get(tableNum).indexOfTable(in.next());
    System.out.println("entry: " + entryNum +" Tablenum: " + tableNum);
    for(int i:entryNum){
      dbase.get(tableNum).printTable(i);
    }
  }
//restore private spacificatior
   private boolean controlTable(){
   boolean cntrl=true;
    while(cntrl==true){
      Inst.printInstructionsTable();
      switch (parseIn(in)-T_MENU) {
        case T_NEW:
          newTable(in);
          break;
        case T_RMV:
          removeTable(in);
          break;
        case PRINT:
          controlPrintOne();
          break;
        case TEST:
          addTestTable();
          break;
        case T_RENAME:
           renameTable(in);
           break;
        case QUIT:
          cntrl=false;
          break;
        default:
          break;
      }
    }
    return true;
  }
  private void newTable(Scanner in){
    Inst.printInstructionsNewTable();
    String name = in.next();
    int numOfFields = Integer.parseInt(in.next());
    Table temp = new Table(name ,numOfFields,in.next());
    if (temp.valid==true) dbase.add(temp);
    else System.out.println("This table has duplicate columns and has not been stored");
  }
  private void removeTable(Scanner in){
    Inst.printInstructionsRmvTable();
    int tableNum = returnTableByName(in.next());
    dbase.remove(tableNum);
  }
  private int returnTableByName(String name){
    int i=0;
    for (Table tab: dbase){
      if(tab.getName().equals(name)) return i;
      i++;
    }
    return NOT_EXIST;
  }
  private void renameTable(Scanner in){
    Inst.printInstructionsRnmTable();
    int tableNum = returnTableByName(in.next());
    dbase.get(tableNum).rename(in.next());
  }
  //public interface methods for cotrol through GUI class
  private void controlPrint(){
    System.out.println("printing...");
    for(Table tab: dbase){
      tab.printTable(ENTIRE);
    }
  }
  private void controlPrintOne(){
    System.out.println("Chose the name of table to be printed");
    getTableNames("Chose from:");
    dbase.get(returnTableByName(in.next())).printTable(ENTIRE);
  }

  private void controlSave(){
    System.out.println("saving...");
    for(Table tab: dbase){
      tab.saveTable();
    }
  }
  private void controlLoad(){
    System.out.println("Provide the name of the file to be loaded");
    dbase.add(Table.loadTable(in.next()));
  }

  private void getTableNames(String str){
    System.out.println(str);
    for(Table tab: dbase){
      System.out.println(tab.getName());
    }

  }
  // -----------Testing------------------
  public int getDBsize(){
    return dbase.size();
  }
  private void addTestTable(){
    System.out.println("creating table: \n animals(String name, String type)");
    dbase.add(new Table("animals",2,"name,type"));
    dbase.get(0).addEntry(new Record(2,"Wanda,fish"));
    dbase.get(0).addEntry(new Record(2,"Lassy,dog"));
  }
  private void testing(){
    Tester t=new Tester();
    t.run();
  }
}
