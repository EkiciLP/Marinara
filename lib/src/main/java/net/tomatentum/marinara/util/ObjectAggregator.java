package net.tomatentum.marinara.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ObjectAggregator<O, K, V> {
    private Function<O, K> keySupplier;
    private BiConsumer<V, O> valueConsumer;
    private Function<K, V> defaultGenerator;

    public ObjectAggregator(Function<O, K> keySupplier, BiConsumer<V, O> valueConsumer, Function<K, V> defaultGenerator) {
        this.keySupplier = keySupplier;
        this.valueConsumer = valueConsumer;
        this.defaultGenerator = defaultGenerator;
    }

    public Collection<V> aggregate(Iterable<O> iterator) {
        Map<K, V> map = new HashMap<>();
        for (O element : iterator) {
            K key = this.keySupplier.apply(element);
            V value = map.getOrDefault(key, this.defaultGenerator.apply(key));
            this.valueConsumer.accept(value, element);
            map.putIfAbsent(key, value);
        }
        return map.values();
    }

}
