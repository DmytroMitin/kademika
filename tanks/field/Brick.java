package tanks.field;

import java.awt.*;

public class Brick extends FieldObject {
    public Brick(int v, int h) {
        super(v, h);
        color = new Color(161,120,41);
        file = "bin/tanks/img/brick.jpg";
    }

}
