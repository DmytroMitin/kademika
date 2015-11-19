package tanks;

public class BattleField {

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
	
}
