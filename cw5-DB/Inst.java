class Inst{
    public static void printInstructions(){
      System.out.println("Usage:");
      System.out.println("1: Records, 2: Tables, 4: print all, 5: save, 6:load table, 7:test 0: exit");
    }
    public static void printInstructionsRecord(){
      System.out.println("1: Add record, 2: Remove record, 4: retrieve value by index, 5: retrieve value by query, 0: back");
    }
    public static void printInstructionsTable(){
      System.out.println("1: Add table, 2: Remove table, 4: print, 6:add example Table, 0: back");
    }
    public static void printInstructionsRetrieveIndex(){
      System.out.println("First declare the name of the table");
      System.out.println("Then the number of the entry");
      System.out.println("eg. animals 10");
    }
    public static void printInstructionsRetrieveValue(){
      System.out.println("First declare the name of the table");
      System.out.println("Then a value cantained in the entry");
      System.out.println("eg. animals dog");
    }
    public static void printInstructionsNewTable(){
      System.out.println("First declare the name and number of fields");
      System.out.println("Then the name of each column separeted by commas");
      System.out.println("eg. Cohort 3 \nstudent,unit,grade");
    }
    public static void printInstructionsRmvTable(){
      System.out.println("Input the name of the table you want to remove");
    }
    public static void printInstructionsRnmTable(){
      System.out.println("Input the name of the table you want to rename and the new name");
    }
    public static void printInstructionsNewRecord(){
      System.out.println("Select table name");
      System.out.println("Then insert the exact number of \nvalues as the columns in the table");
      System.out.println("eg. Student \nNikos,Databases,95");
    }
    public static void printInstructionsRmvRecord(){
      System.out.println("Select table name");
      System.out.println("Then insert the number of the record you want to remove");
      System.out.println("eg. Student 3");
    }

}
