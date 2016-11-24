package io.antielectron.framework.event;

/**
 * TODO Document
 * @author Evan Geng
 */
public interface ICancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();

}
