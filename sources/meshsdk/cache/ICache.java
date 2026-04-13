package meshsdk.cache;

public abstract class ICache<T> {
    public abstract T get(String str);

    public abstract void put(String str, T t);
}
