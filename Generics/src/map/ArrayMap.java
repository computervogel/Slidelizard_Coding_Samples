package map;

import java.util.Iterator;

public class ArrayMap<K, V> implements Map<K,V> {
    private static final int INITIAL_SIZE = 4;
    Entry<K, V>[] entries;
    private int size;

    public ArrayMap(){
        entries = new Entry[INITIAL_SIZE];
        size = 0;
    }

    @Override
    public void put(Object key, Object value) {
        for(int i = 0; i < size; i++){
            if(entries[i].getKey().equals(key)){
                entries[i].setValue((V) value);
                return;
            }
        }
        //double array size
        if(size == entries.length){
            int newSize = entries.length * 2;
            Entry<K, V>[] newEntries = new Entry[newSize];
            System.arraycopy(entries, 0, newEntries, 0, size);
            entries = newEntries;
        }

        entries[size++] = (Entry<K, V>) new Entry<>(key, value);
    }

    @Override
    public V get(Object key) {
        for(int i = 0; i < size ; i++){
            if(entries[i].getKey().equals(key)){
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public boolean contains(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean remove(Object key) {
        for(int i = 0; i < size; i++){
            if(entries[i].getKey().equals(key)){
                //left shifting
                for(int j = 0; j < size - 1; j++){
                    entries[j] = entries[j + 1];
                }
                entries[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
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

    public static class Entry<K, V> implements KeyValuePair<K, V> {
        private final K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public void setValue(V value){
            this.value = value;
        }
    }

    private abstract class AbstractMapIterator<T> implements Iterator<T>{
        protected int idx;

        @Override
        public boolean hasNext() {
            return idx < size;
        }
    }

    private class EntryIterator extends AbstractMapIterator<KeyValuePair<K, V>>{

        @Override
        public KeyValuePair<K, V> next() {
            return entries[idx++];
        }
    }

    private class KeyIterator extends AbstractMapIterator<K>{

        @Override
        public K next() {
            return entries[idx++].getKey();
        }
    }

    private class ValueIterator extends AbstractMapIterator<V>{

        @Override
        public V next() {
            return entries[idx++].getValue();
        }
    }
}
