package org.glassfish.grizzly;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.utils.Holder;

public class ReadResult<K, L> implements Result, Cacheable {
    private static final ThreadCache.CachedTypeIndex<ReadResult> CACHE_IDX = ThreadCache.obtainIndex(ReadResult.class, 4);
    private Connection<L> connection;
    private boolean isRecycled;
    private K message;
    private int readSize;
    private Holder<L> srcAddressHolder;

    public static <K, L> ReadResult<K, L> create(Connection<L> connection2) {
        ReadResult<K, L> readResult = takeFromCache();
        if (readResult == null) {
            return new ReadResult<>(connection2);
        }
        readResult.connection = connection2;
        readResult.isRecycled = false;
        return readResult;
    }

    public static <K, L> ReadResult<K, L> create(Connection<L> connection2, K message2, L srcAddress, int readSize2) {
        ReadResult<K, L> readResult = takeFromCache();
        if (readResult == null) {
            return new ReadResult<>(connection2, message2, srcAddress, readSize2);
        }
        readResult.connection = connection2;
        readResult.message = message2;
        readResult.srcAddressHolder = Holder.staticHolder(srcAddress);
        readResult.readSize = readSize2;
        readResult.isRecycled = false;
        return readResult;
    }

    private static <K, L> ReadResult<K, L> takeFromCache() {
        return (ReadResult) ThreadCache.takeFromCache(CACHE_IDX);
    }

    protected ReadResult() {
        this.isRecycled = false;
    }

    protected ReadResult(Connection<L> connection2) {
        this(connection2, (Object) null, (Object) null, 0);
    }

    protected ReadResult(Connection<L> connection2, K message2, L srcAddress, int readSize2) {
        this.isRecycled = false;
        this.connection = connection2;
        this.message = message2;
        this.srcAddressHolder = Holder.staticHolder(srcAddress);
        this.readSize = readSize2;
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

    public final L getSrcAddress() {
        checkRecycled();
        Holder<L> holder = this.srcAddressHolder;
        if (holder != null) {
            return holder.get();
        }
        return null;
    }

    public final Holder<L> getSrcAddressHolder() {
        checkRecycled();
        return this.srcAddressHolder;
    }

    public final void setSrcAddress(L srcAddress) {
        checkRecycled();
        this.srcAddressHolder = Holder.staticHolder(srcAddress);
    }

    public final void setSrcAddressHolder(Holder<L> srcAddressHolder2) {
        checkRecycled();
        this.srcAddressHolder = srcAddressHolder2;
    }

    public final int getReadSize() {
        checkRecycled();
        return this.readSize;
    }

    public final void setReadSize(int readSize2) {
        checkRecycled();
        this.readSize = readSize2;
    }

    /* access modifiers changed from: protected */
    public void set(Connection<L> connection2, K message2, L srcAddress, int readSize2) {
        this.connection = connection2;
        this.message = message2;
        this.srcAddressHolder = Holder.staticHolder(srcAddress);
        this.readSize = readSize2;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.connection = null;
        this.message = null;
        this.srcAddressHolder = null;
        this.readSize = 0;
    }

    private void checkRecycled() {
        if (Grizzly.isTrackingThreadCache() && this.isRecycled) {
            throw new IllegalStateException("ReadResult has been recycled!");
        }
    }

    public void recycle() {
        reset();
        this.isRecycled = true;
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public Object copy() {
        return create(getConnection(), getMessage(), getSrcAddress(), getReadSize());
    }
}
