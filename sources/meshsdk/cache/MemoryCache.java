package meshsdk.cache;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache<T> extends ICache<T> {
    private Map<String, T> cacheMap = new HashMap();

    public void put(String key, T t) {
        this.cacheMap.put(key, t);
    }

    public T get(String key) {
        return this.cacheMap.get(key);
    }
}
