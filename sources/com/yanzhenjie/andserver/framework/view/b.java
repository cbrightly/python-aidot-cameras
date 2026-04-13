package com.yanzhenjie.andserver.framework.view;

import androidx.annotation.Nullable;

/* compiled from: ObjectView */
public class b implements c {
    private final boolean a;
    private final Object b;

    public b(boolean isRest, Object output) {
        this.a = isRest;
        this.b = output;
    }

    public boolean a() {
        return this.a;
    }

    @Nullable
    public Object b() {
        return this.b;
    }
}
