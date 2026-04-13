package io.ktor.application;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.ktor.util.a;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ApplicationFeature.kt */
public final class MissingApplicationFeatureException extends IllegalStateException implements g0<MissingApplicationFeatureException> {
    @NotNull
    private final a<?> key;

    public MissingApplicationFeatureException(@NotNull a<?> key2) {
        k.f(key2, CacheEntity.KEY);
        this.key = key2;
    }

    @NotNull
    public final a<?> getKey() {
        return this.key;
    }

    @NotNull
    public String getMessage() {
        return "Application feature " + this.key.a() + " is not installed";
    }

    @Nullable
    public MissingApplicationFeatureException createCopy() {
        MissingApplicationFeatureException it = new MissingApplicationFeatureException(this.key);
        it.initCause(this);
        return it;
    }
}
