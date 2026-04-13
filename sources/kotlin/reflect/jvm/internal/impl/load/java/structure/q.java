package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: javaElements.kt */
public interface q extends p, x {
    boolean G();

    @NotNull
    List<y> g();

    @NotNull
    v getReturnType();

    @Nullable
    b m();

    /* compiled from: javaElements.kt */
    public static final class a {
        public static boolean a(q $this) {
            return $this.m() != null;
        }
    }
}
