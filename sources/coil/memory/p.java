package coil.memory;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.VisibleForTesting;
import coil.memory.MemoryCache;
import coil.memory.o;
import coil.util.m;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WeakMemoryCache.kt */
public final class p implements u {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @Nullable
    private final m b;
    @NotNull
    private final HashMap<MemoryCache.Key, ArrayList<c>> c = new HashMap<>();
    private int d;

    public p(@Nullable m logger) {
        this.b = logger;
    }

    @NotNull
    public final HashMap<MemoryCache.Key, ArrayList<c>> h() {
        return this.c;
    }

    @Nullable
    public synchronized o.a b(@NotNull MemoryCache.Key key) {
        b bVar;
        k.e(key, CacheEntity.KEY);
        List values = (ArrayList) this.c.get(key);
        b strongValue = null;
        if (values == null) {
            return null;
        }
        List $this$firstNotNullIndices$iv = values;
        int i = 0;
        int size = $this$firstNotNullIndices$iv.size() - 1;
        if (size >= 0) {
            while (true) {
                int i$iv = i;
                i++;
                c value = (c) $this$firstNotNullIndices$iv.get(i$iv);
                Bitmap it = (Bitmap) value.a().get();
                if (it == null) {
                    bVar = null;
                } else {
                    bVar = new b(it, value.d());
                }
                if (bVar == null) {
                    if (i > size) {
                        break;
                    }
                } else {
                    strongValue = bVar;
                    break;
                }
            }
        }
        g();
        return strongValue;
    }

    public synchronized void d(@NotNull MemoryCache.Key key, @NotNull Bitmap bitmap, boolean isSampled, int size) {
        Object answer$iv;
        k.e(key, CacheEntity.KEY);
        k.e(bitmap, "bitmap");
        Map $this$getOrPut$iv = this.c;
        Object value$iv = $this$getOrPut$iv.get(key);
        if (value$iv == null) {
            answer$iv = new ArrayList();
            $this$getOrPut$iv.put(key, answer$iv);
        } else {
            answer$iv = value$iv;
        }
        ArrayList values = (ArrayList) answer$iv;
        int identityHashCode = System.identityHashCode(bitmap);
        c newValue = new c(identityHashCode, new WeakReference(bitmap), isSampled, size);
        int i = 0;
        int size2 = values.size() - 1;
        if (size2 >= 0) {
            while (true) {
                int index = i;
                i++;
                Object obj = values.get(index);
                k.d(obj, "values[index]");
                c value = (c) obj;
                if (size < value.c()) {
                    if (i > size2) {
                        break;
                    }
                } else if (value.b() == identityHashCode && value.a().get() == bitmap) {
                    values.set(index, newValue);
                } else {
                    values.add(index, newValue);
                }
            }
            values.add(newValue);
        } else {
            values.add(newValue);
        }
        g();
    }

    public synchronized boolean c(@NotNull Bitmap bitmap) {
        boolean z;
        boolean removed;
        k.e(bitmap, "bitmap");
        int identityHashCode = System.identityHashCode(bitmap);
        Iterable $this$forEach$iv = h().values();
        k.d($this$forEach$iv, "cache.values");
        Iterator<T> it = $this$forEach$iv.iterator();
        loop0:
        while (true) {
            z = true;
            int i = 0;
            if (!it.hasNext()) {
                z = false;
                break;
            }
            ArrayList values = (ArrayList) it.next();
            int size = values.size() - 1;
            if (size >= 0) {
                do {
                    int index = i;
                    i++;
                    if (((c) values.get(index)).b() == identityHashCode) {
                        values.remove(index);
                        break loop0;
                    }
                } while (i <= size);
            }
        }
        removed = z;
        g();
        return removed;
    }

    public synchronized void a(int level) {
        m $this$log$iv = this.b;
        if ($this$log$iv != null) {
            if ($this$log$iv.b() <= 2) {
                $this$log$iv.a("RealWeakMemoryCache", 2, k.l("trimMemory, level=", Integer.valueOf(level)), (Throwable) null);
            }
        }
        if (level >= 10 && level != 20) {
            e();
        }
    }

    private final void g() {
        int i = this.d;
        this.d = i + 1;
        if (i >= 10) {
            e();
        }
    }

    @VisibleForTesting
    public final void e() {
        this.d = 0;
        Iterator iterator = this.c.values().iterator();
        while (iterator.hasNext()) {
            ArrayList<c> next = iterator.next();
            k.d(next, "iterator.next()");
            ArrayList list = next;
            if (list.size() <= 1) {
                c cVar = (c) y.U(list);
                if ((cVar == null ? null : (Bitmap) cVar.a().get()) == null) {
                    iterator.remove();
                }
            } else {
                if (Build.VERSION.SDK_INT >= 24) {
                    list.removeIf(a.a);
                } else {
                    List $this$removeIfIndices$iv = list;
                    int numDeleted$iv = 0;
                    int size = $this$removeIfIndices$iv.size() - 1;
                    if (size >= 0) {
                        int i = 0;
                        do {
                            int rawIndex$iv = i;
                            i++;
                            int index$iv = rawIndex$iv - numDeleted$iv;
                            if ((((c) $this$removeIfIndices$iv.get(index$iv)).a().get() == null ? 1 : null) != null) {
                                $this$removeIfIndices$iv.remove(index$iv);
                                numDeleted$iv++;
                                continue;
                            }
                        } while (i <= size);
                    }
                }
                if (list.isEmpty()) {
                    iterator.remove();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static final boolean f(c it) {
        k.e(it, "it");
        return it.a().get() == null;
    }

    @VisibleForTesting
    /* compiled from: WeakMemoryCache.kt */
    public static final class c {
        private final int a;
        @NotNull
        private final WeakReference<Bitmap> b;
        private final boolean c;
        private final int d;

        public c(int identityHashCode, @NotNull WeakReference<Bitmap> bitmap, boolean isSampled, int size) {
            k.e(bitmap, "bitmap");
            this.a = identityHashCode;
            this.b = bitmap;
            this.c = isSampled;
            this.d = size;
        }

        public final int b() {
            return this.a;
        }

        @NotNull
        public final WeakReference<Bitmap> a() {
            return this.b;
        }

        public final boolean d() {
            return this.c;
        }

        public final int c() {
            return this.d;
        }
    }

    /* compiled from: WeakMemoryCache.kt */
    public static final class b implements o.a {
        @NotNull
        private final Bitmap a;
        private final boolean b;

        public b(@NotNull Bitmap bitmap, boolean isSampled) {
            k.e(bitmap, "bitmap");
            this.a = bitmap;
            this.b = isSampled;
        }

        @NotNull
        public Bitmap b() {
            return this.a;
        }

        public boolean a() {
            return this.b;
        }
    }

    /* compiled from: WeakMemoryCache.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
