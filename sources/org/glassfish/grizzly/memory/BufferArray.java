package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;

public final class BufferArray extends AbstractBufferArray<Buffer> {
    private static final ThreadCache.CachedTypeIndex<BufferArray> CACHE_IDX;

    static {
        Class<BufferArray> cls = BufferArray.class;
        CACHE_IDX = ThreadCache.obtainIndex(cls, Integer.getInteger(cls.getName() + "ba-cache-size", 4).intValue());
    }

    public static BufferArray create() {
        BufferArray array = (BufferArray) ThreadCache.takeFromCache(CACHE_IDX);
        if (array != null) {
            return array;
        }
        return new BufferArray();
    }

    private BufferArray() {
        super(Buffer.class);
    }

    public void recycle() {
        super.recycle();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    /* access modifiers changed from: protected */
    public void setPositionLimit(Buffer buffer, int position, int limit) {
        Buffers.setPositionLimit(buffer, position, limit);
    }

    /* access modifiers changed from: protected */
    public int getPosition(Buffer buffer) {
        return buffer.position();
    }

    /* access modifiers changed from: protected */
    public int getLimit(Buffer buffer) {
        return buffer.limit();
    }
}
