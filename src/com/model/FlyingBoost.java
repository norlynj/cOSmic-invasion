package model;


public class FlyingBoost extends Entity {
    String type;

    public FlyingBoost(String filename, double scaleSize) {
        super(-50, (int) (Math.random() * 401 + 100), filename + ".png", scaleSize);
        type = filename;
    }

    public void move() {
        x += 10;
    }

    public boolean isType(String checkType) {
        return type.equals(checkType);
    }

}



