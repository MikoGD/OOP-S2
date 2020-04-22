package ie.tudublin.starmap;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class StarMap extends PApplet {
  public void settings() {
    size(1000, 1000);

  }

  public void setup() {
    loadData();
    printStars();
  }

  // Arraylist can grow and shrink
  // Generic
  ArrayList<Star> starsList = new ArrayList<Star>();

  float[] point1 = {0, 0};
  float[] point2 = {0, 0};
  boolean isMousePressed = false;

  public void drawStars() {
    for (Star star : starsList) {
      star.render(this);
    }
  }

  public void drawGrid() {
    float border = width * 0.05f;

    stroke(0, 0, 255);

    textAlign(CENTER, CENTER);

    for (int i = -5; i <= 5; i++) {
      float x = map(i, -5, 5, border, width - border);

      line(x, border, x, height - border);
      line(border, x, width - border, x);

      fill(255);
      text(i, x, border / 2);
      text(i, border / 2, x);
    }
  }

  public void loadData() {
    Table table = loadTable("HabHYG15ly.csv", "header");

    for (TableRow tableRow : table.rows()) {
      Star star = new Star(tableRow);
      starsList.add(star);
    }
  }

  public void printStars() {
    for (Star star : starsList) {
      println(star);
    }
  }

  public void mousePressed() {
    point1[0] = pmouseX;
    point1[1] = pmouseY;
  }

  public void mouseDragged() {
    point2[0] = mouseX;
    point2[1] = mouseY;
  }

  public void mouseReleased() {
    point2[0] = pmouseX;
    point2[1] = pmouseY;
  }

  public void draw() {
    background(0);

    drawGrid();
    drawStars();

    stroke(255, 255, 0);
    line(point1[0], point1[1], point2[0], point2[1]);
  }

}
