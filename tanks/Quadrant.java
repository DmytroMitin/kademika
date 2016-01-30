package tanks;

import tanks.field.BattleField;

public class Quadrant {
	
	public final int v, h;
	
	public Quadrant(int v, int h) {
		this.v = v;
		this.h = h;
	}

	public boolean isValid(BattleField bf) {
		return v >= 1 && v <= bf.getDimensionX() && h >= 1 && h <= bf.getDimensionY();
	}

	public Quadrant getNeighbor(Direction direction) {
        return new Quadrant(v + direction.stepX, h + direction.stepY);
    }

	public boolean hasNeighbor(Direction direction, BattleField bf) {
		return getNeighbor(direction).isValid(bf);
	}

	public boolean hasEmptyNeighbor(Direction direction, BattleField bf) {
        Quadrant neighborQuadrant = getNeighbor(direction);
        return this.hasNeighbor(direction, bf) && bf.scan(neighborQuadrant) == null;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		Quadrant that = (Quadrant) object;

		return this.v == that.v && this.h == that.h;

	}

	@Override
	public int hashCode() {
		return 31 * v + h;
	}

    @Override
    public String toString() {
        return "Quadrant{" +
                "v=" + v +
                ", h=" + h +
                '}';
    }
}
