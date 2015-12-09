package tanks;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ActionField extends JPanel {

	private BattleField battleField;

	private AbstractTank defender;

	private AbstractTank aggressor;
	
	private Bullet bullet;

	private Quadrant[] quadrants;

	public void runTheGame() throws InterruptedException {
		defender.move();
		defender.move();
		defender.turn(Direction.DOWN);
		defender.fire();
		defender.fire();
		defender.fire();

//		defender.move();
//		defender.move();
//		defender.fire();
//		defender.move();
//		defender.turn(Direction.DOWN);
//		defender.move();
//		defender.turn(Direction.LEFT);
//		defender.move();
//		defender.turn(Direction.UP);
//		defender.move();
//		defender.destroy();

//		defender.moveRandom();

//		defender.moveToQuadrantAndFire(new Quadrant(9,9));
//		defender.moveToQuadrantAndFire(new Quadrant(5,5));
//		defender.moveToQuadrantAndFire(new Quadrant(1,1));

//		defender.fire();
//		defender.move();
//		defender.move();
//		defender.move();
//		defender.move();
//		defender.turn(Direction.DOWN);
//		defender.fire();

//		defender.clean();
	}
	
	public boolean processInterception() throws InterruptedException {
		Quadrant bulletQuadrant = getQuadrant(bullet.getX(), bullet.getY());
		Quadrant aggressorQuadrant = getQuadrant(aggressor.getX(), aggressor.getY());

		if (aggressorQuadrant.equals(bulletQuadrant)) {
			aggressor.destroy();
			bullet.destroy();
			repaint();
			Thread.sleep(3000);
			aggressor = new Tiger(getRandomQuadrant(), Direction.RIGHT, this, battleField);
			repaint();
			return true;
		}

		if (!battleField.scan(bulletQuadrant).equals(" ")) {
			battleField.update(bulletQuadrant, " ");
			System.out.println("QUADRANT CLEANED, vertical: " + bulletQuadrant.v + ", horizontal: " + bulletQuadrant.h);
			return true;
		}

		return false;
	}
	
	public void processMove(AbstractTank tank) throws InterruptedException {
		this.defender = tank;
		Quadrant startQuadrant = getQuadrant(tank.getX(), tank.getY());
		Direction direction = tank.getDirection();

		if (startQuadrant.hasEmptyNeighbor(direction, battleField)) {
			while (!moveIsFinished(startQuadrant)) {
				tank.updateX(direction.stepX);
				tank.updateY(direction.stepY);
				repaint();
				Thread.sleep(tank.getSpeed());
			}
			System.out.println("MOVED " + direction.toString());
		} else {
			System.out.println("NO MOVE");
			Thread.sleep(tank.getSpeed());
		}

	}

	private boolean moveIsFinished(Quadrant startQuadrant) {
		Direction direction = defender.getDirection();
		Quadrant currentQuadrant = getQuadrant(defender.getX(), defender.getY());
		Quadrant nextQuadrant = getQuadrant(defender.getX() + direction.stepX, defender.getY() + direction.stepY);
		return currentQuadrant.v >= startQuadrant.v + 1 || currentQuadrant.h >= startQuadrant.h + 1
				|| nextQuadrant.v < startQuadrant.v - 1 || nextQuadrant.h < startQuadrant.h - 1;
	}
	
	public void processTurn(AbstractTank tank) {
		repaint();
	}
	
	public void processFire(Bullet bullet) throws InterruptedException {
		this.bullet = bullet;
		Direction bulletDirection = bullet.getDirection();

		while (true) {
			Quadrant nextQuadrant = getQuadrant(bullet.getX() + bulletDirection.stepX, bullet.getY() + bulletDirection.stepY);
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
	
	public ActionField() {
		String[][] field = {
				{ " ", " ", " ", "B", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", "B", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", "B", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
		};
//		String[][] field = {
//				{ "B", "B", "B", "B", "B", "B", "B", "B", "B" },
//				{ "B", "B", " ", " ", " ", " ", " ", " ", "B" },
//				{ " ", "B", "B", " ", "B", " ", "B", "B", "B" },
//				{ " ", "B", "B", " ", " ", " ", "B", "B", "B" },
//				{ " ", "B", "B", " ", "B", " ", "B", "B", "B" },
//				{ " ", "B", " ", "B", "B", "B", " ", "B", "B" },
//				{ " ", "B", " ", " ", " ", " ", " ", "B", "B" },
//				{ " ", " ", " ", "B", "B", "B", " ", " ", "B" },
//				{ " ", " ", " ", "B", "B", "B", " ", " ", "B" },
//		};
		battleField = new BattleField(field);
		defender = new T34(new Quadrant(1, 1), Direction.RIGHT, this, battleField);
		bullet = new Bullet(-100, -100, Direction.NONE);
		quadrants = new Quadrant[]{new Quadrant(3, 4)};
//		quadrants = new Quadrant[]{new Quadrant(8, 8), new Quadrant(7, 7), new Quadrant(6, 6)};
		aggressor = new Tiger(getRandomQuadrant(), Direction.RIGHT, this, battleField);

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
		repaint();
	}

	public Quadrant getRandomQuadrant() {
		Random random = new Random();
		int randomNumber = random.nextInt(quadrants.length);
		return quadrants[randomNumber];
	}
}
