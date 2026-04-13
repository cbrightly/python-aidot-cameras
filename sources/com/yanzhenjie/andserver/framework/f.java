package com.yanzhenjie.andserver.framework;

import android.util.Log;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.http.b;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.d;
import com.yanzhenjie.andserver.http.e;

/* compiled from: ModifiedInterceptor */
public class f implements c {
    public boolean a(@NonNull c request, @NonNull d response, @NonNull com.yanzhenjie.andserver.framework.handler.f handler) {
        b method = request.getMethod();
        if (method != b.GET && method != b.HEAD) {
            return false;
        }
        String eTag = null;
        try {
            eTag = handler.f(request);
        } catch (Throwable e) {
            Log.w("AndServer", e);
        }
        long lastModified = -1;
        try {
            lastModified = handler.e(request);
        } catch (Throwable e2) {
            Log.w("AndServer", e2);
        }
        return new e(request, response).d(eTag, lastModified);
    }
}
