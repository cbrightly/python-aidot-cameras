package io.ktor.utils.io.jvm.javaio;

import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: Blocking.kt */
public final class e extends i0 {
    public static final e c = new e();

    private e() {
    }

    public boolean isDispatchNeeded(@NotNull g context) {
        k.f(context, "context");
        return true;
    }

    public void dispatch(@NotNull g context, @NotNull Runnable block) {
        k.f(context, "context");
        k.f(block, "block");
        block.run();
    }
}
