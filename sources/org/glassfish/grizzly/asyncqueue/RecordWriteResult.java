package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.utils.Holder;

public final class RecordWriteResult<K, L> extends WriteResult<K, L> {
    private long bytesToReleaseAfterLastWrite;
    private final SettableHolder<L> dstAddressHolder = new SettableHolder<>();
    private long lastWrittenBytes;

    /* access modifiers changed from: protected */
    public void set(Connection<L> connection, K message, L dstAddress, long writtenSize) {
        super.set(connection, message, dstAddress, writtenSize);
    }

    /* access modifiers changed from: protected */
    public Holder<L> createAddrHolder(L dstAddress) {
        return this.dstAddressHolder.set(dstAddress);
    }

    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }

    public long bytesToReleaseAfterLastWrite() {
        return this.bytesToReleaseAfterLastWrite;
    }

    public RecordWriteResult<K, L> lastWriteResult(long lastWrittenBytes2, long bytesToReleaseAfterLastWrite2) {
        this.lastWrittenBytes = lastWrittenBytes2;
        this.bytesToReleaseAfterLastWrite = bytesToReleaseAfterLastWrite2;
        return this;
    }

    public void recycle() {
        this.lastWrittenBytes = 0;
        this.bytesToReleaseAfterLastWrite = 0;
        Object unused = this.dstAddressHolder.obj = null;
        reset();
    }

    public static class SettableHolder<L> extends Holder<L> {
        /* access modifiers changed from: private */
        public L obj;

        private SettableHolder() {
        }

        public SettableHolder<L> set(L obj2) {
            this.obj = obj2;
            return this;
        }

        public L get() {
            return this.obj;
        }
    }
}
