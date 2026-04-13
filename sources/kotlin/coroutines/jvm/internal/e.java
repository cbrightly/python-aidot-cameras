package kotlin.coroutines.jvm.internal;

import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineStackFrame.kt */
public interface e {
    @Nullable
    e getCallerFrame();

    @Nullable
    StackTraceElement getStackTraceElement();
}
