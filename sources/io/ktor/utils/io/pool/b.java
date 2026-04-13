package io.ktor.utils.io.pool;

import io.ktor.utils.io.pool.d;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DefaultPool.kt */
public abstract class b<T> implements d<T> {
    private static final AtomicLongFieldUpdater<b<?>> c;
    public static final a d = new a((DefaultConstructorMarker) null);
    private final int f;
    private final int q;
    /* access modifiers changed from: private */
    public volatile long top;
    private final AtomicReferenceArray<T> x;
    private final int[] y;
    private final int z;

    /* access modifiers changed from: protected */
    @NotNull
    public abstract T l();

    public b(int capacity) {
        this.z = capacity;
        boolean z2 = false;
        if (capacity > 0) {
            if (capacity <= 536870911 ? true : z2) {
                int highestOneBit = Integer.highestOneBit((capacity * 4) - 1) * 2;
                this.f = highestOneBit;
                this.q = Integer.numberOfLeadingZeros(highestOneBit) + 1;
                this.x = new AtomicReferenceArray<>(highestOneBit + 1);
                this.y = new int[(highestOneBit + 1)];
                return;
            }
            throw new IllegalArgumentException(("capacity should be less or equal to 536870911 but it is " + capacity).toString());
        }
        throw new IllegalArgumentException(("capacity should be positive but it is " + capacity).toString());
    }

    public void close() {
        d.a.a(this);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public T g(@NotNull T instance) {
        k.f(instance, "instance");
        return instance;
    }

    /* access modifiers changed from: protected */
    public void r(@NotNull T instance) {
        k.f(instance, "instance");
    }

    /* access modifiers changed from: protected */
    public void i(@NotNull T instance) {
        k.f(instance, "instance");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = g(r0);
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final T p0() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.n()
            if (r0 == 0) goto L_0x000e
            r1 = 0
            java.lang.Object r0 = r2.g(r0)
            if (r0 == 0) goto L_0x000e
            goto L_0x0012
        L_0x000e:
            java.lang.Object r0 = r2.l()
        L_0x0012:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.pool.b.p0():java.lang.Object");
    }

    public final void N0(@NotNull T instance) {
        k.f(instance, "instance");
        r(instance);
        if (!o(instance)) {
            i(instance);
        }
    }

    public final void dispose() {
        while (true) {
            Object instance = n();
            if (instance != null) {
                i(instance);
            } else {
                return;
            }
        }
    }

    private final boolean o(T instance) {
        int index = ((System.identityHashCode(instance) * -1640531527) >>> this.q) + 1;
        for (int i = 0; i < 8; i++) {
            int i2 = i;
            if (this.x.compareAndSet(index, (Object) null, instance)) {
                m(index);
                return true;
            }
            index--;
            if (index == 0) {
                index = this.f;
            }
        }
        return false;
    }

    private final T n() {
        int index = j();
        if (index == 0) {
            return null;
        }
        return this.x.getAndSet(index, (Object) null);
    }

    private final void m(int index) {
        long top2;
        long newTop;
        if (index > 0) {
            do {
                top2 = this.top;
                newTop = ((((top2 >> 32) & 4294967295L) + 1) << 32) | ((long) index);
                this.y[index] = (int) (top2 & 4294967295L);
            } while (!c.compareAndSet(this, top2, newTop));
            return;
        }
        throw new IllegalArgumentException("index should be positive".toString());
    }

    private final int j() {
        long top2;
        long newVersion;
        int topIndex;
        do {
            top2 = this.top;
            if (top2 == 0) {
                return 0;
            }
            newVersion = 1 + ((top2 >> 32) & 4294967295L);
            topIndex = (int) (top2 & 4294967295L);
            if (topIndex == 0) {
                return 0;
            }
        } while (!c.compareAndSet(this, top2, (newVersion << 32) | ((long) this.y[topIndex])));
        return topIndex;
    }

    /* compiled from: DefaultPool.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        AtomicLongFieldUpdater<b<?>> newUpdater = AtomicLongFieldUpdater.newUpdater(b.class, a.INSTANCE.getName());
        k.b(newUpdater, "AtomicLongFieldUpdater.n…wner::class.java, p.name)");
        c = newUpdater;
    }
}
