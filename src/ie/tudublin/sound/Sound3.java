package ie.tudublin.sound;

import processing.core.PApplet;
import ddf.minim.*;

public class Sound3 extends PApplet {
  public void settings() {
    size(1024, 500);
  }

  public void setup() {
    minim = new Minim(this);
    as = minim.loadSample("Silver For Monsters.mp3", frameSize);
    colorMode(HSB);
    lerpedCY = height / 2;
  }

  Minim minim;
  AudioSample as;

  int frameSize = 1024;

  float frameToSecond = 44100 / (float) frameSize;

  float lerpedw = 0;
  float lerpedY = 0;
  float lerpedX = 0;
  float lerpedCY = 0;
  float average = 0;

  float offs = 0;

  int countZeroCrossings() {
    int count = 0;

    for (int i = 1; i < as.bufferSize(); i++) {
      if (as.left.get(i - 1) > 0 && as.left.get(i) <= 0) {
        count++;
      }
    }
    return count;
  }

  public void keyPressed() {
    if (key == ' ') {
      as.stop();
      as.trigger();
    }

  }


  public void circleVisual() {
    strokeWeight(2);

    float circleX = width / 2;
    float circleY = height / 2;

    float sum = 0;

    for (int i = 0; i < as.bufferSize(); i++) {
      float theta = map(i, 0, as.bufferSize(), 0, TWO_PI);

      float x = circleX + sin(theta) * circleX * abs(as.left.get(i));
      float y = circleY + cos(theta) * circleX * abs(as.left.get(i));

      lerpedX = lerp(lerpedX, x, 0.1f);
      lerpedY = lerp(lerpedY, y, 0.1f);

      stroke(map(i + offs, 0, as.bufferSize(), 0, 255) % 255, 255, 255);

      line(circleX, circleY, lerpedX, lerpedY);

      sum += abs(as.left.get(i));
    }
    // offs += mouseY;
    average = sum / as.bufferSize();
    offs += average * 10f;

    noStroke();
    fill(0);
    ellipse(width / 2, height / 2, 100, 100);

    fill(255);
    textSize(22);
    textAlign(CENTER, CENTER);
    text("HIP", width / 2, height / 2);
    // System.out.printf("average: %f, sum: %f, offset: %f\n", average, sum, offs);
  }

  public void draw() {
    background(0);

    circleVisual();
  }
}
