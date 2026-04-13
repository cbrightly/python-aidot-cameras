package io.ktor.features;

import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import kotlin.reflect.full.d;
import kotlin.reflect.n;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Errors.kt */
public final class CannotTransformContentToTypeException extends ContentTransformationException implements g0<CannotTransformContentToTypeException> {
    private final n type;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CannotTransformContentToTypeException(@NotNull n type2) {
        super("Cannot transform this request's content to " + type2);
        k.f(type2, IjkMediaMeta.IJKM_KEY_TYPE);
        this.type = type2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ CannotTransformContentToTypeException(@NotNull c<?> type2) {
        this(d.d(type2));
        k.f(type2, IjkMediaMeta.IJKM_KEY_TYPE);
    }

    @Nullable
    public CannotTransformContentToTypeException createCopy() {
        CannotTransformContentToTypeException it = new CannotTransformContentToTypeException(this.type);
        it.initCause(this);
        return it;
    }
}
