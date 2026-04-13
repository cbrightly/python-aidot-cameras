package okio;

import java.io.Closeable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Source.kt */
public interface e0 extends Closeable {
    void close();

    @Nullable
    g cursor();

    long read(@NotNull c cVar, long j);

    @NotNull
    f0 timeout();
}
