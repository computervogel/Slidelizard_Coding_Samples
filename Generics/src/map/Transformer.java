package map;

public interface Transformer<A, B> {
    B transform(A object);
}
