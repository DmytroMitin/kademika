package tanks;

import java.awt.*;

public class Tiger extends AbstractTank {
    int armor = 2;

    public Tiger(int x, int y, Direction direction, ActionField af, BattleField bf) {
        super(x, y, direction, af, bf);
    }

    public Tiger(Quadrant quadrant, Direction direction, ActionField af, BattleField bf) {
        super(quadrant, direction, af, bf);
    }

    @Override
    public void destroy() {
        armor--;
        if (armor == 0) {
            super.destroy();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 255, 0));
        g.fillRect(x, y, 64, 64);

        g.setColor(new Color(255, 0, 0));
        if (getDirection() == Direction.UP) {
            g.fillRect(x + 20, y, 24, 32);
        } else if (getDirection() == Direction.DOWN) {
            g.fillRect(x + 20, y + 32, 24, 32);
        } else if (getDirection() == Direction.LEFT) {
            g.fillRect(x, y + 20, 32, 24);
        } else if (getDirection() == Direction.RIGHT) {
            g.fillRect(x + 32, y + 20, 32, 24);
        }
    }
}
