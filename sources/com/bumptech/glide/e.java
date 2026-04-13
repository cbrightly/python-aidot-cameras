package com.bumptech.glide;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: GlideExperiments */
public class e {
    private final Map<Class<?>, ?> a;

    e(a builder) {
        this.a = Collections.unmodifiableMap(new HashMap(builder.a));
    }

    public boolean a(Class<?> clazz) {
        return this.a.containsKey(clazz);
    }

    /* compiled from: GlideExperiments */
    public static final class a {
        /* access modifiers changed from: private */
        public final Map<Class<?>, ?> a = new HashMap();

        a() {
        }

        /* access modifiers changed from: package-private */
        public e b() {
            return new e(this);
        }
    }
}
