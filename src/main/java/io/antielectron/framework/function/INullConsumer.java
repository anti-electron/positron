package io.antielectron.framework.function;

/**
 * TODO Document
 * @author Evan Geng
 */
@FunctionalInterface
public interface INullConsumer extends Runnable {

    void accept();

    default void run() {
        accept();
    }

    default INullConsumer andThen(INullConsumer other) {
        return () -> {
            accept();
            other.accept();
        };
    }

    default INullConsumer andThen(Runnable other) {
        return () -> {
            accept();
            other.run();
        };
    }

}
