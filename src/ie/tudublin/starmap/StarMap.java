package ie.tudublin.starmap;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class StarMap extends PApplet {
  public void settings() {
    size(1000, 1000);
    smooth(8);
  }

  public void setup() {
    border = width * 0.05f;

    loadData();
    printStars();
  }

  // Arraylist can grow and shrink
  // Generic
  ArrayList<Star> starsList = new ArrayList<Star>();
  ArrayList<Star> stars = new ArrayList<Star>();

  float[] point1 = {0, 0};
  float[] point2 = {0, 0};

  float border = 0;

  public void drawStars() {
    for (Star star : starsList) {
      star.render(this);
    }
  }

  public void drawGrid() {
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

  public Star getStar() {
    Star star = null;
    float x, y, xG, yG;

    for (Star currStar : starsList) {
      xG = currStar.getxG();
      yG = currStar.getyG();

      x = map(xG, -5, 5, border, width - border);
      y = map(yG, -5, 5, border, height - border);

      if (pmouseX >= x - 5 && pmouseX <= x + 5 && pmouseY >= y - 5 && pmouseY <= y + 5) {
        return currStar;
      }
    }

    return star;
  }

  public void displayDistance() {
    Star star1 = stars.get(0);
    Star star2 = stars.get(1);
    String display;

    float starsDistance = dist(star1.getxG(), star1.getyG(), star1.getzG(), star2.getxG(),
        star2.getyG(), star2.getzG());

    textAlign(LEFT, CENTER);
    fill(255);

    display = "Distance from " + star1.getDisplayName() + " to " + star2.getDisplayName() + " is "
        + starsDistance + " parsecs";

    text(display, border, height - (border / 2));
    // text(display, width / 2, height / 2);
  }

  public void mousePressed() {
    Star star = getStar();
    stars.clear();

    point1[0] = pmouseX;
    point1[1] = pmouseY;
    point2[0] = pmouseX;
    point2[1] = pmouseY;

    if (star != null) {
      stars.add(getStar());
    } else {
      stars.clear();
    }
  }

  public void mouseDragged() {
    point2[0] = pmouseX;
    point2[1] = pmouseY;
  }

  public void mouseReleased() {
    Star star = getStar();
    point2[0] = pmouseX;
    point2[1] = pmouseY;

    if (star != null) {
      stars.add(getStar());
    } else {
      stars.clear();
    }
  }

  public void draw() {
    background(0);

    drawGrid();
    drawStars();

    stroke(255, 255, 0);
    line(point1[0], point1[1], point2[0], point2[1]);

    if (stars.size() == 2) {
      displayDistance();
    }
  }

}
