package io.antielectron.framework.geometry;

import java.awt.*;
import java.io.Serializable;

/**
 * TODO Document
 * @author Evan Geng
 */
public class DimensionWrapper implements IRectDimensional {

    private final Dimension dim;

    public DimensionWrapper(Dimension dim) {
        this.dim = dim;
    }

    @Override
    public int getWidth() {
        return (int)dim.getWidth();
    }

    @Override
    public int getHeight() {
        return (int)dim.getHeight();
    }

}
