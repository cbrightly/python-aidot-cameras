package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSystemContext.kt */
public interface m extends o {
    boolean A(@NotNull k kVar, @NotNull k kVar2);

    int B(@NotNull k kVar);

    boolean E(@NotNull k kVar);

    @NotNull
    Collection<g> F(@NotNull k kVar);

    @NotNull
    Collection<g> G(@NotNull h hVar);

    @NotNull
    k H(@NotNull g gVar);

    boolean I(@NotNull k kVar);

    @Nullable
    f K(@NotNull g gVar);

    @NotNull
    g L(@NotNull List<? extends g> list);

    @Nullable
    g M(@NotNull c cVar);

    boolean N(@NotNull k kVar);

    @NotNull
    h O(@NotNull h hVar, boolean z);

    boolean P(@NotNull k kVar);

    @NotNull
    h R(@NotNull f fVar);

    @Nullable
    c S(@NotNull h hVar);

    @NotNull
    h T(@NotNull g gVar);

    @NotNull
    p U(@NotNull j jVar);

    @Nullable
    h X(@NotNull h hVar, @NotNull b bVar);

    @Nullable
    d Y(@NotNull h hVar);

    boolean Z(@NotNull k kVar);

    @Nullable
    h a(@NotNull g gVar);

    @NotNull
    g a0(@NotNull j jVar);

    @NotNull
    k b(@NotNull h hVar);

    @Nullable
    e b0(@NotNull f fVar);

    int d(@NotNull g gVar);

    @NotNull
    i e(@NotNull h hVar);

    @NotNull
    j f(@NotNull i iVar, int i);

    @NotNull
    l g(@NotNull k kVar, int i);

    boolean i(@NotNull j jVar);

    @NotNull
    p j(@NotNull l lVar);

    boolean k(@NotNull h hVar);

    int l(@NotNull i iVar);

    @NotNull
    h m(@NotNull g gVar);

    boolean n(@NotNull h hVar);

    boolean o(@NotNull g gVar);

    @NotNull
    j q(@NotNull g gVar);

    @NotNull
    j r(@NotNull g gVar, int i);

    boolean t(@NotNull k kVar);

    boolean u(@NotNull h hVar);

    boolean v(@NotNull g gVar);

    boolean w(@NotNull k kVar);

    @NotNull
    h y(@NotNull f fVar);

    boolean z(@NotNull h hVar);

    /* compiled from: TypeSystemContext.kt */
    public static final class a {
        @Nullable
        public static j c(m $this, @NotNull h $this$getArgumentOrNull, int index) {
            k.f($this$getArgumentOrNull, "$this$getArgumentOrNull");
            int d = $this.d($this$getArgumentOrNull);
            if (index >= 0 && d > index) {
                return $this.r($this$getArgumentOrNull, index);
            }
            return null;
        }

        @NotNull
        public static h j(m $this, @NotNull g $this$lowerBoundIfFlexible) {
            h hVar;
            k.f($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
            f K = $this.K($this$lowerBoundIfFlexible);
            if ((K == null || (hVar = $this.y(K)) == null) && (hVar = $this.a($this$lowerBoundIfFlexible)) == null) {
                k.n();
            }
            return hVar;
        }

        @NotNull
        public static h m(m $this, @NotNull g $this$upperBoundIfFlexible) {
            h hVar;
            k.f($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
            f K = $this.K($this$upperBoundIfFlexible);
            if ((K == null || (hVar = $this.R(K)) == null) && (hVar = $this.a($this$upperBoundIfFlexible)) == null) {
                k.n();
            }
            return hVar;
        }

        public static boolean g(m $this, @NotNull g $this$isDynamic) {
            k.f($this$isDynamic, "$this$isDynamic");
            f K = $this.K($this$isDynamic);
            return (K != null ? $this.b0(K) : null) != null;
        }

        public static boolean f(m $this, @NotNull g $this$isDefinitelyNotNullType) {
            k.f($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
            h a = $this.a($this$isDefinitelyNotNullType);
            return (a != null ? $this.Y(a) : null) != null;
        }

        public static boolean d(m $this, @NotNull g $this$hasFlexibleNullability) {
            k.f($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
            return $this.n($this.T($this$hasFlexibleNullability)) != $this.n($this.m($this$hasFlexibleNullability));
        }

        @NotNull
        public static k l(m $this, @NotNull g $this$typeConstructor) {
            k.f($this$typeConstructor, "$this$typeConstructor");
            h a = $this.a($this$typeConstructor);
            if (a == null) {
                a = $this.T($this$typeConstructor);
            }
            return $this.b(a);
        }

        public static boolean i(m $this, @NotNull g $this$isNothing) {
            k.f($this$isNothing, "$this$isNothing");
            return $this.E($this.H($this$isNothing)) && !$this.v($this$isNothing);
        }

        public static boolean e(m $this, @NotNull h $this$isClassType) {
            k.f($this$isClassType, "$this$isClassType");
            return $this.N($this.b($this$isClassType));
        }

        @Nullable
        public static List<h> a(m $this, @NotNull h $this$fastCorrespondingSupertypes, @NotNull k constructor) {
            k.f($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
            k.f(constructor, "constructor");
            return null;
        }

        public static boolean h(m $this, @NotNull h $this$isIntegerLiteralType) {
            k.f($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
            return $this.w($this.b($this$isIntegerLiteralType));
        }

        @NotNull
        public static j b(m $this, @NotNull i $this$get, int index) {
            k.f($this$get, "$this$get");
            if ($this$get instanceof h) {
                return $this.r((g) $this$get, index);
            }
            if ($this$get instanceof a) {
                Object obj = ((a) $this$get).get(index);
                k.b(obj, "get(index)");
                return (j) obj;
            }
            throw new IllegalStateException(("unknown type argument list type: " + $this$get + ", " + a0.b($this$get.getClass())).toString());
        }

        public static int k(m $this, @NotNull i $this$size) {
            k.f($this$size, "$this$size");
            if ($this$size instanceof h) {
                return $this.d((g) $this$size);
            }
            if ($this$size instanceof a) {
                return ((a) $this$size).size();
            }
            throw new IllegalStateException(("unknown type argument list type: " + $this$size + ", " + a0.b($this$size.getClass())).toString());
        }
    }
}
