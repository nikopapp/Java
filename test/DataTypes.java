import java.util.*;

class DataTypes
{
  public static void main(String args[])
  {
    List<Integer> lint = new ArrayList<Integer>();
    lint.add(3);
    lint.add(4);
    lint.add(9);
    for(int x: lint){
      System.out.println(x);
    }
    TreeNode<Integer> tint = new TreeNode<Integer>();
    System.out.println(lint.isEmpty());
    byte x=1;
    System.out.println(x);
  }
}
