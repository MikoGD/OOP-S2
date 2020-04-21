package ie.tudublin.bugzap;

import processing.core.PApplet;

public class Player {

  public Player(PApplet p, float width, float padding) {
    this.p = p;
    this.width = width;
    this.padding = padding;
    this.height = width;
  }

  public Player(PApplet p, float width, float height, float padding) {
    this.p = p;
    this.width = width;
    this.padding = padding;
    this.height = height;
  }

  protected final PApplet p;

  // Player attributes
  float playerX, playerY;
  float playerWidth = 40;
  float playerHeight = playerWidth / 2;
  float halfPlayerWidth = playerWidth / 2;
  float playerSpeed = 5;

  // Bullet and clip attributes
  int clipSize = 50;
  int bulletsFired = 0;
  int leadBullet = 0;

  Bullet bullet;
  Bullet clip[] = new Bullet[clipSize];

  boolean firing = false;

  // Screen attributes
  float width, height, padding;

  public void drawPlayer(float x, float y) {
    p.stroke(255);
    p.line(x - halfPlayerWidth, y + playerHeight, x + halfPlayerWidth, y + playerHeight);
    p.line(x - halfPlayerWidth, y + playerHeight, x - halfPlayerWidth, y + playerHeight * 0.5f);
    p.line(x + halfPlayerWidth, y + playerHeight, x + halfPlayerWidth, y + playerHeight * 0.5f);

    p.line(x - halfPlayerWidth, y + playerHeight * 0.5f, x - (halfPlayerWidth * 0.8f),
        y + playerHeight * 0.3f);
    p.line(x + halfPlayerWidth, y + playerHeight * 0.5f, x + (halfPlayerWidth * 0.8f),
        y + playerHeight * 0.3f);

    p.line(x - (halfPlayerWidth * 0.8f), y + playerHeight * 0.3f, x + (halfPlayerWidth * 0.8f),
        y + playerHeight * 0.3f);

    p.line(x, y, x, y + playerHeight * 0.3f);
  }

  public void fire() {
    if (firing == false) {
      bullet = new Bullet(playerX, playerY);

      clip[bulletsFired] = bullet;

      bulletsFired = (bulletsFired + 1) % clipSize;
    }
  }

  public void display() {
    if (bulletsFired > 0) {
      firing = true;

      drawPlayer(playerX, playerY);

      leadBullet = bullet.moveBullet(bulletsFired, leadBullet, clip, clipSize);

      System.out.println(bulletsFired + ", " + leadBullet);

      bullet.drawBullet(bullet.bulletX, bullet.bulletY, bulletsFired, leadBullet, clip);

      firing = false;
    } else {
      drawPlayer(playerX, playerY);
    }
  }

  private class Bullet {
    public Bullet(float bulletX, float bulletY) {
      this.bulletX = bulletX;
      this.bulletY = bulletY;
    }

    float bulletX, bulletY;
    float bulletSpeed = 5;
    int bulletHeight = 4;

    public void drawBullet(float bulletX, float bulletY, int bulletsFired, int leadBullet,
        Bullet clip[]) {
      p.stroke(255);

      if (bulletsFired < leadBullet) {
        bulletsFired = clip.length;
      }

      for (int i = leadBullet; i < bulletsFired; i++) {
        p.line(clip[i].bulletX, clip[i].bulletY, clip[i].bulletX, clip[i].bulletY + bulletHeight);
      }
    }

    public int moveBullet(int bulletsFired, int leadBullet, Bullet clip[], int clipSize) {
      if (bulletsFired < leadBullet) {
        bulletsFired = clip.length;
      }

      for (int i = leadBullet; i < bulletsFired; i++) {
        clip[i].bulletY -= bulletSpeed;

        if (clip[i].bulletY < padding) {
          clip[i] = null;
          return ((leadBullet + 1) % clipSize);
        }
      }

      return leadBullet;
    }
  }
}
