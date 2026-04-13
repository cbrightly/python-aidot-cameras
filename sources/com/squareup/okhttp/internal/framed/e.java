package com.squareup.okhttp.internal.framed;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import okio.b0;
import okio.d0;
import okio.e0;
import okio.f0;
import okio.g;

/* compiled from: FramedStream */
public final class e {
    long a = 0;
    long b;
    /* access modifiers changed from: private */
    public final int c;
    /* access modifiers changed from: private */
    public final d d;
    private final List<f> e;
    private List<f> f;
    private final c g;
    final b h;
    /* access modifiers changed from: private */
    public final d i = new d();
    /* access modifiers changed from: private */
    public final d j = new d();
    /* access modifiers changed from: private */
    public a k = null;

    e(int id, d connection, boolean outFinished, boolean inFinished, List<f> requestHeaders) {
        if (connection == null) {
            throw new NullPointerException("connection == null");
        } else if (requestHeaders != null) {
            this.c = id;
            this.d = connection;
            this.b = (long) connection.C4.e(65536);
            c cVar = new c((long) connection.B4.e(65536));
            this.g = cVar;
            b bVar = new b();
            this.h = bVar;
            boolean unused = cVar.x = inFinished;
            boolean unused2 = bVar.f = outFinished;
            this.e = requestHeaders;
        } else {
            throw new NullPointerException("requestHeaders == null");
        }
    }

    public int o() {
        return this.c;
    }

    public synchronized boolean t() {
        if (this.k != null) {
            return false;
        }
        if ((this.g.x || this.g.q) && ((this.h.f || this.h.d) && this.f != null)) {
            return false;
        }
        return true;
    }

    public boolean s() {
        if (this.d.f == ((this.c & 1) == 1)) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<com.squareup.okhttp.internal.framed.f> p() {
        /*
            r3 = this;
            monitor-enter(r3)
            com.squareup.okhttp.internal.framed.e$d r0 = r3.i     // Catch:{ all -> 0x0041 }
            r0.u()     // Catch:{ all -> 0x0041 }
        L_0x0006:
            java.util.List<com.squareup.okhttp.internal.framed.f> r0 = r3.f     // Catch:{ all -> 0x003a }
            if (r0 != 0) goto L_0x0014
            com.squareup.okhttp.internal.framed.a r0 = r3.k     // Catch:{ all -> 0x0012 }
            if (r0 != 0) goto L_0x0014
            r3.z()     // Catch:{ all -> 0x0012 }
            goto L_0x0006
        L_0x0012:
            r0 = move-exception
            goto L_0x003b
        L_0x0014:
            com.squareup.okhttp.internal.framed.e$d r0 = r3.i     // Catch:{ all -> 0x0041 }
            r0.B()     // Catch:{ all -> 0x0041 }
            java.util.List<com.squareup.okhttp.internal.framed.f> r0 = r3.f     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0020
            monitor-exit(r3)
            return r0
        L_0x0020:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            r1.<init>()     // Catch:{ all -> 0x0041 }
            java.lang.String r2 = "stream was reset: "
            r1.append(r2)     // Catch:{ all -> 0x0041 }
            com.squareup.okhttp.internal.framed.a r2 = r3.k     // Catch:{ all -> 0x0041 }
            r1.append(r2)     // Catch:{ all -> 0x0041 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0041 }
            r0.<init>(r1)     // Catch:{ all -> 0x0041 }
            throw r0     // Catch:{ all -> 0x0041 }
        L_0x003a:
            r0 = move-exception
        L_0x003b:
            com.squareup.okhttp.internal.framed.e$d r1 = r3.i     // Catch:{ all -> 0x0041 }
            r1.B()     // Catch:{ all -> 0x0041 }
            throw r0     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.p():java.util.List");
    }

    public f0 u() {
        return this.i;
    }

    public f0 A() {
        return this.j;
    }

    public e0 r() {
        return this.g;
    }

    public b0 q() {
        synchronized (this) {
            if (this.f == null) {
                if (!s()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            }
        }
        return this.h;
    }

    public void l(a rstStatusCode) {
        if (m(rstStatusCode)) {
            this.d.q1(this.c, rstStatusCode);
        }
    }

    public void n(a errorCode) {
        if (m(errorCode)) {
            this.d.r1(this.c, errorCode);
        }
    }

    private boolean m(a errorCode) {
        if (!Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.k != null) {
                    return false;
                }
                if (this.g.x && this.h.f) {
                    return false;
                }
                this.k = errorCode;
                notifyAll();
                this.d.j1(this.c);
                return true;
            }
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void x(List<f> headers, g headersMode) {
        if (!Thread.holdsLock(this)) {
            a errorCode = null;
            boolean open = true;
            synchronized (this) {
                if (this.f == null) {
                    if (headersMode.failIfHeadersAbsent()) {
                        errorCode = a.PROTOCOL_ERROR;
                    } else {
                        this.f = headers;
                        open = t();
                        notifyAll();
                    }
                } else if (headersMode.failIfHeadersPresent()) {
                    errorCode = a.STREAM_IN_USE;
                } else {
                    List<Header> newHeaders = new ArrayList<>();
                    newHeaders.addAll(this.f);
                    newHeaders.addAll(headers);
                    this.f = newHeaders;
                }
            }
            if (errorCode != null) {
                n(errorCode);
            } else if (!open) {
                this.d.j1(this.c);
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: package-private */
    public void v(okio.e in, int length) {
        if (!Thread.holdsLock(this)) {
            this.g.j(in, (long) length);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        r3.d.j1(r3.c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
        if (r0 != false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w() {
        /*
            r3 = this;
            boolean r0 = java.lang.Thread.holdsLock(r3)
            if (r0 != 0) goto L_0x0025
            monitor-enter(r3)
            r0 = 0
            com.squareup.okhttp.internal.framed.e$c r1 = r3.g     // Catch:{ all -> 0x0020 }
            r2 = 1
            boolean unused = r1.x = r2     // Catch:{ all -> 0x0020 }
            boolean r0 = r3.t()     // Catch:{ all -> 0x0020 }
            r3.notifyAll()     // Catch:{ all -> 0x0023 }
            monitor-exit(r3)     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x001f
            com.squareup.okhttp.internal.framed.d r1 = r3.d
            int r2 = r3.c
            r1.j1(r2)
        L_0x001f:
            return
        L_0x0020:
            r1 = move-exception
        L_0x0021:
            monitor-exit(r3)     // Catch:{ all -> 0x0023 }
            throw r1
        L_0x0023:
            r1 = move-exception
            goto L_0x0021
        L_0x0025:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.w():void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void y(a errorCode) {
        if (this.k == null) {
            this.k = errorCode;
            notifyAll();
        }
    }

    /* compiled from: FramedStream */
    public final class c implements e0 {
        private final okio.c c;
        private final okio.c d;
        private final long f;
        /* access modifiers changed from: private */
        public boolean q;
        /* access modifiers changed from: private */
        public boolean x;

        public /* synthetic */ g cursor() {
            return d0.a(this);
        }

        private c(long maxByteCount) {
            this.c = new okio.c();
            this.d = new okio.c();
            this.f = maxByteCount;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0063, code lost:
            r10 = com.squareup.okhttp.internal.framed.e.d(r1.y);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0069, code lost:
            monitor-enter(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            com.squareup.okhttp.internal.framed.e.d(r1.y).z4 += r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x008e, code lost:
            if (com.squareup.okhttp.internal.framed.e.d(r1.y).z4 < ((long) (com.squareup.okhttp.internal.framed.e.d(r1.y).B4.e(65536) / 2))) goto L_0x00aa;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0090, code lost:
            com.squareup.okhttp.internal.framed.e.d(r1.y).s1(0, com.squareup.okhttp.internal.framed.e.d(r1.y).z4);
            com.squareup.okhttp.internal.framed.e.d(r1.y).z4 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00aa, code lost:
            monitor-exit(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ab, code lost:
            return r7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long read(okio.c r16, long r17) {
            /*
                r15 = this;
                r1 = r15
                r2 = r17
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 < 0) goto L_0x00bb
                com.squareup.okhttp.internal.framed.e r6 = com.squareup.okhttp.internal.framed.e.this
                monitor-enter(r6)
                r15.l()     // Catch:{ all -> 0x00b4 }
                r15.i()     // Catch:{ all -> 0x00b4 }
                okio.c r0 = r1.d     // Catch:{ all -> 0x00b4 }
                long r7 = r0.e1()     // Catch:{ all -> 0x00b4 }
                int r0 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
                if (r0 != 0) goto L_0x0020
                r7 = -1
                monitor-exit(r6)     // Catch:{ all -> 0x00b4 }
                return r7
            L_0x0020:
                okio.c r0 = r1.d     // Catch:{ all -> 0x00b4 }
                long r7 = r0.e1()     // Catch:{ all -> 0x00b4 }
                long r7 = java.lang.Math.min(r2, r7)     // Catch:{ all -> 0x00b4 }
                r9 = r16
                long r7 = r0.read(r9, r7)     // Catch:{ all -> 0x00b2 }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00af }
                long r10 = r0.a     // Catch:{ all -> 0x00af }
                long r10 = r10 + r7
                r0.a = r10     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.n r0 = r0.B4     // Catch:{ all -> 0x00af }
                r12 = 65536(0x10000, float:9.18355E-41)
                int r0 = r0.e(r12)     // Catch:{ all -> 0x00af }
                int r0 = r0 / 2
                long r13 = (long) r0     // Catch:{ all -> 0x00af }
                int r0 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
                if (r0 < 0) goto L_0x0062
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.e r10 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00af }
                int r10 = r10.c     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.e r11 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00af }
                long r13 = r11.a     // Catch:{ all -> 0x00af }
                r0.s1(r10, r13)     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00af }
                r0.a = r4     // Catch:{ all -> 0x00af }
            L_0x0062:
                monitor-exit(r6)     // Catch:{ all -> 0x00af }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.d r10 = r0.d
                monitor-enter(r10)
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00ac }
                long r13 = r0.z4     // Catch:{ all -> 0x00ac }
                long r13 = r13 + r7
                r0.z4 = r13     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00ac }
                long r13 = r0.z4     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.n r0 = r0.B4     // Catch:{ all -> 0x00ac }
                int r0 = r0.e(r12)     // Catch:{ all -> 0x00ac }
                int r0 = r0 / 2
                long r11 = (long) r0     // Catch:{ all -> 0x00ac }
                int r0 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
                if (r0 < 0) goto L_0x00aa
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00ac }
                r6 = 0
                com.squareup.okhttp.internal.framed.e r11 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.d r11 = r11.d     // Catch:{ all -> 0x00ac }
                long r11 = r11.z4     // Catch:{ all -> 0x00ac }
                r0.s1(r6, r11)     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x00ac }
                com.squareup.okhttp.internal.framed.d r0 = r0.d     // Catch:{ all -> 0x00ac }
                r0.z4 = r4     // Catch:{ all -> 0x00ac }
            L_0x00aa:
                monitor-exit(r10)     // Catch:{ all -> 0x00ac }
                return r7
            L_0x00ac:
                r0 = move-exception
                monitor-exit(r10)     // Catch:{ all -> 0x00ac }
                throw r0
            L_0x00af:
                r0 = move-exception
                r4 = r7
                goto L_0x00b7
            L_0x00b2:
                r0 = move-exception
                goto L_0x00b7
            L_0x00b4:
                r0 = move-exception
                r9 = r16
            L_0x00b7:
                monitor-exit(r6)     // Catch:{ all -> 0x00b9 }
                throw r0
            L_0x00b9:
                r0 = move-exception
                goto L_0x00b7
            L_0x00bb:
                r9 = r16
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "byteCount < 0: "
                r4.append(r5)
                r4.append(r2)
                java.lang.String r4 = r4.toString()
                r0.<init>(r4)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.c.read(okio.c, long):long");
        }

        private void l() {
            e.this.i.u();
            while (this.d.e1() == 0 && !this.x && !this.q && e.this.k == null) {
                try {
                    e.this.z();
                } finally {
                    e.this.i.B();
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0028, code lost:
            if (r1 == false) goto L_0x0035;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
            r12.skip(r13);
            r11.y.n(com.squareup.okhttp.internal.framed.a.FLOW_CONTROL_ERROR);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0035, code lost:
            if (r2 == false) goto L_0x003b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
            r12.skip(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
            r7 = r12.read(r11.c, r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0045, code lost:
            if (r7 == -1) goto L_0x006f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0047, code lost:
            r9 = r13 - r7;
            r5 = r11.y;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
            monitor-enter(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0054, code lost:
            if (r11.d.e1() != 0) goto L_0x0057;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0057, code lost:
            r6 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0058, code lost:
            r13 = r6;
            r11.d.writeAll(r11.c);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0060, code lost:
            if (r13 == false) goto L_0x0067;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
            r11.y.notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
            monitor-exit(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0068, code lost:
            r13 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x006a, code lost:
            r13 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
            monitor-exit(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x006c, code lost:
            throw r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x006d, code lost:
            r13 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x0074, code lost:
            throw new java.io.EOFException();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void j(okio.e r12, long r13) {
            /*
                r11 = this;
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                boolean r0 = java.lang.Thread.holdsLock(r0)
                if (r0 != 0) goto L_0x007d
                r0 = 0
                r1 = r0
                r2 = r1
            L_0x000b:
                r3 = 0
                int r5 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
                if (r5 <= 0) goto L_0x007c
                com.squareup.okhttp.internal.framed.e r5 = com.squareup.okhttp.internal.framed.e.this
                monitor-enter(r5)
                boolean r2 = r11.x     // Catch:{ all -> 0x0077 }
                okio.c r6 = r11.d     // Catch:{ all -> 0x0075 }
                long r6 = r6.e1()     // Catch:{ all -> 0x0075 }
                long r6 = r6 + r13
                long r8 = r11.f     // Catch:{ all -> 0x0075 }
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                r6 = 1
                if (r1 <= 0) goto L_0x0026
                r1 = r6
                goto L_0x0027
            L_0x0026:
                r1 = r0
            L_0x0027:
                monitor-exit(r5)     // Catch:{ all -> 0x007a }
                if (r1 == 0) goto L_0x0035
                r12.skip(r13)
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.a r3 = com.squareup.okhttp.internal.framed.a.FLOW_CONTROL_ERROR
                r0.n(r3)
                return
            L_0x0035:
                if (r2 == 0) goto L_0x003b
                r12.skip(r13)
                return
            L_0x003b:
                okio.c r5 = r11.c
                long r7 = r12.read(r5, r13)
                r9 = -1
                int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r5 == 0) goto L_0x006f
                long r9 = r13 - r7
                com.squareup.okhttp.internal.framed.e r5 = com.squareup.okhttp.internal.framed.e.this
                monitor-enter(r5)
                okio.c r13 = r11.d     // Catch:{ all -> 0x006a }
                long r13 = r13.e1()     // Catch:{ all -> 0x006a }
                int r13 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
                if (r13 != 0) goto L_0x0057
                goto L_0x0058
            L_0x0057:
                r6 = r0
            L_0x0058:
                r13 = r6
                okio.c r14 = r11.d     // Catch:{ all -> 0x006a }
                okio.c r3 = r11.c     // Catch:{ all -> 0x006a }
                r14.writeAll(r3)     // Catch:{ all -> 0x006a }
                if (r13 == 0) goto L_0x0067
                com.squareup.okhttp.internal.framed.e r14 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x006d }
                r14.notifyAll()     // Catch:{ all -> 0x006d }
            L_0x0067:
                monitor-exit(r5)     // Catch:{ all -> 0x006a }
                r13 = r9
                goto L_0x000b
            L_0x006a:
                r13 = move-exception
            L_0x006b:
                monitor-exit(r5)     // Catch:{ all -> 0x006d }
                throw r13
            L_0x006d:
                r13 = move-exception
                goto L_0x006b
            L_0x006f:
                java.io.EOFException r0 = new java.io.EOFException
                r0.<init>()
                throw r0
            L_0x0075:
                r0 = move-exception
                goto L_0x0078
            L_0x0077:
                r0 = move-exception
            L_0x0078:
                monitor-exit(r5)     // Catch:{ all -> 0x007a }
                throw r0
            L_0x007a:
                r0 = move-exception
                goto L_0x0078
            L_0x007c:
                return
            L_0x007d:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.c.j(okio.e, long):void");
        }

        public f0 timeout() {
            return e.this.i;
        }

        public void close() {
            synchronized (e.this) {
                this.q = true;
                this.d.clear();
                e.this.notifyAll();
            }
            e.this.j();
        }

        private void i() {
            if (this.q) {
                throw new IOException("stream closed");
            } else if (e.this.k != null) {
                throw new IOException("stream was reset: " + e.this.k);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0031, code lost:
        if (r2 == false) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0033, code lost:
        l(com.squareup.okhttp.internal.framed.a.CANCEL);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
        if (r0 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        r4.d.j1(r4.c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void j() {
        /*
            r4 = this;
            boolean r0 = java.lang.Thread.holdsLock(r4)
            if (r0 != 0) goto L_0x004b
            monitor-enter(r4)
            r0 = 0
            com.squareup.okhttp.internal.framed.e$c r1 = r4.g     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.x     // Catch:{ all -> 0x0045 }
            if (r1 != 0) goto L_0x002a
            com.squareup.okhttp.internal.framed.e$c r1 = r4.g     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.q     // Catch:{ all -> 0x0045 }
            if (r1 == 0) goto L_0x002a
            com.squareup.okhttp.internal.framed.e$b r1 = r4.h     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.f     // Catch:{ all -> 0x0045 }
            if (r1 != 0) goto L_0x0028
            com.squareup.okhttp.internal.framed.e$b r1 = r4.h     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.d     // Catch:{ all -> 0x0045 }
            if (r1 == 0) goto L_0x002a
        L_0x0028:
            r1 = 1
            goto L_0x002b
        L_0x002a:
            r1 = r0
        L_0x002b:
            r2 = r1
            boolean r0 = r4.t()     // Catch:{ all -> 0x0043 }
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0039
            com.squareup.okhttp.internal.framed.a r1 = com.squareup.okhttp.internal.framed.a.CANCEL
            r4.l(r1)
            goto L_0x0042
        L_0x0039:
            if (r0 != 0) goto L_0x0042
            com.squareup.okhttp.internal.framed.d r1 = r4.d
            int r3 = r4.c
            r1.j1(r3)
        L_0x0042:
            return
        L_0x0043:
            r1 = move-exception
            goto L_0x0047
        L_0x0045:
            r1 = move-exception
            r2 = r0
        L_0x0047:
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            throw r1
        L_0x0049:
            r1 = move-exception
            goto L_0x0047
        L_0x004b:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.j():void");
    }

    /* compiled from: FramedStream */
    public final class b implements b0 {
        private final okio.c c = new okio.c();
        /* access modifiers changed from: private */
        public boolean d;
        /* access modifiers changed from: private */
        public boolean f;

        b() {
        }

        public void write(okio.c source, long byteCount) {
            if (!Thread.holdsLock(e.this)) {
                this.c.write(source, byteCount);
                while (this.c.e1() >= 16384) {
                    i(false);
                }
                return;
            }
            throw new AssertionError();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            com.squareup.okhttp.internal.framed.e.b(r9.q).B();
            com.squareup.okhttp.internal.framed.e.c(r9.q);
            r1 = java.lang.Math.min(r9.q.b, r9.c.e1());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            r3 = r9.q;
            r3.b -= r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
            com.squareup.okhttp.internal.framed.e.b(r3).u();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            r3 = com.squareup.okhttp.internal.framed.e.d(r9.q);
            r4 = com.squareup.okhttp.internal.framed.e.e(r9.q);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0062, code lost:
            if (r10 == false) goto L_0x0070;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x006c, code lost:
            if (r1 != r9.c.e1()) goto L_0x0070;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x006e, code lost:
            r0 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0070, code lost:
            r0 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0071, code lost:
            r3.n1(r4, r0, r9.c, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0082, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0083, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0084, code lost:
            com.squareup.okhttp.internal.framed.e.b(r9.q).B();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x008d, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x009c, code lost:
            r3 = th;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void i(boolean r10) {
            /*
                r9 = this;
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                monitor-enter(r0)
                r1 = 0
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0099 }
                com.squareup.okhttp.internal.framed.e$d r3 = r3.j     // Catch:{ all -> 0x0099 }
                r3.u()     // Catch:{ all -> 0x0099 }
            L_0x000e:
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x008e }
                long r4 = r3.b     // Catch:{ all -> 0x008e }
                int r4 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
                if (r4 > 0) goto L_0x002a
                boolean r4 = r9.f     // Catch:{ all -> 0x008e }
                if (r4 != 0) goto L_0x002a
                boolean r4 = r9.d     // Catch:{ all -> 0x008e }
                if (r4 != 0) goto L_0x002a
                com.squareup.okhttp.internal.framed.a r3 = r3.k     // Catch:{ all -> 0x008e }
                if (r3 != 0) goto L_0x002a
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x008e }
                r3.z()     // Catch:{ all -> 0x008e }
                goto L_0x000e
            L_0x002a:
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0099 }
                com.squareup.okhttp.internal.framed.e$d r3 = r3.j     // Catch:{ all -> 0x0099 }
                r3.B()     // Catch:{ all -> 0x0099 }
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0099 }
                r3.k()     // Catch:{ all -> 0x0099 }
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0099 }
                long r3 = r3.b     // Catch:{ all -> 0x0099 }
                okio.c r5 = r9.c     // Catch:{ all -> 0x0099 }
                long r5 = r5.e1()     // Catch:{ all -> 0x0099 }
                long r1 = java.lang.Math.min(r3, r5)     // Catch:{ all -> 0x0099 }
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x009c }
                long r4 = r3.b     // Catch:{ all -> 0x009c }
                long r4 = r4 - r1
                r3.b = r4     // Catch:{ all -> 0x009c }
                monitor-exit(r0)     // Catch:{ all -> 0x009c }
                com.squareup.okhttp.internal.framed.e$d r0 = r3.j
                r0.u()
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0083 }
                com.squareup.okhttp.internal.framed.d r3 = r0.d     // Catch:{ all -> 0x0083 }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0083 }
                int r4 = r0.c     // Catch:{ all -> 0x0083 }
                if (r10 == 0) goto L_0x0070
                okio.c r0 = r9.c     // Catch:{ all -> 0x0083 }
                long r5 = r0.e1()     // Catch:{ all -> 0x0083 }
                int r0 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
                if (r0 != 0) goto L_0x0070
                r0 = 1
                goto L_0x0071
            L_0x0070:
                r0 = 0
            L_0x0071:
                r5 = r0
                okio.c r6 = r9.c     // Catch:{ all -> 0x0083 }
                r7 = r1
                r3.n1(r4, r5, r6, r7)     // Catch:{ all -> 0x0083 }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.e$d r0 = r0.j
                r0.B()
                return
            L_0x0083:
                r0 = move-exception
                com.squareup.okhttp.internal.framed.e r3 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.e$d r3 = r3.j
                r3.B()
                throw r0
            L_0x008e:
                r3 = move-exception
                com.squareup.okhttp.internal.framed.e r4 = com.squareup.okhttp.internal.framed.e.this     // Catch:{ all -> 0x0099 }
                com.squareup.okhttp.internal.framed.e$d r4 = r4.j     // Catch:{ all -> 0x0099 }
                r4.B()     // Catch:{ all -> 0x0099 }
                throw r3     // Catch:{ all -> 0x0099 }
            L_0x0099:
                r3 = move-exception
            L_0x009a:
                monitor-exit(r0)     // Catch:{ all -> 0x009c }
                throw r3
            L_0x009c:
                r3 = move-exception
                goto L_0x009a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.b.i(boolean):void");
        }

        public void flush() {
            if (!Thread.holdsLock(e.this)) {
                synchronized (e.this) {
                    e.this.k();
                }
                while (this.c.e1() > 0) {
                    i(false);
                    e.this.d.flush();
                }
                return;
            }
            throw new AssertionError();
        }

        public f0 timeout() {
            return e.this.j;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
            if (r8.q.h.f != false) goto L_0x0048;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
            if (r8.c.e1() <= 0) goto L_0x0035;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x002f, code lost:
            if (r8.c.e1() <= 0) goto L_0x0048;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
            i(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0035, code lost:
            com.squareup.okhttp.internal.framed.e.d(r8.q).n1(com.squareup.okhttp.internal.framed.e.e(r8.q), true, (okio.c) null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0048, code lost:
            r2 = r8.q;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x004a, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r8.d = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x004d, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
            com.squareup.okhttp.internal.framed.e.d(r8.q).flush();
            com.squareup.okhttp.internal.framed.e.a(r8.q);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
                r8 = this;
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                boolean r0 = java.lang.Thread.holdsLock(r0)
                if (r0 != 0) goto L_0x0063
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                monitor-enter(r0)
                boolean r1 = r8.d     // Catch:{ all -> 0x0060 }
                if (r1 == 0) goto L_0x0011
                monitor-exit(r0)     // Catch:{ all -> 0x0060 }
                return
            L_0x0011:
                monitor-exit(r0)     // Catch:{ all -> 0x0060 }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.e$b r0 = r0.h
                boolean r0 = r0.f
                r1 = 1
                if (r0 != 0) goto L_0x0048
                okio.c r0 = r8.c
                long r2 = r0.e1()
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x0035
            L_0x0027:
                okio.c r0 = r8.c
                long r2 = r0.e1()
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x0048
                r8.i(r1)
                goto L_0x0027
            L_0x0035:
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.d r2 = r0.d
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                int r3 = r0.c
                r4 = 1
                r5 = 0
                r6 = 0
                r2.n1(r3, r4, r5, r6)
            L_0x0048:
                com.squareup.okhttp.internal.framed.e r2 = com.squareup.okhttp.internal.framed.e.this
                monitor-enter(r2)
                r8.d = r1     // Catch:{ all -> 0x005d }
                monitor-exit(r2)     // Catch:{ all -> 0x005d }
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                com.squareup.okhttp.internal.framed.d r0 = r0.d
                r0.flush()
                com.squareup.okhttp.internal.framed.e r0 = com.squareup.okhttp.internal.framed.e.this
                r0.j()
                return
            L_0x005d:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x005d }
                throw r0
            L_0x0060:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0060 }
                throw r1
            L_0x0063:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.e.b.close():void");
        }
    }

    /* access modifiers changed from: package-private */
    public void i(long delta) {
        this.b += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        if (this.h.d) {
            throw new IOException("stream closed");
        } else if (this.h.f) {
            throw new IOException("stream finished");
        } else if (this.k != null) {
            throw new IOException("stream was reset: " + this.k);
        }
    }

    /* access modifiers changed from: private */
    public void z() {
        try {
            wait();
        } catch (InterruptedException e2) {
            throw new InterruptedIOException();
        }
    }

    /* compiled from: FramedStream */
    public class d extends okio.a {
        d() {
        }

        /* access modifiers changed from: protected */
        public void A() {
            e.this.n(a.CANCEL);
        }

        /* access modifiers changed from: protected */
        public IOException w(IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }

        public void B() {
            if (v()) {
                throw w((IOException) null);
            }
        }
    }
}
