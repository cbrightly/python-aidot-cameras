package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.util.h;
import java.io.OutputStream;

/* compiled from: ResponseBody */
public interface i {
    long a();

    @Nullable
    h d();

    void writeTo(@NonNull OutputStream outputStream);
}
