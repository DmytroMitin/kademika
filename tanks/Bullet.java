package tanks;

import java.awt.*;

public class Bullet implements Drowable, Destroyable {
	
	public final static int SPEED = 5;
	
	private int x;

	private int y;
	
	private Direction direction;

	public Bullet(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public Bullet(AbstractTank tank) {
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

	@Override
	public void destroy() {
		x = -100;
		y = -100;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 0));
		g.fillRect(x, y, 14, 14);
	}
}
