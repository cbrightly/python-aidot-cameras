package io.ktor.util.pipeline;

import io.ktor.util.u;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.a0;
import kotlin.reflect.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PipelineContext.kt */
public final class k implements e, d<?> {
    public static final k c = new k();

    private k() {
    }

    @Nullable
    public e getCallerFrame() {
        return null;
    }

    @Nullable
    public StackTraceElement getStackTraceElement() {
        c b = a0.b(j.class);
        j jVar = j.a;
        return u.a(b, "failedToCaptureStackFrame", "StackWalkingFailed.kt", 8);
    }

    @NotNull
    public g getContext() {
        return h.INSTANCE;
    }

    public void resumeWith(@NotNull Object result) {
        j.a.a();
    }
}
