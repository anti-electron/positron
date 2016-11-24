package io.antielectron.framework.geometry;

/**
 * TODO Document
 * @author Evan Geng
 */
public interface IRectResizable extends IRectDimensional {

    IRectResizable setWidth(int width);

    IRectResizable setHeight(int height);

    IRectResizable setMinWidth(int width);

    IRectResizable setMaxWidth(int width);

    IRectResizable setMinHeight(int height);

    IRectResizable setMaxHeight(int height);

}
