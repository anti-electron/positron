package io.antielectron.framework.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * TODO Document
 * @author Evan Geng
 */
public class UnaryEventStream<E> implements Consumer<E> {

    private Consumer<E> handlerStack;
    private List<Consumer<E>> tempHandlers = new ArrayList<>();

    public UnaryEventStream(Consumer<E> first) {
        this.handlerStack = first;
    }

    public UnaryEventStream() {
        this(e -> {});
    }

    public UnaryEventStream<E> always(Consumer<E> handler) {
        handlerStack = handlerStack.andThen(handler);
        return this;
    }

    public UnaryEventStream<E> once(Consumer<E> handler) {
        tempHandlers.add(handler);
        return this;
    }

    @Override
    public void accept(E event) {
        handlerStack.accept(event);
        tempHandlers.removeIf(h -> {
            h.accept(event);
            return true;
        });
    }

}
