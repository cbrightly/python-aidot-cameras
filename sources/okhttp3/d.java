package okhttp3;

import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: CacheControl.kt */
public final class d {
    @NotNull
    public static final d a = new a().d().a();
    @NotNull
    public static final d b = new a().f().c(Integer.MAX_VALUE, TimeUnit.SECONDS).a();
    public static final b c = new b((DefaultConstructorMarker) null);
    private final boolean d;
    private final boolean e;
    private final int f;
    private final int g;
    private final boolean h;
    private final boolean i;
    private final boolean j;
    private final int k;
    private final int l;
    private final boolean m;
    private final boolean n;
    private final boolean o;
    private String p;

    private d(boolean noCache, boolean noStore, int maxAgeSeconds, int sMaxAgeSeconds, boolean isPrivate, boolean isPublic, boolean mustRevalidate, int maxStaleSeconds, int minFreshSeconds, boolean onlyIfCached, boolean noTransform, boolean immutable, String headerValue) {
        this.d = noCache;
        this.e = noStore;
        this.f = maxAgeSeconds;
        this.g = sMaxAgeSeconds;
        this.h = isPrivate;
        this.i = isPublic;
        this.j = mustRevalidate;
        this.k = maxStaleSeconds;
        this.l = minFreshSeconds;
        this.m = onlyIfCached;
        this.n = noTransform;
        this.o = immutable;
        this.p = headerValue;
    }

    public /* synthetic */ d(boolean noCache, boolean noStore, int maxAgeSeconds, int sMaxAgeSeconds, boolean isPrivate, boolean isPublic, boolean mustRevalidate, int maxStaleSeconds, int minFreshSeconds, boolean onlyIfCached, boolean noTransform, boolean immutable, String headerValue, DefaultConstructorMarker $constructor_marker) {
        this(noCache, noStore, maxAgeSeconds, sMaxAgeSeconds, isPrivate, isPublic, mustRevalidate, maxStaleSeconds, minFreshSeconds, onlyIfCached, noTransform, immutable, headerValue);
    }

    public final boolean g() {
        return this.d;
    }

    public final boolean h() {
        return this.e;
    }

    public final int c() {
        return this.f;
    }

    public final boolean a() {
        return this.h;
    }

    public final boolean b() {
        return this.i;
    }

    public final boolean f() {
        return this.j;
    }

    public final int d() {
        return this.k;
    }

    public final int e() {
        return this.l;
    }

    public final boolean i() {
        return this.m;
    }

    @NotNull
    public String toString() {
        String result = this.p;
        if (result != null) {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        if (this.d) {
            $this$buildString.append("no-cache, ");
        }
        if (this.e) {
            $this$buildString.append("no-store, ");
        }
        if (this.f != -1) {
            $this$buildString.append("max-age=");
            $this$buildString.append(this.f);
            $this$buildString.append(", ");
        }
        if (this.g != -1) {
            $this$buildString.append("s-maxage=");
            $this$buildString.append(this.g);
            $this$buildString.append(", ");
        }
        if (this.h) {
            $this$buildString.append("private, ");
        }
        if (this.i) {
            $this$buildString.append("public, ");
        }
        if (this.j) {
            $this$buildString.append("must-revalidate, ");
        }
        if (this.k != -1) {
            $this$buildString.append("max-stale=");
            $this$buildString.append(this.k);
            $this$buildString.append(", ");
        }
        if (this.l != -1) {
            $this$buildString.append("min-fresh=");
            $this$buildString.append(this.l);
            $this$buildString.append(", ");
        }
        if (this.m) {
            $this$buildString.append("only-if-cached, ");
        }
        if (this.n) {
            $this$buildString.append("no-transform, ");
        }
        if (this.o) {
            $this$buildString.append("immutable, ");
        }
        if ($this$buildString.length() == 0) {
            return "";
        }
        $this$buildString.delete($this$buildString.length() - 2, $this$buildString.length());
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        String result2 = sb2;
        this.p = result2;
        return result2;
    }

    /* compiled from: CacheControl.kt */
    public static final class a {
        private boolean a;
        private boolean b;
        private int c = -1;
        private int d = -1;
        private int e = -1;
        private boolean f;
        private boolean g;
        private boolean h;

        @NotNull
        public final a d() {
            this.a = true;
            return this;
        }

        @NotNull
        public final a e() {
            this.b = true;
            return this;
        }

        @NotNull
        public final a c(int maxStale, @NotNull TimeUnit timeUnit) {
            k.f(timeUnit, "timeUnit");
            if (maxStale >= 0) {
                this.d = b(timeUnit.toSeconds((long) maxStale));
                return this;
            }
            throw new IllegalArgumentException(("maxStale < 0: " + maxStale).toString());
        }

        @NotNull
        public final a f() {
            this.f = true;
            return this;
        }

        private final int b(long $this$clampToInt) {
            if ($this$clampToInt > ((long) Integer.MAX_VALUE)) {
                return Integer.MAX_VALUE;
            }
            return (int) $this$clampToInt;
        }

        @NotNull
        public final d a() {
            return new d(this.a, this.b, this.c, -1, false, false, false, this.d, this.e, this.f, this.g, this.h, (String) null, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: CacheControl.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX WARNING: Removed duplicated region for block: B:35:0x00f9  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0103  */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final okhttp3.d b(@org.jetbrains.annotations.NotNull okhttp3.u r37) {
            /*
                r36 = this;
                r0 = r36
                r1 = r37
                java.lang.String r2 = "headers"
                kotlin.jvm.internal.k.f(r1, r2)
                r2 = 0
                r3 = 0
                r4 = -1
                r5 = -1
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = -1
                r10 = -1
                r11 = 0
                r12 = 0
                r13 = 0
                r14 = 1
                r15 = 0
                r16 = r15
                int r15 = r37.size()
                r17 = 0
                r35 = r17
                r17 = r13
                r13 = r35
            L_0x0025:
                if (r13 >= r15) goto L_0x021c
                r18 = r15
                java.lang.String r15 = r1.b(r13)
                r33 = r12
                java.lang.String r12 = r1.h(r13)
                java.lang.String r1 = "Cache-Control"
                r34 = r11
                r11 = 1
                boolean r1 = kotlin.text.w.y(r15, r1, r11)
                if (r1 == 0) goto L_0x0046
                if (r16 == 0) goto L_0x0043
                r14 = 0
                goto L_0x004f
            L_0x0043:
                r16 = r12
                goto L_0x004f
            L_0x0046:
                java.lang.String r1 = "Pragma"
                boolean r1 = kotlin.text.w.y(r15, r1, r11)
                if (r1 == 0) goto L_0x020f
                r14 = 0
            L_0x004f:
                r1 = 0
            L_0x0051:
                int r11 = r12.length()
                if (r1 >= r11) goto L_0x0200
                r11 = r1
                r26 = r2
                java.lang.String r2 = "=,;"
                int r1 = r0.a(r12, r2, r1)
                java.lang.String r2 = r12.substring(r11, r1)
                r27 = r3
                java.lang.String r3 = "(this as java.lang.Strin…ing(startIndex, endIndex)"
                kotlin.jvm.internal.k.b(r2, r3)
                r28 = r4
                java.lang.String r4 = "null cannot be cast to non-null type kotlin.CharSequence"
                if (r2 == 0) goto L_0x01fa
                java.lang.CharSequence r2 = kotlin.text.x.e1(r2)
                java.lang.String r2 = r2.toString()
                r29 = 0
                r30 = r5
                int r5 = r12.length()
                if (r1 == r5) goto L_0x00e7
                char r5 = r12.charAt(r1)
                r31 = r6
                r6 = 44
                if (r5 == r6) goto L_0x00e9
                char r5 = r12.charAt(r1)
                r6 = 59
                if (r5 != r6) goto L_0x0096
                goto L_0x00e9
            L_0x0096:
                int r1 = r1 + 1
                int r1 = okhttp3.internal.b.A(r12, r1)
                int r5 = r12.length()
                if (r1 >= r5) goto L_0x00c8
                char r5 = r12.charAt(r1)
                r6 = 34
                if (r5 != r6) goto L_0x00c8
                int r1 = r1 + 1
                r4 = r1
                r20 = 34
                r22 = 0
                r23 = 4
                r24 = 0
                r19 = r12
                r21 = r1
                int r1 = kotlin.text.x.e0(r19, r20, r21, r22, r23, r24)
                java.lang.String r5 = r12.substring(r4, r1)
                kotlin.jvm.internal.k.b(r5, r3)
                r3 = r5
                r4 = 1
                int r1 = r1 + r4
                goto L_0x00ed
            L_0x00c8:
                r5 = r1
                java.lang.String r6 = ",;"
                int r1 = r0.a(r12, r6, r1)
                java.lang.String r6 = r12.substring(r5, r1)
                kotlin.jvm.internal.k.b(r6, r3)
                if (r6 == 0) goto L_0x00e1
                java.lang.CharSequence r3 = kotlin.text.x.e1(r6)
                java.lang.String r3 = r3.toString()
                goto L_0x00ed
            L_0x00e1:
                kotlin.TypeCastException r3 = new kotlin.TypeCastException
                r3.<init>(r4)
                throw r3
            L_0x00e7:
                r31 = r6
            L_0x00e9:
                int r1 = r1 + 1
                r3 = 0
                r4 = r3
            L_0x00ed:
                java.lang.String r4 = "no-cache"
                r5 = 1
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x0103
                r4 = 1
                r2 = r4
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x0103:
                java.lang.String r4 = "no-store"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x0115
                r4 = 1
                r3 = r4
                r2 = r26
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x0115:
                java.lang.String r4 = "max-age"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                r6 = -1
                if (r4 == 0) goto L_0x012a
                int r4 = okhttp3.internal.b.U(r3, r6)
                r2 = r26
                r3 = r27
                r6 = r31
                goto L_0x01f4
            L_0x012a:
                java.lang.String r4 = "s-maxage"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x0143
                int r4 = okhttp3.internal.b.U(r3, r6)
                r30 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x0143:
                java.lang.String r4 = "private"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x0156
                r4 = 1
                r6 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                goto L_0x01f4
            L_0x0156:
                java.lang.String r4 = "public"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x016b
                r4 = 1
                r7 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x016b:
                java.lang.String r4 = "must-revalidate"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x017f
                r4 = 1
                r8 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x017f:
                java.lang.String r4 = "max-stale"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x0199
                r4 = 2147483647(0x7fffffff, float:NaN)
                int r4 = okhttp3.internal.b.U(r3, r4)
                r9 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x0199:
                java.lang.String r4 = "min-fresh"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x01af
                int r4 = okhttp3.internal.b.U(r3, r6)
                r10 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x01af:
                java.lang.String r4 = "only-if-cached"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x01c4
                r4 = 1
                r34 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x01c4:
                java.lang.String r4 = "no-transform"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x01d8
                r4 = 1
                r33 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x01d8:
                java.lang.String r4 = "immutable"
                boolean r4 = kotlin.text.w.y(r4, r2, r5)
                if (r4 == 0) goto L_0x01ec
                r4 = 1
                r17 = r4
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
                goto L_0x01f4
            L_0x01ec:
                r2 = r26
                r3 = r27
                r4 = r28
                r6 = r31
            L_0x01f4:
                r11 = r5
                r5 = r30
                goto L_0x0051
            L_0x01fa:
                kotlin.TypeCastException r2 = new kotlin.TypeCastException
                r2.<init>(r4)
                throw r2
            L_0x0200:
                r26 = r2
                r27 = r3
                r28 = r4
                r30 = r5
                r31 = r6
                r12 = r33
                r11 = r34
                goto L_0x0213
            L_0x020f:
                r12 = r33
                r11 = r34
            L_0x0213:
                int r13 = r13 + 1
                r1 = r37
                r15 = r18
                goto L_0x0025
            L_0x021c:
                r34 = r11
                r33 = r12
                if (r14 != 0) goto L_0x0225
                r1 = 0
                r16 = r1
            L_0x0225:
                okhttp3.d r1 = new okhttp3.d
                r32 = 0
                r18 = r1
                r19 = r2
                r20 = r3
                r21 = r4
                r22 = r5
                r23 = r6
                r24 = r7
                r25 = r8
                r26 = r9
                r27 = r10
                r28 = r34
                r29 = r33
                r30 = r17
                r31 = r16
                r18.<init>(r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.d.b.b(okhttp3.u):okhttp3.d");
        }

        private final int a(@NotNull String $this$indexOfElement, String characters, int startIndex) {
            int length = $this$indexOfElement.length();
            for (int i = startIndex; i < length; i++) {
                if (x.R(characters, $this$indexOfElement.charAt(i), false, 2, (Object) null)) {
                    return i;
                }
            }
            return $this$indexOfElement.length();
        }
    }
}
