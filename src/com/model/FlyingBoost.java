package model;


public class FlyingBoost extends GameObject {
    String type;
    private int speed;

    public FlyingBoost(String filename, double scaleSize) {
        super(-50, (int) (Math.random() * 401 + 100), filename + ".png", scaleSize);
        type = filename;
        speed = 7;
    }

    public void move() {
        x += speed;
    }

    public boolean isType(String checkType) {
        return type.equals(checkType);
    }

}



