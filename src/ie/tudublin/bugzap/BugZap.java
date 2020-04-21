package ie.tudublin.bugzap;

import processing.core.PApplet;

public class BugZap extends PApplet {
  public void settings() {
    size(500, 500);
  }

  public void setup() {
    borderWidth = width - padding;
    borderHeight = height - padding;

    bug = new Bug(this, borderWidth, padding);
    player = new Player(this, borderWidth, padding);

    startGame();
  }

  Bug bug;
  Player player;

  float padding = 20;
  float borderWidth, borderHeight;

  void startGame() {
    resetBug();
    resetPlayer();
  }

  void resetBug() {
    bug.bugX = random(bug.halfBugWidth, width - bug.halfBugWidth);
    bug.bugY = 50;
  }

  public void resetPlayer() {
    player.playerX = width / 2;
    player.playerY = height - 50;
  }

  public void hit() {
  }

  public void keyPressed() {
    if (keyCode == LEFT) {
      if (player.playerX > player.halfPlayerWidth) {
        player.playerX -= player.playerSpeed;
      }
    }

    if (keyCode == RIGHT) {
      if (player.playerX < width - player.halfPlayerWidth) {
        player.playerX += player.playerSpeed;
      }
    }

    if (key == ' ') {
      player.fire();
    }

    if (key == 'r') {
      startGame();
    }

  }

  public void draw() {
    background(0);
    fill(255);
    player.display();
    bug.display();
  }
}
