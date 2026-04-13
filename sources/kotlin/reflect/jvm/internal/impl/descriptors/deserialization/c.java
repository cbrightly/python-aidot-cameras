package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformDependentDeclarationFilter.kt */
public interface c {
    boolean b(@NotNull e eVar, @NotNull n0 n0Var);

    /* compiled from: PlatformDependentDeclarationFilter.kt */
    public static final class a implements c {
        public static final a a = new a();

        private a() {
        }

        public boolean b(@NotNull e classDescriptor, @NotNull n0 functionDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            k.f(functionDescriptor, "functionDescriptor");
            return true;
        }
    }

    /* compiled from: PlatformDependentDeclarationFilter.kt */
    public static final class b implements c {
        public static final b a = new b();

        private b() {
        }

        public boolean b(@NotNull e classDescriptor, @NotNull n0 functionDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            k.f(functionDescriptor, "functionDescriptor");
            return !functionDescriptor.getAnnotations().I(d.a());
        }
    }
}
