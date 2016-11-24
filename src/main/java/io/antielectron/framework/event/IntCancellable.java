package io.antielectron.framework.event;

/**
 * TODO Document
 * @author Evan Geng
 */
public class IntCancellable extends DefaultCancellable {

    private final int value;

    public IntCancellable(int value) {
        this.value = value;
    }

    public int getInt() {
        return value;
    }

}
