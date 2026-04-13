package com.bumptech.glide.load.engine.bitmap_recycle;

import android.util.Log;
import androidx.annotation.Nullable;
import com.bumptech.glide.util.i;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* compiled from: LruArrayPool */
public final class j implements b {
    private final h<a, Object> a = new h<>();
    private final b b = new b();
    private final Map<Class<?>, NavigableMap<Integer, Integer>> c = new HashMap();
    private final Map<Class<?>, a<?>> d = new HashMap();
    private final int e;
    private int f;

    public j(int maxSize) {
        this.e = maxSize;
    }

    @Deprecated
    public <T> void g(T array, Class<T> cls) {
        put(array);
    }

    public synchronized <T> void put(T array) {
        Class<?> cls = array.getClass();
        ArrayAdapterInterface<T> arrayAdapter = j(cls);
        int size = arrayAdapter.b(array);
        int arrayBytes = arrayAdapter.a() * size;
        if (o(arrayBytes)) {
            a key = this.b.e(size, cls);
            this.a.d(key, array);
            NavigableMap<Integer, Integer> sizes = m(cls);
            Integer current = (Integer) sizes.get(Integer.valueOf(key.b));
            Integer valueOf = Integer.valueOf(key.b);
            int i = 1;
            if (current != null) {
                i = 1 + current.intValue();
            }
            sizes.put(valueOf, Integer.valueOf(i));
            this.f += arrayBytes;
            c();
        }
    }

    public synchronized <T> T f(int size, Class<T> arrayClass) {
        return l(this.b.e(size, arrayClass), arrayClass);
    }

    public synchronized <T> T e(int size, Class<T> arrayClass) {
        a key;
        Integer possibleSize = m(arrayClass).ceilingKey(Integer.valueOf(size));
        if (p(size, possibleSize)) {
            key = this.b.e(possibleSize.intValue(), arrayClass);
        } else {
            key = this.b.e(size, arrayClass);
        }
        return l(key, arrayClass);
    }

    private <T> T l(a key, Class<T> arrayClass) {
        ArrayAdapterInterface<T> arrayAdapter = j(arrayClass);
        T result = k(key);
        if (result != null) {
            this.f -= arrayAdapter.b(result) * arrayAdapter.a();
            b(arrayAdapter.b(result), arrayClass);
        }
        if (result != null) {
            return result;
        }
        if (Log.isLoggable(arrayAdapter.getTag(), 2)) {
            Log.v(arrayAdapter.getTag(), "Allocated " + key.b + " bytes");
        }
        return arrayAdapter.newArray(key.b);
    }

    @Nullable
    private <T> T k(a key) {
        return this.a.a(key);
    }

    private boolean o(int byteSize) {
        return byteSize <= this.e / 2;
    }

    private boolean p(int requestedSize, Integer actualSize) {
        return actualSize != null && (n() || actualSize.intValue() <= requestedSize * 8);
    }

    private boolean n() {
        int i = this.f;
        return i == 0 || this.e / i >= 2;
    }

    public synchronized void d() {
        h(0);
    }

    public synchronized void a(int level) {
        if (level >= 40) {
            try {
                d();
            } catch (Throwable th) {
                throw th;
            }
        } else if (level >= 20 || level == 15) {
            h(this.e / 2);
        }
    }

    private void c() {
        h(this.e);
    }

    private void h(int size) {
        while (this.f > size) {
            Object evicted = this.a.f();
            i.d(evicted);
            ArrayAdapterInterface<Object> arrayAdapter = i(evicted);
            this.f -= arrayAdapter.b(evicted) * arrayAdapter.a();
            b(arrayAdapter.b(evicted), evicted.getClass());
            if (Log.isLoggable(arrayAdapter.getTag(), 2)) {
                Log.v(arrayAdapter.getTag(), "evicted: " + arrayAdapter.b(evicted));
            }
        }
    }

    private void b(int size, Class<?> arrayClass) {
        NavigableMap<Integer, Integer> sizes = m(arrayClass);
        Integer current = (Integer) sizes.get(Integer.valueOf(size));
        if (current == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + size + ", this: " + this);
        } else if (current.intValue() == 1) {
            sizes.remove(Integer.valueOf(size));
        } else {
            sizes.put(Integer.valueOf(size), Integer.valueOf(current.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> m(Class<?> arrayClass) {
        NavigableMap<Integer, Integer> sizes = this.c.get(arrayClass);
        if (sizes != null) {
            return sizes;
        }
        NavigableMap<Integer, Integer> sizes2 = new TreeMap<>();
        this.c.put(arrayClass, sizes2);
        return sizes2;
    }

    private <T> a<T> i(T object) {
        return j(object.getClass());
    }

    private <T> a<T> j(Class<T> arrayPoolClass) {
        ArrayAdapterInterface<?> adapter = (a) this.d.get(arrayPoolClass);
        if (adapter == null) {
            if (arrayPoolClass.equals(int[].class)) {
                adapter = new i();
            } else if (arrayPoolClass.equals(byte[].class)) {
                adapter = new g();
            } else {
                throw new IllegalArgumentException("No array pool found for: " + arrayPoolClass.getSimpleName());
            }
            this.d.put(arrayPoolClass, adapter);
        }
        return adapter;
    }

    /* compiled from: LruArrayPool */
    public static final class b extends d<a> {
        b() {
        }

        /* access modifiers changed from: package-private */
        public a e(int size, Class<?> arrayClass) {
            a result = (a) b();
            result.b(size, arrayClass);
            return result;
        }

        /* access modifiers changed from: protected */
        /* renamed from: d */
        public a a() {
            return new a(this);
        }
    }

    /* compiled from: LruArrayPool */
    public static final class a implements m {
        private final b a;
        int b;
        private Class<?> c;

        a(b pool) {
            this.a = pool;
        }

        /* access modifiers changed from: package-private */
        public void b(int length, Class<?> arrayClass) {
            this.b = length;
            this.c = arrayClass;
        }

        public boolean equals(Object o) {
            if (!(o instanceof a)) {
                return false;
            }
            a other = (a) o;
            if (this.b == other.b && this.c == other.c) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "Key{size=" + this.b + "array=" + this.c + '}';
        }

        public void a() {
            this.a.c(this);
        }

        public int hashCode() {
            int i = this.b * 31;
            Class<?> cls = this.c;
            return i + (cls != null ? cls.hashCode() : 0);
        }
    }
}
