package io.antielectron.framework.js;

import com.teamdev.jxbrowser.chromium.JSArray;
import io.antielectron.framework.window.SubEngine;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * TODO Document
 * @author Evan Geng
 */
public class JSArrayCollector<T> implements Collector<T, List<T>, JSArray> {

    private final SubEngine engine;

    public JSArrayCollector(SubEngine engine) {
        this.engine = engine;
    }

    @Override
    public Supplier<List<T>> supplier() {
        return CopyOnWriteArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (a, b) -> {
            a.addAll(b);
            return a;
        };
    }

    @Override
    public Function<List<T>, JSArray> finisher() {
        return a -> JSUtils.preInitArray(engine, a.toArray(new Object[a.size()]));
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }

}
