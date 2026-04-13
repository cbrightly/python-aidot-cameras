package io.ktor.utils.io;

import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;

/* compiled from: Coroutines.kt */
public final class o implements w, z, o0 {
    @NotNull
    private final f c;
    private final /* synthetic */ o0 d;

    @NotNull
    public g getCoroutineContext() {
        return this.d.getCoroutineContext();
    }

    public o(@NotNull o0 delegate, @NotNull f channel) {
        k.f(delegate, "delegate");
        k.f(channel, "channel");
        this.d = delegate;
        this.c = channel;
    }

    @NotNull
    /* renamed from: a */
    public f getChannel() {
        return this.c;
    }
}
