package io.antielectron.framework.js;

import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.PACScriptErrorParams;
import io.antielectron.framework.window.SubEngine;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * TODO Document
 * @author Evan Geng
 */
class JSObjectCollector<T, V> implements Collector<T, Map<String, V>, JSObject> {

    private final SubEngine engine;
    private final Function<T, String> keyMapper;
    private final Function<T, V> valMapper;

    JSObjectCollector(SubEngine engine, Function<T, String> keyMapper, Function<T, V> valMapper) {
        this.engine = engine;
        this.keyMapper = keyMapper;
        this.valMapper = valMapper;
    }

    @Override
    public Supplier<Map<String, V>> supplier() {
        return ConcurrentHashMap::new;
    }

    @Override
    public BiConsumer<Map<String, V>, T> accumulator() {
        return (a, t) -> a.put(keyMapper.apply(t), valMapper.apply(t));
    }

    @Override
    public BinaryOperator<Map<String, V>> combiner() {
        return (a, b) -> {
            a.putAll(b);
            return a;
        };
    }

    @Override
    public Function<Map<String, V>, JSObject> finisher() {
        return t -> {
            JSObject obj = JSUtils.newObject(engine);
            t.forEach(obj::setProperty);
            return obj;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT, Characteristics.UNORDERED);
    }
}
