package tanks;

import java.awt.*;

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
    public boolean destroy() {
        armor--;
        if (armor == 0) {
            super.destroy();
            return true;
        }
        return false;
    }
}
