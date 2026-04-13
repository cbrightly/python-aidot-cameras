package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.data.e;
import com.bumptech.glide.util.i;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DataRewinderRegistry */
public class f {
    private static final e.a<?> a = new a();
    private final Map<Class<?>, e.a<?>> b = new HashMap();

    /* compiled from: DataRewinderRegistry */
    public class a implements e.a<Object> {
        a() {
        }

        @NonNull
        public e<Object> b(@NonNull Object data) {
            return new b(data);
        }

        @NonNull
        public Class<Object> a() {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

    public synchronized void b(@NonNull e.a<?> factory) {
        this.b.put(factory.a(), factory);
    }

    @NonNull
    public synchronized <T> e<T> a(@NonNull T data) {
        DataRewinder.Factory<T> result;
        i.d(data);
        result = (e.a) this.b.get(data.getClass());
        if (result == null) {
            Iterator<e.a<?>> it = this.b.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DataRewinder.Factory<T> registeredFactory = (e.a) it.next();
                if (registeredFactory.a().isAssignableFrom(data.getClass())) {
                    result = registeredFactory;
                    break;
                }
            }
        }
        if (result == null) {
            result = a;
        }
        return result.b(data);
    }

    /* compiled from: DataRewinderRegistry */
    public static final class b implements e<Object> {
        private final Object a;

        b(@NonNull Object data) {
            this.a = data;
        }

        @NonNull
        public Object a() {
            return this.a;
        }

        public void b() {
        }
    }
}
