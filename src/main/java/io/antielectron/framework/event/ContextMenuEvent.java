package io.antielectron.framework.event;

import io.antielectron.framework.geometry.IPositional;
import javafx.scene.input.MouseButton;

import java.awt.event.InputEvent;

/**
 * TODO Document
 * @author Evan Geng
 */
public class ContextMenuEvent extends DefaultCancellable implements IPositional {

    private final int x;
    private final int y;
    private final int modifiers;

    public ContextMenuEvent(int x, int y, int modifiers) {
        this.x = x;
        this.y = y;
        this.modifiers = modifiers;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public boolean isAltDown() {
        return (modifiers & InputEvent.ALT_DOWN_MASK) != 0;
    }

    public boolean isControlDown() {
        return (modifiers & InputEvent.CTRL_DOWN_MASK) != 0;
    }

    public boolean isShiftDown() {
        return (modifiers & InputEvent.SHIFT_DOWN_MASK) != 0;
    }

}
