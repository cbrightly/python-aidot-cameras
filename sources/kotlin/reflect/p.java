package kotlin.reflect;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.e;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: KTypeProjection.kt */
public final class p {
    @NotNull
    public static final p a = new p((r) null, (n) null);
    @NotNull
    public static final a b = new a((DefaultConstructorMarker) null);
    @Nullable
    private final r c;
    @Nullable
    private final n d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p)) {
            return false;
        }
        p pVar = (p) obj;
        return k.a(this.c, pVar.c) && k.a(this.d, pVar.d);
    }

    public int hashCode() {
        r rVar = this.c;
        int i = 0;
        int hashCode = (rVar != null ? rVar.hashCode() : 0) * 31;
        n nVar = this.d;
        if (nVar != null) {
            i = nVar.hashCode();
        }
        return hashCode + i;
    }

    public p(@Nullable r variance, @Nullable n type) {
        String str;
        this.c = variance;
        this.d = type;
        if (!((variance == null) != (type == null) ? false : true)) {
            if (variance == null) {
                str = "Star projection must have no type specified.";
            } else {
                str = "The projection variance " + variance + " requires type to be specified.";
            }
            throw new IllegalArgumentException(str.toString());
        }
    }

    @Nullable
    public final r b() {
        return this.c;
    }

    @Nullable
    public final n a() {
        return this.d;
    }

    @NotNull
    public String toString() {
        r rVar = this.c;
        if (rVar == null) {
            return e.ANY_MARKER;
        }
        switch (q.a[rVar.ordinal()]) {
            case 1:
                return String.valueOf(this.d);
            case 2:
                return "in " + this.d;
            case 3:
                return "out " + this.d;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* compiled from: KTypeProjection.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final p c() {
            return p.a;
        }

        @NotNull
        public final p d(@NotNull n type) {
            k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
            return new p(r.INVARIANT, type);
        }

        @NotNull
        public final p a(@NotNull n type) {
            k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
            return new p(r.IN, type);
        }

        @NotNull
        public final p b(@NotNull n type) {
            k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
            return new p(r.OUT, type);
        }
    }
}
