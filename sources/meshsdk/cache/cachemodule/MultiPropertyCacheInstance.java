package meshsdk.cache.cachemodule;

import meshsdk.cache.ICache;
import meshsdk.cache.ICacheInstance;
import meshsdk.cache.MemoryCache;
import meshsdk.model.json.MultiPropertyData;

public class MultiPropertyCacheInstance extends ICacheInstance<MultiPropertyData> {
    private static MultiPropertyCacheInstance instance = new MultiPropertyCacheInstance(new MultiPropertyCache());

    public static class MultiPropertyCache extends MemoryCache<MultiPropertyData> {
    }

    public MultiPropertyCacheInstance(ICache cache) {
        super(cache);
    }

    public static MultiPropertyCacheInstance getInstance() {
        return instance;
    }
}
