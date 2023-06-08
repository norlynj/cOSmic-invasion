package model;

public class Tux extends GameObject {
    protected int vx;
    protected int hits;
    protected boolean shooting;
    protected int[] cooldown = { 0, 50 };
    protected int kills;
    protected int lives;
    protected int shotsFired;

    public Tux(int x, int y) {
        super(x, y, "Tux.png", 1);
        vx = 0;
        hits = 0;
        shooting = false;

        range += 50;

        kills = 0;
        lives = 5;
        shotsFired = 0;
    }

    public void move() {
        if (x - oX > range) { // left side
            x = oX + range;
        } else if (x - oX < -range) { // right side
            x = oX - range;
        }

        x += vx * 25;

        if (hits > 0) {
            changePicture("Tux.png");
        }

        if (cooldown[0] > 0) {
            cooldown[0]--;
        }
    }

    /*
     * -1: left, 0: stop 1: right
     */
    public void motion(int key) {
        vx = key;
    }

    public void hit() {
        hits++;
        lives--;
    }

    public boolean checkShot() {
        if (shooting && cooldown[0] == 0) {
            cooldown[0] = cooldown[1];
            return true;
        }
        return false;
    }

    public void decreaseCooldown() {
        if (cooldown[1] > 1) {
            cooldown[1] /= 2;
        }
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public int[] getCooldown() {
        return cooldown;
    }

    public int getKills() {
        return kills;
    }

    public void increaseKills() {
        kills++;
    }

    public int lives() {
        return lives;
    }

    public void addLife(int updown) {
        this.lives += 1 * updown;
    }

    public int vx() {
        return vx;
    }

    public void reset() {
        lives = 3;
    }


}
