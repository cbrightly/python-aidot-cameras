package kotlin.coroutines;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.meituan.robust.Constants;
import java.io.Serializable;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContextImpl.kt */
public final class c implements g, Serializable {
    private final g.b element;
    private final g left;

    /* renamed from: kotlin.coroutines.c$c  reason: collision with other inner class name */
    /* compiled from: CoroutineContextImpl.kt */
    public static final class C0321c extends l implements p<x, g.b, x> {
        final /* synthetic */ g[] $elements;
        final /* synthetic */ kotlin.jvm.internal.x $index;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0321c(g[] gVarArr, kotlin.jvm.internal.x xVar) {
            super(2);
            this.$elements = gVarArr;
            this.$index = xVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((x) obj, (g.b) obj2);
            return x.a;
        }

        public final void invoke(@NotNull x $noName_0, @NotNull g.b element) {
            k.e($noName_0, "<anonymous parameter 0>");
            k.e(element, "element");
            g[] gVarArr = this.$elements;
            kotlin.jvm.internal.x xVar = this.$index;
            int i = xVar.element;
            xVar.element = i + 1;
            gVarArr[i] = element;
        }
    }

    public c(@NotNull g left2, @NotNull g.b element2) {
        k.e(left2, "left");
        k.e(element2, "element");
        this.left = left2;
        this.element = element2;
    }

    @NotNull
    public g plus(@NotNull g context) {
        k.e(context, "context");
        return g.a.a(this, context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: kotlin.coroutines.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: kotlin.coroutines.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <E extends kotlin.coroutines.g.b> E get(@org.jetbrains.annotations.NotNull kotlin.coroutines.g.c<E> r4) {
        /*
            r3 = this;
            java.lang.String r0 = "key"
            kotlin.jvm.internal.k.e(r4, r0)
            r0 = r3
        L_0x0006:
            kotlin.coroutines.g$b r1 = r0.element
            kotlin.coroutines.g$b r1 = r1.get(r4)
            if (r1 == 0) goto L_0x0011
            r2 = 0
            return r1
        L_0x0011:
            kotlin.coroutines.g r1 = r0.left
            boolean r2 = r1 instanceof kotlin.coroutines.c
            if (r2 == 0) goto L_0x001c
            r0 = r1
            kotlin.coroutines.c r0 = (kotlin.coroutines.c) r0
            goto L_0x0006
        L_0x001c:
            kotlin.coroutines.g$b r2 = r1.get(r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.coroutines.c.get(kotlin.coroutines.g$c):kotlin.coroutines.g$b");
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        k.e(operation, "operation");
        return operation.invoke(this.left.fold(initial, operation), this.element);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        k.e(key, CacheEntity.KEY);
        if (this.element.get(key) != null) {
            return this.left;
        }
        g newLeft = this.left.minusKey(key);
        if (newLeft == this.left) {
            return this;
        }
        if (newLeft == h.INSTANCE) {
            return this.element;
        }
        return new c(newLeft, this.element);
    }

    private final int e() {
        c cur = this;
        int size = 2;
        while (true) {
            g gVar = cur.left;
            if (!(gVar instanceof c)) {
                gVar = null;
            }
            c cVar = (c) gVar;
            if (cVar == null) {
                return size;
            }
            cur = cVar;
            size++;
        }
    }

    private final boolean b(g.b element2) {
        return k.a(get(element2.getKey()), element2);
    }

    private final boolean d(c context) {
        c cur = context;
        while (b(cur.element)) {
            g next = cur.left;
            if (next instanceof c) {
                cur = (c) next;
            } else if (next != null) {
                return b((g.b) next);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
            }
        }
        return false;
    }

    public boolean equals(@Nullable Object other) {
        return this == other || ((other instanceof c) && ((c) other).e() == e() && ((c) other).d(this));
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    /* compiled from: CoroutineContextImpl.kt */
    public static final class b extends l implements p<String, g.b, String> {
        public static final b INSTANCE = new b();

        b() {
            super(2);
        }

        @NotNull
        public final String invoke(@NotNull String acc, @NotNull g.b element) {
            k.e(acc, "acc");
            k.e(element, "element");
            if (acc.length() == 0) {
                return element.toString();
            }
            return acc + ", " + element;
        }
    }

    @NotNull
    public String toString() {
        return Constants.ARRAY_TYPE + ((String) fold("", b.INSTANCE)) + "]";
    }

    private final Object writeReplace() {
        int n = e();
        g[] elements = new g[n];
        kotlin.jvm.internal.x index = new kotlin.jvm.internal.x();
        boolean z = false;
        index.element = 0;
        fold(x.a, new C0321c(elements, index));
        if (index.element == n) {
            z = true;
        }
        if (z) {
            return new a(elements);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    /* compiled from: CoroutineContextImpl.kt */
    public static final class a implements Serializable {
        @NotNull
        public static final C0320a Companion = new C0320a((DefaultConstructorMarker) null);
        private static final long serialVersionUID = 0;
        @NotNull
        private final g[] elements;

        /* renamed from: kotlin.coroutines.c$a$a  reason: collision with other inner class name */
        /* compiled from: CoroutineContextImpl.kt */
        public static final class C0320a {
            private C0320a() {
            }

            public /* synthetic */ C0320a(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }

        public a(@NotNull g[] elements2) {
            k.e(elements2, "elements");
            this.elements = elements2;
        }

        @NotNull
        public final g[] getElements() {
            return this.elements;
        }

        private final Object readResolve() {
            g[] gVarArr = this.elements;
            g p1 = h.INSTANCE;
            for (g p2 : gVarArr) {
                p1 = p1.plus(p2);
            }
            return p1;
        }
    }
}
