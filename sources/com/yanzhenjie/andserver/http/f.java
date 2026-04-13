package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.util.h;
import java.io.InputStream;

/* compiled from: RequestBody */
public interface f {
    String a();

    @NonNull
    String b();

    @Nullable
    h d();

    long length();

    @NonNull
    InputStream stream();
}
