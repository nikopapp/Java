package API;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.awt.*;

public class APItest{
  protected GUI gui;
  protected Explain ex;
  protected ColorCombination cc;
  private Graphics2D g;
  @Before
  public void setUp(){
       gui = new GUI();
       ex = new Explain();
       cc = new ColorCombination();
  }

  @Test
  public void isRunning(){
    assertEquals(gui.getSceneCnt(),0);
  }
  @Test
  public void hasChanged(){

  }


}
