class Tester{
  private int numTests = 0;
  private int tests = 0;
  void run(){
    test_Table();
    test_DB();
    result();
  }
  void result() {
    System.out.println("Tests passed " + tests+" of "+numTests);
  }
  private void test_Table(){
    Table t = new Table("Cohort", 3,"Student,unit,grade");
    is(t.returnTableColumns(),3);
    is(t.returnTableSize(),0);
    t.t_addEntry();
    is(t.returnEntryIndex(0).values.get(0),"Nikos");
    is(t.returnEntryIndex(0).values.get(1),"Databases");
    is(t.returnEntryIndex(0).values.get(2),"95");
  }
  private void test_DB(){
    DB tdb = new DB();
    is(tdb.getDBsize(),0);
  }
  // Test whether two objects or primitive values such as ints are equal.
  // Throw an error if not, to cause a backtrace with line numbers.
  void is(Object x, Object y) {
      numTests++;
      System.out.println(x +" = "+y);
      if (x == y) {
        tests++;
        return;
      }else if (x != null && x.equals(y)){
        tests++;
        return;
      }
      System.out.println("Test " + tests + " failed: expected: " + y + ", actual " + x);
  }

}
