package model;

public class Blast extends GameObject {
    protected int type;
    private int bombSpeed;
    private int ammoSpeed;
    public Blast(int x, int y, String fileName, int type) {
        super(x - 10, y - 30, fileName + ".png", 1);
        this.type = type;

        ammoSpeed = 15;
        bombSpeed = 10;
    }

    public void move() {
        if (type == 0) {
            y -= ammoSpeed;
        } else if (type == 1) {
            y += bombSpeed;
        }
    }

    public void setBombSpeed(int bombSpeed) {
        this.bombSpeed = bombSpeed;
    }

    public void setAmmoSpeed(int ammoSpeed) {
        this.ammoSpeed = ammoSpeed;
    }

    public boolean hit(GameObject obj) {
        if (obj.y < -10) {
            return false;
        }

        int objX = obj.x;
        int objY = obj.y;
        double objW = obj.width;
        double objH = obj.height;

        if (objX - 5 < this.x) {
            if (objY - 5 < this.y) {
                if (objX + objW + 5 > this.x) {
                    if (objY + objH + 5 > this.y) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
