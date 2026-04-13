package io.ktor.routing;

import io.ktor.http.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RouteSelector.kt */
public final class k {
    /* access modifiers changed from: private */
    @NotNull
    public static final k a = new k(false, 0.0d, (y) null, 0, 12, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final k b = new k(true, 0.2d, (y) null, 0, 12, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final k c = new k(true, 1.0d, (y) null, 0, 12, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final k d = new k(true, 1.0d, (y) null, 1, 4, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final k e = new k(true, 0.5d, (y) null, 1, 4, (DefaultConstructorMarker) null);
    public static final a f = new a((DefaultConstructorMarker) null);
    private final boolean g;
    private final double h;
    @NotNull
    private final y i;
    private final int j;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        return this.g == kVar.g && Double.compare(this.h, kVar.h) == 0 && kotlin.jvm.internal.k.a(this.i, kVar.i) && this.j == kVar.j;
    }

    public int hashCode() {
        boolean z = this.g;
        if (z) {
            z = true;
        }
        long doubleToLongBits = Double.doubleToLongBits(this.h);
        int i2 = (((z ? 1 : 0) * true) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31;
        y yVar = this.i;
        return ((i2 + (yVar != null ? yVar.hashCode() : 0)) * 31) + this.j;
    }

    @NotNull
    public String toString() {
        return "RouteSelectorEvaluation(succeeded=" + this.g + ", quality=" + this.h + ", parameters=" + this.i + ", segmentIncrement=" + this.j + ")";
    }

    public k(boolean succeeded, double quality, @NotNull y parameters, int segmentIncrement) {
        kotlin.jvm.internal.k.f(parameters, "parameters");
        this.g = succeeded;
        this.h = quality;
        this.i = parameters;
        this.j = segmentIncrement;
    }

    public final boolean i() {
        return this.g;
    }

    public final double g() {
        return this.h;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ k(boolean r7, double r8, io.ktor.http.y r10, int r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 4
            if (r13 == 0) goto L_0x000c
            io.ktor.http.y$a r10 = io.ktor.http.y.b
            io.ktor.http.y r10 = r10.a()
            r4 = r10
            goto L_0x000d
        L_0x000c:
            r4 = r10
        L_0x000d:
            r10 = r12 & 8
            if (r10 == 0) goto L_0x0014
            r11 = 0
            r5 = r11
            goto L_0x0015
        L_0x0014:
            r5 = r11
        L_0x0015:
            r0 = r6
            r1 = r7
            r2 = r8
            r0.<init>(r1, r2, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.routing.k.<init>(boolean, double, io.ktor.http.y, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final y f() {
        return this.i;
    }

    /* compiled from: RouteSelector.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final k c() {
            return k.a;
        }

        @NotNull
        public final k d() {
            return k.b;
        }

        @NotNull
        public final k a() {
            return k.c;
        }

        @NotNull
        public final k b() {
            return k.d;
        }

        @NotNull
        public final k e() {
            return k.e;
        }
    }

    public final int h() {
        return this.j;
    }
}
