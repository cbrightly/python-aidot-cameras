package coil.fetch;

import android.graphics.drawable.Drawable;
import coil.decode.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FetchResult.kt */
public final class e extends f {
    @NotNull
    private final Drawable a;
    private final boolean b;
    @NotNull
    private final b c;

    public static /* synthetic */ e e(e eVar, Drawable drawable, boolean z, b bVar, int i, Object obj) {
        if ((i & 1) != 0) {
            drawable = eVar.a;
        }
        if ((i & 2) != 0) {
            z = eVar.b;
        }
        if ((i & 4) != 0) {
            bVar = eVar.c;
        }
        return eVar.d(drawable, z, bVar);
    }

    @NotNull
    public final Drawable a() {
        return this.a;
    }

    public final boolean b() {
        return this.b;
    }

    @NotNull
    public final b c() {
        return this.c;
    }

    @NotNull
    public final e d(@NotNull Drawable drawable, boolean z, @NotNull b bVar) {
        k.e(drawable, "drawable");
        k.e(bVar, "dataSource");
        return new e(drawable, z, bVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return k.a(this.a, eVar.a) && this.b == eVar.b && this.c == eVar.c;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        return ((hashCode + (z ? 1 : 0)) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "DrawableResult(drawable=" + this.a + ", isSampled=" + this.b + ", dataSource=" + this.c + ')';
    }

    @NotNull
    public final Drawable f() {
        return this.a;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@NotNull Drawable drawable, boolean isSampled, @NotNull b dataSource) {
        super((DefaultConstructorMarker) null);
        k.e(drawable, "drawable");
        k.e(dataSource, "dataSource");
        this.a = drawable;
        this.b = isSampled;
        this.c = dataSource;
    }
}
