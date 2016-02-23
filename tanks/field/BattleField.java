package tanks.field;

import tanks.Drowable;
import tanks.Quadrant;

import java.awt.*;
import java.util.List;

public class BattleField implements Drowable {

	private final boolean COLORED_MODE = false;

	private final int width;
	
	private final int height;

    private final int dimensionX;

    private final int dimensionY;
	
	private FieldObject[][] field;

	public BattleField(List<FieldObject> objects, int dimensionX, int dimensionY) {
		this.field = new FieldObject[dimensionY][dimensionX];
        for (FieldObject object : objects) {
            update(object.getQuadrant(), object);
        }
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
		this.width = dimensionX * 64;
		this.height = dimensionY * 64;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDimensionX() {
		return dimensionX;
	}
	
	public int getDimensionY() {
		return dimensionY;
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

		for (int h = 1; h <= getDimensionY(); h++) {
			for (int v = 1; v <= getDimensionX(); v++) {
				FieldObject object = scanQuadrant(v, h);
				if (object != null) {
					object.draw(graphics);
                }
			}
		}
	}

	public Quadrant findEagle() {
		for (int h = 1; h <= getDimensionY(); h++) {
			for (int v = 1; v <= getDimensionX(); v++) {
				FieldObject object = scanQuadrant(v, h);
				if (object != null && object.getClass() == Eagle.class) {
					return new Quadrant(v, h);
				}
			}
		}
		return null;
	}
}
