package io.antielectron.framework.geometry;

import java.awt.*;

/**
 * TODO Document
 * @author Phanta
 */
public class PointWrapper implements IPositional {

    private final Point point;

    public PointWrapper(Point point) {
        this.point = point;
    }

    @Override
    public int getX() {
        return (int)point.getX();
    }

    @Override
    public int getY() {
        return (int)point.getY();
    }

}
