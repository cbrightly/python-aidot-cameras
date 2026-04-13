package com.petterp.floatingx.assist;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxBorderMargin.kt */
public final class b {
    private float a;
    private float b;
    private float c;
    private float d;

    public b() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 15, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(Float.valueOf(this.a), Float.valueOf(bVar.a)) && k.a(Float.valueOf(this.b), Float.valueOf(bVar.b)) && k.a(Float.valueOf(this.c), Float.valueOf(bVar.c)) && k.a(Float.valueOf(this.d), Float.valueOf(bVar.d));
    }

    public int hashCode() {
        return (((((Float.floatToIntBits(this.a) * 31) + Float.floatToIntBits(this.b)) * 31) + Float.floatToIntBits(this.c)) * 31) + Float.floatToIntBits(this.d);
    }

    @NotNull
    public String toString() {
        return "FxBorderMargin(t=" + this.a + ", l=" + this.b + ", b=" + this.c + ", r=" + this.d + ')';
    }

    public b(float t, float l, float b2, float r) {
        this.a = t;
        this.b = l;
        this.c = b2;
        this.d = r;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? 0.0f : f4);
    }

    public final float a() {
        return this.c;
    }

    public final float b() {
        return this.b;
    }

    public final float c() {
        return this.d;
    }

    public final float d() {
        return this.a;
    }

    public final void e(float f) {
        this.c = f;
    }

    public final void f(float f) {
        this.b = f;
    }

    public final void g(float f) {
        this.d = f;
    }

    public final void h(float f) {
        this.a = f;
    }
}
