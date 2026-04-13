package io.ktor.network.selector;

import java.nio.channels.spi.SelectorProvider;
import kotlin.coroutines.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SelectorManager.kt */
public interface m {
    @NotNull
    SelectorProvider a();

    void c(@NotNull k kVar);

    @Nullable
    Object g(@NotNull k kVar, @NotNull j jVar, @NotNull d<? super x> dVar);
}
