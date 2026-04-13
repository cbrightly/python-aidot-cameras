package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.l;
import com.bumptech.glide.provider.ResourceEncoderRegistry;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ResourceEncoderRegistry */
public class f {
    private final List<a<?>> a = new ArrayList();

    public synchronized <Z> void a(@NonNull Class<Z> resourceClass, @NonNull l<Z> encoder) {
        this.a.add(new a(resourceClass, encoder));
    }

    public synchronized <Z> void c(@NonNull Class<Z> resourceClass, @NonNull l<Z> encoder) {
        this.a.add(0, new a(resourceClass, encoder));
    }

    @Nullable
    public synchronized <Z> l<Z> b(@NonNull Class<Z> resourceClass) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            ResourceEncoderRegistry.Entry<?> entry = (a) this.a.get(i);
            if (entry.a(resourceClass)) {
                return entry.b;
            }
        }
        return null;
    }

    /* compiled from: ResourceEncoderRegistry */
    public static final class a<T> {
        private final Class<T> a;
        final l<T> b;

        a(@NonNull Class<T> resourceClass, @NonNull l<T> encoder) {
            this.a = resourceClass;
            this.b = encoder;
        }

        /* access modifiers changed from: package-private */
        public boolean a(@NonNull Class<?> resourceClass) {
            return this.a.isAssignableFrom(resourceClass);
        }
    }
}
