package com.bumptech.glide.load.resource.transcode;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.resource.transcode.TranscoderRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: TranscoderRegistry */
public class f {
    private final List<a<?, ?>> a = new ArrayList();

    public synchronized <Z, R> void c(@NonNull Class<Z> decodedClass, @NonNull Class<R> transcodedClass, @NonNull e<Z, R> transcoder) {
        this.a.add(new a(decodedClass, transcodedClass, transcoder));
    }

    @NonNull
    public synchronized <Z, R> e<Z, R> a(@NonNull Class<Z> resourceClass, @NonNull Class<R> transcodedClass) {
        if (transcodedClass.isAssignableFrom(resourceClass)) {
            return g.b();
        }
        Iterator<a<?, ?>> it = this.a.iterator();
        while (it.hasNext()) {
            TranscoderRegistry.Entry<?, ?> entry = (a) it.next();
            if (entry.a(resourceClass, transcodedClass)) {
                return entry.c;
            }
        }
        throw new IllegalArgumentException("No transcoder registered to transcode from " + resourceClass + " to " + transcodedClass);
    }

    @NonNull
    public synchronized <Z, R> List<Class<R>> b(@NonNull Class<Z> resourceClass, @NonNull Class<R> transcodeClass) {
        List<Class<R>> transcodeClasses = new ArrayList<>();
        if (transcodeClass.isAssignableFrom(resourceClass)) {
            transcodeClasses.add(transcodeClass);
            return transcodeClasses;
        }
        Iterator<a<?, ?>> it = this.a.iterator();
        while (it.hasNext()) {
            if (((a) it.next()).a(resourceClass, transcodeClass)) {
                transcodeClasses.add(transcodeClass);
            }
        }
        return transcodeClasses;
    }

    /* compiled from: TranscoderRegistry */
    public static final class a<Z, R> {
        private final Class<Z> a;
        private final Class<R> b;
        final e<Z, R> c;

        a(@NonNull Class<Z> fromClass, @NonNull Class<R> toClass, @NonNull e<Z, R> transcoder) {
            this.a = fromClass;
            this.b = toClass;
            this.c = transcoder;
        }

        public boolean a(@NonNull Class<?> fromClass, @NonNull Class<?> toClass) {
            return this.a.isAssignableFrom(fromClass) && toClass.isAssignableFrom(this.b);
        }
    }
}
