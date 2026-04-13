package coil.decode;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DecodeResult.kt */
public final class c {
    @NotNull
    private final Drawable a;
    private final boolean b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && this.b == cVar.b;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        return hashCode + (z ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "DecodeResult(drawable=" + this.a + ", isSampled=" + this.b + ')';
    }

    public c(@NotNull Drawable drawable, boolean isSampled) {
        k.e(drawable, "drawable");
        this.a = drawable;
        this.b = isSampled;
    }

    @NotNull
    public final Drawable a() {
        return this.a;
    }

    public final boolean b() {
        return this.b;
    }
}
