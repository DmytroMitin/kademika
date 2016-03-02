package tanks.tank;

import tanks.*;
import tanks.field.BattleField;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class AbstractTank implements Drowable, Destroyable {
	protected int speed = 20;

	protected int x;

	protected int y;

	protected Direction direction;

	protected ActionField af;

	protected BattleField bf;

	protected Color tankColor;

	protected Color towerColor;

    protected String filePrefix = "bin/tanks/img/tank";

	public AbstractTank(int x , int y, Direction direction, ActionField af, BattleField bf) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.af = af;
		this.bf = bf;
	}

	public AbstractTank(Quadrant quadrant, Direction direction, ActionField af, BattleField bf) {
		this(ActionField.getQuadrantXY(quadrant).x,
			 ActionField.getQuadrantXY(quadrant).y,
			 direction,
			 af,
			 bf);
	}

	public int getSpeed() {
		return speed;
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

	public void turn(Direction direction) {
        this.direction = direction;
		af.processTurn(this);
	}

	public void move() throws InterruptedException{
		af.processMove(this);
	}

	public void fire () throws InterruptedException {
		Bullet bullet = new Bullet(this);
		af.processFire(bullet, this);
	}

	public void moveAndFire() throws InterruptedException {
        Quadrant quadrant = getLocation();
		Quadrant nextQuadrant = quadrant.getNeighbor(direction);
        while (nextQuadrant.isValid(bf) && !af.isLegalMove(this)) {
			fire();
		}
		move();
	}

	public void moveRandom() throws InterruptedException {
		while (true) {
			turn(Direction.getRandomDirection());
			move();
		}
	}

    public void moveRandomAndFire() throws InterruptedException {
        while (true) {
            turn(Direction.getRandomDirection());
            moveAndFire();
        }
    }

	public void moveToQuadrantAndFire(Quadrant quadrant) throws InterruptedException {
        Quadrant currentQuadrant = getLocation();

		int differenceV = quadrant.v - currentQuadrant.v;
		Direction directionV = Direction.getByStepX((int)Math.signum(differenceV));

		turn(directionV);
		for (int i = 0; i < Math.abs(differenceV); i++) {
			moveAndFire();
		}

		int differenceH = quadrant.h - currentQuadrant.h;
		Direction directionH = Direction.getByStepY((int)Math.signum(differenceH));

		turn(directionH);
		for (int i = 0; i < Math.abs(differenceH); i++) {
			moveAndFire();
		}
	}

	public void clean() throws InterruptedException {
		for (int h = 1; h <= bf.getDimensionY(); h++) {
			if (h % 2 != 0) {
				for (int v = 1; v <= bf.getDimensionX(); v++) {
					if (bf.scanQuadrant(v,h) != null) {
						moveToQuadrantAndFire(new Quadrant(v,h));
					}
				}
			} else {
				for (int v = bf.getDimensionX(); v >= 1; v--) {
					if (bf.scanQuadrant(v,h) != null) {
						moveToQuadrantAndFire(new Quadrant(v,h));
					}
				}
			}
		}
	}

	public void attackEagle() throws InterruptedException {
        moveToQuadrantAndFire(bf.findEagle());
    }

    public void destroyOpponent() throws InterruptedException {
        Quadrant quadrant;
        do {
            quadrant = af.getOpponentLocation();
            moveToQuadrantAndFire(quadrant);
        } while (quadrant.isValid(bf));
    }

    @Override
    public void draw(Graphics graphics) {
        try {
            File file = new File(filePrefix + "_" + direction.name() + ".jpg");
            graphics.drawImage(ImageIO.read(file), x, y, null);
        } catch (IOException e) {
            e.printStackTrace();

            graphics.setColor(tankColor);
            graphics.fillRect(x, y, 64, 64);

            graphics.setColor(towerColor);
            if (getDirection() == Direction.UP) {
                graphics.fillRect(x + 20, y, 24, 32);
            } else if (getDirection() == Direction.DOWN) {
                graphics.fillRect(x + 20, y + 32, 24, 32);
            } else if (getDirection() == Direction.LEFT) {
                graphics.fillRect(x, y + 20, 32, 24);
            } else if (getDirection() == Direction.RIGHT) {
                graphics.fillRect(x + 32, y + 20, 32, 24);
            }
        }
    }

	@Override
	public boolean destroy() {
		x = - 100;
		y = - 100;
		direction = Direction.NONE;
		af.processDestroy(this);
		return true;
	}
}
