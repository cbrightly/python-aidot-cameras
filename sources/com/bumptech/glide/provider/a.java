package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.d;
import com.bumptech.glide.provider.EncoderRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: EncoderRegistry */
public class a {
    private final List<C0044a<?>> a = new ArrayList();

    @Nullable
    public synchronized <T> d<T> b(@NonNull Class<T> dataClass) {
        Iterator<C0044a<?>> it = this.a.iterator();
        while (it.hasNext()) {
            EncoderRegistry.Entry<?> entry = (C0044a) it.next();
            if (entry.a(dataClass)) {
                return entry.b;
            }
        }
        return null;
    }

    public synchronized <T> void a(@NonNull Class<T> dataClass, @NonNull d<T> encoder) {
        this.a.add(new C0044a(dataClass, encoder));
    }

    /* renamed from: com.bumptech.glide.provider.a$a  reason: collision with other inner class name */
    /* compiled from: EncoderRegistry */
    public static final class C0044a<T> {
        private final Class<T> a;
        final d<T> b;

        C0044a(@NonNull Class<T> dataClass, @NonNull d<T> encoder) {
            this.a = dataClass;
            this.b = encoder;
        }

        /* access modifiers changed from: package-private */
        public boolean a(@NonNull Class<?> dataClass) {
            return this.a.isAssignableFrom(dataClass);
        }
    }
}
