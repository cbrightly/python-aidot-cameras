package com.yanzhenjie.andserver.http.multipart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.h;
import com.yanzhenjie.andserver.util.g;
import com.yanzhenjie.andserver.util.j;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: StandardMultipartRequest */
public class f extends h implements c {
    private c b;
    private j<String, b> c;
    private j<String, String> d;
    private Map<String, String> e;

    public f(@NonNull c request, @NonNull j<String, b> mpFiles, @NonNull j<String, String> mpParams, @NonNull Map<String, String> mpContentTypes) {
        super(request);
        this.b = request;
        this.c = new g(Collections.unmodifiableMap(mpFiles));
        this.d = new g(Collections.unmodifiableMap(mpParams));
        this.e = Collections.unmodifiableMap(mpContentTypes);
    }

    @Nullable
    public b a(String name) {
        return this.c.getFirst(name);
    }

    @NonNull
    public j<String, b> b() {
        return this.c;
    }

    @NonNull
    public List<String> w() {
        if (this.d.isEmpty()) {
            return this.b.w();
        }
        List<String> paramNames = new LinkedList<>();
        List<String> names = this.b.w();
        if (!names.isEmpty()) {
            paramNames.addAll(names);
        }
        paramNames.addAll(this.d.keySet());
        return paramNames;
    }

    @NonNull
    public List<String> y(@NonNull String name) {
        List<String> values = (List) this.d.get(name);
        return values == null ? this.b.y(name) : values;
    }
}
