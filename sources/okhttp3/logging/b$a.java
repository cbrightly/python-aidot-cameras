package okhttp3.logging;

import kotlin.jvm.internal.k;
import okhttp3.internal.platform.h;
import okhttp3.logging.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpLoggingInterceptor.kt */
public final class b$a implements a.b {
    b$a() {
    }

    public void a(@NotNull String message) {
        k.f(message, "message");
        h.l(h.c.g(), message, 0, (Throwable) null, 6, (Object) null);
    }
}
