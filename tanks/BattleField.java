package tanks;

import java.awt.*;

public class BattleField implements Drowable {

	private final boolean COLORED_MODE = false;

	private final int width;
	
	private final int height;
	
	private String[][] battleField;

	public BattleField(String[][] battleField) {
		this.battleField = battleField;
		width = battleField[0].length * 64;
		height = battleField.length * 64;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDimensionX() {
		return width / 64;
	}
	
	public int getDimensionY() {
		return height / 64;
	}

	public String scanQuadrant(int v, int h) {
		return battleField[h - 1][v - 1];
	}

	public String scan(Quadrant quadrant) {
		return scanQuadrant(quadrant.v, quadrant.h);
	}

	public void updateQuadrant(int v, int h, String str) {
		battleField[h - 1][v - 1] = str;
	}

	public void update(Quadrant quadrant, String str) {
		updateQuadrant(quadrant.v, quadrant.h, str);
	}

	@Override
	public void draw(Graphics g) {
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

		for (int j = 1; j <= getDimensionY(); j++) {
			for (int k = 1; k <= getDimensionX(); k++) {
				if (scanQuadrant(k, j).equals("B")) {
					Coordinates coordinates = ActionField.getQuadrantXY(k, j);
					int x = coordinates.x;
					int y = coordinates.y;
					g.setColor(new Color(0, 0, 255));
					g.fillRect(x, y, 64, 64);
				}
			}
		}
	}
}
