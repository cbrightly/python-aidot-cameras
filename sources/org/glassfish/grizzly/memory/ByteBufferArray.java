package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import org.glassfish.grizzly.ThreadCache;

public final class ByteBufferArray extends AbstractBufferArray<ByteBuffer> {
    private static final ThreadCache.CachedTypeIndex<ByteBufferArray> CACHE_IDX;

    static {
        Class<ByteBufferArray> cls = ByteBufferArray.class;
        CACHE_IDX = ThreadCache.obtainIndex(cls, Integer.getInteger(cls.getName() + "bba-cache-size", 4).intValue());
    }

    public static ByteBufferArray create() {
        ByteBufferArray array = (ByteBufferArray) ThreadCache.takeFromCache(CACHE_IDX);
        if (array != null) {
            return array;
        }
        return new ByteBufferArray();
    }

    private ByteBufferArray() {
        super(ByteBuffer.class);
    }

    public void recycle() {
        super.recycle();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    /* access modifiers changed from: protected */
    public void setPositionLimit(ByteBuffer buffer, int position, int limit) {
        Buffers.setPositionLimit(buffer, position, limit);
    }

    /* access modifiers changed from: protected */
    public int getPosition(ByteBuffer buffer) {
        return buffer.position();
    }

    /* access modifiers changed from: protected */
    public int getLimit(ByteBuffer buffer) {
        return buffer.limit();
    }
}
