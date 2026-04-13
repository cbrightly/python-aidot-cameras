package kotlin.reflect.jvm.internal.impl.utils;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartSet.kt */
public final class j<T> extends AbstractSet<T> {
    public static final b c = new b((DefaultConstructorMarker) null);
    private Object d;
    private int f;

    @NotNull
    public static final <T> j<T> a() {
        return c.a();
    }

    /* compiled from: SmartSet.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final <T> j<T> a() {
            return new j<>((DefaultConstructorMarker) null);
        }

        @NotNull
        public final <T> j<T> b(@NotNull Collection<? extends T> set) {
            k.f(set, "set");
            j $this$apply = new j((DefaultConstructorMarker) null);
            $this$apply.addAll(set);
            return $this$apply;
        }
    }

    private j() {
    }

    public /* synthetic */ j(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    public final /* bridge */ int size() {
        return b();
    }

    public int b() {
        return this.f;
    }

    public void d(int i) {
        this.f = i;
    }

    @NotNull
    public Iterator<T> iterator() {
        if (size() == 0) {
            return Collections.emptySet().iterator();
        }
        if (size() == 1) {
            return new c(this.d);
        }
        if (size() < 5) {
            Object obj = this.d;
            if (obj != null) {
                return new a((Object[]) obj);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Object obj2 = this.d;
        if (obj2 != null) {
            return e0.d(obj2).iterator();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableSet<T>");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.util.LinkedHashSet} */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean add(T r7) {
        /*
            r6 = this;
            int r0 = r6.size()
            r1 = 1
            if (r0 != 0) goto L_0x000c
            r6.d = r7
            goto L_0x0080
        L_0x000c:
            int r0 = r6.size()
            r2 = 0
            if (r0 != r1) goto L_0x0028
            java.lang.Object r0 = r6.d
            boolean r0 = kotlin.jvm.internal.k.a(r0, r7)
            if (r0 == 0) goto L_0x001c
            return r2
        L_0x001c:
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Object r3 = r6.d
            r0[r2] = r3
            r0[r1] = r7
            r6.d = r0
            goto L_0x0080
        L_0x0028:
            int r0 = r6.size()
            r3 = 5
            if (r0 >= r3) goto L_0x0071
            java.lang.Object r0 = r6.d
            if (r0 == 0) goto L_0x0069
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            boolean r3 = kotlin.collections.l.r(r0, r7)
            if (r3 == 0) goto L_0x003c
            return r2
        L_0x003c:
            int r2 = r6.size()
            r3 = 4
            if (r2 != r3) goto L_0x0052
            int r2 = r0.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r0, r2)
            java.util.LinkedHashSet r2 = kotlin.collections.o0.e(r2)
            r3 = r2
            r4 = 0
            r3.add(r7)
            goto L_0x0066
        L_0x0052:
            int r2 = r6.size()
            int r2 = r2 + r1
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r0, r2)
            java.lang.String r3 = "java.util.Arrays.copyOf(this, newSize)"
            kotlin.jvm.internal.k.b(r2, r3)
            r3 = r2
            r4 = 0
            int r5 = r3.length
            int r5 = r5 - r1
            r3[r5] = r7
        L_0x0066:
            r6.d = r2
            goto L_0x0080
        L_0x0069:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type kotlin.Array<T>"
            r0.<init>(r1)
            throw r0
        L_0x0071:
            java.lang.Object r0 = r6.d
            if (r0 == 0) goto L_0x008a
            java.util.Set r0 = kotlin.jvm.internal.e0.d(r0)
            boolean r3 = r0.add(r7)
            if (r3 != 0) goto L_0x0080
            return r2
        L_0x0080:
            int r0 = r6.size()
            int r0 = r0 + r1
            r6.d(r0)
            return r1
        L_0x008a:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type kotlin.collections.MutableSet<T>"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.utils.j.add(java.lang.Object):boolean");
    }

    public void clear() {
        this.d = null;
        d(0);
    }

    public boolean contains(Object element) {
        if (size() == 0) {
            return false;
        }
        if (size() == 1) {
            return k.a(this.d, element);
        }
        if (size() < 5) {
            Object obj = this.d;
            if (obj != null) {
                return l.r((Object[]) obj, element);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Object obj2 = this.d;
        if (obj2 != null) {
            return ((Set) obj2).contains(element);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Set<T>");
    }

    /* compiled from: SmartSet.kt */
    public static final class c<T> implements Iterator<T>, kotlin.jvm.internal.markers.a {
        private boolean c = true;
        private final T d;

        public c(T element) {
            this.d = element;
        }

        public T next() {
            if (this.c) {
                this.c = false;
                return this.d;
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            return this.c;
        }

        @NotNull
        /* renamed from: b */
        public Void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: SmartSet.kt */
    public static final class a<T> implements Iterator<T>, kotlin.jvm.internal.markers.a {
        private final Iterator<T> c;

        public a(@NotNull T[] array) {
            k.f(array, "array");
            this.c = kotlin.jvm.internal.b.a(array);
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        public T next() {
            return this.c.next();
        }

        @NotNull
        /* renamed from: b */
        public Void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
