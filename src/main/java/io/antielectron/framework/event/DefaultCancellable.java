package io.antielectron.framework.event;

/**
 * TODO Document
 * @author Evan Geng
 */
public class DefaultCancellable implements ICancellable {

    private boolean cancelled = false;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

}
