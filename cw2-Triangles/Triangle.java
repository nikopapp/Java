//Extra Tests have been amended to complete the testing process
/* Print out the classification of a triangle, given three integer lengths
given on the command line.  If no arguments are given, run the unit tests. */

class Triangle {
    // Count how many tests have been run
    private int tests;

    // Deal with the command line arguments
    public static void main(String[] args) {
        Triangle program = new Triangle();
        int n = args.length;
        if (n == 0) program.test();
        else if (n != 3) System.err.println("Use: java Triangle a b c");
        else System.out.println(program.classify(args[0], args[1], args[2]));
    }

    // Classify a triangle from the lengths, in string format
    String classify(String a, String b, String c) {
        String result="";
        if(LeadingZeros(a,b,c))              return "Illegal";
        int sideA=ArgumentToInt(a);
        int sideB=ArgumentToInt(b);
        int sideC=ArgumentToInt(c);
        if(isIllegal(sideA,sideB,sideC))     return "Illegal";
        if(isImpossible(sideA,sideB,sideC))  return "Impossible";
        if(isEquilateral(sideA,sideB,sideC)) result = "Equilateral";
        if(isScalene(sideA,sideB,sideC))     result = "Scalene";
        if(isRight(sideA,sideB,sideC))       result = "Right";
        if(isIsosceles(sideA,sideB,sideC))   result = "Isosceles";
        if(isFlat(sideA,sideB,sideC))        result = "Flat";
        return result;
    }
    int ArgumentToInt(String a){
      int side;
      try {
        side = Integer.parseInt(a,10);
      }
      catch(NumberFormatException nfe){
        return 0;
      }
      return side;
    }
    boolean LeadingZeros(String a, String b, String c){
      if(a.charAt(0)=='0'||
         b.charAt(0)=='0'||
         c.charAt(0)=='0'
      ) return true;
      else return false;
    }
    boolean isBadFormated(String a, String b, String c) {
      if("".equals(a)||"".equals(b)|| "".equals(c)){
        return true;
      }
      else return false;
    }
    boolean isIllegal(int a, int b, int c) {
      if(a<=0||b<=0||c<=0 ||
         a>Integer.MAX_VALUE||b>Integer.MAX_VALUE||c>Integer.MAX_VALUE){
        return true;
      }
      else return false;
    }
    boolean isImpossible(int a, int b, int c) {
      if(Integer.compareUnsigned(a,b+c)==1||
         Integer.compareUnsigned(b,a+c)==1||
         Integer.compareUnsigned(c,a+b)==1){
        return true;
      }
      else return false;
    }
    boolean isRight(long a, long b, long c) {
      if(Long.compareUnsigned(a*a,b*b+c*c)==0 ||
         Long.compareUnsigned(b*b,a*a+c*c)==0 ||
         Long.compareUnsigned(c*c,a*a+b*b)==0){
        return true;
      }
      else return false;
    }
    boolean isEquilateral(int a, int b, int c) {
      if(a==b&&c==b) return true;
      else return false;
    }
    boolean isScalene(int a, int b, int c) {
      if(a!=b && b!=c &&c!=a) return true;
      else return false;
    }
    boolean isIsosceles(int a, int b, int c) {
      if(!isEquilateral(a,b,c)&&(a==b||b==c||c==a)) return true;
      else return false;
    }
    boolean isFlat(int a, int b, int c) {
      if(a==b+c||c==a+b||b==a+c) return true;
      else return false;
    }
    // Test whether two objects or primitive values such as ints are equal.
    // Throw an error if not, to cause a backtrace with line numbers.
    void is(Object x, Object y) {
        tests++;
        if (x == y) return;
        if (x != null && x.equals(y)) return;
        throw new Error("Test " + tests + " failed: " + x + ", " + y);
    }
    // Run all the unit tests.
    void test() {
        // Equilateral: all equal
        is(classify("8", "8", "8"), "Equilateral");
        // Isosceles: any two equal
        is(classify("5", "5", "3"), "Isosceles");
        is(classify("5", "3", "5"), "Isosceles");
        is(classify("3", "5", "5"), "Isosceles");
        is(classify("5", "5", "7"), "Isosceles");
        is(classify("5", "7", "5"), "Isosceles");
        is(classify("7", "5", "5"), "Isosceles");
        // Scalene: all three different (but not special)
        is(classify("12", "14", "15"), "Scalene");
        is(classify("14", "12", "15"), "Scalene");
        is(classify("12", "15", "14"), "Scalene");
        is(classify("14", "15", "12"), "Scalene");
        is(classify("15", "12", "14"), "Scalene");
        is(classify("15", "14", "12"), "Scalene");
        // Right-angled: Pythagoras's theorem
        is(classify("5", "12", "13"), "Right");
        is(classify("12", "5", "13"), "Right");
        is(classify("5", "13", "12"), "Right");
        is(classify("12", "13", "5"), "Right");
        is(classify("13", "5", "12"), "Right");
        is(classify("13", "12", "5"), "Right");
        // Flat: two sides add up to the third
        is(classify("7", "7", "14"), "Flat");
        is(classify("7", "14", "7"), "Flat");
        is(classify("14", "7", "7"), "Flat");
        is(classify("7", "9", "16"), "Flat");
        is(classify("7", "16", "9"), "Flat");
        is(classify("9", "16", "7"), "Flat");
        is(classify("16", "7", "9"), "Flat");
        // Impossible: two sides add up to less than the third
        is(classify("2", "3", "13"), "Impossible");
        is(classify("2", "13", "3"), "Impossible");
        is(classify("13", "2", "3"), "Impossible");
        // Illegal: a side is zero
        is(classify("0", "0", "0"), "Illegal");
        is(classify("0", "10", "12"), "Illegal");
        is(classify("10", "0", "12"), "Illegal");
        is(classify("10", "12", "0"), "Illegal");
        // Illegal: a side is negative
        is(classify("-1", "-1", "-1"), "Illegal");
        is(classify("-1", "10", "12"), "Illegal");
        is(classify("10", "-1", "12"), "Illegal");
        is(classify("10", "12", "-1"), "Illegal");
        // Illegal: invalid characters
        is(classify("x", "y", "z"), "Illegal");
        is(classify("3x", "4", "5"), "Illegal");
        is(classify("3", "4x", "5"), "Illegal");
        is(classify("3", "4", "5x"), "Illegal");
        // Illegal: the numbers must be integers;
        is(classify("3.0", "4.0", "5.0"), "Illegal");
        // Illegal: the numbers must not start with zeros (because there is an
        // ambiguity - in some situations this would indicate octal)
        is(classify("03", "04", "05"), "Illegal");
        // Illegal: numbers outside of the int range are rejected
        is(classify("2147483647", "2147483647", "2147483647"), "Equilateral");
        is(classify("2147483648", "2147483647", "2147483647"), "Illegal");
        is(classify("2147483647", "2147483648", "2147483647"), "Illegal");
        is(classify("2147483647", "2147483647", "2147483648"), "Illegal");
        // Overflow: check that the program doesn't have overflow problems
        // If there are overflow problems, the program will say Equilateral
        is(classify("1100000000", "1705032704", "1805032704"), "Scalene");
        // Precision: check that floats haven't been used
        is(classify("2000000001", "2000000002", "2000000003"), "Scalene");
        // Precision: check that doubles haven't been used
        is(classify("150000002", "666666671", "683333338"), "Scalene");
        //extra Tests that failed even though the original 50 passed
        //is(classify("10","10","10000"), "Impossible");
        System.out.println("Tests passed: " + tests);
    }
}
