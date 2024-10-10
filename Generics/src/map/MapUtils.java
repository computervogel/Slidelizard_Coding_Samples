package map;

public class MapUtils {
    public static <K, V, V1> ArrayMap<K, V1> mapValues(Map<K, V> map, Transformer<? super V, ? extends V1> transformer){
        ArrayMap<K, V1> result = new ArrayMap<>();

        for(KeyValuePair<K, V> entry : map){
            result.put(entry.getKey(), transformer.transform(entry.getValue()));
        }

        return result;
    }
}
