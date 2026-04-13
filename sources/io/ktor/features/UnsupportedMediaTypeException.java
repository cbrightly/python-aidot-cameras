package io.ktor.features;

import io.ktor.http.c;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Errors.kt */
public final class UnsupportedMediaTypeException extends ContentTransformationException implements g0<UnsupportedMediaTypeException> {
    private final c contentType;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UnsupportedMediaTypeException(@NotNull c contentType2) {
        super("Content type " + contentType2 + " is not supported");
        k.f(contentType2, "contentType");
        this.contentType = contentType2;
    }

    @Nullable
    public UnsupportedMediaTypeException createCopy() {
        UnsupportedMediaTypeException it = new UnsupportedMediaTypeException(this.contentType);
        it.initCause(this);
        return it;
    }
}
