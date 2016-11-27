package io.antielectron.framework.js;

import com.teamdev.jxbrowser.chromium.JSArray;
import com.teamdev.jxbrowser.chromium.JSObject;
import io.antielectron.framework.window.SubEngine;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * TODO Document
 * @author Evan Geng
 */
public class JSUtils {

    public static JSArray preInitArray(SubEngine engine, Object... contents) {
        JSArray array = engine.executeJs("new Array(" + Integer.toString(contents.length) + ")").asArray();
        IntStream.range(0, contents.length).forEach(i -> array.set(i, contents[i]));
        return array;
    }

    public static JSArray newArray(SubEngine engine, int length) {
        return engine.executeJs("new Array(" + length + ")").asArray();
    }

    public static JSObject preInitObject(SubEngine engine, Object... mappings) {
        if (mappings.length % 2 == 1)
            throw new IllegalArgumentException("Mappings must be in key-value pairs!");
        JSObject obj = engine.executeJs("{}").asObject();
        for (int i = 0; i < mappings.length; i += 2)
            obj.setProperty(mappings[i].toString(), mappings[i + 1]);
        return obj;
    }

    public static JSObject newObject(SubEngine engine) {
        return engine.executeJs("{}").asObject();
    }

    public static <T> Collector<T, List<T>, JSArray> arrayCollector(SubEngine engine) {
        return new JSArrayCollector<>(engine);
    }

    public static <T, V> Collector<T, Map<String, V>, JSObject> objectCollector(SubEngine engine, Function<T, String> keyMapper, Function<T, V> valMapper) {
        return new JSObjectCollector<>(engine, keyMapper, valMapper);
    }

}
