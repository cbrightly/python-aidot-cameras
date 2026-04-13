package io.ktor.http.cio;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.t;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConnectionOptions.kt */
public final class d {
    @NotNull
    private static final d a;
    /* access modifiers changed from: private */
    @NotNull
    public static final d b;
    @NotNull
    private static final d c;
    /* access modifiers changed from: private */
    public static final io.ktor.http.cio.internals.a<n<String, d>> d;
    public static final c e = new c((DefaultConstructorMarker) null);
    private final boolean f;
    private final boolean g;
    private final boolean h;
    @NotNull
    private final List<String> i;

    public d() {
        this(false, false, false, (List) null, 15, (DefaultConstructorMarker) null);
    }

    public d(boolean close, boolean keepAlive, boolean upgrade, @NotNull List<String> extraOptions) {
        k.f(extraOptions, "extraOptions");
        this.f = close;
        this.g = keepAlive;
        this.h = upgrade;
        this.i = extraOptions;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(boolean z, boolean z2, boolean z3, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? false : z2, (i2 & 4) != 0 ? false : z3, (i2 & 8) != 0 ? q.g() : list);
    }

    public final boolean d() {
        return this.f;
    }

    public final boolean e() {
        return this.g;
    }

    public final boolean f() {
        return this.h;
    }

    /* compiled from: ConnectionOptions.kt */
    public static final class c {

        /* compiled from: ConnectionOptions.kt */
        public static final class a extends l implements p<Character, Integer, Boolean> {
            public static final a INSTANCE = new a();

            a() {
                super(2);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(invoke(((Character) obj).charValue(), ((Number) obj2).intValue()));
            }

            public final boolean invoke(char $noName_0, int $noName_1) {
                return false;
            }
        }

        /* compiled from: ConnectionOptions.kt */
        public static final class b extends l implements p<Character, Integer, Boolean> {
            public static final b INSTANCE = new b();

            b() {
                super(2);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(invoke(((Character) obj).charValue(), ((Number) obj2).intValue()));
            }

            public final boolean invoke(char $noName_0, int $noName_1) {
                return false;
            }
        }

        private c() {
        }

        public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final d a() {
            return d.b;
        }

        @Nullable
        public final d b(@Nullable CharSequence connection) {
            if (connection == null) {
                return null;
            }
            List known = io.ktor.http.cio.internals.a.b(d.d, connection, 0, 0, true, a.INSTANCE, 6, (Object) null);
            if (known.size() == 1) {
                return (d) ((n) known.get(0)).getSecond();
            }
            return c(connection);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: io.ktor.http.cio.d} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final io.ktor.http.cio.d c(java.lang.CharSequence r15) {
            /*
                r14 = this;
                r0 = 0
                r1 = 0
                int r2 = r15.length()
                r3 = 0
                r4 = r3
            L_0x0009:
                if (r0 >= r2) goto L_0x00b4
            L_0x000b:
                char r5 = r15.charAt(r0)
                r6 = 44
                r7 = 32
                if (r5 == r7) goto L_0x001a
                if (r5 == r6) goto L_0x001a
                r1 = r0
                goto L_0x001e
            L_0x001a:
                int r0 = r0 + 1
                if (r0 < r2) goto L_0x000b
            L_0x001e:
                if (r0 >= r2) goto L_0x002d
                char r5 = r15.charAt(r0)
                if (r5 == r7) goto L_0x002d
                if (r5 != r6) goto L_0x0029
                goto L_0x002d
            L_0x0029:
                int r0 = r0 + 1
                goto L_0x001e
            L_0x002d:
                io.ktor.http.cio.internals.a r8 = io.ktor.http.cio.d.d
                r12 = 1
                io.ktor.http.cio.d$c$b r13 = io.ktor.http.cio.d.c.b.INSTANCE
                r9 = r15
                r10 = r1
                r11 = r0
                java.util.List r5 = r8.a(r9, r10, r11, r12, r13)
                java.lang.Object r5 = kotlin.collections.y.s0(r5)
                kotlin.n r5 = (kotlin.n) r5
                if (r5 != 0) goto L_0x005c
                if (r3 != 0) goto L_0x0050
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                r3 = r6
            L_0x0050:
                java.lang.CharSequence r6 = r15.subSequence(r1, r0)
                java.lang.String r6 = r6.toString()
                r3.add(r6)
                goto L_0x00b1
            L_0x005c:
                if (r4 != 0) goto L_0x0066
                java.lang.Object r6 = r5.getSecond()
                r4 = r6
                io.ktor.http.cio.d r4 = (io.ktor.http.cio.d) r4
                goto L_0x00b1
            L_0x0066:
                io.ktor.http.cio.d r6 = new io.ktor.http.cio.d
                boolean r7 = r4.d()
                r8 = 0
                r9 = 1
                if (r7 != 0) goto L_0x007f
                java.lang.Object r7 = r5.getSecond()
                io.ktor.http.cio.d r7 = (io.ktor.http.cio.d) r7
                boolean r7 = r7.d()
                if (r7 == 0) goto L_0x007d
                goto L_0x007f
            L_0x007d:
                r7 = r8
                goto L_0x0080
            L_0x007f:
                r7 = r9
            L_0x0080:
                boolean r10 = r4.e()
                if (r10 != 0) goto L_0x0095
                java.lang.Object r10 = r5.getSecond()
                io.ktor.http.cio.d r10 = (io.ktor.http.cio.d) r10
                boolean r10 = r10.e()
                if (r10 == 0) goto L_0x0093
                goto L_0x0095
            L_0x0093:
                r10 = r8
                goto L_0x0096
            L_0x0095:
                r10 = r9
            L_0x0096:
                boolean r11 = r4.f()
                if (r11 != 0) goto L_0x00a8
                java.lang.Object r11 = r5.getSecond()
                io.ktor.http.cio.d r11 = (io.ktor.http.cio.d) r11
                boolean r11 = r11.f()
                if (r11 == 0) goto L_0x00a9
            L_0x00a8:
                r8 = r9
            L_0x00a9:
                java.util.List r9 = kotlin.collections.q.g()
                r6.<init>(r7, r10, r8, r9)
                r4 = r6
            L_0x00b1:
                goto L_0x0009
            L_0x00b4:
                if (r4 != 0) goto L_0x00ba
                io.ktor.http.cio.d r4 = r14.a()
            L_0x00ba:
                if (r3 != 0) goto L_0x00be
                r5 = r4
                goto L_0x00d0
            L_0x00be:
                io.ktor.http.cio.d r5 = new io.ktor.http.cio.d
                boolean r6 = r4.d()
                boolean r7 = r4.e()
                boolean r8 = r4.f()
                r5.<init>(r6, r7, r8, r3)
            L_0x00d0:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.d.c.c(java.lang.CharSequence):io.ktor.http.cio.d");
        }
    }

    /* compiled from: ConnectionOptions.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<n<? extends String, ? extends d>, Integer> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Integer.valueOf(invoke((n<String, d>) (n) obj));
        }

        public final int invoke(@NotNull n<String, d> it) {
            k.f(it, "it");
            return it.getFirst().length();
        }
    }

    /* compiled from: ConnectionOptions.kt */
    public static final class b extends l implements p<n<? extends String, ? extends d>, Integer, Character> {
        public static final b INSTANCE = new b();

        b() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Character.valueOf(invoke((n<String, d>) (n) obj, ((Number) obj2).intValue()));
        }

        public final char invoke(@NotNull n<String, d> t, int idx) {
            k.f(t, "t");
            return t.getFirst().charAt(idx);
        }
    }

    static {
        d dVar = new d(true, false, false, (List) null, 14, (DefaultConstructorMarker) null);
        a = dVar;
        d dVar2 = new d(false, true, false, (List) null, 13, (DefaultConstructorMarker) null);
        b = dVar2;
        d dVar3 = new d(false, false, true, (List) null, 11, (DefaultConstructorMarker) null);
        c = dVar3;
        d = io.ktor.http.cio.internals.a.a.b(q.j(t.a("close", dVar), t.a("keep-alive", dVar2), t.a("upgrade", dVar3)), a.INSTANCE, b.INSTANCE);
    }

    @NotNull
    public String toString() {
        if (!this.i.isEmpty()) {
            return c();
        }
        boolean z = this.f;
        if (z && !this.g && !this.h) {
            return "close";
        }
        if (!z && this.g && !this.h) {
            return "keep-alive";
        }
        if (z || !this.g || !this.h) {
            return c();
        }
        return "keep-alive, Upgrade";
    }

    private final String c() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        ArrayList items = new ArrayList(this.i.size() + 3);
        if (this.f) {
            items.add("close");
        }
        if (this.g) {
            items.add("keep-alive");
        }
        if (this.h) {
            items.add(UpgradeRequest.UPGRADE);
        }
        if (!this.i.isEmpty()) {
            items.addAll(this.i);
        }
        y.Z(items, $this$buildString, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 126, (Object) null);
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || (!k.a(a0.b(getClass()), a0.b(other.getClass())))) {
            return false;
        }
        d dVar = (d) other;
        if (this.f == ((d) other).f && this.g == ((d) other).g && this.h == ((d) other).h && !(!k.a(this.i, ((d) other).i))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((Boolean.valueOf(this.f).hashCode() * 31) + Boolean.valueOf(this.g).hashCode()) * 31) + Boolean.valueOf(this.h).hashCode()) * 31) + this.i.hashCode();
    }
}
