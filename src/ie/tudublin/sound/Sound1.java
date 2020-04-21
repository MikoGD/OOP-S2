package ie.tudublin.sound;

import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class Sound1 extends PApplet {
  public void settings() {
    size(1024, 500);
  }

  public void setup() {
    minim = new Minim(this);
    ai = minim.getLineIn(Minim.MONO, width, 44100, 16);

    colorMode(HSB);
    circleY = height / 2;
    lerpedCircleY = circleY;

    fft = new FFT(frameSize, sampleRate);

  }

  Minim minim;
  AudioInput ai;

  FFT fft;

  int frameSize = 1024;
  int sampleRate = 44100;

  float offs = 0;

  float circleY;
  float lerpedCircleY;
  float lerpedCircleWidth = 0;

  public void drawRainbowSpectrum() {
    stroke(255);

    float colorY = height / 2;

    // Get colors for middle wave
    for (int i = 0; i < ai.bufferSize(); i++) {
      stroke(map(i, 0, ai.bufferSize(), 0, 255), 255, 255);
      line(i, colorY, i, colorY + ai.left.get(i) * colorY);
    }
  }

  public void drawCircleWidths() {
    stroke(255);

    float colorY = height / 2;
    float sum = 0;

    // Get colors for middle wave
    for (int i = 0; i < ai.bufferSize(); i++) {
      sum += abs(ai.left.get(i));
    }

    // Average of audio channel
    float average = sum / ai.bufferSize();
    float circleDiameter = average * 1000;

    lerpedCircleWidth = lerp(lerpedCircleWidth, circleDiameter, 0.2f);

    noStroke();
    fill(map(average, 0, 1, 0, 255), 255, 255);

    /*
     * 2 Ellipses are drawn, one with a lerped diameter and one with non lerped The one with non
     * lerped spikes suddenly and is more sporadic. The one with lerped is more smooth but also
     * smaller.
     */

    ellipse(400, colorY, circleDiameter, circleDiameter);
    ellipse(600, colorY, lerpedCircleWidth, lerpedCircleWidth);
  }

  public void drawCircleY() {
    // Another exampled of lerped with Y position of circle
    circleY += random(-20, 20);
    lerpedCircleY = lerp(lerpedCircleY, circleY, 0.1f);

    fill(134, 255, 255);

    ellipse(100, circleY, 50, 50);
    ellipse(200, lerpedCircleY, 50, 50);
  }

  public void drawAudioSpectrum() {
    // Draws the audio sample from the time domain to a frequency domain
    fft.window(FFT.HAMMING);
    fft.forward(ai.left);

    stroke(255);

    for (int i = 0; i < fft.specSize(); i++) {
      line(i, 0, i, fft.getBand(i) * 100);
    }
  }

  public void draw() {
    background(0);
    drawAudioSpectrum();
  }
}
