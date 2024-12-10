package utils;

public class Pair<T, K> {
    public T left;
    public K right;


    public Pair(T left, K right) {
        this.left = left;
        this.right = right;
    }

    public static <T,K> Pair<T, K> of(T left, K right) {
        return new Pair<>(left, right);
    }

}
