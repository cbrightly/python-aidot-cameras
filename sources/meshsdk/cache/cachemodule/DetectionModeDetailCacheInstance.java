package meshsdk.cache.cachemodule;

import meshsdk.cache.FileCache;
import meshsdk.cache.ICache;
import meshsdk.cache.ICacheInstance;
import meshsdk.model.json.DetectMode;

public class DetectionModeDetailCacheInstance extends ICacheInstance<DetectMode> {
    private static DetectionModeDetailCacheInstance instance = new DetectionModeDetailCacheInstance(new DetectionModeCache());

    public static class DetectionModeCache extends FileCache<DetectMode> {
    }

    public DetectionModeDetailCacheInstance(ICache cache) {
        super(cache);
    }

    public static DetectionModeDetailCacheInstance getInstance() {
        return instance;
    }
}
