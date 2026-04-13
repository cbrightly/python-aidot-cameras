package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.a;
import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: StackFramesJvm.kt */
public final class u {
    @NotNull
    public static final StackTraceElement a(@NotNull c<?> kClass, @NotNull String methodName, @NotNull String fileName, int lineNumber) {
        k.f(kClass, "kClass");
        k.f(methodName, "methodName");
        k.f(fileName, Progress.FILE_NAME);
        return new StackTraceElement(a.b(kClass).getName(), methodName, fileName, lineNumber);
    }
}
