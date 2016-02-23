package tanks.tank;

import tanks.ActionField;
import tanks.field.BattleField;
import tanks.Direction;
import tanks.Quadrant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Tiger extends AbstractTank {
    private int armor = 2;

    public Tiger(int x, int y, Direction direction, ActionField af, BattleField bf) {
        super(x, y, direction, af, bf);
        tankColor = Color.GREEN;
        towerColor = Color.RED;
    }

    public Tiger(Quadrant quadrant, Direction direction, ActionField af, BattleField bf) {
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
                file = new File("bin/tanks/img/tank1_left.jpg");
            } else if (direction == Direction.RIGHT) {
                file = new File("bin/tanks/img/tank1_right.jpg");
            } else if (direction == Direction.UP) {
                file = new File("bin/tanks/img/tank1_up.jpg");
            } else if (direction == Direction.DOWN) {
                file = new File("bin/tanks/img/tank1_down.jpg");
            }
            graphics.drawImage(ImageIO.read(file), x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean destroy() {
        armor--;
        if (armor == 0) {
            super.destroy();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tiger";
    }
}
