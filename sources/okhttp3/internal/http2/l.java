package okhttp3.internal.http2;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Settings.kt */
public final class l {
    public static final a a = new a((DefaultConstructorMarker) null);
    private int b;
    private final int[] c = new int[10];

    public final int b() {
        if ((this.b & 2) != 0) {
            return this.c[1];
        }
        return -1;
    }

    public final int c() {
        if ((this.b & 128) != 0) {
            return this.c[7];
        }
        return 65535;
    }

    @NotNull
    public final l h(int id, int value) {
        if (id >= 0) {
            int[] iArr = this.c;
            if (id < iArr.length) {
                this.b |= 1 << id;
                iArr[id] = value;
                return this;
            }
        }
        return this;
    }

    public final boolean f(int id) {
        if ((this.b & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    public final int a(int id) {
        return this.c[id];
    }

    public final int i() {
        return Integer.bitCount(this.b);
    }

    public final int d() {
        if ((this.b & 16) != 0) {
            return this.c[4];
        }
        return Integer.MAX_VALUE;
    }

    public final int e(int defaultValue) {
        return (this.b & 32) != 0 ? this.c[5] : defaultValue;
    }

    public final void g(@NotNull l other) {
        k.f(other, "other");
        for (int i = 0; i < 10; i++) {
            if (other.f(i)) {
                h(i, other.a(i));
            }
        }
    }

    /* compiled from: Settings.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
