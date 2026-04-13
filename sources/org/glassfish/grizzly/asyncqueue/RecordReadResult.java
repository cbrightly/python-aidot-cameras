package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ReadResult;

public final class RecordReadResult<K, L> extends ReadResult<K, L> {
    /* access modifiers changed from: protected */
    public void set(Connection<L> connection, K message, L srcAddress, int readSize) {
        super.set(connection, message, srcAddress, readSize);
    }

    public void recycle() {
        reset();
    }
}
