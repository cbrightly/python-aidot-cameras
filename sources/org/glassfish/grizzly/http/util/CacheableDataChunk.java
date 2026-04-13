package org.glassfish.grizzly.http.util;

import org.glassfish.grizzly.ThreadCache;

public class CacheableDataChunk extends DataChunk {
    private static final ThreadCache.CachedTypeIndex<CacheableDataChunk> CACHE_IDX = ThreadCache.obtainIndex(CacheableDataChunk.class, 2);

    public static CacheableDataChunk create() {
        CacheableDataChunk dataChunk = (CacheableDataChunk) ThreadCache.takeFromCache(CACHE_IDX);
        if (dataChunk != null) {
            return dataChunk;
        }
        return new CacheableDataChunk();
    }

    public void reset() {
        super.reset();
    }

    public void recycle() {
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }
}
