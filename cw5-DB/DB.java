import java.util.*;

class DB{
  public static void main(String args[]){
    Scanner in = new Scanner(System.in);
    System.out.println("creating table: \n animals(String name, String type)");
    Table animals = new Table(2,"name,type");
    FileManipulation file = new FileManipulation("data/example.db");
    animals.addEntry(new Record(2,"Wanda,fish"));
    animals.addEntry(new Record(2,"Lassy,dog"));
    while(true){
      printInstructions();
      int cases=Integer.parseInt( in.next());
      switch (cases-1) {
        case 0:
          animals.addEntry(new Record(2,in.next()));
          break;
        case 1:
          int entryNum = Integer.parseInt(in.next());
          animals.removeEntry(entryNum-1);
          break;
        case 2:
          animals.printTable();
          break;
        case 3:
          file.write(0,animals.saveTable());
          break;
        case 4:
         return;
      }
    }
  }
  private static void printInstructions(){
    System.out.println("Usage:");
    System.out.println("1: add, 2: remove, 3: print, 4: save, 5: exit");
  }
}
