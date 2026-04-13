package okio;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PeekSource.kt */
public final class u implements e0 {
    private final c c;
    private y d;
    private int f;
    private boolean q;
    private long x;
    private final e y;

    public /* synthetic */ g cursor() {
        return d0.a(this);
    }

    public u(@NotNull e upstream) {
        k.e(upstream, "upstream");
        this.y = upstream;
        c buffer = upstream.getBuffer();
        this.c = buffer;
        y yVar = buffer.c;
        this.d = yVar;
        this.f = yVar != null ? yVar.c : -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        if (r2 == r5.c) goto L_0x002d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0074  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long read(@org.jetbrains.annotations.NotNull okio.c r9, long r10) {
        /*
            r8 = this;
            java.lang.String r0 = "sink"
            kotlin.jvm.internal.k.e(r9, r0)
            r0 = 0
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            r3 = 0
            r4 = 1
            if (r2 < 0) goto L_0x0010
            r2 = r4
            goto L_0x0011
        L_0x0010:
            r2 = r3
        L_0x0011:
            if (r2 == 0) goto L_0x0090
            boolean r2 = r8.q
            r2 = r2 ^ r4
            if (r2 == 0) goto L_0x0082
            okio.y r2 = r8.d
            if (r2 == 0) goto L_0x002d
            okio.c r5 = r8.c
            okio.y r5 = r5.c
            if (r2 != r5) goto L_0x002e
            int r2 = r8.f
            kotlin.jvm.internal.k.c(r5)
            int r5 = r5.c
            if (r2 != r5) goto L_0x002e
        L_0x002d:
            r3 = r4
        L_0x002e:
            if (r3 == 0) goto L_0x0074
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r2 != 0) goto L_0x0035
            return r0
        L_0x0035:
            okio.e r0 = r8.y
            long r1 = r8.x
            r3 = 1
            long r1 = r1 + r3
            boolean r0 = r0.request(r1)
            if (r0 != 0) goto L_0x0045
            r0 = -1
            return r0
        L_0x0045:
            okio.y r0 = r8.d
            if (r0 != 0) goto L_0x0058
            okio.c r0 = r8.c
            okio.y r0 = r0.c
            if (r0 == 0) goto L_0x0058
            r8.d = r0
            kotlin.jvm.internal.k.c(r0)
            int r0 = r0.c
            r8.f = r0
        L_0x0058:
            okio.c r0 = r8.c
            long r0 = r0.e1()
            long r2 = r8.x
            long r0 = r0 - r2
            long r0 = java.lang.Math.min(r10, r0)
            okio.c r2 = r8.c
            long r4 = r8.x
            r3 = r9
            r6 = r0
            r2.j(r3, r4, r6)
            long r2 = r8.x
            long r2 = r2 + r0
            r8.x = r2
            return r0
        L_0x0074:
            r0 = 0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Peek source is invalid because upstream source was used"
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0082:
            r0 = 0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "closed"
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0090:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "byteCount < 0: "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r0 = r1.toString()
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.u.read(okio.c, long):long");
    }

    @NotNull
    public f0 timeout() {
        return this.y.timeout();
    }

    public void close() {
        this.q = true;
    }
}
