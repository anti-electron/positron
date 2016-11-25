package io.antielectron.framework.event;

import io.antielectron.framework.geometry.IPositional;
import javafx.scene.input.MouseButton;

/**
 * TODO Document
 * @author Evan Geng
 */
public class ClickEvent extends DefaultCancellable implements IPositional {

    private final int x;
    private final int y;
    private final int button;

    public ClickEvent(int x, int y, int button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getButton() {
        return button;
    }

}
