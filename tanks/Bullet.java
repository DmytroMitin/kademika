package tanks;

public class Bullet {
	
	public final static int SPEED = 5;
	
	private int x;

	private int y;
	
	private Direction direction;

	public Bullet(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public Bullet(T34 tank) {
		this(tank.getX() + 25 + 7 * tank.getDirection().stepX,
			 tank.getY() + 25 + 7 * tank.getDirection().stepY,
			 tank.getDirection());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void updateX(int step) {
		x += step;
	}

	public void updateY(int step) {
		y += step;
	}

	public void destroy() {
		x = -100;
		y = -100;
	}
	
}
