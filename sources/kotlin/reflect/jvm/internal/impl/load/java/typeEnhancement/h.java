package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: signatureEnhancement.kt */
public final class h {
    @NotNull
    private final g a;
    private final boolean b;

    public static /* synthetic */ h b(h hVar, g gVar, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            gVar = hVar.a;
        }
        if ((i & 2) != 0) {
            z = hVar.b;
        }
        return hVar.a(gVar, z);
    }

    @NotNull
    public final h a(@NotNull g gVar, boolean z) {
        k.f(gVar, "qualifier");
        return new h(gVar, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return k.a(this.a, hVar.a) && this.b == hVar.b;
    }

    public int hashCode() {
        g gVar = this.a;
        int hashCode = (gVar != null ? gVar.hashCode() : 0) * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        return hashCode + (z ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "NullabilityQualifierWithMigrationStatus(qualifier=" + this.a + ", isForWarningOnly=" + this.b + ")";
    }

    public h(@NotNull g qualifier, boolean isForWarningOnly) {
        k.f(qualifier, "qualifier");
        this.a = qualifier;
        this.b = isForWarningOnly;
    }

    @NotNull
    public final g c() {
        return this.a;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h(g gVar, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(gVar, (i & 2) != 0 ? false : z);
    }

    public final boolean d() {
        return this.b;
    }
}
