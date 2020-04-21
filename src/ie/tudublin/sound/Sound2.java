package ie.tudublin.sound;

import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

public class Sound2 extends PApplet {
  public void settings() {
    size(1024, 500);
  }


  public void setup() {
    minim = new Minim(this);
    as = minim.loadSample("HIP.mp3", frameSize);

    fft = new FFT(frameSize, sampleRate);

    colorMode(HSB);
  }

  Minim minim;

  AudioSample as;

  int frameSize = 1024;
  int sampleRate = 44100;

  FFT fft;

  float frameToSecond = sampleRate / (float) frameSize;

  float lerpedWidth = 0;
  float average = 0;
  float offs = 0;

  int countZeroCrossings() {
    int count = 0;

    for (int i = 1; i < as.bufferSize(); i++) {
      // Band before > 0 && band current <= 0
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

  public void drawRainbowBuffer() {
    float circleY = height / 2;
    float sum = 0;

    for (int i = 0; i < as.bufferSize(); i++) {
      stroke(map(i, 0, as.bufferSize(), 0, 255), 255, 255);
      line(i, circleY, i, circleY + as.left.get(i) * circleY);
      sum += abs(as.left.get(i));
    }
  }

  public void drawCircleWidth() {
    float circleY = height / 2;
    float sum = 0;

    for (int i = 0; i < as.bufferSize(); i++) {
      sum += abs(as.left.get(i));
    }

    average = sum / as.bufferSize();

    float width = average * 1000;
    lerpedWidth = lerp(lerpedWidth, width, 0.5f);

    noStroke();

    fill(map(average, 0, 1, 0, 255), 255, 255);

    ellipse(400, circleY, width, width);
    ellipse(600, circleY, lerpedWidth, lerpedWidth);
  }

  public void writeFrequency() {
    stroke(255);

    int count = countZeroCrossings();

    float freq = count * frameToSecond;

    textSize(22);
    text("Zero crossings frequenyCircleY: " + freq, 100, 50);
  }

  public void drawAudioSpectrum() {
    fft.window(FFT.HAMMING);
    fft.forward(as.left);

    stroke(255);

    int highestBin = 0;

    for (int i = 0; i < fft.specSize(); i++) {
      line(i, 0, i, fft.getBand(i) * 100);

      if (fft.getBand(i) > fft.getBand(highestBin)) {
        highestBin = i;
      }
    }

    float freq1 = fft.indexToFreq(highestBin);

    fill(255);
    text("FFT Freq: " + freq1, 100, 100);
  }

  public void draw() {
    background(0);
    drawCircleWidth();
  }
}
