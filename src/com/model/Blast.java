package model;

public class Blast extends Entity{
    protected int type;
    public Blast(int x, int y, String fileName, int type) {
        super(x - 10, y - 30, fileName + ".png", 2);
        this.type = type;
    }

    public void move() {
        int v = 20;
        if (type == 0) {
            y -= v;
        } else if (type == 1) {
            y += v;
        }
    }

    public boolean hit(Entity obj) {
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
