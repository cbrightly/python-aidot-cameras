package okio.internal;

import kotlin.jvm.internal.k;
import okio.c;
import okio.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: -Buffer.kt */
public final class a implements g {
    private final c c;
    private final g d;

    public a(@NotNull c buffer, @NotNull g sourceCursor) {
        k.e(buffer, "buffer");
        k.e(sourceCursor, "sourceCursor");
        this.c = buffer;
        this.d = sourceCursor;
    }
}
