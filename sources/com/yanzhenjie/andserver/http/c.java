package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.util.h;
import java.util.List;

/* compiled from: HttpRequest */
public interface c extends a {
    long A(@NonNull String str);

    @NonNull
    List<String> c(@NonNull String str);

    @Nullable
    h getContentType();

    @Nullable
    String getHeader(@NonNull String str);

    @NonNull
    b getMethod();

    @NonNull
    String getPath();

    @NonNull
    String t();

    @Nullable
    g u(@NonNull String str);

    @NonNull
    List<h> v();

    @NonNull
    List<String> w();

    @NonNull
    List<String> x();

    @NonNull
    List<String> y(@NonNull String str);

    @Nullable
    f z();
}
