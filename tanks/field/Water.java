package tanks.field;

import java.awt.*;

public class Water extends FieldObject {
    public Water(int v, int h) {
        super(v, h);
        color = Color.BLUE;
        file = "bin/tanks/img/water.jpg";
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        Composite composite = graphics2D.getComposite();
        AlphaComposite translucent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F);
        graphics2D.setComposite(translucent);

        super.draw(graphics);

        graphics2D.setComposite(composite);
    }

}
