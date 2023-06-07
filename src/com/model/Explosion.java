package model;

public class Explosion extends Entity {
	private int time = 35;

	public Explosion(int x, int y) {
		super(x, y, "spark.png", 0.75);
	}

	public Explosion(Entity obj) {
		this(obj.x, obj.y);
	}

	public void move() {
		time--;
	}

	public int time() {
		return time;
	}

}
