package io.ktor.features;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Errors.kt */
public final class ParameterConversionException extends BadRequestException implements g0<ParameterConversionException> {
    @NotNull
    private final String parameterName;
    @NotNull
    private final String type;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ParameterConversionException(@NotNull String parameterName2, @NotNull String type2, @Nullable Throwable cause) {
        super("Request parameter " + parameterName2 + " couldn't be parsed/converted to " + type2, cause);
        k.f(parameterName2, "parameterName");
        k.f(type2, IjkMediaMeta.IJKM_KEY_TYPE);
        this.parameterName = parameterName2;
        this.type = type2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ParameterConversionException(String str, String str2, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : th);
    }

    @NotNull
    public final String getParameterName() {
        return this.parameterName;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    @NotNull
    public ParameterConversionException createCopy() {
        ParameterConversionException it = new ParameterConversionException(this.parameterName, this.type, this);
        it.initCause(this);
        return it;
    }
}
