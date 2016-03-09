import java.util.*;

class DB{
  private List<Table> dbase = new ArrayList<Table>();
  private IOF file = new IOF("data/example.db");
  private Tester t=new Tester();
    public static void main(String args[]){
    DB db=new DB();
    Scanner in = new Scanner(System.in);
    // System.out.println("creating table: \n animals(String name, String type)");
    // dbase.add(new Table(2,"name,type"));
    // dbase.get(0).addEntry(new Record(2,"Wanda,fish"));
    // dbase.get(0).addEntry(new Record(2,"Lassy,dog"));
    while(true){
      printInstructions();
      int cases=-1;
      try{
        cases=Integer.parseInt(in.next(),10);
      } catch(Exception e){System.out.println("please stop messing around");}
      int tableNum;
      int numOfFields;
      db.control(in,cases-1);
    }
  }
  private void control(Scanner in, int cases){
    switch (cases) {
      case 0:
        newRecord(in);
        break;
      case 1:
        removeRecord(in);
        break;
      case 2:
        dbase.get(0).printTable();
        break;
      case 3:
        System.out.println("saving...");
        file.write(0,dbase.get(0).saveTable());
        break;
      case 4:
        newTable(in);
        break;
      case 5:
        removeTable(in);
        break;
      case 6:
       t.run();
       break;
      case 7:
        return;
      default:
    }
  }
  private void newRecord(Scanner in){
    printInstructionsNewRecord();
    System.out.println("this is the size "+dbase.size());
    int tableNum =  Integer.parseInt(in.next())-1;
    int numOfFields=dbase.get(tableNum).numOfFields;
    dbase.get(tableNum).addEntry(new Record(numOfFields,in.next()));
  }
  private void removeRecord(Scanner in){
    printInstructionsRmvRecord();
    System.out.println("this is the size "+dbase.size());
    int tableNum =  Integer.parseInt(in.next())-1;
    int entryNum = Integer.parseInt(in.next())-1;
    dbase.get(tableNum).removeEntry(entryNum);
  }

  private void newTable(Scanner in){
    printInstructionsNewTable();
    int numOfFields = Integer.parseInt(in.next());
    dbase.add(new Table(numOfFields,in.next()));
  }
  private void removeTable(Scanner in){
    printInstructionsRmvTable();
    int tableNum = Integer.parseInt(in.next())-1;
    dbase.remove(tableNum);
  }
  private static void printInstructions(){
    System.out.println("Usage:");
    System.out.println("1: add Record, 2: remove Record, 3: print, 4: save,");
    System.out.println("5: add Table, 6: remove Table, 7: Test, 8: exit");
  }
  private static void printInstructionsNewTable(){
    System.out.println("First declare number of fields");
    System.out.println("Then the name of each column separeted by commas");
    System.out.println("eg. 3 \nstudent,unit,grade");
  }
  private static void printInstructionsRmvTable(){
    System.out.println("Input the number of the table you want to remove");
  }

  private static void printInstructionsNewRecord(){
    System.out.println("Select table");
    System.out.println("Then insert the exact number of \nvalues as the columns in the table");
    System.out.println("eg. 1 \nNikos,Databases,95");
  }
  private static void printInstructionsRmvRecord(){
    System.out.println("Select table");
    System.out.println("Then insert the number of the record you want to remove");
    System.out.println("eg. 1 3");
  }
  public int getDBsize(){
    return dbase.size();
  }

}
