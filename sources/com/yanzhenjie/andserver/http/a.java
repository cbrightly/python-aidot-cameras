package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: HttpContext */
public interface a {
    @Nullable
    Object getAttribute(@NonNull String str);

    void setAttribute(@NonNull String str, @Nullable Object obj);
}
