package ie.tudublin;

import ie.tudublin.bugzap.BugZap;
import ie.tudublin.sound.*;
import ie.tudublin.starmap.*;

public class Main {
  public void bugZap() {
    String[] a = {"Main"};
    processing.core.PApplet.runSketch(a, new BugZap());
  }

  public void sound(String name) {
    String[] a = {"Main"};

    if (name == "sound1") {
      processing.core.PApplet.runSketch(a, new Sound1());
    } else if (name == "sound2") {
      processing.core.PApplet.runSketch(a, new Sound2());
    } else if (name == "sound3") {
      processing.core.PApplet.runSketch(a, new Sound3());
    }
  }

  public void starMap() {
    String[] a = {"Main"};
    processing.core.PApplet.runSketch(a, new StarMap());
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.starMap();
  }
}
