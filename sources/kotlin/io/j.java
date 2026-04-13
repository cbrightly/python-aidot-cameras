package kotlin.io;

import java.io.File;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Utils.kt */
public class j extends i {
    @NotNull
    public static final String d(@NotNull File $this$extension) {
        k.e($this$extension, "$this$extension");
        String name = $this$extension.getName();
        k.d(name, "name");
        return x.S0(name, '.', "");
    }

    @NotNull
    public static final String e(@NotNull File $this$nameWithoutExtension) {
        k.e($this$nameWithoutExtension, "$this$nameWithoutExtension");
        String name = $this$nameWithoutExtension.getName();
        k.d(name, "name");
        return x.d1(name, ".", (String) null, 2, (Object) null);
    }

    public static final boolean c(@NotNull File $this$deleteRecursively) {
        k.e($this$deleteRecursively, "$this$deleteRecursively");
        boolean accumulator$iv = true;
        for (File it : i.b($this$deleteRecursively)) {
            accumulator$iv = (it.delete() || !it.exists()) && accumulator$iv;
        }
        return accumulator$iv;
    }
}
