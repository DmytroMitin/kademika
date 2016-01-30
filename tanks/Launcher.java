package tanks;

import tanks.field.*;

public class Launcher {
	public static void main(String[] args) throws InterruptedException {
		FieldObject[][] field = {
				{ null, null, null, new Brick(), null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, new Brick(), null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, new Brick(), null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ new Rock(), null, null, new Brick(), new Brick(), new Brick(), null, null, new Rock() },
				{ new Water(), null, null, new Brick(), new Eagle(), new Brick(), null, null, new Water() },
		};
		BattleField battleField = new BattleField(field);
		ActionField actionField = new ActionField(battleField, new Quadrant(1, 1), Direction.RIGHT,
				new Quadrant(9, 1), Direction.LEFT);
		actionField.runTheGame();
	}
}
