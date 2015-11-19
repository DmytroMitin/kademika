package tanks;

public class Quadrant {
	
	public final int v, h;
	
	public Quadrant(int v, int h) {
		this.v = v;
		this.h = h;
	}

	public boolean isValid(BattleField bf) {
		return v >= 1 && v <= bf.getDimensionX() && h >= 1 && h <= bf.getDimensionY();
	}

	public boolean hasNeighbor(Direction direction, BattleField bf) {
		return new Quadrant(v + direction.stepX, h + direction.stepY).isValid(bf);
	}

	public boolean hasEmptyNeighbor(Direction direction, BattleField bf) {
		return this.hasNeighbor(direction, bf)
				&& bf.scanQuadrant(v + direction.stepX, h + direction.stepY).equals(" ");
	}
}