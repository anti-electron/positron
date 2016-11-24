package io.antielectron.framework.event;

import io.antielectron.framework.function.INullConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Document
 * @author Evan Geng
 */
public class NullaryEventStream implements INullConsumer {

    private INullConsumer handlerStack;
    private List<INullConsumer> tempHandlers = new ArrayList<>();

    public NullaryEventStream(INullConsumer first) {
        this.handlerStack = first;
    }

    public NullaryEventStream() {
        this(() -> {});
    }

    public NullaryEventStream always(INullConsumer handler) {
        handlerStack = handlerStack.andThen(handler);
        return this;
    }

    public NullaryEventStream once(INullConsumer handler) {
        tempHandlers.add(handler);
        return this;
    }

    @Override
    public void accept() {
        handlerStack.accept();
        tempHandlers.removeIf(h -> {
            h.accept();
            return true;
        });
    }

}
