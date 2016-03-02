package tanks;

import tanks.field.BattleField;
import tanks.tank.AbstractTank;
import tanks.tank.T34;
import tanks.tank.Tiger;

public class ActionField {
	private final BattleField battleField;

	private final AbstractTank defender;

	private final AbstractTank aggressor;

	private AbstractTank currentTank;

	private Bullet bullet;

    public ActionField(BattleField battleField,
                       Quadrant defenderQuadrant, Direction defenderDirection, String defenderType,
                       Quadrant aggressorQuadrant, Direction aggressorDirection)
    {
        this.battleField = battleField;
        if (defenderType.equals("Tiger")) {
            defender = new Tiger(defenderQuadrant, defenderDirection, this, battleField);
        } else {
            defender = new T34(defenderQuadrant, defenderDirection, this, battleField);
        }
        bullet = new Bullet(-100, -100, Direction.NONE);
        aggressor = new Tiger(aggressorQuadrant, aggressorDirection, this, battleField);
        currentTank = defender;
    }

    public AbstractTank getAggressor() {
        return aggressor;
    }

    public AbstractTank getDefender() {
        return defender;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public void runTheGame() throws InterruptedException {
        defender.move();
        defender.fire();
        defender.move();
        defender.fire();
    }

    public Quadrant getOpponentLocation() {
        return getOpponent().getLocation();
    }

	private AbstractTank getOpponent() {
		if (currentTank.equals(defender)) {
			return aggressor;
		} else {
			return defender;
		}
	}

	public boolean processInterception() {
		Quadrant bulletQuadrant = bullet.getLocation();
		AbstractTank opponent = getOpponent();
		Quadrant opponentQuadrant = getOpponentLocation();

		if (opponentQuadrant.equals(bulletQuadrant)) {
			bullet.destroy();
            boolean isDestroyed = opponent.destroy();
            if (isDestroyed) {
                System.out.println("TANK DESTROYED: " + opponent);
            }
            return true;
		}

		if (battleField.scan(bulletQuadrant) != null) {
			battleField.update(bulletQuadrant, null);
			System.out.println("QUADRANT CLEANED, vertical: " + bulletQuadrant.v + ", horizontal: " + bulletQuadrant.h);
			return true;
		}

		return false;
	}
	
	public void processMove(AbstractTank tank) throws InterruptedException {
        currentTank = tank;
        Quadrant startQuadrant = tank.getLocation();
        Direction direction = tank.getDirection();

        if (isLegalMove()) {
            while (!isFinishedMove(startQuadrant)) {
                tank.updateX(direction.stepX);
                tank.updateY(direction.stepY);
                Thread.sleep(tank.getSpeed());
            }
            System.out.println("MOVED " + direction);
        } else {
            System.out.println("NO MOVE, direction: " + direction);
            Thread.sleep(tank.getSpeed());
        }
	}

    public boolean isLegalMove(AbstractTank tank) {
        currentTank = tank;
        return isLegalMove();
    }

    private boolean isLegalMove() {
        Quadrant quadrant = currentTank.getLocation();
        Direction direction = currentTank.getDirection();
        Quadrant neighborQuadrant = quadrant.getNeighbor(direction);
        return quadrant.hasEmptyNeighbor(direction, battleField) && !getOpponentLocation().equals(neighborQuadrant);
    }

    private boolean isFinishedMove(Quadrant startQuadrant) {
        Quadrant currentQuadrant = currentTank.getLocation();
        Direction direction = currentTank.getDirection();
        Quadrant nextQuadrant = ActionField.getQuadrant(currentTank.getX() + direction.stepX,
                                                        currentTank.getY() + direction.stepY);
        // nextQuadrant is not currentQuadrant.getNeighbor !
        return currentQuadrant.v >= startQuadrant.v + 1 || currentQuadrant.h >= startQuadrant.h + 1
                || nextQuadrant.v < startQuadrant.v - 1 || nextQuadrant.h < startQuadrant.h - 1;
    }

	public void processTurn(AbstractTank tank) {
		currentTank = tank;
	}
	
	public void processFire(Bullet bullet, AbstractTank tank) throws InterruptedException {
        this.bullet = bullet;
        this.currentTank = tank;
        Direction bulletDirection = bullet.getDirection();

        while (true) {
            Quadrant nextQuadrant = getQuadrant(bullet.getX() + bulletDirection.stepX,
                                                bullet.getY() + bulletDirection.stepY);

            if (!nextQuadrant.isValid(battleField)) {
                bullet.destroy();
                break;
            }

            bullet.updateX(bulletDirection.stepX);
            bullet.updateY(bulletDirection.stepY);
            Thread.sleep(Bullet.SPEED);

            if (processInterception()) {
                bullet.destroy();
                break;
            }
        }
    }

    public void processDestroy(AbstractTank tank) {
        currentTank = tank;
    }

	public static Coordinates getQuadrantXY(int v, int h) {
		return new Coordinates((v - 1) * 64, (h - 1) * 64);
	}

	public static Coordinates getQuadrantXY(Quadrant quadrant) {
		return getQuadrantXY(quadrant.v, quadrant.h);
	}

	public static Quadrant getQuadrant(int x, int y) {
		return new Quadrant(1 + Math.floorDiv(x, 64), 1 + Math.floorDiv(y, 64));
	}
}