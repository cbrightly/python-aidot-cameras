package okio;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmOkio.kt */
public final class c0 extends a {
    private final Socket m;

    public c0(@NotNull Socket socket) {
        k.e(socket, "socket");
        this.m = socket;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public IOException w(@Nullable IOException cause) {
        SocketTimeoutException ioe = new SocketTimeoutException("timeout");
        if (cause != null) {
            ioe.initCause(cause);
        }
        return ioe;
    }

    /* access modifiers changed from: protected */
    public void A() {
        try {
            this.m.close();
        } catch (Exception e) {
            Logger a = q.a;
            Level level = Level.WARNING;
            a.log(level, "Failed to close timed out socket " + this.m, e);
        } catch (AssertionError e2) {
            if (p.e(e2)) {
                Logger a2 = q.a;
                Level level2 = Level.WARNING;
                a2.log(level2, "Failed to close timed out socket " + this.m, e2);
                return;
            }
            throw e2;
        }
    }
}
