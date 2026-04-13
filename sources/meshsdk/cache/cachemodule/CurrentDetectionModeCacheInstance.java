package meshsdk.cache.cachemodule;

import meshsdk.cache.FileCache;
import meshsdk.cache.ICache;
import meshsdk.cache.ICacheInstance;

public class CurrentDetectionModeCacheInstance extends ICacheInstance<Integer> {
    private static CurrentDetectionModeCacheInstance instance = new CurrentDetectionModeCacheInstance(new CurrentDetectionModeCache());

    public static class CurrentDetectionModeCache extends FileCache<Integer> {
    }

    public CurrentDetectionModeCacheInstance(ICache cache) {
        super(cache);
    }

    public static CurrentDetectionModeCacheInstance getInstance() {
        return instance;
    }
}
