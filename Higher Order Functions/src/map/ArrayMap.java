package map;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ArrayMap<K, V> implements Map<K, V> {

    protected static class Entry<K, V> implements KeyValuePair<K, V> {
        protected final K key;
        protected V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private abstract class AbstractMapIterator<X> implements Iterator<X> {
        protected int cur = 0;

        @Override
        public boolean hasNext() {
            return cur < size;
        }

        protected void moveToNext() {
            cur++;
        }
    }

    private class KeyIterator extends AbstractMapIterator<K> {
        @Override
        public K next() {
            K toReturn = entries[cur].key;
            moveToNext();
            return toReturn;
        }
    }

    private class ValueIterator extends AbstractMapIterator<V> {
        @Override
        public V next() {
            V toReturn = entries[cur].value;
            moveToNext();
            return toReturn;
        }
    }

    private class EntryIterator extends AbstractMapIterator<KeyValuePair<K, V>> {
        @Override
        public Entry<K, V> next() {
            Entry<K, V> toReturn = entries[cur];
            moveToNext();
            return toReturn;
        }
    }

    private static final int INITIAL_SIZE = 4;
    Entry<K, V>[] entries = new Entry[INITIAL_SIZE];
    protected int size = 0;

    private int indexOf(Object key) {
        for (int i = 0; i < size; i++) {
            KeyValuePair<K, V> entry = entries[i];
            if (entry.getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public V get(Object key) {
        int idx = indexOf(key);
        if (idx >= 0) {
            return entries[idx].getValue();
        }
        return null;
    }

    public boolean contains(Object key) {
        return indexOf(key) >= 0;
    }

    public boolean remove(Object key) {
        int idx = indexOf(key);
        if (idx >= 0) {
            moveLeft(idx);
            size--;
            return true;
        }
        return false;
    }

    private void moveLeft(int idx) {
        for (int i = idx; i < size - 1; i++) {
            entries[i] = entries[i + 1];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> keyIterator() {
        return new KeyIterator();
    }

    @Override
    public Iterator<V> valueIterator() {
        return new ValueIterator();
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator() {
        return new EntryIterator();
    }

    @Override
    public boolean putIfAbsent(K k, Supplier<? extends V> valueSupplier) {
        if(contains(k)){
            return false;
        }
        put(k, valueSupplier.get());
        return true;
        /* DONE */
    }

    @Override
    public Map<K, V> filter(Predicate<? super K> keyPredicate) {
        ArrayMap<K, V> arrayMap = new ArrayMap<>();
        for(KeyValuePair<K, V> kvp : this){
            if(keyPredicate.test(kvp.getKey())){
                arrayMap.put(kvp.getKey(), kvp.getValue());
            }
        }
        return arrayMap;
        /* DONE */
    }

    @Override
    public Map<K, V> filter(BiPredicate<? super K, ? super V> keyAndValuePredicate) {
        ArrayMap<K, V> arrayMap = new ArrayMap<>();
        for(KeyValuePair<K, V> kvp : this){
            if(keyAndValuePredicate.test(kvp.getKey(), kvp.getValue())){
                arrayMap.put(kvp.getKey(), kvp.getValue());
            }
        }
        return arrayMap;
        /* DONE */
    }

    @Override
    public <NV> Map<K, NV> mapValues(Function<? super KeyValuePair<K, V>, ? extends NV> newValueGenerator) {
        ArrayMap<K, NV> arrayMap = new ArrayMap<>();
        for(KeyValuePair<K, V> kvp : this){
            NV newValue = newValueGenerator.apply(kvp);
            arrayMap.put(kvp.getKey(), newValue);
        }
        return arrayMap;
        /* DONE */
    }

    @Override
    public Optional<V> find(Predicate<? super KeyValuePair<K, V>> predicate) {
        for(KeyValuePair<K, V> kvp : this){
            if(predicate.test(kvp)){
                return Optional.of(kvp.getValue());
            }
        }
        return Optional.empty();
        /* DONE */
    }

    @Override
    public boolean none(BiPredicate<? super K, ? super V> predicate) {
        return find(entries -> predicate.test(entries.getKey(), entries.getValue())).isEmpty();
        /* DONE */
    }

    @Override
    public boolean all(BiPredicate<? super K, ? super V> predicate) {
        return find(entries -> !predicate.test(entries.getKey(), entries.getValue())).isEmpty();
        /* DONE */
    }

    @Override
    public boolean some(BiPredicate<? super K, ? super V> predicate) {
        return find(entries -> predicate.test(entries.getKey(), entries.getValue())).isPresent();
        /* DONE */
    }

    @Override
    public void put(K key, V value) {
        int idx = indexOf(key);
        if (idx >= 0) {
            // Key already contained, replace value and do not increase size
            entries[idx].value = value;
            return;
        }
        ensureSize();
        entries[size] = new Entry<>(key, value);
        size++;
    }

    private void ensureSize() {
        if (size == entries.length) {
            Entry<K, V>[] newArray = new Entry[size * 2];
            // Alternative: System.arraycopy(entries, 0, newArray, 0, size);
            for (int i = 0; i < size; i++) {
                newArray[i] = entries[i];
            }
            entries = newArray;
        }
    }
}