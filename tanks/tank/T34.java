package tanks.tank;

import tanks.ActionField;
import tanks.field.BattleField;
import tanks.Direction;
import tanks.Quadrant;

import java.awt.*;

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
}
