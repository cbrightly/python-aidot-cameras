package okhttp3.internal.ws;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.Closeable;
import java.util.zip.Inflater;
import kotlin.jvm.internal.k;
import okio.e0;
import okio.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessageInflater.kt */
public final class c implements Closeable {
    private final okio.c c;
    private final Inflater d;
    private final n f;
    private final boolean q;

    public c(boolean noContextTakeover) {
        this.q = noContextTakeover;
        okio.c cVar = new okio.c();
        this.c = cVar;
        Inflater inflater = new Inflater(true);
        this.d = inflater;
        this.f = new n((e0) cVar, inflater);
    }

    public final void a(@NotNull okio.c buffer) {
        k.f(buffer, "buffer");
        if (this.c.e1() == 0) {
            if (this.q) {
                this.d.reset();
            }
            this.c.writeAll(buffer);
            this.c.writeInt(65535);
            long totalBytesToRead = this.d.getBytesRead() + this.c.e1();
            do {
                this.f.a(buffer, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
            } while (this.d.getBytesRead() < totalBytesToRead);
            return;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    public void close() {
        this.f.close();
    }
}
