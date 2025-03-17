package net.tomatentum.marinara.util;

import java.util.ArrayList;
import java.util.function.Function;

public class ObjectListAggregator<O, K, V> extends ObjectAggregator<O, K, ArrayList<V>> {

    public ObjectListAggregator(Function<O, Iterable<K>> keySupplier, Function<O, V> valueConsumer) {
        super(keySupplier, 
        (l, o) -> l.add(valueConsumer.apply(o)),
        () -> new ArrayList<>());
    }

    
}
