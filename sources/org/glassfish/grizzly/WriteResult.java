package org.glassfish.grizzly;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.utils.Holder;

public class WriteResult<K, L> implements Result, Cacheable {
    private static final ThreadCache.CachedTypeIndex<WriteResult> CACHE_IDX = ThreadCache.obtainIndex(WriteResult.class, 4);
    private Connection<L> connection;
    private Holder<L> dstAddressHolder;
    private boolean isRecycled;
    private K message;
    private long writtenSize;

    public static <K, L> WriteResult<K, L> create(Connection<L> connection2) {
        WriteResult<K, L> writeResult = takeFromCache();
        if (writeResult == null) {
            return new WriteResult<>(connection2);
        }
        writeResult.connection = connection2;
        writeResult.isRecycled = false;
        return writeResult;
    }

    public static <K, L> WriteResult<K, L> create(Connection<L> connection2, K message2, L dstAddress, long writeSize) {
        WriteResult<K, L> writeResult = takeFromCache();
        if (writeResult == null) {
            return new WriteResult(connection2, message2, dstAddress, writeSize);
        }
        writeResult.set(connection2, message2, dstAddress, writeSize);
        writeResult.isRecycled = false;
        return writeResult;
    }

    private static <K, L> WriteResult<K, L> takeFromCache() {
        return (WriteResult) ThreadCache.takeFromCache(CACHE_IDX);
    }

    protected WriteResult() {
        this.isRecycled = false;
    }

    private WriteResult(Connection<L> connection2) {
        this(connection2, (Object) null, (Object) null, 0);
    }

    private WriteResult(Connection<L> connection2, K message2, L dstAddress, long writeSize) {
        this.isRecycled = false;
        set(connection2, message2, dstAddress, writeSize);
    }

    public final Connection<L> getConnection() {
        checkRecycled();
        return this.connection;
    }

    public final K getMessage() {
        checkRecycled();
        return this.message;
    }

    public final void setMessage(K message2) {
        checkRecycled();
        this.message = message2;
    }

    public final L getDstAddress() {
        checkRecycled();
        Holder<L> holder = this.dstAddressHolder;
        if (holder != null) {
            return holder.get();
        }
        return null;
    }

    public final Holder<L> getDstAddressHolder() {
        checkRecycled();
        return this.dstAddressHolder;
    }

    public final void setDstAddress(L dstAddress) {
        checkRecycled();
        this.dstAddressHolder = createAddrHolder(dstAddress);
    }

    public final void setDstAddressHolder(Holder<L> dstAddressHolder2) {
        checkRecycled();
        this.dstAddressHolder = dstAddressHolder2;
    }

    public final long getWrittenSize() {
        checkRecycled();
        return this.writtenSize;
    }

    public final void setWrittenSize(long writeSize) {
        checkRecycled();
        this.writtenSize = writeSize;
    }

    private void checkRecycled() {
        if (Grizzly.isTrackingThreadCache() && this.isRecycled) {
            throw new IllegalStateException("ReadResult has been recycled!");
        }
    }

    /* access modifiers changed from: protected */
    public void set(Connection<L> connection2, K message2, L dstAddress, long writtenSize2) {
        this.connection = connection2;
        this.message = message2;
        this.dstAddressHolder = createAddrHolder(dstAddress);
        this.writtenSize = writtenSize2;
    }

    /* access modifiers changed from: protected */
    public Holder<L> createAddrHolder(L dstAddress) {
        return Holder.staticHolder(dstAddress);
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.connection = null;
        this.message = null;
        this.dstAddressHolder = null;
        this.writtenSize = 0;
    }

    public void recycle() {
        reset();
        this.isRecycled = true;
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public Object copy() {
        return create(getConnection(), getMessage(), getDstAddress(), getWrittenSize());
    }
}
