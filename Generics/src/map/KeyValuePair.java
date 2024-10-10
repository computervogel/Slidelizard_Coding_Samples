package map;

/**
 * A pair of a key of type {@code K} and a value of type {@code V}
 *
 * @param <K> the type of the key of this pair
 * @param <V> the type of the value of this pair
 */
public interface KeyValuePair<K, V> {

    /**
     * Gets the key of this pair
     *
     * @return the key
     */
    K getKey();

    /**
     * Gets the value of this pair
     *
     * @return the value
     */
    V getValue();
}
