package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.Set;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: DescriptorRenderer.kt */
public interface i {
    void a(boolean z);

    void b(@NotNull n nVar);

    void c(boolean z);

    boolean d();

    void e(boolean z);

    void f(boolean z);

    void g(@NotNull p pVar);

    void h(@NotNull a aVar);

    @NotNull
    Set<b> i();

    boolean j();

    @NotNull
    a k();

    void l(@NotNull Set<b> set);

    void m(@NotNull Set<? extends h> set);

    void n(@NotNull b bVar);

    void o(boolean z);

    void p(boolean z);

    void q(boolean z);

    /* compiled from: DescriptorRenderer.kt */
    public static final class a {
        public static boolean a(i $this) {
            return $this.k().getIncludeAnnotationArguments();
        }

        public static boolean b(i $this) {
            return $this.k().getIncludeEmptyAnnotationArguments();
        }
    }
}
