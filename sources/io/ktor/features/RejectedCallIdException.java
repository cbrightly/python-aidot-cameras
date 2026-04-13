package io.ktor.features;

import kotlin.jvm.internal.k;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CallId.kt */
public final class RejectedCallIdException extends IllegalArgumentException implements g0<RejectedCallIdException> {
    @NotNull
    private final String illegalCallId;

    public RejectedCallIdException(@NotNull String illegalCallId2) {
        k.f(illegalCallId2, "illegalCallId");
        this.illegalCallId = illegalCallId2;
    }

    @NotNull
    public final String getIllegalCallId() {
        return this.illegalCallId;
    }

    @Nullable
    public RejectedCallIdException createCopy() {
        RejectedCallIdException it = new RejectedCallIdException(this.illegalCallId);
        it.initCause(this);
        return it;
    }
}
