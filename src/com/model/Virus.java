package model;
public class Virus extends GameObject {
    protected int direction;
    private int version;
    private String type;
    protected int vx;
    private int shotChance = 400;
    private static int destroyedCount = 0;

    public Virus() {
        this(100, 100, "");
    }

    public Virus(int x, int y, String type) {
        super(x, y, "alien-" + type + ".png", 1.5);
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
        changePicture("alien-" + type + ".png");
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

    // Add a method to increment the destroyed count
    public static void incrementDestroyedCount() {
        destroyedCount++;
    }

    // Add a method to get the destroyed count
    public static int getDestroyedCount() {
        return destroyedCount;
    }

}
