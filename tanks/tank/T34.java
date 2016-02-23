package tanks.tank;

import tanks.ActionField;
import tanks.field.BattleField;
import tanks.Direction;
import tanks.Quadrant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class T34 extends AbstractTank {
    public T34(int x, int y, Direction direction, ActionField af, BattleField bf) {
        super(x, y, direction, af, bf);
        tankColor = Color.RED;
        towerColor = Color.GREEN;
    }

    public T34(Quadrant quadrant, Direction direction, ActionField af, BattleField bf) {
        this(ActionField.getQuadrantXY(quadrant).x,
             ActionField.getQuadrantXY(quadrant).y,
             direction,
             af,
             bf);
    }

    @Override
    public void draw(Graphics graphics) {
        try {
            File file = null;
            if (direction == Direction.LEFT || direction == Direction.NONE) {
                file = new File("bin/tanks/img/tank_left.jpg");
            } else if (direction == Direction.RIGHT) {
                file = new File("bin/tanks/img/tank_right.jpg");
            } else if (direction == Direction.UP) {
                file = new File("bin/tanks/img/tank_up.jpg");
            } else if (direction == Direction.DOWN) {
                file = new File("bin/tanks/img/tank_down.jpg");
            }
            graphics.drawImage(ImageIO.read(file), x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

//		graphics.setColor(tankColor);
//		graphics.fillRect(x, y, 64, 64);
//
//		graphics.setColor(towerColor);
//		if (getDirection() == Direction.UP) {
//			graphics.fillRect(x + 20, y, 24, 32);
//		} else if (getDirection() == Direction.DOWN) {
//			graphics.fillRect(x + 20, y + 32, 24, 32);
//		} else if (getDirection() == Direction.LEFT) {
//			graphics.fillRect(x, y + 20, 32, 24);
//		} else if (getDirection() == Direction.RIGHT) {
//			graphics.fillRect(x + 32, y + 20, 32, 24);
//		}
    }

    @Override
    public String toString() {
        return "T34";
    }
}
