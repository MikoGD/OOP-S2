package ie.tudublin;

import processing.core.PApplet;

public class Main {
  public void bugZap() {
    String[] a = { "Main" };
    processing.core.PApplet.runSketch(a, new BugZap());
  }

  public static void main(String[] args) {
    Main main = new Main();
  }
}