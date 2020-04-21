package ie.tudublin;

import ie.tudublin.bugzap.BugZap;

public class Main {
  public void bugZap() {
    String[] a = {"Main"};
    processing.core.PApplet.runSketch(a, new BugZap());
  }

  public void sound1() {
    String[] a = {"Main"};
    processing.core.PApplet.runSketch(a, new Sound1());
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.sound1();
  }
}
