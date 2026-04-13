package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.m;
import java.util.Map;

/* compiled from: EngineKeyFactory */
public class n {
    n() {
    }

    /* access modifiers changed from: package-private */
    public m a(Object model, f signature, int width, int height, Map<Class<?>, m<?>> transformations, Class<?> resourceClass, Class<?> transcodeClass, i options) {
        return new m(model, signature, width, height, transformations, resourceClass, transcodeClass, options);
    }
}
