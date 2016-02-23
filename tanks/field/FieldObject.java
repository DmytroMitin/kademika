package tanks.field;

import tanks.ActionField;
import tanks.Coordinates;
import tanks.Drowable;
import tanks.Quadrant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class FieldObject implements Drowable {
    protected Color color;

    protected String file;

    protected Quadrant quadrant;

    public FieldObject(int v, int h) {
        this.quadrant = new Quadrant(v, h);
    }

    public Color getColor() {
        return color;
    }

    public String getFile() {
        return file;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    @Override
    public void draw(Graphics graphics) {
        Coordinates coordinates = ActionField.getQuadrantXY(quadrant);
        try {
            graphics.drawImage(ImageIO.read(new File(file)), coordinates.x, coordinates.y, null);
        } catch (IOException e) {
            e.printStackTrace();
            graphics.setColor(color);
            graphics.fillRect(coordinates.x, coordinates.y, 64, 64);
        }
    }
}
