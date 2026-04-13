package kotlin.reflect.jvm;

import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import kotlin.reflect.jvm.internal.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: KClassesJvm.kt */
public final class a {
    @NotNull
    public static final String a(@NotNull c<?> $this$jvmName) {
        k.f($this$jvmName, "$this$jvmName");
        String name = ((g) $this$jvmName).b().getName();
        k.b(name, "(this as KClassImpl).jClass.name");
        return name;
    }
}
