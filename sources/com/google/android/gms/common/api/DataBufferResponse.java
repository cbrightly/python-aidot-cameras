package com.google.android.gms.common.api;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class DataBufferResponse<T, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {
    @KeepForSdk
    public DataBufferResponse() {
    }

    public final void close() {
        ((AbstractDataBuffer) getResult()).close();
    }

    @NonNull
    public final T get(int i) {
        return ((AbstractDataBuffer) getResult()).get(i);
    }

    public final int getCount() {
        return ((AbstractDataBuffer) getResult()).getCount();
    }

    @Nullable
    public final Bundle getMetadata() {
        return ((AbstractDataBuffer) getResult()).getMetadata();
    }

    public final boolean isClosed() {
        return ((AbstractDataBuffer) getResult()).isClosed();
    }

    @NonNull
    public final Iterator<T> iterator() {
        return ((AbstractDataBuffer) getResult()).iterator();
    }

    public final void release() {
        ((AbstractDataBuffer) getResult()).release();
    }

    @NonNull
    public final Iterator<T> singleRefIterator() {
        return ((AbstractDataBuffer) getResult()).singleRefIterator();
    }

    @KeepForSdk
    public DataBufferResponse(@NonNull R result) {
        super(result);
    }
}
