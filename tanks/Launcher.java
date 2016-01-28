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
				{ new Rock(), null, null, null, null, null, null, null, new Rock() },
				{ new Water(), null, null, null, new Eagle(), null, null, null, new Water() },
		};
		BattleField battleField = new BattleField(field);
		ActionField actionField = new ActionField(battleField);
		actionField.runTheGame();
	}
}
