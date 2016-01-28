package tanks.field;

import tanks.ActionField;
import tanks.Coordinates;
import tanks.Drowable;
import tanks.Quadrant;

import java.awt.*;

public class BattleField implements Drowable {

	private final boolean COLORED_MODE = false;

	private final int width;
	
	private final int height;
	
	private FieldObject[][] field;

	public BattleField(FieldObject[][] field) {
		this.field = field;
		width = field[0].length * 64;
		height = field.length * 64;
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

	public FieldObject scanQuadrant(int v, int h) {
		return field[h - 1][v - 1];
	}

	public FieldObject scan(Quadrant quadrant) {
		return scanQuadrant(quadrant.v, quadrant.h);
	}

	public void updateQuadrant(int v, int h, FieldObject str) {
		field[h - 1][v - 1] = str;
	}

	public void update(Quadrant quadrant, FieldObject str) {
		updateQuadrant(quadrant.v, quadrant.h, str);
	}

	@Override
	public void draw(Graphics graphics) {
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
					cc = Color.LIGHT_GRAY;
				}
				i++;
				graphics.setColor(cc);
				graphics.fillRect(h * 64, v * 64, 64, 64);
			}
		}

		for (int j = 1; j <= getDimensionY(); j++) {
			for (int k = 1; k <= getDimensionX(); k++) {
				FieldObject object = scanQuadrant(k, j);
				if (object != null) {
					Coordinates coordinates = ActionField.getQuadrantXY(k, j);
					graphics.setColor(object.getColor());
					graphics.fillRect(coordinates.x, coordinates.y, 64, 64);
				}
			}
		}
	}
}
