package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.components.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaTypeResolver.kt */
public final class a {
    @NotNull
    private final l a;
    @NotNull
    private final b b;
    private final boolean c;
    @Nullable
    private final t0 d;

    public static /* synthetic */ a b(a aVar, l lVar, b bVar, boolean z, t0 t0Var, int i, Object obj) {
        if ((i & 1) != 0) {
            lVar = aVar.a;
        }
        if ((i & 2) != 0) {
            bVar = aVar.b;
        }
        if ((i & 4) != 0) {
            z = aVar.c;
        }
        if ((i & 8) != 0) {
            t0Var = aVar.d;
        }
        return aVar.a(lVar, bVar, z, t0Var);
    }

    @NotNull
    public final a a(@NotNull l lVar, @NotNull b bVar, boolean z, @Nullable t0 t0Var) {
        k.f(lVar, "howThisTypeIsUsed");
        k.f(bVar, "flexibility");
        return new a(lVar, bVar, z, t0Var);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return k.a(this.a, aVar.a) && k.a(this.b, aVar.b) && this.c == aVar.c && k.a(this.d, aVar.d);
    }

    public int hashCode() {
        l lVar = this.a;
        int i = 0;
        int hashCode = (lVar != null ? lVar.hashCode() : 0) * 31;
        b bVar = this.b;
        int hashCode2 = (hashCode + (bVar != null ? bVar.hashCode() : 0)) * 31;
        boolean z = this.c;
        if (z) {
            z = true;
        }
        int i2 = (hashCode2 + (z ? 1 : 0)) * 31;
        t0 t0Var = this.d;
        if (t0Var != null) {
            i = t0Var.hashCode();
        }
        return i2 + i;
    }

    @NotNull
    public String toString() {
        return "JavaTypeAttributes(howThisTypeIsUsed=" + this.a + ", flexibility=" + this.b + ", isForAnnotationParameter=" + this.c + ", upperBoundOfTypeParameter=" + this.d + ")";
    }

    public a(@NotNull l howThisTypeIsUsed, @NotNull b flexibility, boolean isForAnnotationParameter, @Nullable t0 upperBoundOfTypeParameter) {
        k.f(howThisTypeIsUsed, "howThisTypeIsUsed");
        k.f(flexibility, "flexibility");
        this.a = howThisTypeIsUsed;
        this.b = flexibility;
        this.c = isForAnnotationParameter;
        this.d = upperBoundOfTypeParameter;
    }

    @NotNull
    public final l d() {
        return this.a;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(l lVar, b bVar, boolean z, t0 t0Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lVar, (i & 2) != 0 ? b.INFLEXIBLE : bVar, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : t0Var);
    }

    @NotNull
    public final b c() {
        return this.b;
    }

    public final boolean f() {
        return this.c;
    }

    @Nullable
    public final t0 e() {
        return this.d;
    }

    @NotNull
    public final a g(@NotNull b flexibility) {
        k.f(flexibility, "flexibility");
        return b(this, (l) null, flexibility, false, (t0) null, 13, (Object) null);
    }
}
