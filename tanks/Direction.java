package tanks;

public enum Direction {
	UP (0, -1), DOWN (0, 1), LEFT (-1, 0), RIGHT (1, 0), NONE (0, 0);
	
	public final int stepX, stepY;

	Direction(int stepX, int stepY) {
		this.stepX = stepX;
		this.stepY = stepY;
	}

	public static Direction getByStepX(int stepX) {
		for (Direction direction : Direction.values()) {
			if (direction.stepX == stepX) {
				return direction;
			}
		}
		return Direction.NONE;
	}

	public static Direction getByStepY(int stepY) {
		for (Direction direction : Direction.values()) {
			if (direction.stepY == stepY) {
				return direction;
			}
		}
		return Direction.NONE;
	}

}
