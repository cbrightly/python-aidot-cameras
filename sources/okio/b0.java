package okio;

import java.io.Closeable;
import java.io.Flushable;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sink.kt */
public interface b0 extends Closeable, Flushable {
    void close();

    void flush();

    @NotNull
    f0 timeout();

    void write(@NotNull c cVar, long j);
}
