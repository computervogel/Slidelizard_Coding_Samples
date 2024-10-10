package app;

import inout.Out;
import map.KeyValuePair;
import map.Map;

public class AppUtils {
    public static void printMap(Map<?, ?> map) {
        for (KeyValuePair<?, ?> e : map) {
            Out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}
