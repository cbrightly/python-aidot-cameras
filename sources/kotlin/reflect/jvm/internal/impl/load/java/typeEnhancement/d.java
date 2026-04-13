package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeQualifiers.kt */
public final class d {
    /* access modifiers changed from: private */
    @NotNull
    public static final d a = new d((g) null, (e) null, false, false, 8, (DefaultConstructorMarker) null);
    public static final a b = new a((DefaultConstructorMarker) null);
    @Nullable
    private final g c;
    @Nullable
    private final e d;
    private final boolean e;
    private final boolean f;

    public d(@Nullable g nullability, @Nullable e mutability, boolean isNotNullTypeParameter, boolean isNullabilityQualifierForWarning) {
        this.c = nullability;
        this.d = mutability;
        this.e = isNotNullTypeParameter;
        this.f = isNullabilityQualifierForWarning;
    }

    @Nullable
    public final g c() {
        return this.c;
    }

    @Nullable
    public final e b() {
        return this.d;
    }

    public final boolean d() {
        return this.e;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(g gVar, e eVar, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(gVar, eVar, z, (i & 8) != 0 ? false : z2);
    }

    public final boolean e() {
        return this.f;
    }

    /* compiled from: typeQualifiers.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final d a() {
            return d.a;
        }
    }
}
