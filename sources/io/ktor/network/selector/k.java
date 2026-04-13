package io.ktor.network.selector;

import java.io.Closeable;
import java.nio.channels.SelectableChannel;
import kotlinx.coroutines.f1;
import org.jetbrains.annotations.NotNull;

/* compiled from: Selectable.kt */
public interface k extends Closeable, f1 {
    @NotNull
    g D();

    int X();

    void Z(@NotNull j jVar, boolean z);

    @NotNull
    SelectableChannel getChannel();
}
