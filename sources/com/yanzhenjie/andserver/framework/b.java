package com.yanzhenjie.andserver.framework;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.yanzhenjie.andserver.error.HttpException;
import com.yanzhenjie.andserver.error.MethodNotSupportException;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.d;
import java.util.List;

/* compiled from: ExceptionResolver */
public interface b {
    public static final b a = new a();

    void a(@NonNull c cVar, @NonNull d dVar, @NonNull Throwable th);

    /* renamed from: com.yanzhenjie.andserver.framework.b$b  reason: collision with other inner class name */
    /* compiled from: ExceptionResolver */
    public static class C0230b implements b {
        private final b b;

        public C0230b(b resolver) {
            this.b = resolver;
        }

        public void a(@NonNull c request, @NonNull d response, @NonNull Throwable e) {
            List<com.yanzhenjie.andserver.http.b> methods;
            if ((e instanceof MethodNotSupportException) && (methods = ((MethodNotSupportException) e).getMethods()) != null && methods.size() > 0) {
                response.setHeader(JsonDocumentFields.EFFECT_VALUE_ALLOW, TextUtils.join(", ", methods));
            }
            this.b.a(request, response, e);
        }
    }

    /* compiled from: ExceptionResolver */
    public static final class a implements b {
        a() {
        }

        public void a(@NonNull c request, @NonNull d response, @NonNull Throwable e) {
            if (e instanceof HttpException) {
                response.e(((HttpException) e).getStatusCode());
            } else {
                response.e(500);
            }
            response.b(new com.yanzhenjie.andserver.framework.body.b(e.getMessage()));
        }
    }
}
