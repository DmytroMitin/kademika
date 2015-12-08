package tanks;

public class Tiger extends Tank {
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
}
