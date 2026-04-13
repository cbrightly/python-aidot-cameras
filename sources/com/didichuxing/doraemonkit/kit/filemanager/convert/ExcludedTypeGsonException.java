package com.didichuxing.doraemonkit.kit.filemanager.convert;

import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import kotlin.reflect.jvm.a;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: GsonConverter.kt */
public final class ExcludedTypeGsonException extends Exception implements g0<ExcludedTypeGsonException> {
    @NotNull
    private final c<?> type;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ExcludedTypeGsonException(@NotNull c<?> type2) {
        super("Type " + a.a(type2) + " is excluded so couldn't be used in receive");
        k.f(type2, IjkMediaMeta.IJKM_KEY_TYPE);
        this.type = type2;
    }

    @NotNull
    public final c<?> getType() {
        return this.type;
    }

    @Nullable
    public ExcludedTypeGsonException createCopy() {
        ExcludedTypeGsonException it = new ExcludedTypeGsonException(this.type);
        it.initCause(this);
        return it;
    }
}
