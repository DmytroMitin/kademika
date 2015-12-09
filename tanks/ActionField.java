package tanks;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ActionField extends JPanel {
	
	private final boolean COLORED_MODE = false;

	private BattleField battleField;

	private T34 defender;

	private T34 aggressor;
	
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
		Quadrant quadrant = getQuadrant(bullet.getX(), bullet.getY());

		if (getQuadrant(aggressor.getX(), aggressor.getY()).equals(quadrant)) {
			aggressor.destroy();
			bullet.destroy();
			repaint();
			Thread.sleep(3000);
			aggressor = new T34(getRandomQuadrant(), Direction.RIGHT, this, battleField);
			repaint();
			return true;
		}

		if (!battleField.scan(quadrant).equals(" ")) {
			battleField.update(quadrant, " ");
			System.out.println("QUADRANT CLEANED, vertical: " + quadrant.v + ", horizontal: " + quadrant.h);
			return true;
		}

		return false;
	}
	
	public void processMove(T34 tank) throws InterruptedException {
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
	
	public void processTurn(T34 tank) {
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

		int i = 0;
		Color cc;
		for (int v = 0; v < 9; v++) {
			for (int h = 0; h < 9; h++) {
				if (COLORED_MODE) {
					if (i % 2 == 0) {
						cc = new Color(252, 241, 177);
					} else {
						cc = new Color(233, 243, 255);
					}
				} else {
					cc = new Color(180, 180, 180);
				}
				i++;
				g.setColor(cc);
				g.fillRect(h * 64, v * 64, 64, 64);
			}
		}

		for (int j = 1; j <= battleField.getDimensionY(); j++) {
			for (int k = 1; k <= battleField.getDimensionX(); k++) {
				if (battleField.scanQuadrant(k, j).equals("B")) {
					Coordinates coordinates = getQuadrantXY(k, j);
					int x = coordinates.x;
					int y = coordinates.y;
					g.setColor(new Color(0, 0, 255));
					g.fillRect(x, y, 64, 64);
				}
			}
		}

		if (defender != null) {
			g.setColor(new Color(255, 0, 0));
			g.fillRect(defender.getX(), defender.getY(), 64, 64);
	
			g.setColor(new Color(0, 255, 0));
			if (defender.getDirection() == Direction.UP) {
				g.fillRect(defender.getX() + 20, defender.getY(), 24, 32);
			} else if (defender.getDirection() == Direction.DOWN) {
				g.fillRect(defender.getX() + 20, defender.getY() + 32, 24, 32);
			} else if (defender.getDirection() == Direction.LEFT) {
				g.fillRect(defender.getX(), defender.getY() + 20, 32, 24);
			} else if (defender.getDirection() == Direction.RIGHT) {
				g.fillRect(defender.getX() + 32, defender.getY() + 20, 32, 24);
			}
		}

		if (aggressor != null) {
			T34 tank = aggressor;
			g.setColor(new Color(0, 255, 0));
			g.fillRect(tank.getX(), tank.getY(), 64, 64);

			g.setColor(new Color(255, 0, 0));
			if (tank.getDirection() == Direction.UP) {
				g.fillRect(tank.getX() + 20, tank.getY(), 24, 32);
			} else if (tank.getDirection() == Direction.DOWN) {
				g.fillRect(tank.getX() + 20, tank.getY() + 32, 24, 32);
			} else if (tank.getDirection() == Direction.LEFT) {
				g.fillRect(tank.getX(), tank.getY() + 20, 32, 24);
			} else if (tank.getDirection() == Direction.RIGHT) {
				g.fillRect(tank.getX() + 32, tank.getY() + 20, 32, 24);
			}
		}

		if (bullet != null) {
			g.setColor(new Color(255, 255, 0));
			g.fillRect(bullet.getX(), bullet.getY(), 14, 14);
		}
		
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


	public void processDestroy(T34 tank) {
		repaint();
	}

	public Quadrant getRandomQuadrant() {
		Random random = new Random();
		int randomNumber = random.nextInt(quadrants.length);
		return quadrants[randomNumber];
	}
}
