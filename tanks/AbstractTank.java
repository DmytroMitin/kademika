package tanks;

public abstract class AbstractTank {
    protected int speed = 20;

    protected int x;

    protected int y;

    protected Direction direction;

    protected ActionField af;

    protected BattleField bf;

    public abstract void turn(Direction direction);

    public abstract void move() throws InterruptedException;

    public abstract void fire () throws InterruptedException;

    public abstract void destroy();
}
