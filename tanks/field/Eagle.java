package tanks.field;

import java.awt.*;

public class Eagle extends FieldObject {
    public Eagle(int v, int h) {
        super(v, h);
        color = Color.ORANGE;
        file = "bin/tanks/img/eagle.jpg";
    }

    @Override
    public String toString() {
        return "Eagle";
    }
}
