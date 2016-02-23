package tanks;

import tanks.field.BattleField;
import tanks.tank.AbstractTank;
import tanks.tank.T34;
import tanks.tank.Tiger;

import javax.swing.*;
import java.awt.*;

public class ActionField extends JPanel {

	private BattleField battleField;

	private AbstractTank defender;

	private AbstractTank aggressor;

	private AbstractTank currentTank;
	
	private Bullet bullet;

	public void runTheGame() throws InterruptedException {
		aggressor.destroyOpponent();
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

	public boolean processInterception() throws InterruptedException {
		Quadrant bulletQuadrant = bullet.getLocation();
		AbstractTank opponent = getOpponent();
		Quadrant opponentQuadrant = getOpponentLocation();

		if (opponentQuadrant.equals(bulletQuadrant)) {
			bullet.destroy();
			opponent.destroy();
			repaint();
            System.out.println("TANK DESTROYED: " + opponent);
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
		Quadrant startQuadrant = getQuadrant(tank.getX(), tank.getY());
		Direction direction = tank.getDirection();

		if (moveIsLegal()) {
			while (!moveIsFinished(startQuadrant)) {
				tank.updateX(direction.stepX);
				tank.updateY(direction.stepY);
				repaint();
				Thread.sleep(tank.getSpeed());
			}
			System.out.println("MOVED " + direction);
		} else {
			System.out.println("NO MOVE, direction: " + direction);
			Thread.sleep(tank.getSpeed());
		}

	}

    public boolean moveIsLegal(AbstractTank tank) {
        currentTank = tank;
        return moveIsLegal();
    }

    private boolean moveIsLegal() {
        Quadrant quadrant = currentTank.getLocation();
        Direction direction = currentTank.getDirection();
        Quadrant neighborQuadrant = quadrant.getNeighbor(direction);
        return quadrant.hasEmptyNeighbor(direction, battleField) && !getOpponentLocation().equals(neighborQuadrant);
    }

	private boolean moveIsFinished(Quadrant startQuadrant) {
		Quadrant currentQuadrant = currentTank.getLocation();
        Direction direction = currentTank.getDirection();
		Quadrant nextQuadrant = getQuadrant(currentTank.getX() + direction.stepX, currentTank.getY() + direction.stepY);
        // nextQuadrant is not currentQuadrant.getNeighbor !
		return currentQuadrant.v >= startQuadrant.v + 1 || currentQuadrant.h >= startQuadrant.h + 1
				|| nextQuadrant.v < startQuadrant.v - 1 || nextQuadrant.h < startQuadrant.h - 1;
	}
	
	public void processTurn(AbstractTank tank) {
		currentTank = tank;
		repaint();
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
				repaint();
				break;
			}
			bullet.updateX(bulletDirection.stepX);
			bullet.updateY(bulletDirection.stepY);
			repaint();
			Thread.sleep(Bullet.SPEED);
			if (processInterception()) {
				bullet.destroy();
				repaint();
				break;
			}
		}
	}
	
	public ActionField(BattleField battleField, Quadrant defenderQuadrant, Direction defenderDirection,
                                                Quadrant aggressorQuadrant, Direction aggressorDirection)
    {
		this.battleField = battleField;
		defender = new T34(defenderQuadrant, defenderDirection, this, battleField);
		bullet = new Bullet(-100, -100, Direction.NONE);
		aggressor = new Tiger(aggressorQuadrant, aggressorDirection, this, battleField);
		currentTank = defender;

		JFrame frame = new JFrame("BATTLE FIELD");
		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(battleField.getWidth() + 16, battleField.getHeight() + 39));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		battleField.draw(g);

		defender.draw(g);

		aggressor.draw(g);

		bullet.draw(g);
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


	public void processDestroy(AbstractTank tank) {
		currentTank = tank;
        repaint();
	}

//	public Quadrant getRandomQuadrant() {
//		Random random = new Random();
//		int randomNumber = random.nextInt(quadrants.length);
//		return quadrants[randomNumber];
//	}
}
