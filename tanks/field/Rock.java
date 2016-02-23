package tanks.field;

import java.awt.*;

public class Rock extends FieldObject {
    // can be destroyed only by Tiger

    public Rock(int v, int h) {
        super(v, h);
        color = Color.BLACK;
        file = "bin/tanks/img/rock.jpg";
    }


}
