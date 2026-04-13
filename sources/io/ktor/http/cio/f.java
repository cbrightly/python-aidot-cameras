package io.ktor.http.cio;

import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.m;
import kotlin.sequences.h;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpHeadersMap.kt */
public final class f {
    private int a;
    /* access modifiers changed from: private */
    public int[] b = ((int[]) g.b.p0());
    /* access modifiers changed from: private */
    public final io.ktor.http.cio.internals.b c;

    /* compiled from: HttpHeadersMap.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<Integer, Integer> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(f fVar) {
            super(1);
            this.this$0 = fVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Number) obj).intValue());
        }

        @Nullable
        public final Integer invoke(int it) {
            if (it + 1 >= this.this$0.g()) {
                return null;
            }
            return Integer.valueOf(it + 1);
        }
    }

    /* compiled from: HttpHeadersMap.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<Integer, Integer> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Integer.valueOf(invoke(((Number) obj).intValue()));
        }

        public final int invoke(int it) {
            return it * 8;
        }
    }

    /* compiled from: HttpHeadersMap.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<Integer, Boolean> {
        final /* synthetic */ int $nameHash;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(f fVar, int i) {
            super(1);
            this.this$0 = fVar;
            this.$nameHash = i;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke(((Number) obj).intValue()));
        }

        public final boolean invoke(int it) {
            return this.this$0.b[it] == this.$nameHash;
        }
    }

    /* compiled from: HttpHeadersMap.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<Integer, CharSequence> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(f fVar) {
            super(1);
            this.this$0 = fVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Number) obj).intValue());
        }

        @NotNull
        public final CharSequence invoke(int it) {
            return this.this$0.c.subSequence(this.this$0.b[it + 4], this.this$0.b[it + 5]);
        }
    }

    public f(@NotNull io.ktor.http.cio.internals.b builder) {
        k.f(builder, "builder");
        this.c = builder;
    }

    public final int g() {
        return this.a;
    }

    public final void i(int nameHash, int valueHash, int nameStartIndex, int nameEndIndex, int valueStartIndex, int valueEndIndex) {
        int i = this.a;
        int base = i * 8;
        int[] array = this.b;
        if (base < this.b.length) {
            array[base + 0] = nameHash;
            array[base + 1] = valueHash;
            array[base + 2] = nameStartIndex;
            array[base + 3] = nameEndIndex;
            array[base + 4] = valueStartIndex;
            array[base + 5] = valueEndIndex;
            array[base + 6] = -1;
            array[base + 7] = -1;
            this.a = i + 1;
            return;
        }
        throw new m("An operation is not implemented: " + "Implement headers overflow");
    }

    public static /* synthetic */ int d(f fVar, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return fVar.c(str, i);
    }

    public final int c(@NotNull String name, int fromIndex) {
        k.f(name, "name");
        int nameHash = io.ktor.http.cio.internals.d.e(name, 0, 0, 3, (Object) null);
        int i = this.a;
        for (int i2 = fromIndex; i2 < i; i2++) {
            if (this.b[i2 * 8] == nameHash) {
                return i2;
            }
        }
        return -1;
    }

    @Nullable
    public final CharSequence e(@NotNull String name) {
        k.f(name, "name");
        int nameHash = io.ktor.http.cio.internals.d.e(name, 0, 0, 3, (Object) null);
        int i = this.a;
        for (int i2 = 0; i2 < i; i2++) {
            int offset = i2 * 8;
            int[] iArr = this.b;
            if (iArr[offset] == nameHash) {
                return this.c.subSequence(iArr[offset + 4], iArr[offset + 5]);
            }
        }
        return null;
    }

    @NotNull
    public final h<CharSequence> f(@NotNull String name) {
        k.f(name, "name");
        return o.w(o.o(o.w(kotlin.sequences.m.h(0, new a(this)), b.INSTANCE), new c(this, io.ktor.http.cio.internals.d.e(name, 0, 0, 3, (Object) null))), new d(this));
    }

    @NotNull
    public final CharSequence h(int idx) {
        boolean z = true;
        if (idx >= 0) {
            if (idx >= this.a) {
                z = false;
            }
            if (z) {
                int offset = idx * 8;
                int[] array = this.b;
                return this.c.subSequence(array[offset + 2], array[offset + 3]);
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @NotNull
    public final CharSequence k(int idx) {
        boolean z = true;
        if (idx >= 0) {
            if (idx >= this.a) {
                z = false;
            }
            if (z) {
                int offset = idx * 8;
                int[] array = this.b;
                return this.c.subSequence(array[offset + 4], array[offset + 5]);
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    public final void j() {
        this.a = 0;
        int[] indexes = this.b;
        this.b = g.a;
        if (indexes != g.a) {
            g.b.N0(indexes);
        }
    }

    @NotNull
    public String toString() {
        StringBuilder $this$buildString = new StringBuilder();
        g.c(this, "", $this$buildString);
        String sb = $this$buildString.toString();
        k.b(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }
}
