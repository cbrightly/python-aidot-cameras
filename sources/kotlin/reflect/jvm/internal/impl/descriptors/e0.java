package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackageViewDescriptor.kt */
public interface e0 extends m {
    @NotNull
    List<b0> b0();

    @NotNull
    b e();

    boolean isEmpty();

    @NotNull
    h l();

    @NotNull
    y w0();

    /* compiled from: PackageViewDescriptor.kt */
    public static final class a {
        public static boolean a(e0 $this) {
            return $this.b0().isEmpty();
        }
    }
}
