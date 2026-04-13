package okio;

import java.util.zip.Deflater;
import kotlin.jvm.internal.k;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeflaterSink.kt */
public final class h implements b0 {
    private boolean c;
    private final d d;
    private final Deflater f;

    public h(@NotNull d sink, @NotNull Deflater deflater) {
        k.e(sink, "sink");
        k.e(deflater, "deflater");
        this.d = sink;
        this.f = deflater;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public h(@NotNull b0 sink, @NotNull Deflater deflater) {
        this(p.c(sink), deflater);
        k.e(sink, "sink");
        k.e(deflater, "deflater");
    }

    public void write(@NotNull c source, long byteCount) {
        k.e(source, "source");
        i0.b(source.e1(), 0, byteCount);
        long remaining = byteCount;
        while (remaining > 0) {
            y head = source.c;
            k.c(head);
            int toDeflate = (int) Math.min(remaining, (long) (head.d - head.c));
            this.f.setInput(head.b, head.c, toDeflate);
            a(false);
            source.d1(source.e1() - ((long) toDeflate));
            int i = head.c + toDeflate;
            head.c = i;
            if (i == head.d) {
                source.c = head.b();
                z.b(head);
            }
            remaining -= (long) toDeflate;
        }
    }

    @IgnoreJRERequirement
    private final void a(boolean syncFlush) {
        y s;
        int deflated;
        c buffer = this.d.getBuffer();
        while (true) {
            s = buffer.h1(1);
            if (syncFlush) {
                Deflater deflater = this.f;
                byte[] bArr = s.b;
                int i = s.d;
                deflated = deflater.deflate(bArr, i, 8192 - i, 2);
            } else {
                Deflater deflater2 = this.f;
                byte[] bArr2 = s.b;
                int i2 = s.d;
                deflated = deflater2.deflate(bArr2, i2, 8192 - i2);
            }
            if (deflated > 0) {
                s.d += deflated;
                buffer.d1(buffer.e1() + ((long) deflated));
                this.d.emitCompleteSegments();
            } else if (this.f.needsInput()) {
                break;
            }
        }
        if (s.c == s.d) {
            buffer.c = s.b();
            z.b(s);
        }
    }

    public void flush() {
        a(true);
        this.d.flush();
    }

    public final void c() {
        this.f.finish();
        a(false);
    }

    public void close() {
        if (!this.c) {
            Throwable thrown = null;
            try {
                c();
            } catch (Throwable e) {
                thrown = e;
            }
            try {
                this.f.end();
            } catch (Throwable e2) {
                if (thrown == null) {
                    thrown = e2;
                }
            }
            try {
                this.d.close();
            } catch (Throwable e3) {
                if (thrown == null) {
                    thrown = e3;
                }
            }
            this.c = true;
            if (thrown != null) {
                throw thrown;
            }
        }
    }

    @NotNull
    public f0 timeout() {
        return this.d.timeout();
    }

    @NotNull
    public String toString() {
        return "DeflaterSink(" + this.d + ')';
    }
}
