package io.ktor.network.sockets;

import java.io.Closeable;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sockets.kt */
public interface d extends Closeable, f1 {
    @NotNull
    z1 L0();

    /* compiled from: Sockets.kt */
    public static final class a {
        public static void a(d $this) {
            try {
                $this.close();
            } catch (Throwable th) {
            }
        }
    }
}
