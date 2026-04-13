package okhttp3.internal.cache;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.text.j;
import kotlin.text.w;
import kotlin.x;
import okhttp3.internal.platform.h;
import okio.b0;
import okio.e0;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DiskLruCache.kt */
public final class d implements Closeable, Flushable {
    @NotNull
    public static final String a1 = a1;
    @NotNull
    public static final String a2 = a2;
    @NotNull
    public static final String c = c;
    @NotNull
    public static final String d = d;
    @NotNull
    public static final String f = f;
    @NotNull
    public static final String p0 = p0;
    @NotNull
    public static final String p1 = p1;
    public static final a p2 = new a((DefaultConstructorMarker) null);
    @NotNull
    public static final String q = q;
    @NotNull
    public static final String x = "1";
    public static final long y = -1;
    @NotNull
    public static final j z = new j("[a-z0-9_-]{1,120}");
    private final File A4;
    private long B4;
    /* access modifiers changed from: private */
    public okio.d C4;
    @NotNull
    private final LinkedHashMap<String, c> D4 = new LinkedHashMap<>(0, 0.75f, true);
    /* access modifiers changed from: private */
    public int E4;
    /* access modifiers changed from: private */
    public boolean F4;
    /* access modifiers changed from: private */
    public boolean G4;
    /* access modifiers changed from: private */
    public boolean H4;
    private boolean I4;
    /* access modifiers changed from: private */
    public boolean J4;
    /* access modifiers changed from: private */
    public boolean K4;
    private long L4;
    private final okhttp3.internal.concurrent.d M4;
    private final e N4;
    @NotNull
    private final okhttp3.internal.io.b O4;
    @NotNull
    private final File P4;
    private final int Q4;
    private final int R4;
    private long p3;
    private final File p4;
    private final File z4;

    /* compiled from: DiskLruCache.kt */
    public static final class f extends l implements kotlin.jvm.functions.l<IOException, x> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(d dVar) {
            super(1);
            this.this$0 = dVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((IOException) obj);
            return x.a;
        }

        public final void invoke(@NotNull IOException it) {
            k.f(it, "it");
            Object $this$assertThreadHoldsLock$iv = this.this$0;
            if (!okhttp3.internal.b.h || Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
                this.this$0.F4 = true;
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST hold lock on ");
            sb.append($this$assertThreadHoldsLock$iv);
            throw new AssertionError(sb.toString());
        }
    }

    public d(@NotNull okhttp3.internal.io.b fileSystem, @NotNull File directory, int appVersion, int valueCount, long maxSize, @NotNull okhttp3.internal.concurrent.e taskRunner) {
        k.f(fileSystem, "fileSystem");
        k.f(directory, "directory");
        k.f(taskRunner, "taskRunner");
        this.O4 = fileSystem;
        this.P4 = directory;
        this.Q4 = appVersion;
        this.R4 = valueCount;
        this.p3 = maxSize;
        boolean z2 = false;
        this.M4 = taskRunner.i();
        this.N4 = new e(this, okhttp3.internal.b.i + " Cache");
        if (maxSize > 0) {
            if (valueCount > 0 ? true : z2) {
                this.p4 = new File(directory, c);
                this.z4 = new File(directory, d);
                this.A4 = new File(directory, f);
                return;
            }
            throw new IllegalArgumentException("valueCount <= 0".toString());
        }
        throw new IllegalArgumentException("maxSize <= 0".toString());
    }

    @NotNull
    public final okhttp3.internal.io.b E() {
        return this.O4;
    }

    @NotNull
    public final File z() {
        return this.P4;
    }

    public final int I() {
        return this.R4;
    }

    public final boolean x() {
        return this.I4;
    }

    /* compiled from: DiskLruCache.kt */
    public static final class e extends okhttp3.internal.concurrent.a {
        final /* synthetic */ d e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(d $outer, String $super_call_param$1) {
            super($super_call_param$1, false, 2, (DefaultConstructorMarker) null);
            this.e = $outer;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0054, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long f() {
            /*
                r7 = this;
                okhttp3.internal.cache.d r0 = r7.e
                monitor-enter(r0)
                r1 = 0
                okhttp3.internal.cache.d r2 = r7.e     // Catch:{ all -> 0x0055 }
                boolean r2 = r2.H4     // Catch:{ all -> 0x0055 }
                r3 = -1
                if (r2 == 0) goto L_0x0052
                okhttp3.internal.cache.d r2 = r7.e     // Catch:{ all -> 0x0055 }
                boolean r2 = r2.x()     // Catch:{ all -> 0x0055 }
                if (r2 == 0) goto L_0x0017
                goto L_0x0052
            L_0x0017:
                r2 = 1
                okhttp3.internal.cache.d r5 = r7.e     // Catch:{ IOException -> 0x001f }
                r5.c1()     // Catch:{ IOException -> 0x001f }
                goto L_0x0025
            L_0x001f:
                r5 = move-exception
                okhttp3.internal.cache.d r6 = r7.e     // Catch:{ all -> 0x0055 }
                r6.J4 = r2     // Catch:{ all -> 0x0055 }
            L_0x0025:
                okhttp3.internal.cache.d r5 = r7.e     // Catch:{ IOException -> 0x003b }
                boolean r5 = r5.P()     // Catch:{ IOException -> 0x003b }
                if (r5 == 0) goto L_0x004e
                okhttp3.internal.cache.d r5 = r7.e     // Catch:{ IOException -> 0x003b }
                r5.F0()     // Catch:{ IOException -> 0x003b }
                okhttp3.internal.cache.d r5 = r7.e     // Catch:{ IOException -> 0x003b }
                r6 = 0
                r5.E4 = r6     // Catch:{ IOException -> 0x003b }
                goto L_0x004e
            L_0x003b:
                r5 = move-exception
                okhttp3.internal.cache.d r6 = r7.e     // Catch:{ all -> 0x0055 }
                r6.K4 = r2     // Catch:{ all -> 0x0055 }
                okhttp3.internal.cache.d r2 = r7.e     // Catch:{ all -> 0x0055 }
                okio.b0 r6 = okio.p.b()     // Catch:{ all -> 0x0055 }
                okio.d r6 = okio.p.c(r6)     // Catch:{ all -> 0x0055 }
                r2.C4 = r6     // Catch:{ all -> 0x0055 }
            L_0x004e:
                monitor-exit(r0)
                return r3
            L_0x0052:
                monitor-exit(r0)
                return r3
            L_0x0055:
                r1 = move-exception
                monitor-exit(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.e.f():long");
        }
    }

    public final synchronized void J() {
        if (okhttp3.internal.b.h) {
            if (!Thread.holdsLock(this)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Thread ");
                Thread currentThread = Thread.currentThread();
                k.b(currentThread, "Thread.currentThread()");
                sb.append(currentThread.getName());
                sb.append(" MUST hold lock on ");
                sb.append(this);
                throw new AssertionError(sb.toString());
            }
        }
        if (!this.H4) {
            if (this.O4.b(this.A4)) {
                if (this.O4.b(this.p4)) {
                    this.O4.h(this.A4);
                } else {
                    this.O4.g(this.A4, this.p4);
                }
            }
            this.G4 = okhttp3.internal.b.C(this.O4, this.A4);
            if (this.O4.b(this.p4)) {
                try {
                    o0();
                    W();
                    this.H4 = true;
                    return;
                } catch (IOException journalIsCorrupt) {
                    h g = h.c.g();
                    g.k("DiskLruCache " + this.P4 + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing", 5, journalIsCorrupt);
                    s();
                    this.I4 = false;
                } catch (Throwable th) {
                    this.I4 = false;
                    throw th;
                }
            }
            F0();
            this.H4 = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c2, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c3, code lost:
        kotlin.io.b.a(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c6, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void o0() {
        /*
            r12 = this;
            java.lang.String r0 = ", "
            okhttp3.internal.io.b r1 = r12.O4
            java.io.File r2 = r12.p4
            okio.e0 r1 = r1.e(r2)
            okio.e r1 = okio.p.d(r1)
            r2 = r1
            r3 = 0
            java.lang.String r4 = r2.d0()     // Catch:{ all -> 0x00c0 }
            java.lang.String r5 = r2.d0()     // Catch:{ all -> 0x00c0 }
            java.lang.String r6 = r2.d0()     // Catch:{ all -> 0x00c0 }
            java.lang.String r7 = r2.d0()     // Catch:{ all -> 0x00c0 }
            java.lang.String r8 = r2.d0()     // Catch:{ all -> 0x00c0 }
            java.lang.String r9 = q     // Catch:{ all -> 0x00c0 }
            boolean r9 = kotlin.jvm.internal.k.a(r9, r4)     // Catch:{ all -> 0x00c0 }
            r10 = 1
            r9 = r9 ^ r10
            if (r9 != 0) goto L_0x0091
            java.lang.String r9 = x     // Catch:{ all -> 0x00c0 }
            boolean r9 = kotlin.jvm.internal.k.a(r9, r5)     // Catch:{ all -> 0x00c0 }
            r9 = r9 ^ r10
            if (r9 != 0) goto L_0x0091
            int r9 = r12.Q4     // Catch:{ all -> 0x00c0 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x00c0 }
            boolean r9 = kotlin.jvm.internal.k.a(r9, r6)     // Catch:{ all -> 0x00c0 }
            r9 = r9 ^ r10
            if (r9 != 0) goto L_0x0091
            int r9 = r12.R4     // Catch:{ all -> 0x00c0 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x00c0 }
            boolean r9 = kotlin.jvm.internal.k.a(r9, r7)     // Catch:{ all -> 0x00c0 }
            r9 = r9 ^ r10
            if (r9 != 0) goto L_0x0091
            int r9 = r8.length()     // Catch:{ all -> 0x00c0 }
            if (r9 <= 0) goto L_0x005c
            goto L_0x005d
        L_0x005c:
            r10 = 0
        L_0x005d:
            if (r10 != 0) goto L_0x0091
            r0 = 0
        L_0x0060:
            java.lang.String r9 = r2.d0()     // Catch:{ EOFException -> 0x006c }
            r12.u0(r9)     // Catch:{ EOFException -> 0x006c }
            int r0 = r0 + 1
            goto L_0x0060
        L_0x006c:
            r9 = move-exception
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r9 = r12.D4     // Catch:{ all -> 0x00c0 }
            int r9 = r9.size()     // Catch:{ all -> 0x00c0 }
            int r9 = r0 - r9
            r12.E4 = r9     // Catch:{ all -> 0x00c0 }
            boolean r9 = r2.r0()     // Catch:{ all -> 0x00c0 }
            if (r9 != 0) goto L_0x0082
            r12.F0()     // Catch:{ all -> 0x00c0 }
            goto L_0x0088
        L_0x0082:
            okio.d r9 = r12.T()     // Catch:{ all -> 0x00c0 }
            r12.C4 = r9     // Catch:{ all -> 0x00c0 }
        L_0x0088:
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x00c0 }
            r0 = 0
            kotlin.io.b.a(r1, r0)
            return
        L_0x0091:
            java.io.IOException r9 = new java.io.IOException     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c0 }
            r10.<init>()     // Catch:{ all -> 0x00c0 }
            java.lang.String r11 = "unexpected journal header: ["
            r10.append(r11)     // Catch:{ all -> 0x00c0 }
            r10.append(r4)     // Catch:{ all -> 0x00c0 }
            r10.append(r0)     // Catch:{ all -> 0x00c0 }
            r10.append(r5)     // Catch:{ all -> 0x00c0 }
            r10.append(r0)     // Catch:{ all -> 0x00c0 }
            r10.append(r7)     // Catch:{ all -> 0x00c0 }
            r10.append(r0)     // Catch:{ all -> 0x00c0 }
            r10.append(r8)     // Catch:{ all -> 0x00c0 }
            r0 = 93
            r10.append(r0)     // Catch:{ all -> 0x00c0 }
            java.lang.String r0 = r10.toString()     // Catch:{ all -> 0x00c0 }
            r9.<init>(r0)     // Catch:{ all -> 0x00c0 }
            throw r9     // Catch:{ all -> 0x00c0 }
        L_0x00c0:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x00c2 }
        L_0x00c2:
            r2 = move-exception
            kotlin.io.b.a(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.o0():void");
    }

    private final okio.d T() {
        return p.c(new e(this.O4.c(this.p4), new f(this)));
    }

    private final void u0(String line) {
        String key;
        String str = line;
        int firstSpace = kotlin.text.x.e0(line, ' ', 0, false, 6, (Object) null);
        if (firstSpace != -1) {
            int keyBegin = firstSpace + 1;
            int secondSpace = kotlin.text.x.e0(line, ' ', keyBegin, false, 4, (Object) null);
            if (secondSpace == -1) {
                if (str != null) {
                    String substring = str.substring(keyBegin);
                    k.b(substring, "(this as java.lang.String).substring(startIndex)");
                    key = substring;
                    String str2 = p1;
                    if (firstSpace == str2.length() && w.N(str, str2, false, 2, (Object) null)) {
                        this.D4.remove(key);
                        return;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            } else if (str != null) {
                String substring2 = str.substring(keyBegin, secondSpace);
                k.b(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                key = substring2;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            c entry = this.D4.get(key);
            if (entry == null) {
                entry = new c(this, key);
                this.D4.put(key, entry);
            }
            if (secondSpace != -1) {
                String str3 = p0;
                if (firstSpace == str3.length() && w.N(str, str3, false, 2, (Object) null)) {
                    int i = secondSpace + 1;
                    if (str != null) {
                        String substring3 = str.substring(i);
                        k.b(substring3, "(this as java.lang.String).substring(startIndex)");
                        List parts = kotlin.text.x.E0(substring3, new char[]{' '}, false, 0, 6, (Object) null);
                        entry.o(true);
                        entry.l((b) null);
                        entry.m(parts);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            }
            if (secondSpace == -1) {
                String str4 = a1;
                if (firstSpace == str4.length() && w.N(str, str4, false, 2, (Object) null)) {
                    entry.l(new b(this, entry));
                    return;
                }
            }
            if (secondSpace == -1) {
                String str5 = a2;
                if (firstSpace == str5.length() && w.N(str, str5, false, 2, (Object) null)) {
                    return;
                }
            }
            throw new IOException("unexpected journal line: " + str);
        }
        throw new IOException("unexpected journal line: " + str);
    }

    private final void W() {
        this.O4.h(this.z4);
        Iterator i = this.D4.values().iterator();
        while (i.hasNext()) {
            c next = i.next();
            k.b(next, "i.next()");
            c entry = next;
            int t = 0;
            if (entry.b() == null) {
                int i2 = this.R4;
                while (t < i2) {
                    this.B4 += entry.e()[t];
                    t++;
                }
            } else {
                entry.l((b) null);
                int i3 = this.R4;
                while (t < i3) {
                    this.O4.h(entry.a().get(t));
                    this.O4.h(entry.c().get(t));
                    t++;
                }
                i.remove();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c2, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        kotlin.io.b.a(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c6, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void F0() {
        /*
            r9 = this;
            monitor-enter(r9)
            okio.d r0 = r9.C4     // Catch:{ all -> 0x00c7 }
            if (r0 == 0) goto L_0x0008
            r0.close()     // Catch:{ all -> 0x00c7 }
        L_0x0008:
            okhttp3.internal.io.b r0 = r9.O4     // Catch:{ all -> 0x00c7 }
            java.io.File r1 = r9.z4     // Catch:{ all -> 0x00c7 }
            okio.b0 r0 = r0.f(r1)     // Catch:{ all -> 0x00c7 }
            okio.d r0 = okio.p.c(r0)     // Catch:{ all -> 0x00c7 }
            r1 = 0
            r2 = r0
            r3 = 0
            java.lang.String r4 = q     // Catch:{ all -> 0x00c0 }
            okio.d r4 = r2.writeUtf8(r4)     // Catch:{ all -> 0x00c0 }
            r5 = 10
            r4.writeByte(r5)     // Catch:{ all -> 0x00c0 }
            java.lang.String r4 = x     // Catch:{ all -> 0x00c0 }
            okio.d r4 = r2.writeUtf8(r4)     // Catch:{ all -> 0x00c0 }
            r4.writeByte(r5)     // Catch:{ all -> 0x00c0 }
            int r4 = r9.Q4     // Catch:{ all -> 0x00c0 }
            long r6 = (long) r4     // Catch:{ all -> 0x00c0 }
            okio.d r4 = r2.writeDecimalLong(r6)     // Catch:{ all -> 0x00c0 }
            r4.writeByte(r5)     // Catch:{ all -> 0x00c0 }
            int r4 = r9.R4     // Catch:{ all -> 0x00c0 }
            long r6 = (long) r4     // Catch:{ all -> 0x00c0 }
            okio.d r4 = r2.writeDecimalLong(r6)     // Catch:{ all -> 0x00c0 }
            r4.writeByte(r5)     // Catch:{ all -> 0x00c0 }
            r2.writeByte(r5)     // Catch:{ all -> 0x00c0 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r4 = r9.D4     // Catch:{ all -> 0x00c0 }
            java.util.Collection r4 = r4.values()     // Catch:{ all -> 0x00c0 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x00c0 }
        L_0x004c:
            boolean r6 = r4.hasNext()     // Catch:{ all -> 0x00c0 }
            if (r6 == 0) goto L_0x008b
            java.lang.Object r6 = r4.next()     // Catch:{ all -> 0x00c0 }
            okhttp3.internal.cache.d$c r6 = (okhttp3.internal.cache.d.c) r6     // Catch:{ all -> 0x00c0 }
            okhttp3.internal.cache.d$b r7 = r6.b()     // Catch:{ all -> 0x00c0 }
            r8 = 32
            if (r7 == 0) goto L_0x0074
            java.lang.String r7 = a1     // Catch:{ all -> 0x00c0 }
            okio.d r7 = r2.writeUtf8(r7)     // Catch:{ all -> 0x00c0 }
            r7.writeByte(r8)     // Catch:{ all -> 0x00c0 }
            java.lang.String r7 = r6.d()     // Catch:{ all -> 0x00c0 }
            r2.writeUtf8(r7)     // Catch:{ all -> 0x00c0 }
            r2.writeByte(r5)     // Catch:{ all -> 0x00c0 }
            goto L_0x008a
        L_0x0074:
            java.lang.String r7 = p0     // Catch:{ all -> 0x00c0 }
            okio.d r7 = r2.writeUtf8(r7)     // Catch:{ all -> 0x00c0 }
            r7.writeByte(r8)     // Catch:{ all -> 0x00c0 }
            java.lang.String r7 = r6.d()     // Catch:{ all -> 0x00c0 }
            r2.writeUtf8(r7)     // Catch:{ all -> 0x00c0 }
            r6.s(r2)     // Catch:{ all -> 0x00c0 }
            r2.writeByte(r5)     // Catch:{ all -> 0x00c0 }
        L_0x008a:
            goto L_0x004c
        L_0x008b:
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x00c0 }
            kotlin.io.b.a(r0, r1)     // Catch:{ all -> 0x00c7 }
            okhttp3.internal.io.b r0 = r9.O4     // Catch:{ all -> 0x00c7 }
            java.io.File r1 = r9.p4     // Catch:{ all -> 0x00c7 }
            boolean r0 = r0.b(r1)     // Catch:{ all -> 0x00c7 }
            if (r0 == 0) goto L_0x00a3
            okhttp3.internal.io.b r0 = r9.O4     // Catch:{ all -> 0x00c7 }
            java.io.File r1 = r9.p4     // Catch:{ all -> 0x00c7 }
            java.io.File r2 = r9.A4     // Catch:{ all -> 0x00c7 }
            r0.g(r1, r2)     // Catch:{ all -> 0x00c7 }
        L_0x00a3:
            okhttp3.internal.io.b r0 = r9.O4     // Catch:{ all -> 0x00c7 }
            java.io.File r1 = r9.z4     // Catch:{ all -> 0x00c7 }
            java.io.File r2 = r9.p4     // Catch:{ all -> 0x00c7 }
            r0.g(r1, r2)     // Catch:{ all -> 0x00c7 }
            okhttp3.internal.io.b r0 = r9.O4     // Catch:{ all -> 0x00c7 }
            java.io.File r1 = r9.A4     // Catch:{ all -> 0x00c7 }
            r0.h(r1)     // Catch:{ all -> 0x00c7 }
            okio.d r0 = r9.T()     // Catch:{ all -> 0x00c7 }
            r9.C4 = r0     // Catch:{ all -> 0x00c7 }
            r0 = 0
            r9.F4 = r0     // Catch:{ all -> 0x00c7 }
            r9.K4 = r0     // Catch:{ all -> 0x00c7 }
            monitor-exit(r9)
            return
        L_0x00c0:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x00c2 }
        L_0x00c2:
            r2 = move-exception
            kotlin.io.b.a(r0, r1)     // Catch:{ all -> 0x00c7 }
            throw r2     // Catch:{ all -> 0x00c7 }
        L_0x00c7:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.F0():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0060, code lost:
        return r1;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized okhttp3.internal.cache.d.C0461d v(@org.jetbrains.annotations.NotNull java.lang.String r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            java.lang.String r0 = "key"
            kotlin.jvm.internal.k.f(r10, r0)     // Catch:{ all -> 0x0065 }
            r9.J()     // Catch:{ all -> 0x0065 }
            r9.o()     // Catch:{ all -> 0x0065 }
            r9.d1(r10)     // Catch:{ all -> 0x0065 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r0 = r9.D4     // Catch:{ all -> 0x0065 }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x0065 }
            okhttp3.internal.cache.d$c r0 = (okhttp3.internal.cache.d.c) r0     // Catch:{ all -> 0x0065 }
            r1 = 0
            if (r0 == 0) goto L_0x0063
            java.lang.String r2 = "lruEntries[key] ?: return null"
            kotlin.jvm.internal.k.b(r0, r2)     // Catch:{ all -> 0x0065 }
            okhttp3.internal.cache.d$d r2 = r0.r()     // Catch:{ all -> 0x0065 }
            if (r2 == 0) goto L_0x0061
            r1 = r2
            int r2 = r9.E4     // Catch:{ all -> 0x0065 }
            int r2 = r2 + 1
            r9.E4 = r2     // Catch:{ all -> 0x0065 }
            okio.d r2 = r9.C4     // Catch:{ all -> 0x0065 }
            if (r2 != 0) goto L_0x0039
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0065 }
        L_0x0039:
            java.lang.String r3 = a2     // Catch:{ all -> 0x0065 }
            okio.d r2 = r2.writeUtf8(r3)     // Catch:{ all -> 0x0065 }
            r3 = 32
            okio.d r2 = r2.writeByte(r3)     // Catch:{ all -> 0x0065 }
            okio.d r2 = r2.writeUtf8(r10)     // Catch:{ all -> 0x0065 }
            r3 = 10
            r2.writeByte(r3)     // Catch:{ all -> 0x0065 }
            boolean r2 = r9.P()     // Catch:{ all -> 0x0065 }
            if (r2 == 0) goto L_0x005f
            okhttp3.internal.concurrent.d r3 = r9.M4     // Catch:{ all -> 0x0065 }
            okhttp3.internal.cache.d$e r4 = r9.N4     // Catch:{ all -> 0x0065 }
            r5 = 0
            r7 = 2
            r8 = 0
            okhttp3.internal.concurrent.d.j(r3, r4, r5, r7, r8)     // Catch:{ all -> 0x0065 }
        L_0x005f:
            monitor-exit(r9)
            return r1
        L_0x0061:
            monitor-exit(r9)
            return r1
        L_0x0063:
            monitor-exit(r9)
            return r1
        L_0x0065:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.v(java.lang.String):okhttp3.internal.cache.d$d");
    }

    public static /* synthetic */ b u(d dVar, String str, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = y;
        }
        return dVar.t(str, j);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        return null;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized okhttp3.internal.cache.d.b t(@org.jetbrains.annotations.NotNull java.lang.String r10, long r11) {
        /*
            r9 = this;
            monitor-enter(r9)
            java.lang.String r0 = "key"
            kotlin.jvm.internal.k.f(r10, r0)     // Catch:{ all -> 0x0099 }
            r9.J()     // Catch:{ all -> 0x0099 }
            r9.o()     // Catch:{ all -> 0x0099 }
            r9.d1(r10)     // Catch:{ all -> 0x0099 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r0 = r9.D4     // Catch:{ all -> 0x0099 }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x0099 }
            okhttp3.internal.cache.d$c r0 = (okhttp3.internal.cache.d.c) r0     // Catch:{ all -> 0x0099 }
            long r1 = y     // Catch:{ all -> 0x0099 }
            int r1 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            r2 = 0
            if (r1 == 0) goto L_0x002b
            if (r0 == 0) goto L_0x0029
            long r3 = r0.h()     // Catch:{ all -> 0x0099 }
            int r1 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r1 == 0) goto L_0x002b
        L_0x0029:
            monitor-exit(r9)
            return r2
        L_0x002b:
            if (r0 == 0) goto L_0x0032
            okhttp3.internal.cache.d$b r1 = r0.b()     // Catch:{ all -> 0x0099 }
            goto L_0x0033
        L_0x0032:
            r1 = r2
        L_0x0033:
            if (r1 == 0) goto L_0x0037
            monitor-exit(r9)
            return r2
        L_0x0037:
            if (r0 == 0) goto L_0x0041
            int r1 = r0.f()     // Catch:{ all -> 0x0099 }
            if (r1 == 0) goto L_0x0041
            monitor-exit(r9)
            return r2
        L_0x0041:
            boolean r1 = r9.J4     // Catch:{ all -> 0x0099 }
            if (r1 != 0) goto L_0x008c
            boolean r1 = r9.K4     // Catch:{ all -> 0x0099 }
            if (r1 == 0) goto L_0x004a
            goto L_0x008c
        L_0x004a:
            okio.d r1 = r9.C4     // Catch:{ all -> 0x0099 }
            if (r1 != 0) goto L_0x0051
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0099 }
        L_0x0051:
            java.lang.String r3 = a1     // Catch:{ all -> 0x0099 }
            okio.d r3 = r1.writeUtf8(r3)     // Catch:{ all -> 0x0099 }
            r4 = 32
            okio.d r3 = r3.writeByte(r4)     // Catch:{ all -> 0x0099 }
            okio.d r3 = r3.writeUtf8(r10)     // Catch:{ all -> 0x0099 }
            r4 = 10
            r3.writeByte(r4)     // Catch:{ all -> 0x0099 }
            r1.flush()     // Catch:{ all -> 0x0099 }
            boolean r3 = r9.F4     // Catch:{ all -> 0x0099 }
            if (r3 == 0) goto L_0x0075
            monitor-exit(r9)
            return r2
        L_0x0075:
            if (r0 != 0) goto L_0x0082
            okhttp3.internal.cache.d$c r2 = new okhttp3.internal.cache.d$c     // Catch:{ all -> 0x0099 }
            r2.<init>(r9, r10)     // Catch:{ all -> 0x0099 }
            r0 = r2
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r2 = r9.D4     // Catch:{ all -> 0x0099 }
            r2.put(r10, r0)     // Catch:{ all -> 0x0099 }
        L_0x0082:
            okhttp3.internal.cache.d$b r2 = new okhttp3.internal.cache.d$b     // Catch:{ all -> 0x0099 }
            r2.<init>(r9, r0)     // Catch:{ all -> 0x0099 }
            r0.l(r2)     // Catch:{ all -> 0x0099 }
            monitor-exit(r9)
            return r2
        L_0x008c:
            okhttp3.internal.concurrent.d r3 = r9.M4     // Catch:{ all -> 0x0099 }
            okhttp3.internal.cache.d$e r4 = r9.N4     // Catch:{ all -> 0x0099 }
            r5 = 0
            r7 = 2
            r8 = 0
            okhttp3.internal.concurrent.d.j(r3, r4, r5, r7, r8)     // Catch:{ all -> 0x0099 }
            monitor-exit(r9)
            return r2
        L_0x0099:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.t(java.lang.String, long):okhttp3.internal.cache.d$b");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0141, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void r(@org.jetbrains.annotations.NotNull okhttp3.internal.cache.d.b r12, boolean r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.lang.String r0 = "editor"
            kotlin.jvm.internal.k.f(r12, r0)     // Catch:{ all -> 0x014e }
            okhttp3.internal.cache.d$c r0 = r12.d()     // Catch:{ all -> 0x014e }
            okhttp3.internal.cache.d$b r1 = r0.b()     // Catch:{ all -> 0x014e }
            boolean r1 = kotlin.jvm.internal.k.a(r1, r12)     // Catch:{ all -> 0x014e }
            if (r1 == 0) goto L_0x0142
            r1 = 0
            if (r13 == 0) goto L_0x0064
            boolean r2 = r0.g()     // Catch:{ all -> 0x014e }
            if (r2 != 0) goto L_0x0064
            int r2 = r11.R4     // Catch:{ all -> 0x014e }
            r3 = r1
        L_0x0020:
            if (r3 >= r2) goto L_0x0064
            boolean[] r4 = r12.e()     // Catch:{ all -> 0x014e }
            if (r4 != 0) goto L_0x002b
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x014e }
        L_0x002b:
            boolean r4 = r4[r3]     // Catch:{ all -> 0x014e }
            if (r4 == 0) goto L_0x004a
            okhttp3.internal.io.b r4 = r11.O4     // Catch:{ all -> 0x014e }
            java.util.List r5 = r0.c()     // Catch:{ all -> 0x014e }
            java.lang.Object r5 = r5.get(r3)     // Catch:{ all -> 0x014e }
            java.io.File r5 = (java.io.File) r5     // Catch:{ all -> 0x014e }
            boolean r4 = r4.b(r5)     // Catch:{ all -> 0x014e }
            if (r4 != 0) goto L_0x0046
            r12.a()     // Catch:{ all -> 0x014e }
            monitor-exit(r11)
            return
        L_0x0046:
            int r3 = r3 + 1
            goto L_0x0020
        L_0x004a:
            r12.a()     // Catch:{ all -> 0x014e }
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x014e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x014e }
            r2.<init>()     // Catch:{ all -> 0x014e }
            java.lang.String r4 = "Newly created entry didn't create value for index "
            r2.append(r4)     // Catch:{ all -> 0x014e }
            r2.append(r3)     // Catch:{ all -> 0x014e }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x014e }
            r1.<init>(r2)     // Catch:{ all -> 0x014e }
            throw r1     // Catch:{ all -> 0x014e }
        L_0x0064:
            int r2 = r11.R4     // Catch:{ all -> 0x014e }
        L_0x0066:
            if (r1 >= r2) goto L_0x00b5
            java.util.List r3 = r0.c()     // Catch:{ all -> 0x014e }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x014e }
            java.io.File r3 = (java.io.File) r3     // Catch:{ all -> 0x014e }
            if (r13 == 0) goto L_0x00ab
            boolean r4 = r0.i()     // Catch:{ all -> 0x014e }
            if (r4 != 0) goto L_0x00ab
            okhttp3.internal.io.b r4 = r11.O4     // Catch:{ all -> 0x014e }
            boolean r4 = r4.b(r3)     // Catch:{ all -> 0x014e }
            if (r4 == 0) goto L_0x00b0
            java.util.List r4 = r0.a()     // Catch:{ all -> 0x014e }
            java.lang.Object r4 = r4.get(r1)     // Catch:{ all -> 0x014e }
            java.io.File r4 = (java.io.File) r4     // Catch:{ all -> 0x014e }
            okhttp3.internal.io.b r5 = r11.O4     // Catch:{ all -> 0x014e }
            r5.g(r3, r4)     // Catch:{ all -> 0x014e }
            long[] r5 = r0.e()     // Catch:{ all -> 0x014e }
            r6 = r5[r1]     // Catch:{ all -> 0x014e }
            r5 = r6
            okhttp3.internal.io.b r7 = r11.O4     // Catch:{ all -> 0x014e }
            long r7 = r7.d(r4)     // Catch:{ all -> 0x014e }
            long[] r9 = r0.e()     // Catch:{ all -> 0x014e }
            r9[r1] = r7     // Catch:{ all -> 0x014e }
            long r9 = r11.B4     // Catch:{ all -> 0x014e }
            long r9 = r9 - r5
            long r9 = r9 + r7
            r11.B4 = r9     // Catch:{ all -> 0x014e }
            goto L_0x00b0
        L_0x00ab:
            okhttp3.internal.io.b r4 = r11.O4     // Catch:{ all -> 0x014e }
            r4.h(r3)     // Catch:{ all -> 0x014e }
        L_0x00b0:
            int r1 = r1 + 1
            goto L_0x0066
        L_0x00b5:
            r1 = 0
            r0.l(r1)     // Catch:{ all -> 0x014e }
            boolean r1 = r0.i()     // Catch:{ all -> 0x014e }
            if (r1 == 0) goto L_0x00c4
            r11.a1(r0)     // Catch:{ all -> 0x014e }
            monitor-exit(r11)
            return
        L_0x00c4:
            int r1 = r11.E4     // Catch:{ all -> 0x014e }
            r2 = 1
            int r1 = r1 + r2
            r11.E4 = r1     // Catch:{ all -> 0x014e }
            okio.d r1 = r11.C4     // Catch:{ all -> 0x014e }
            if (r1 != 0) goto L_0x00d1
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x014e }
        L_0x00d1:
            r3 = 0
            boolean r4 = r0.g()     // Catch:{ all -> 0x014e }
            r5 = 10
            r6 = 32
            if (r4 != 0) goto L_0x00fc
            if (r13 == 0) goto L_0x00df
            goto L_0x00fc
        L_0x00df:
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r2 = r11.D4     // Catch:{ all -> 0x014e }
            java.lang.String r4 = r0.d()     // Catch:{ all -> 0x014e }
            r2.remove(r4)     // Catch:{ all -> 0x014e }
            java.lang.String r2 = p1     // Catch:{ all -> 0x014e }
            okio.d r2 = r1.writeUtf8(r2)     // Catch:{ all -> 0x014e }
            r2.writeByte(r6)     // Catch:{ all -> 0x014e }
            java.lang.String r2 = r0.d()     // Catch:{ all -> 0x014e }
            r1.writeUtf8(r2)     // Catch:{ all -> 0x014e }
            r1.writeByte(r5)     // Catch:{ all -> 0x014e }
            goto L_0x0121
        L_0x00fc:
            r0.o(r2)     // Catch:{ all -> 0x014e }
            java.lang.String r2 = p0     // Catch:{ all -> 0x014e }
            okio.d r2 = r1.writeUtf8(r2)     // Catch:{ all -> 0x014e }
            r2.writeByte(r6)     // Catch:{ all -> 0x014e }
            java.lang.String r2 = r0.d()     // Catch:{ all -> 0x014e }
            r1.writeUtf8(r2)     // Catch:{ all -> 0x014e }
            r0.s(r1)     // Catch:{ all -> 0x014e }
            r1.writeByte(r5)     // Catch:{ all -> 0x014e }
            if (r13 == 0) goto L_0x0121
            long r4 = r11.L4     // Catch:{ all -> 0x014e }
            r6 = 1
            long r6 = r6 + r4
            r11.L4 = r6     // Catch:{ all -> 0x014e }
            r0.p(r4)     // Catch:{ all -> 0x014e }
        L_0x0121:
            r1.flush()     // Catch:{ all -> 0x014e }
            long r1 = r11.B4     // Catch:{ all -> 0x014e }
            long r3 = r11.p3     // Catch:{ all -> 0x014e }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0135
            boolean r1 = r11.P()     // Catch:{ all -> 0x014e }
            if (r1 == 0) goto L_0x0140
        L_0x0135:
            okhttp3.internal.concurrent.d r1 = r11.M4     // Catch:{ all -> 0x014e }
            okhttp3.internal.cache.d$e r2 = r11.N4     // Catch:{ all -> 0x014e }
            r3 = 0
            r5 = 2
            r6 = 0
            okhttp3.internal.concurrent.d.j(r1, r2, r3, r5, r6)     // Catch:{ all -> 0x014e }
        L_0x0140:
            monitor-exit(r11)
            return
        L_0x0142:
            java.lang.String r1 = "Check failed."
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x014e }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x014e }
            r2.<init>(r1)     // Catch:{ all -> 0x014e }
            throw r2     // Catch:{ all -> 0x014e }
        L_0x014e:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.r(okhttp3.internal.cache.d$b, boolean):void");
    }

    /* access modifiers changed from: private */
    public final boolean P() {
        int i = this.E4;
        return i >= 2000 && i >= this.D4.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0030, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean P0(@org.jetbrains.annotations.NotNull java.lang.String r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.String r0 = "key"
            kotlin.jvm.internal.k.f(r8, r0)     // Catch:{ all -> 0x0033 }
            r7.J()     // Catch:{ all -> 0x0033 }
            r7.o()     // Catch:{ all -> 0x0033 }
            r7.d1(r8)     // Catch:{ all -> 0x0033 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.d$c> r0 = r7.D4     // Catch:{ all -> 0x0033 }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x0033 }
            okhttp3.internal.cache.d$c r0 = (okhttp3.internal.cache.d.c) r0     // Catch:{ all -> 0x0033 }
            r1 = 0
            if (r0 == 0) goto L_0x0031
            java.lang.String r2 = "lruEntries[key] ?: return false"
            kotlin.jvm.internal.k.b(r0, r2)     // Catch:{ all -> 0x0033 }
            boolean r2 = r7.a1(r0)     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x002f
            long r3 = r7.B4     // Catch:{ all -> 0x0033 }
            long r5 = r7.p3     // Catch:{ all -> 0x0033 }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 > 0) goto L_0x002f
            r7.J4 = r1     // Catch:{ all -> 0x0033 }
        L_0x002f:
            monitor-exit(r7)
            return r2
        L_0x0031:
            monitor-exit(r7)
            return r1
        L_0x0033:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.d.P0(java.lang.String):boolean");
    }

    public final boolean a1(@NotNull c entry) {
        okio.d it;
        k.f(entry, "entry");
        if (!this.G4) {
            if (entry.f() > 0 && (it = this.C4) != null) {
                it.writeUtf8(a1);
                it.writeByte(32);
                it.writeUtf8(entry.d());
                it.writeByte(10);
                it.flush();
            }
            if (entry.f() > 0 || entry.b() != null) {
                entry.q(true);
                return true;
            }
        }
        b b2 = entry.b();
        if (b2 != null) {
            b2.c();
        }
        int i = this.R4;
        for (int i2 = 0; i2 < i; i2++) {
            this.O4.h(entry.a().get(i2));
            this.B4 -= entry.e()[i2];
            entry.e()[i2] = 0;
        }
        this.E4++;
        okio.d it2 = this.C4;
        if (it2 != null) {
            it2.writeUtf8(p1);
            it2.writeByte(32);
            it2.writeUtf8(entry.d());
            it2.writeByte(10);
        }
        this.D4.remove(entry.d());
        if (P()) {
            okhttp3.internal.concurrent.d.j(this.M4, this.N4, 0, 2, (Object) null);
        }
        return true;
    }

    private final synchronized void o() {
        if (!(!this.I4)) {
            throw new IllegalStateException("cache is closed".toString());
        }
    }

    public synchronized void flush() {
        if (this.H4) {
            o();
            c1();
            okio.d dVar = this.C4;
            if (dVar == null) {
                k.n();
            }
            dVar.flush();
        }
    }

    public synchronized void close() {
        b b2;
        if (this.H4) {
            if (!this.I4) {
                Collection thisCollection$iv = this.D4.values();
                k.b(thisCollection$iv, "lruEntries.values");
                Object[] array = thisCollection$iv.toArray(new c[0]);
                if (array != null) {
                    for (c entry : (c[]) array) {
                        if (!(entry.b() == null || (b2 = entry.b()) == null)) {
                            b2.c();
                        }
                    }
                    c1();
                    okio.d dVar = this.C4;
                    if (dVar == null) {
                        k.n();
                    }
                    dVar.close();
                    this.C4 = null;
                    this.I4 = true;
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
        }
        this.I4 = true;
    }

    public final void c1() {
        while (this.B4 > this.p3) {
            if (!b1()) {
                return;
            }
        }
        this.J4 = false;
    }

    private final boolean b1() {
        for (c toEvict : this.D4.values()) {
            if (!toEvict.i()) {
                k.b(toEvict, "toEvict");
                a1(toEvict);
                return true;
            }
        }
        return false;
    }

    public final void s() {
        close();
        this.O4.a(this.P4);
    }

    private final void d1(String key) {
        if (!z.matches(key)) {
            throw new IllegalArgumentException(("keys must match regex [a-z0-9_-]{1,120}: \"" + key + StringUtil.DOUBLE_QUOTE).toString());
        }
    }

    /* renamed from: okhttp3.internal.cache.d$d  reason: collision with other inner class name */
    /* compiled from: DiskLruCache.kt */
    public final class C0461d implements Closeable {
        private final String c;
        private final long d;
        private final List<e0> f;
        private final long[] q;
        final /* synthetic */ d x;

        public C0461d(@NotNull d $outer, String key, @NotNull long sequenceNumber, @NotNull List<? extends e0> sources, long[] lengths) {
            k.f(key, CacheEntity.KEY);
            k.f(sources, "sources");
            k.f(lengths, "lengths");
            this.x = $outer;
            this.c = key;
            this.d = sequenceNumber;
            this.f = sources;
            this.q = lengths;
        }

        @Nullable
        public final b a() {
            return this.x.t(this.c, this.d);
        }

        @NotNull
        public final e0 c(int index) {
            return this.f.get(index);
        }

        public void close() {
            for (e0 source : this.f) {
                okhttp3.internal.b.j(source);
            }
        }
    }

    /* compiled from: DiskLruCache.kt */
    public final class b {
        @Nullable
        private final boolean[] a;
        private boolean b;
        @NotNull
        private final c c;
        final /* synthetic */ d d;

        /* compiled from: DiskLruCache.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<IOException, x> {
            final /* synthetic */ int $index$inlined;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar, int i) {
                super(1);
                this.this$0 = bVar;
                this.$index$inlined = i;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((IOException) obj);
                return x.a;
            }

            public final void invoke(@NotNull IOException it) {
                k.f(it, "it");
                synchronized (this.this$0.d) {
                    this.this$0.c();
                    x xVar = x.a;
                }
            }
        }

        public b(@NotNull d $outer, c entry) {
            k.f(entry, "entry");
            this.d = $outer;
            this.c = entry;
            this.a = entry.g() ? null : new boolean[$outer.I()];
        }

        @NotNull
        public final c d() {
            return this.c;
        }

        @Nullable
        public final boolean[] e() {
            return this.a;
        }

        public final void c() {
            if (!k.a(this.c.b(), this)) {
                return;
            }
            if (this.d.G4) {
                this.d.r(this, false);
            } else {
                this.c.q(true);
            }
        }

        @NotNull
        public final b0 f(int index) {
            synchronized (this.d) {
                if (!(!this.b)) {
                    throw new IllegalStateException("Check failed.".toString());
                } else if (!k.a(this.c.b(), this)) {
                    b0 b2 = p.b();
                    return b2;
                } else {
                    if (!this.c.g()) {
                        boolean[] zArr = this.a;
                        if (zArr == null) {
                            k.n();
                        }
                        zArr[index] = true;
                    }
                    try {
                        e eVar = new e(this.d.E().f(this.c.c().get(index)), new a(this, index));
                        return eVar;
                    } catch (FileNotFoundException e) {
                        return p.b();
                    }
                }
            }
        }

        public final void b() {
            synchronized (this.d) {
                if (!this.b) {
                    if (k.a(this.c.b(), this)) {
                        this.d.r(this, true);
                    }
                    this.b = true;
                    x xVar = x.a;
                } else {
                    throw new IllegalStateException("Check failed.".toString());
                }
            }
        }

        public final void a() {
            synchronized (this.d) {
                if (!this.b) {
                    if (k.a(this.c.b(), this)) {
                        this.d.r(this, false);
                    }
                    this.b = true;
                    x xVar = x.a;
                } else {
                    throw new IllegalStateException("Check failed.".toString());
                }
            }
        }
    }

    /* compiled from: DiskLruCache.kt */
    public final class c {
        @NotNull
        private final long[] a;
        @NotNull
        private final List<File> b = new ArrayList();
        @NotNull
        private final List<File> c = new ArrayList();
        private boolean d;
        private boolean e;
        @Nullable
        private b f;
        private int g;
        private long h;
        @NotNull
        private final String i;
        final /* synthetic */ d j;

        public c(@NotNull d $outer, String key) {
            k.f(key, CacheEntity.KEY);
            this.j = $outer;
            this.i = key;
            this.a = new long[$outer.I()];
            StringBuilder fileBuilder = new StringBuilder(key).append('.');
            int truncateTo = fileBuilder.length();
            int I = $outer.I();
            for (int i2 = 0; i2 < I; i2++) {
                fileBuilder.append(i2);
                this.b.add(new File($outer.z(), fileBuilder.toString()));
                fileBuilder.append(".tmp");
                this.c.add(new File($outer.z(), fileBuilder.toString()));
                fileBuilder.setLength(truncateTo);
            }
        }

        @NotNull
        public final String d() {
            return this.i;
        }

        @NotNull
        public final long[] e() {
            return this.a;
        }

        @NotNull
        public final List<File> a() {
            return this.b;
        }

        @NotNull
        public final List<File> c() {
            return this.c;
        }

        public final boolean g() {
            return this.d;
        }

        public final void o(boolean z) {
            this.d = z;
        }

        public final boolean i() {
            return this.e;
        }

        public final void q(boolean z) {
            this.e = z;
        }

        @Nullable
        public final b b() {
            return this.f;
        }

        public final void l(@Nullable b bVar) {
            this.f = bVar;
        }

        public final int f() {
            return this.g;
        }

        public final void n(int i2) {
            this.g = i2;
        }

        public final long h() {
            return this.h;
        }

        public final void p(long j2) {
            this.h = j2;
        }

        public final void m(@NotNull List<String> strings) {
            k.f(strings, "strings");
            if (strings.size() == this.j.I()) {
                try {
                    int size = strings.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        this.a[i2] = Long.parseLong(strings.get(i2));
                    }
                } catch (NumberFormatException e2) {
                    j(strings);
                    throw null;
                }
            } else {
                j(strings);
                throw null;
            }
        }

        public final void s(@NotNull okio.d writer) {
            k.f(writer, "writer");
            for (long length : this.a) {
                writer.writeByte(32).writeDecimalLong(length);
            }
        }

        private final Void j(List<String> strings) {
            throw new IOException("unexpected journal line: " + strings);
        }

        @Nullable
        public final C0461d r() {
            Object $this$assertThreadHoldsLock$iv = this.j;
            if (okhttp3.internal.b.h && !Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Thread ");
                Thread currentThread = Thread.currentThread();
                k.b(currentThread, "Thread.currentThread()");
                sb.append(currentThread.getName());
                sb.append(" MUST hold lock on ");
                sb.append($this$assertThreadHoldsLock$iv);
                throw new AssertionError(sb.toString());
            } else if (!this.d) {
                return null;
            } else {
                if (!this.j.G4 && (this.f != null || this.e)) {
                    return null;
                }
                List<e0> sources = new ArrayList<>();
                long[] lengths = (long[]) this.a.clone();
                try {
                    int I = this.j.I();
                    for (int i2 = 0; i2 < I; i2++) {
                        sources.add(k(i2));
                    }
                    return new C0461d(this.j, this.i, this.h, sources, lengths);
                } catch (FileNotFoundException e2) {
                    for (e0 source : sources) {
                        okhttp3.internal.b.j(source);
                    }
                    try {
                        this.j.a1(this);
                    } catch (IOException e3) {
                    }
                    return null;
                }
            }
        }

        private final e0 k(int index) {
            e0 fileSource = this.j.E().e(this.b.get(index));
            if (this.j.G4) {
                return fileSource;
            }
            this.g++;
            return new a(this, fileSource, fileSource);
        }

        /* compiled from: DiskLruCache.kt */
        public static final class a extends okio.k {
            private boolean c;
            final /* synthetic */ c d;
            final /* synthetic */ e0 f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c $outer, e0 $captured_local_variable$1, e0 $super_call_param$2) {
                super($super_call_param$2);
                this.d = $outer;
                this.f = $captured_local_variable$1;
            }

            public void close() {
                super.close();
                if (!this.c) {
                    this.c = true;
                    synchronized (this.d.j) {
                        c cVar = this.d;
                        cVar.n(cVar.f() - 1);
                        if (this.d.f() == 0 && this.d.i()) {
                            c cVar2 = this.d;
                            cVar2.j.a1(cVar2);
                        }
                        x xVar = x.a;
                    }
                }
            }
        }
    }

    /* compiled from: DiskLruCache.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
