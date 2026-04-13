package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.http.cookie.a;

/* compiled from: HttpResponse */
public interface d {
    void a(@NonNull String str, long j);

    void b(i iVar);

    void c(@NonNull String str);

    void d(@NonNull a aVar);

    void e(int i);

    @Nullable
    String getHeader(@NonNull String str);

    void setHeader(@NonNull String str, @NonNull String str2);
}
