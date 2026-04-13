package com.airbnb.lottie.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.Closeable;
import java.io.InputStream;

/* compiled from: LottieFetchResult */
public interface d extends Closeable {
    @NonNull
    InputStream Y();

    @Nullable
    String d();

    boolean h0();

    @Nullable
    String p();
}
