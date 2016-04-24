package API;

import java.util.*;
public class CompRunnable implements Runnable {

  private final ArrayList<Runnable> runnables;

  public CompRunnable(ArrayList<Runnable> runnables) {
      this.runnables = runnables;
  }

  public void run() {
      for (Runnable runnable : runnables) {
          runnable.run();
      }
  }

  public static Runnable combine(ArrayList<Runnable> runnables) {
      return new CompRunnable(runnables);
  }
}
