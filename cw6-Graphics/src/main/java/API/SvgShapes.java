package API;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
// import java
public class SvgShapes{
  public SvgShapes(){  }
  public static PolygonExt loadSvg(String filePath, int x , int y){
    PolygonExt nGon = new PolygonExt();
    BufferedReader b;
    String temp;
    ArrayList<Integer> dimensionsX = new ArrayList<Integer>();
    ArrayList<Integer> dimensionsY = new ArrayList<Integer>();
    try{
      b = new BufferedReader(new FileReader(filePath));
      while((temp = b.readLine()) != null){
        temp=temp.trim();
        getPoint("x:",temp,dimensionsX);
        getPoint("y:",temp,dimensionsY);
      }
      for(int i=0; i<dimensionsY.size(); i++){
        nGon.addPoint(2*Double.valueOf(dimensionsX.get(i)).intValue(),
                      2*Double.valueOf(dimensionsY.get(i)).intValue());
      }
    }catch(Exception e){}
    nGon.translate(x,y);
    return nGon;
  }
  private static void getPoint(String ident, String line, ArrayList<Integer> l){
    if(line.startsWith(ident)){
      line = line.replace(ident,"");
      l.add(Double.valueOf(line).intValue());
    }
  }

}
