package io.ktor.features;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;

/* compiled from: Errors.kt */
public final class MissingRequestParameterException extends BadRequestException implements g0<MissingRequestParameterException> {
    @NotNull
    private final String parameterName;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MissingRequestParameterException(@NotNull String parameterName2) {
        super("Request parameter " + parameterName2 + " is missing", (Throwable) null, 2, (DefaultConstructorMarker) null);
        k.f(parameterName2, "parameterName");
        this.parameterName = parameterName2;
    }

    @NotNull
    public final String getParameterName() {
        return this.parameterName;
    }

    @NotNull
    public MissingRequestParameterException createCopy() {
        MissingRequestParameterException it = new MissingRequestParameterException(this.parameterName);
        it.initCause(this);
        return it;
    }
}
