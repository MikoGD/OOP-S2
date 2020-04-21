package ie.tudublin.bugzap;

import processing.core.PApplet;

public class Bug {

  public Bug(PApplet pa, float width, float padding) {
    this.p = pa;
    this.width = width;
    this.padding = padding;
    this.height = width;
  }

  public Bug(PApplet pa, float width, float height, float padding) {
    this.p = pa;
    this.width = width;
    this.padding = padding;
    this.height = height;
  }

  protected final PApplet p;

  // Bug attributes
  float bugX, bugY;
  float bugWidth = 30;
  float halfBugWidth = bugWidth / 2;
  float bugSpeed = 5;
  float bugDirection = 1;

  // Screen attributes
  float width, height, padding;

  void drawBug(float x, float y) {
    // Draw the bug
    p.stroke(255);
    float saucerHeight = bugWidth * 0.7f;
    p.line(x, y - saucerHeight, x - halfBugWidth, y);
    p.line(x, y - saucerHeight, x + halfBugWidth, y);
    p.line(x - halfBugWidth, y, x - halfBugWidth, y);
    p.line(x - halfBugWidth, y, x + halfBugWidth, y);
    float feet = bugWidth * 0.1f;
    p.line(x - feet, y, x - halfBugWidth, y + halfBugWidth);
    p.line(x + feet, y, x + halfBugWidth, y + halfBugWidth);

    feet = bugWidth * 0.3f;
    p.line(x - feet, y, x - halfBugWidth, y + halfBugWidth);
    p.line(x + feet, y, x + halfBugWidth, y + halfBugWidth);

    float eyes = bugWidth * 0.1f;
    p.line(x - eyes, y - eyes, x - eyes, y - eyes * 2f);
    p.line(x + eyes, y - eyes, x + eyes, y - eyes * 2f);
  }

  public void moveBug() {
    if ((p.frameCount % 10) == 0) {
      bugX = bugX + (bugSpeed * bugDirection);

      if (bugX > width) {
        bugDirection = -1;
      } else if (bugX < padding) {
        bugDirection = 1;
      }

      bugY++;
    }
  }

  public void display() {
    drawBug(bugX, bugY);
    moveBug();
  }
}
