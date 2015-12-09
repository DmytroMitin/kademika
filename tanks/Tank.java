package tanks;

public class Tank extends AbstractTank {
	public Tank(int x , int y, Direction direction, ActionField af, BattleField bf) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.af = af;
		this.bf = bf;
	}

	public Tank(Quadrant quadrant, Direction direction, ActionField af, BattleField bf) {
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
	public void turn(Direction direction) {
		this.direction = direction;
		af.processTurn(this);
	}

	@Override
	public void move() throws InterruptedException {
		af.processMove(this);
	}

	@Override
	public void fire () throws InterruptedException {
		Bullet bullet = new Bullet(this);
		af.processFire(bullet);
	}

	public void moveAndFire() throws InterruptedException {
		Quadrant currentQuadrant = ActionField.getQuadrant(x,y);
		Quadrant nextQuadrant = new Quadrant(currentQuadrant.v + direction.stepX,
				                             currentQuadrant.h + direction.stepY);
		if (!bf.scan(nextQuadrant).equals(" ")) {
			fire();
		}
		move();
	}

	public void moveRandom() throws InterruptedException {
//		Random random = new Random();
		while (true) {
//			int randomNumber = random.nextInt(4);
			int randomNumber = (int)(System.currentTimeMillis() % 4);
			Direction randomDirection = Direction.values()[randomNumber];
			turn(randomDirection);
			move();
		}
	}

	public void moveToQuadrantAndFire(Quadrant quadrant) throws InterruptedException {
		Quadrant currentQuadrant = ActionField.getQuadrant(x,y);

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
		for (int i = 1; i <= bf.getDimensionY(); i++) {
			if (i % 2 != 0) {
				for (int j = 1; j <= bf.getDimensionX(); j++) {
					if (!bf.scanQuadrant(j,i).equals(" ")) {
						moveToQuadrantAndFire(new Quadrant(j,i));
					}
				}
			} else {
				for (int j = bf.getDimensionX(); j >= 1; j--) {
					if (!bf.scanQuadrant(j,i).equals(" ")) {
						moveToQuadrantAndFire(new Quadrant(j,i));
					}
				}
			}
		}
	}

	@Override
	public void destroy() {
		x = - 100;
		y = - 100;
		direction = Direction.NONE;
		af.processDestroy(this);
	}

}
