package com.yanzhenjie.andserver.framework.handler;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.framework.mapping.a;
import com.yanzhenjie.andserver.framework.mapping.b;
import com.yanzhenjie.andserver.framework.mapping.f;
import com.yanzhenjie.andserver.http.d;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.tyrus.spi.UpgradeRequest;

/* compiled from: MappingHandler */
public abstract class c implements d {
    private final Object h;
    private final b i;
    private final a j;
    private final com.yanzhenjie.andserver.framework.cross.a k;

    /* access modifiers changed from: protected */
    public abstract com.yanzhenjie.andserver.framework.view.c g(com.yanzhenjie.andserver.http.c cVar, d dVar);

    public c(@NonNull Object host, @NonNull b mapping, @NonNull a addition, @Nullable com.yanzhenjie.andserver.framework.cross.a crossOrigin) {
        this.h = host;
        this.i = mapping;
        this.j = addition;
    }

    public String f(@NonNull com.yanzhenjie.andserver.http.c request) {
        Object o = a();
        if (o instanceof com.yanzhenjie.andserver.framework.a) {
            return ((com.yanzhenjie.andserver.framework.a) o).f(request);
        }
        return null;
    }

    public long e(@NonNull com.yanzhenjie.andserver.http.c request) {
        Object o = a();
        if (o instanceof com.yanzhenjie.andserver.framework.d) {
            return ((com.yanzhenjie.andserver.framework.d) o).e(request);
        }
        return -1;
    }

    @Nullable
    public com.yanzhenjie.andserver.framework.cross.a b() {
        return this.k;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Object a() {
        return this.h;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Map<String, String> d(@NonNull String httpPath) {
        List<f.b> d = f.d(httpPath);
        for (f.a rule : this.i.e().b()) {
            List<f.b> a = rule.a();
            if (d.size() == a.size()) {
                if (f.c(a).equals(httpPath)) {
                    return Collections.emptyMap();
                }
                boolean matches = true;
                boolean isBlurred = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= a.size()) {
                        break;
                    }
                    f.b segment = a.get(i2);
                    boolean blurred = segment.b();
                    isBlurred = isBlurred || blurred;
                    if (!segment.equals(d.get(i2)) && !blurred) {
                        matches = false;
                        break;
                    }
                    i2++;
                }
                if (matches && isBlurred) {
                    Map<String, String> map = new HashMap<>();
                    for (int i3 = 0; i3 < a.size(); i3++) {
                        f.b segment2 = a.get(i3);
                        if (segment2.b()) {
                            String key = segment2.a();
                            map.put(key.substring(1, key.length() - 1), d.get(i3).a());
                        }
                    }
                    return map;
                }
            }
        }
        return Collections.emptyMap();
    }

    public com.yanzhenjie.andserver.framework.view.c c(@NonNull com.yanzhenjie.andserver.http.c request, @NonNull d response) {
        if (TextUtils.isEmpty(request.getHeader(UpgradeRequest.ORIGIN_HEADER)) || this.k == null) {
            return g(request, response);
        }
        request.getMethod();
        this.k.d();
        throw null;
    }
}
