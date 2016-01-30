package tanks;

import tanks.tank.AbstractTank;

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

	public Quadrant getLocation() {
        return ActionField.getQuadrant(getX(), getY());
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
	public boolean destroy() {
		x = -100;
		y = -100;
		return true;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.YELLOW);
		graphics.fillRect(x, y, 14, 14);
	}
}
