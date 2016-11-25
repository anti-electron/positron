package io.antielectron.framework.window;

import io.antielectron.framework.geometry.DimensionWrapper;
import io.antielectron.framework.geometry.IPositional;
import io.antielectron.framework.geometry.IRectDimensional;
import io.antielectron.framework.geometry.PointWrapper;

import java.awt.*;

/**
 * TODO Document
 * @author Evan Geng
 */
public class ScreenInfo {

    public IRectDimensional getSize() {
        return new DimensionWrapper(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public IPositional getCursorScreenPoint() {
        return new PointWrapper(MouseInfo.getPointerInfo().getLocation());
    }

}
