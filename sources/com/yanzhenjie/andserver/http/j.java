package com.yanzhenjie.andserver.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.apache.httpcore.protocol.d;

/* compiled from: StandardContext */
public class j implements a {
    private d a;

    public j(d context) {
        this.a = context;
    }

    @Nullable
    public Object getAttribute(@NonNull String id) {
        return this.a.getAttribute(id);
    }

    public void setAttribute(@NonNull String id, @NonNull Object obj) {
        this.a.setAttribute(id, obj);
    }
}
