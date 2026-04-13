package com.yanzhenjie.andserver.framework.website;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.framework.d;
import com.yanzhenjie.andserver.framework.handler.f;
import com.yanzhenjie.andserver.http.i;

/* compiled from: Website */
public abstract class c implements com.yanzhenjie.andserver.framework.handler.a, com.yanzhenjie.andserver.framework.a, d {
    @NonNull
    public abstract i g(@NonNull com.yanzhenjie.andserver.http.c cVar, @NonNull com.yanzhenjie.andserver.http.d dVar);

    @Nullable
    public String f(@NonNull com.yanzhenjie.andserver.http.c request) {
        return null;
    }

    public long e(@NonNull com.yanzhenjie.andserver.http.c request) {
        return 0;
    }

    /* compiled from: Website */
    public class a implements f {
        a() {
        }

        @Nullable
        public String f(@NonNull com.yanzhenjie.andserver.http.c request) {
            return c.this.f(request);
        }

        public long e(@NonNull com.yanzhenjie.andserver.http.c request) {
            return c.this.e(request);
        }

        public com.yanzhenjie.andserver.framework.view.c c(@NonNull com.yanzhenjie.andserver.http.c request, @NonNull com.yanzhenjie.andserver.http.d response) {
            return new com.yanzhenjie.andserver.framework.view.a(c.this.g(request, response));
        }
    }

    @Nullable
    public f d(@NonNull com.yanzhenjie.andserver.http.c request) {
        return new a();
    }
}
