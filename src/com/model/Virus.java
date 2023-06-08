package model;

public class Virus extends GameObject {
    protected int direction;
    private int version;
    private String type;
    protected int vx;
    private int shotChance = 400;
    private static int destroyedCount = 0;
    private int shotsRequired; // New instance variable
    private boolean alive;

    public Virus() {
        this(100, 100, "");
    }

    public Virus(int x, int y, String type) {
        super(x, y, "alien-" + type + ".png", 1.5);
        this.type = type;
        direction = 1;
        version = 0;
        vx = 5;
        shotsRequired = determineShotsRequired(type);
        alive = true;
    }

    public void move() {
        // Change direction and go down when reaches edge of range
        if (Math.abs(x - oX) > range) {
            direction *= -1;
            y += 20;
        }
        // Go left/right
        x += vx * direction;

        // Toggle through versions of alien type
        version++;
        if (version > 3) {
            version = 0;
        }
        // Update picture
        changePicture("alien-" + type + ".png");
    }

    public void respawn() {
        // Max = 750, min = 250
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

    private int determineShotsRequired(String type) {
        return switch (type) {
            case "red", "orange" -> 3;
            case "yellow", "green" -> 2;
            default -> 1; // Default shots required
        };
    }

    public int getShotsRequired() {
        return shotsRequired;
    }

    public void hit() {
        shotsRequired -= 1;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
