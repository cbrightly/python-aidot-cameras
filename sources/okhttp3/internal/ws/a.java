package okhttp3.internal.ws;

import java.io.Closeable;
import java.util.zip.Deflater;
import okio.b0;
import okio.c;
import okio.f;
import okio.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessageDeflater.kt */
public final class a implements Closeable {
    private final c c;
    private final Deflater d;
    private final h f;
    private final boolean q;

    public a(boolean noContextTakeover) {
        this.q = noContextTakeover;
        c cVar = new c();
        this.c = cVar;
        Deflater deflater = new Deflater(-1, true);
        this.d = deflater;
        this.f = new h((b0) cVar, deflater);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0056, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0057, code lost:
        kotlin.io.b.a(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005a, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(@org.jetbrains.annotations.NotNull okio.c r7) {
        /*
            r6 = this;
            java.lang.String r0 = "buffer"
            kotlin.jvm.internal.k.f(r7, r0)
            okio.c r0 = r6.c
            long r0 = r0.e1()
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0015
            r0 = r1
            goto L_0x0016
        L_0x0015:
            r0 = r2
        L_0x0016:
            if (r0 == 0) goto L_0x006b
            boolean r0 = r6.q
            if (r0 == 0) goto L_0x0021
            java.util.zip.Deflater r0 = r6.d
            r0.reset()
        L_0x0021:
            okio.h r0 = r6.f
            long r3 = r7.e1()
            r0.write(r7, r3)
            okio.h r0 = r6.f
            r0.flush()
            okio.c r0 = r6.c
            okio.f r3 = okhttp3.internal.ws.b.a
            boolean r0 = r6.c(r0, r3)
            if (r0 == 0) goto L_0x005b
            okio.c r0 = r6.c
            long r2 = r0.e1()
            r0 = 4
            long r4 = (long) r0
            long r2 = r2 - r4
            okio.c r0 = r6.c
            r4 = 0
            okio.c$a r0 = okio.c.v(r0, r4, r1, r4)
            r1 = r0
            r5 = 0
            r1.g(r2)     // Catch:{ all -> 0x0054 }
            kotlin.io.b.a(r0, r4)
            goto L_0x0060
        L_0x0054:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0056 }
        L_0x0056:
            r4 = move-exception
            kotlin.io.b.a(r0, r1)
            throw r4
        L_0x005b:
            okio.c r0 = r6.c
            r0.writeByte(r2)
        L_0x0060:
            okio.c r0 = r6.c
            long r1 = r0.e1()
            r7.write((okio.c) r0, (long) r1)
            return
        L_0x006b:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Failed requirement."
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.a.a(okio.c):void");
    }

    public void close() {
        this.f.close();
    }

    private final boolean c(@NotNull c $this$endsWith, f suffix) {
        return $this$endsWith.V($this$endsWith.e1() - ((long) suffix.size()), suffix);
    }
}
