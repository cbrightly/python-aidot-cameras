package com.google.android.gms.common.data;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    @KeepForSdk
    @Nullable
    protected final DataHolder mDataHolder;

    @KeepForSdk
    protected AbstractDataBuffer(@Nullable DataHolder dataHolder) {
        this.mDataHolder = dataHolder;
    }

    public final void close() {
        release();
    }

    @NonNull
    public abstract T get(int i);

    public int getCount() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder == null) {
            return 0;
        }
        return dataHolder.getCount();
    }

    @Nullable
    public final Bundle getMetadata() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder == null) {
            return null;
        }
        return dataHolder.getMetadata();
    }

    @Deprecated
    public boolean isClosed() {
        DataHolder dataHolder = this.mDataHolder;
        return dataHolder == null || dataHolder.isClosed();
    }

    @NonNull
    public Iterator<T> iterator() {
        return new DataBufferIterator(this);
    }

    public void release() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }

    @NonNull
    public Iterator<T> singleRefIterator() {
        return new SingleRefDataBufferIterator(this);
    }
}
