package tanks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ActionField extends JPanel {
	
	private final boolean COLORED_MODE = false;

	private BattleField battleField;

	private Tank tank;
	
	private Bullet bullet;

	public void runTheGame() throws InterruptedException {
//		tank.move();
//		tank.move();
//		tank.fire();
//		tank.move();
//		tank.turn(Direction.DOWN);
//		tank.move();
//		tank.turn(Direction.LEFT);
//		tank.move();
//		tank.turn(Direction.UP);
//		tank.move();

//		tank.moveRandom();

//		tank.moveToQuadrantAndFire(new Quadrant(9,9));
//		tank.moveToQuadrantAndFire(new Quadrant(5,5));
//		tank.moveToQuadrantAndFire(new Quadrant(1,1));

//		tank.fire();
//		tank.move();
//		tank.move();
//		tank.move();
//		tank.move();
//		tank.turn(Direction.DOWN);
//		tank.fire();

		tank.clean();
	}
	
	public boolean processInterception() {
		Quadrant quadrant = getQuadrant(bullet.getX(), bullet.getY());

		if (!battleField.scan(quadrant).equals(" ")) {
			battleField.update(quadrant, " ");
			System.out.println("QUADRANT CLEANED, vertical: " + quadrant.v + ", horizontal: " + quadrant.h);
			return true;
		}
		return false;
	}
	
	public void processMove(Tank tank) throws InterruptedException {
		this.tank = tank;
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
		Direction direction = tank.getDirection();
		Quadrant currentQuadrant = getQuadrant(tank.getX(), tank.getY());
		Quadrant nextQuadrant = getQuadrant(tank.getX() + direction.stepX, tank.getY() + direction.stepY);
		return currentQuadrant.v >= startQuadrant.v + 1 || currentQuadrant.h >= startQuadrant.h + 1
				|| nextQuadrant.v < startQuadrant.v - 1 || nextQuadrant.h < startQuadrant.h - 1;
	}
	
	public void processTurn(Tank tank) {
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
//		String[][] field = {
//				{ " ", " ", " ", "B", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", "B", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//				{ " ", " ", " ", " ", " ", " ", " ", " ", " " },
//		};
		String[][] field = {
				{ "B", "B", "B", "B", "B", "B", "B", "B", "B" },
				{ "B", "B", " ", " ", " ", " ", " ", " ", "B" },
				{ " ", "B", "B", " ", "B", " ", "B", "B", "B" },
				{ " ", "B", "B", " ", " ", " ", "B", "B", "B" },
				{ " ", "B", "B", " ", "B", " ", "B", "B", "B" },
				{ " ", "B", " ", "B", "B", "B", " ", "B", "B" },
				{ " ", "B", " ", " ", " ", " ", " ", "B", "B" },
				{ " ", " ", " ", "B", "B", "B", " ", " ", "B" },
				{ " ", " ", " ", "B", "B", "B", " ", " ", "B" },
		};
		battleField = new BattleField(field);
		tank = new Tank(new Quadrant(1, 1), Direction.RIGHT, this, battleField);
		bullet = new Bullet(-100, -100, Direction.NONE);

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

		if (tank != null) {
			g.setColor(new Color(255, 0, 0));
			g.fillRect(tank.getX(), tank.getY(), 64, 64);
	
			g.setColor(new Color(0, 255, 0));
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


}