package okio;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Segment.kt */
public final class y {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    public final byte[] b;
    public int c;
    public int d;
    public boolean e;
    public boolean f;
    @Nullable
    public y g;
    @Nullable
    public y h;

    public y() {
        this.b = new byte[8192];
        this.f = true;
        this.e = false;
    }

    public y(@NotNull byte[] data, int pos, int limit, boolean shared, boolean owner) {
        k.e(data, "data");
        this.b = data;
        this.c = pos;
        this.d = limit;
        this.e = shared;
        this.f = owner;
    }

    @NotNull
    public final y d() {
        this.e = true;
        return new y(this.b, this.c, this.d, true, false);
    }

    @NotNull
    public final y f() {
        byte[] bArr = this.b;
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length);
        k.d(copyOf, "java.util.Arrays.copyOf(this, size)");
        return new y(copyOf, this.c, this.d, false, true);
    }

    @Nullable
    public final y b() {
        y result = this.g;
        if (result == this) {
            result = null;
        }
        y yVar = this.h;
        k.c(yVar);
        yVar.g = this.g;
        y yVar2 = this.g;
        k.c(yVar2);
        yVar2.h = this.h;
        this.g = null;
        this.h = null;
        return result;
    }

    @NotNull
    public final y c(@NotNull y segment) {
        k.e(segment, "segment");
        segment.h = this;
        segment.g = this.g;
        y yVar = this.g;
        k.c(yVar);
        yVar.h = segment;
        this.g = segment;
        return segment;
    }

    @NotNull
    public final y e(int byteCount) {
        y prefix;
        if (byteCount > 0 && byteCount <= this.d - this.c) {
            if (byteCount >= 1024) {
                prefix = d();
            } else {
                prefix = z.c();
                byte[] bArr = this.b;
                byte[] bArr2 = prefix.b;
                int i = this.c;
                kotlin.collections.k.f(bArr, bArr2, 0, i, i + byteCount, 2, (Object) null);
            }
            prefix.d = prefix.c + byteCount;
            this.c += byteCount;
            y yVar = this.h;
            k.c(yVar);
            yVar.c(prefix);
            return prefix;
        }
        throw new IllegalArgumentException("byteCount out of range".toString());
    }

    public final void a() {
        y yVar = this.h;
        int i = 0;
        if (yVar != this) {
            k.c(yVar);
            if (yVar.f) {
                int byteCount = this.d - this.c;
                y yVar2 = this.h;
                k.c(yVar2);
                int i2 = 8192 - yVar2.d;
                y yVar3 = this.h;
                k.c(yVar3);
                if (!yVar3.e) {
                    y yVar4 = this.h;
                    k.c(yVar4);
                    i = yVar4.c;
                }
                if (byteCount <= i2 + i) {
                    y yVar5 = this.h;
                    k.c(yVar5);
                    g(yVar5, byteCount);
                    b();
                    z.b(this);
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("cannot compact".toString());
    }

    public final void g(@NotNull y sink, int byteCount) {
        k.e(sink, "sink");
        if (sink.f) {
            int i = sink.d;
            if (i + byteCount > 8192) {
                if (!sink.e) {
                    int i2 = sink.c;
                    if ((i + byteCount) - i2 <= 8192) {
                        byte[] bArr = sink.b;
                        kotlin.collections.k.f(bArr, bArr, 0, i2, i, 2, (Object) null);
                        sink.d -= sink.c;
                        sink.c = 0;
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            byte[] bArr2 = this.b;
            byte[] bArr3 = sink.b;
            int i3 = sink.d;
            int i4 = this.c;
            kotlin.collections.k.d(bArr2, bArr3, i3, i4, i4 + byteCount);
            sink.d += byteCount;
            this.c += byteCount;
            return;
        }
        throw new IllegalStateException("only owner can write".toString());
    }

    /* compiled from: Segment.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
