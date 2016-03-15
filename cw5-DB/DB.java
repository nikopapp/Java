//at the moment allows duplicate table name do smthng about it

import java.util.*;
import java.util.Scanner;
import javax.swing.*;
//id Records

class DB{
  public static final int NOT_EXIST  = -1;
  public static final int ENTIRE  = -1;
  public static final int DECIMAL = 10;
  //---Base Menu Switch
  public static final int RECORD  = 1;
  public static final int TABLE   = 2;
  public static final int PRINT   = 3;
  public static final int SAVE    = 4;
  public static final int LOAD    = 5;
  public static final int TEST    = 6;
  public static final int QUIT    = 0;
  //---Record Menu Switch
  public static final int R_NEW   = 11;
  public static final int R_RMV   = 12;
  public static final int R_RET_INDEX = 14;
  public static final int R_RET_VALUE = 15;
  //---Table Menu Switch
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
      printInstructions();
      cases=parseIn(in);
      cntrl=db.control(in,cases);
    }
  }
  private static int parseIn(Scanner in){
    int cases=10;
    try{
      cases=Integer.parseInt(in.next(), DECIMAL);
    } catch(Exception e){System.out.println("please stop messing around");}
    return cases;
  }
  private boolean control(Scanner in, int cases){
      switch (cases) {
      case RECORD:
        controlRecord(in);
        break;
      case TABLE:
        controlTable(in);
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
      case T_RENAME:
        renameTable(in);
        break;
      default:
        break;
    }
  }
  private boolean controlRecord(Scanner in){
   boolean cntrl=true;
    while(cntrl==true){
      printInstructionsRecord();
      switch (parseIn(in)) {
        case R_NEW:
          newRecord(in);
          break;
        case R_RMV:
          removeRecord(in);
          break;
        case PRINT:
          dbase.get(0).printTable(ENTIRE);
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
    printInstructionsNewRecord();
    getTableNames("the tables are ");
    int tableNum =  returnTableByName(in.next());
    int numOfFields=dbase.get(tableNum).numOfFields;
    dbase.get(tableNum).addEntry(new Record(numOfFields,in.next()));
  }
  private void removeRecord(Scanner in){
    printInstructionsRmvRecord();
    getTableNames("the tables are: ");
    int tableNum =  returnTableByName(in.next());
    int entryNum = parseIn(in)-1;
    dbase.get(tableNum).removeEntry(entryNum);
  }
  private void retrieveIndex(Scanner in){
    printInstructionsRetrieveIndex();
    int tableNum = returnTableByName(in.next());
    int entryNum = parseIn(in)-1;
    dbase.get(tableNum).printTable(entryNum);
  }
  private void retrieveValue(Scanner in){
    printInstructionsRetrieveValue();
    int tableNum = returnTableByName(in.next());
    List<Integer> entryNum = dbase.get(tableNum).indexOfTable(in.next());
    System.out.println("entry: " + entryNum +" Tablenum: " + tableNum);
    for(int i:entryNum){
      dbase.get(tableNum).printTable(i);
    }
  }
//restore private spacificatior
   private boolean controlTable(Scanner in){
   boolean cntrl=true;
    while(cntrl==true){
      printInstructionsTable();
      switch (parseIn(in)) {
        case T_NEW:
          newTable(in);
          break;
        case T_RMV:
          removeTable(in);
          break;
        case PRINT:
          System.out.println("Chose the name of table to be printed");
          getTableNames("Chose from:");
          dbase.get(returnTableByName(in.next())).printTable(ENTIRE);
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
  private int returnTableByName(String name){
    int i=0;
    for (Table tab: dbase){
      if(tab.getName().equals(name)) return i;
      i++;
    }
    return NOT_EXIST;
  }
  private void renameTable(Scanner in){
    printInstructionsRnmTable();
    int tableNum = returnTableByName(in.next());
    dbase.get(tableNum).rename(in.next());
  }
  //public interface methods for cotrol through GUI class
  public void controlPrint(){
    System.out.println("printing...");
    for(Table tab: dbase){
      tab.printTable(ENTIRE);
    }
  }
  public void controlSave(){
    System.out.println("saving...");
    for(Table tab: dbase){
      tab.saveTable();
    }
  }
  public void controlLoad(){
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


  // -----------Instructions--------------
  private static void printInstructions(){
    System.out.println("Usage:");
    System.out.println("1: Records, 2: Tables, 3: print all, 4: save, 5:load table, 6:test 0: exit");
  }
  private static void printInstructionsRecord(){
    System.out.println("1: Add record, 2: Remove record, 3: print all, 4: retrieve value by index, 5: retrieve value by query, 0: back");
  }
  private static void printInstructionsTable(){
    System.out.println("1: Add table, 2: Remove table, 3: print, 6:add example Table, 0: back");
  }
  private static void printInstructionsRetrieveIndex(){
    System.out.println("First declare the name of the table");
    System.out.println("Then the number of the entry");
    System.out.println("eg. animals 10");
  }
  private static void printInstructionsRetrieveValue(){
    System.out.println("First declare the name of the table");
    System.out.println("Then a value cantained in the entry");
    System.out.println("eg. animals dog");
  }
  private static void printInstructionsNewTable(){
    System.out.println("First declare the name and number of fields");
    System.out.println("Then the name of each column separeted by commas");
    System.out.println("eg. Cohort 3 \nstudent,unit,grade");
  }
  private static void printInstructionsRmvTable(){
    System.out.println("Input the name of the table you want to remove");
  }
  private static void printInstructionsRnmTable(){
    System.out.println("Input the name of the table you want to rename and the new name");
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

}
