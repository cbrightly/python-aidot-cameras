package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.p0;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.n;
import kotlin.reflect.jvm.internal.impl.load.java.sources.b;
import kotlin.reflect.jvm.internal.impl.load.java.structure.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeSourceElementFactory.kt */
public final class m implements b {
    public static final m a = new m();

    /* compiled from: RuntimeSourceElementFactory.kt */
    public static final class a implements kotlin.reflect.jvm.internal.impl.load.java.sources.a {
        @NotNull
        private final n b;

        public a(@NotNull n javaElement) {
            k.f(javaElement, "javaElement");
            this.b = javaElement;
        }

        @NotNull
        /* renamed from: d */
        public n c() {
            return this.b;
        }

        @NotNull
        public String toString() {
            return getClass().getName() + ": " + c().toString();
        }

        @NotNull
        public p0 b() {
            p0 p0Var = p0.a;
            k.b(p0Var, "SourceFile.NO_SOURCE_FILE");
            return p0Var;
        }
    }

    private m() {
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.load.java.sources.a a(@NotNull l javaElement) {
        k.f(javaElement, "javaElement");
        return new a((n) javaElement);
    }
}
