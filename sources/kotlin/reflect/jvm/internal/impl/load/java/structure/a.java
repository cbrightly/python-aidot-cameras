package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: javaElements.kt */
public interface a extends l {
    @Nullable
    kotlin.reflect.jvm.internal.impl.name.a d();

    @NotNull
    Collection<b> getArguments();

    boolean h();

    @Nullable
    g resolve();

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.structure.a$a  reason: collision with other inner class name */
    /* compiled from: javaElements.kt */
    public static final class C0366a {
        public static boolean a(a $this) {
            return false;
        }
    }
}
