package model;
public class Virus extends Entity {
    protected int direction;
    private int version;
    private int type;
    protected int vx;
    private int shotChance = 400;

    public Virus() {
        this(100, 100, 0);
    }

    public Virus(int x, int y, int type) {
        super(x, y, "alien-blue.png", 1.5);
        this.type = type;
        direction = 1;
        version = 0;
        vx = 5;
    }

    public void move() {
        // change direction and go down when reaches edge of range
        if (Math.abs(x - oX) > range) {
            direction *= -1;
            y += 20;
        }
        // go left/right
        x += vx * direction;

        // toggle through versions of alien type
        version++;
        if (version > 3) {
            version = 0;
        }
        // update picture
        changePicture("alien-blue.png");
    }

    public void respawn() {
        // max = 750, min = 250
        y = -1 * ((int) (Math.random() * 501) + 250);
        vx++;
        shotChance -= 50;
    }

    public boolean shoot() {
        if (y < -10) {
            return false;
        }
        return (int) (Math.random() * (shotChance) + 1) > shotChance - 1;
    }

}
