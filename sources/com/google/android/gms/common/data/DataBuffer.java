package com.google.android.gms.common.data;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Releasable;
import java.io.Closeable;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public interface DataBuffer<T> extends Iterable<T>, Releasable, Closeable {
    void close();

    @NonNull
    T get(int i);

    int getCount();

    @KeepForSdk
    @Nullable
    Bundle getMetadata();

    @Deprecated
    boolean isClosed();

    @NonNull
    Iterator<T> iterator();

    void release();

    @NonNull
    Iterator<T> singleRefIterator();
}
