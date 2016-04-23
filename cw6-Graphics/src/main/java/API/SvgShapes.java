package API;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.*;

public class SvgShapes{
  public SvgShapes(){  }
  public PolygonExt loadSvg(String filePath, int x , int y){
    PolygonExt nGon = new PolygonExt();
    BufferedReader b;
    // URL file = getClass().getClassLoader().getResource(filePath);
    // InputStream resource;
    String temp;
    ArrayList<Integer> dimensionsX = new ArrayList<Integer>();
    ArrayList<Integer> dimensionsY = new ArrayList<Integer>();
    try{
      // resource = file.openStream();
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
  public PolygonExt loadSvg(URL filePath, int x , int y){
    PolygonExt nGon = new PolygonExt();
    BufferedReader b;
    InputStream resource;
    String temp;
    ArrayList<Integer> dimensionsX = new ArrayList<Integer>();
    ArrayList<Integer> dimensionsY = new ArrayList<Integer>();
    try{
      resource = filePath.openStream();
      b = new BufferedReader(new InputStreamReader(resource));
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
