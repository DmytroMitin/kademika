package tanks;

import tanks.field.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Launcher {
	public static void main(String[] args) throws InterruptedException {
        List<FieldObject> objects = Arrays.asList(
                new Brick(4, 1),
                new Brick(3, 3),
                new Brick(5, 5),
                new Brick(4, 8),
                new Brick(5, 8),
                new Brick(6, 8),
                new Rock(9, 8),
                new Rock(1, 8),
                new Water(1, 9),
                new Brick(4, 9),
                new Eagle(5, 9),
                new Brick(6, 9),
                new Water(9, 9)
        );

		BattleField battleField = new BattleField(objects, 9, 9);

        SwingUtilities.invokeLater(() -> new StartPanel(battleField));

	}
}
