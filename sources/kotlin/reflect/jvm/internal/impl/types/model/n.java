package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.model.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSystemContext.kt */
public interface n extends m {

    /* compiled from: TypeSystemContext.kt */
    public static final class a {
        @Nullable
        public static List<h> a(n nVar, @NotNull h hVar, @NotNull k kVar) {
            k.f(hVar, "$this$fastCorrespondingSupertypes");
            k.f(kVar, "constructor");
            return m.a.a(nVar, hVar, kVar);
        }

        @NotNull
        public static j b(n nVar, @NotNull i iVar, int i) {
            k.f(iVar, "$this$get");
            return m.a.b(nVar, iVar, i);
        }

        @Nullable
        public static j c(n nVar, @NotNull h hVar, int i) {
            k.f(hVar, "$this$getArgumentOrNull");
            return m.a.c(nVar, hVar, i);
        }

        public static boolean d(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$hasFlexibleNullability");
            return m.a.d(nVar, gVar);
        }

        public static boolean e(n nVar, @NotNull h hVar) {
            k.f(hVar, "$this$isClassType");
            return m.a.e(nVar, hVar);
        }

        public static boolean f(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$isDefinitelyNotNullType");
            return m.a.f(nVar, gVar);
        }

        public static boolean g(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$isDynamic");
            return m.a.g(nVar, gVar);
        }

        public static boolean h(n nVar, @NotNull h hVar) {
            k.f(hVar, "$this$isIntegerLiteralType");
            return m.a.h(nVar, hVar);
        }

        public static boolean i(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$isNothing");
            return m.a.i(nVar, gVar);
        }

        @NotNull
        public static h j(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$lowerBoundIfFlexible");
            return m.a.j(nVar, gVar);
        }

        public static int k(n nVar, @NotNull i iVar) {
            k.f(iVar, "$this$size");
            return m.a.k(nVar, iVar);
        }

        @NotNull
        public static k l(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$typeConstructor");
            return m.a.l(nVar, gVar);
        }

        @NotNull
        public static h m(n nVar, @NotNull g gVar) {
            k.f(gVar, "$this$upperBoundIfFlexible");
            return m.a.m(nVar, gVar);
        }
    }
}
