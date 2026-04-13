package okhttp3.internal.cache;

import java.io.IOException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import okio.b0;
import okio.c;
import okio.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: FaultHidingSink.kt */
public class e extends j {
    private boolean c;
    @NotNull
    private final l<IOException, x> d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@NotNull b0 delegate, @NotNull l<? super IOException, x> onException) {
        super(delegate);
        k.f(delegate, "delegate");
        k.f(onException, "onException");
        this.d = onException;
    }

    public void write(@NotNull c source, long byteCount) {
        k.f(source, "source");
        if (this.c) {
            source.skip(byteCount);
            return;
        }
        try {
            super.write(source, byteCount);
        } catch (IOException e) {
            this.c = true;
            this.d.invoke(e);
        }
    }

    public void flush() {
        if (!this.c) {
            try {
                super.flush();
            } catch (IOException e) {
                this.c = true;
                this.d.invoke(e);
            }
        }
    }

    public void close() {
        if (!this.c) {
            try {
                super.close();
            } catch (IOException e) {
                this.c = true;
                this.d.invoke(e);
            }
        }
    }
}
