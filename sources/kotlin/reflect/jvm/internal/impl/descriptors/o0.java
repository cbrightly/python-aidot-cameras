package kotlin.reflect.jvm.internal.impl.descriptors;

import org.jetbrains.annotations.NotNull;

/* compiled from: SourceElement */
public interface o0 {
    public static final o0 a = new a();

    @NotNull
    p0 b();

    /* compiled from: SourceElement */
    public static final class a implements o0 {
        private static /* synthetic */ void d(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[]{"kotlin/reflect/jvm/internal/impl/descriptors/SourceElement$1", "getContainingFile"}));
        }

        a() {
        }

        public String toString() {
            return "NO_SOURCE";
        }

        @NotNull
        public p0 b() {
            p0 p0Var = p0.a;
            if (p0Var == null) {
                d(0);
            }
            return p0Var;
        }
    }
}
