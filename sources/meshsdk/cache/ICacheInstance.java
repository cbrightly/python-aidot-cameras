package meshsdk.cache;

import android.os.Looper;

public class ICacheInstance<T> {
    private CacheHandler cacheHandler = new CacheHandler(Looper.getMainLooper());
    private ICache<T> iCache;

    public ICacheInstance(ICache cache) {
        this.iCache = cache;
    }

    public void put(String key, T t) {
        this.iCache.put(key, t);
    }

    public T get(String key) {
        return this.iCache.get(key);
    }

    public void sendWrapperDelay(CacheHanderMsgWrapper wrapper) {
        this.cacheHandler.sendWrapperDelay(wrapper);
    }

    public void removeCacheHandlerMsg(int what) {
        this.cacheHandler.removeWrapperMsg(what);
    }
}
