package io.antielectron.framework.geometry;

/**
 * TODO Document
 * @author Evan Geng
 */
public interface IRectResizable extends IRectDimensional {

    IRectResizable setSize(int width, int height);

    IRectResizable setMinSize(int width, int height);

    IRectResizable setMaxSize(int width, int height);

}
