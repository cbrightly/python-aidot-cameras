package io.ktor.application;

import io.ktor.util.a;
import io.ktor.util.pipeline.c;
import kotlin.jvm.functions.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationFeature.kt */
public interface f<TPipeline extends c<?, b>, TConfiguration, TFeature> {
    @NotNull
    TFeature a(@NotNull TPipeline tpipeline, @NotNull l<? super TConfiguration, x> lVar);

    @NotNull
    a<TFeature> getKey();
}
