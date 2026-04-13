package io.ktor.server.engine;

import io.ktor.application.i;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.TypeCastException;
import kotlin.collections.k0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.j;
import kotlin.t;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationEngineEnvironmentReloading.kt */
public final class e implements b {
    private static final ThreadLocal<List<String>> a = new ThreadLocal<>();
    /* access modifiers changed from: private */
    public static final Class<io.ktor.application.d> b = io.ktor.application.d.class;
    /* access modifiers changed from: private */
    public static final Class<io.ktor.application.a> c = io.ktor.application.a.class;
    public static final a d = new a((DefaultConstructorMarker) null);
    private io.ktor.application.a e;
    private ClassLoader f;
    private final ReentrantReadWriteLock g = new ReentrantReadWriteLock();
    private List<? extends WatchKey> h = q.g();
    private final List<String> i;
    /* access modifiers changed from: private */
    public final List<String> j;
    private final kotlin.g k;
    @NotNull
    private final io.ktor.application.e l;
    @NotNull
    private final ClassLoader m;
    @NotNull
    private final org.slf4j.b n;
    @NotNull
    private final io.ktor.config.a o;
    @NotNull
    private final List<r> p;
    private final List<l<io.ktor.application.a, x>> q;
    private final List<String> r;
    @NotNull
    private final kotlin.coroutines.g s;
    @NotNull
    private final String t;

    private final WatchService w() {
        return (WatchService) this.k.getValue();
    }

    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<kotlin.reflect.f<? extends R>, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.f) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.f<? extends R> it) {
            k.f(it, "it");
            return (it.getParameters().isEmpty() ^ true) && e.d.f(it.getParameters().get(0));
        }
    }

    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<kotlin.reflect.f<? extends R>, Integer> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Integer.valueOf(invoke((kotlin.reflect.f) obj));
        }

        public final int invoke(@NotNull kotlin.reflect.f<? extends R> it) {
            k.f(it, "it");
            List<j> parameters = it.getParameters();
            if ((parameters instanceof Collection) && parameters.isEmpty()) {
                return 0;
            }
            int count$iv = 0;
            for (j it2 : parameters) {
                if ((!it2.m()) && (count$iv = count$iv + 1) < 0) {
                    q.p();
                }
            }
            return count$iv;
        }
    }

    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<kotlin.reflect.f<? extends R>, Integer> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Integer.valueOf(invoke((kotlin.reflect.f) obj));
        }

        public final int invoke(@NotNull kotlin.reflect.f<? extends R> it) {
            k.f(it, "it");
            return it.getParameters().size();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0069, code lost:
        r9 = r9.b();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public e(@org.jetbrains.annotations.NotNull java.lang.ClassLoader r23, @org.jetbrains.annotations.NotNull org.slf4j.b r24, @org.jetbrains.annotations.NotNull io.ktor.config.a r25, @org.jetbrains.annotations.NotNull java.util.List<? extends io.ktor.server.engine.r> r26, @org.jetbrains.annotations.NotNull java.util.List<? extends kotlin.jvm.functions.l<? super io.ktor.application.a, kotlin.x>> r27, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> r28, @org.jetbrains.annotations.NotNull kotlin.coroutines.g r29, @org.jetbrains.annotations.NotNull java.lang.String r30) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            r5 = r27
            r6 = r28
            r7 = r29
            r8 = r30
            java.lang.String r9 = "classLoader"
            kotlin.jvm.internal.k.f(r1, r9)
            java.lang.String r9 = "log"
            kotlin.jvm.internal.k.f(r2, r9)
            java.lang.String r9 = "config"
            kotlin.jvm.internal.k.f(r3, r9)
            java.lang.String r9 = "connectors"
            kotlin.jvm.internal.k.f(r4, r9)
            java.lang.String r9 = "modules"
            kotlin.jvm.internal.k.f(r5, r9)
            java.lang.String r9 = "watchPaths"
            kotlin.jvm.internal.k.f(r6, r9)
            java.lang.String r9 = "parentCoroutineContext"
            kotlin.jvm.internal.k.f(r7, r9)
            java.lang.String r9 = "rootPath"
            kotlin.jvm.internal.k.f(r8, r9)
            r22.<init>()
            r0.m = r1
            r0.n = r2
            r0.o = r3
            r0.p = r4
            r0.q = r5
            r0.r = r6
            r0.s = r7
            r0.t = r8
            java.util.concurrent.locks.ReentrantReadWriteLock r9 = new java.util.concurrent.locks.ReentrantReadWriteLock
            r9.<init>()
            r0.g = r9
            java.util.List r9 = kotlin.collections.q.g()
            r0.h = r9
            io.ktor.config.a r9 = r22.getConfig()
            java.lang.String r10 = "ktor.deployment.watch"
            io.ktor.config.b r9 = r9.a(r10)
            if (r9 == 0) goto L_0x0070
            java.util.List r9 = r9.b()
            if (r9 == 0) goto L_0x0070
            goto L_0x0074
        L_0x0070:
            java.util.List r9 = kotlin.collections.q.g()
        L_0x0074:
            java.util.List r9 = kotlin.collections.y.n0(r9, r6)
            r0.i = r9
            r9 = r22
            r10 = 0
            io.ktor.config.a r11 = r9.getConfig()
            java.lang.String r12 = "ktor.application.modules"
            io.ktor.config.b r11 = r11.a(r12)
            if (r11 == 0) goto L_0x008e
            java.util.List r11 = r11.b()
            goto L_0x008f
        L_0x008e:
            r11 = 0
        L_0x008f:
            java.util.List<java.lang.String> r13 = r9.i
            boolean r13 = r13.isEmpty()
            if (r13 == 0) goto L_0x0099
            goto L_0x011a
        L_0x0099:
            java.util.List<kotlin.jvm.functions.l<io.ktor.application.a, kotlin.x>> r13 = r9.q
            r14 = 0
            java.util.ArrayList r15 = new java.util.ArrayList
            r12 = 10
            int r12 = kotlin.collections.r.r(r13, r12)
            r15.<init>(r12)
            r12 = r15
            r15 = r13
            r16 = 0
            java.util.Iterator r17 = r15.iterator()
        L_0x00af:
            boolean r18 = r17.hasNext()
            if (r18 == 0) goto L_0x010f
            java.lang.Object r18 = r17.next()
            r1 = r18
            kotlin.jvm.functions.l r1 = (kotlin.jvm.functions.l) r1
            r19 = 0
            boolean r2 = r1 instanceof kotlin.reflect.f
            if (r2 != 0) goto L_0x00c5
            r2 = 0
            goto L_0x00c6
        L_0x00c5:
            r2 = r1
        L_0x00c6:
            kotlin.reflect.f r2 = (kotlin.reflect.f) r2
            if (r2 == 0) goto L_0x0105
            java.lang.reflect.Method r2 = kotlin.reflect.jvm.d.a(r2)
            if (r2 == 0) goto L_0x0105
            r20 = r1
            java.lang.Class r1 = r2.getDeclaringClass()
            java.lang.String r3 = r2.getName()
            r21 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "clazz"
            kotlin.jvm.internal.k.b(r1, r4)
            java.lang.String r4 = r1.getName()
            r2.append(r4)
            r4 = 46
            r2.append(r4)
            r2.append(r3)
            java.lang.String r1 = r2.toString()
            r12.add(r1)
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            goto L_0x00af
        L_0x0105:
            r20 = r1
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Module function provided as lambda cannot be unlinked for reload"
            r1.<init>(r2)
            throw r1
        L_0x010f:
            r1 = r12
            if (r11 != 0) goto L_0x0115
            r11 = r1
            goto L_0x011a
        L_0x0115:
            java.util.List r2 = kotlin.collections.y.n0(r11, r1)
            r11 = r2
        L_0x011a:
            r0.j = r11
            io.ktor.server.engine.e$g r1 = io.ktor.server.engine.e.g.INSTANCE
            kotlin.g r1 = kotlin.i.b(r1)
            r0.k = r1
            io.ktor.application.e r1 = new io.ktor.application.e
            r1.<init>()
            r0.l = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.e.<init>(java.lang.ClassLoader, org.slf4j.b, io.ktor.config.a, java.util.List, java.util.List, java.util.List, kotlin.coroutines.g, java.lang.String):void");
    }

    @NotNull
    public ClassLoader v() {
        return this.m;
    }

    @NotNull
    public org.slf4j.b f() {
        return this.n;
    }

    @NotNull
    public io.ktor.config.a getConfig() {
        return this.o;
    }

    @NotNull
    public List<r> c() {
        return this.p;
    }

    @NotNull
    public kotlin.coroutines.g e() {
        return this.s;
    }

    @NotNull
    public String d() {
        return this.t;
    }

    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<WatchService> {
        public static final g INSTANCE = new g();

        g() {
            super(0);
        }

        public final WatchService invoke() {
            return FileSystems.getDefault().newWatchService();
        }
    }

    @NotNull
    public io.ktor.application.e b() {
        return this.l;
    }

    @NotNull
    public io.ktor.application.a a() {
        return s();
    }

    private final io.ktor.application.a s() {
        ReentrantReadWriteLock.ReadLock readLock;
        int i2;
        int readHoldCount;
        ReentrantReadWriteLock.WriteLock writeLock;
        ReentrantReadWriteLock.ReadLock readLock2 = this.g.readLock();
        readLock2.lock();
        try {
            if (!this.i.isEmpty()) {
                Iterable<WatchKey> $this$flatMapTo$iv$iv = this.h;
                List arrayList = new ArrayList();
                for (WatchKey it : $this$flatMapTo$iv$iv) {
                    v.x(arrayList, it.pollEvents());
                }
                List changes = arrayList;
                if (!changes.isEmpty()) {
                    f().info("Changes in application detected.");
                    int count = changes.size();
                    while (true) {
                        Thread.sleep(200);
                        Iterable<WatchKey> $this$flatMapTo$iv$iv2 = this.h;
                        List arrayList2 = new ArrayList();
                        for (WatchKey it2 : $this$flatMapTo$iv$iv2) {
                            v.x(arrayList2, it2.pollEvents());
                        }
                        List moreChanges = arrayList2;
                        if (moreChanges.isEmpty()) {
                            break;
                        }
                        f().debug("Waiting for more changes.");
                        count += moreChanges.size();
                    }
                    f().debug("Changes to " + count + " files caused application restart.");
                    for (WatchEvent it3 : y.w0(changes, 5)) {
                        f().debug("...  " + it3.context());
                    }
                    ReentrantReadWriteLock reentrantReadWriteLock = this.g;
                    readLock = reentrantReadWriteLock.readLock();
                    i2 = 0;
                    readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                    for (int i3 = 0; i3 < readHoldCount; i3++) {
                        readLock.unlock();
                    }
                    writeLock = reentrantReadWriteLock.writeLock();
                    writeLock.lock();
                    t();
                    n<io.ktor.application.a, ClassLoader> p2 = p();
                    this.e = p2.component1();
                    this.f = p2.component2();
                    x xVar = x.a;
                    while (i2 < readHoldCount) {
                        readLock.lock();
                        i2++;
                    }
                    writeLock.unlock();
                }
            }
            io.ktor.application.a aVar = this.e;
            if (aVar != null) {
                readLock2.unlock();
                return aVar;
            }
            throw new IllegalStateException("ApplicationEngineEnvironment was not started");
        } catch (Throwable th) {
            readLock2.unlock();
            throw th;
        }
    }

    private final n<io.ktor.application.a, ClassLoader> p() {
        ClassLoader classLoader = q();
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "currentThread");
        ClassLoader oldThreadClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(classLoader);
        try {
            return t.a(y(classLoader), classLoader);
        } finally {
            currentThread.setContextClassLoader(oldThreadClassLoader);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x01fc  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01ff A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.ClassLoader q() {
        /*
            r29 = this;
            r0 = r29
            java.lang.ClassLoader r1 = r29.v()
            java.util.List<java.lang.String> r2 = r0.i
            boolean r3 = r2.isEmpty()
            if (r3 == 0) goto L_0x0018
            org.slf4j.b r3 = r29.f()
            java.lang.String r4 = "No ktor.deployment.watch patterns specified, automatic reload is not active"
            r3.info(r4)
            return r1
        L_0x0018:
            java.util.Set r3 = io.ktor.server.engine.k.a(r1)
            java.io.File r4 = new java.io.File
            java.lang.String r5 = "java.home"
            java.lang.String r5 = java.lang.System.getProperty(r5)
            r4.<init>(r5)
            java.lang.String r4 = r4.getParent()
            r5 = r3
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r8 = 10
            int r8 = kotlin.collections.r.r(r5, r8)
            r7.<init>(r8)
            r8 = r5
            r9 = 0
            java.util.Iterator r10 = r8.iterator()
        L_0x003e:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0054
            java.lang.Object r11 = r10.next()
            r12 = r11
            java.net.URL r12 = (java.net.URL) r12
            r13 = 0
            java.lang.String r12 = r12.getFile()
            r7.add(r12)
            goto L_0x003e
        L_0x0054:
            r5 = r7
            org.slf4j.b r6 = r29.f()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Java Home: "
            r7.append(r8)
            r7.append(r4)
            java.lang.String r7 = r7.toString()
            r6.debug(r7)
            org.slf4j.b r6 = r29.f()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Class Loader: "
            r7.append(r8)
            r7.append(r1)
            java.lang.String r8 = ": "
            r7.append(r8)
            r8 = r5
            r9 = 0
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            r11 = r8
            r12 = 0
            java.util.Iterator r13 = r11.iterator()
        L_0x0091:
            boolean r14 = r13.hasNext()
            java.lang.String r15 = "jre"
            r16 = r5
            r17 = 1
            r5 = 0
            if (r14 == 0) goto L_0x00c7
            java.lang.Object r14 = r13.next()
            r18 = r14
            java.lang.String r18 = (java.lang.String) r18
            r19 = 0
            r20 = r8
            java.lang.String r8 = r18.toString()
            kotlin.jvm.internal.k.b(r4, r15)
            r21 = r9
            r9 = 2
            r15 = 0
            boolean r5 = kotlin.text.w.N(r8, r4, r5, r9, r15)
            r5 = r5 ^ 1
            if (r5 == 0) goto L_0x00c0
            r10.add(r14)
        L_0x00c0:
            r5 = r16
            r8 = r20
            r9 = r21
            goto L_0x0091
        L_0x00c7:
            r20 = r8
            r21 = r9
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            r6.debug(r7)
            r6 = 8
            java.lang.Class[] r6 = new java.lang.Class[r6]
            java.lang.Class<io.ktor.application.d> r7 = io.ktor.application.d.class
            r6[r5] = r7
            java.lang.Class<io.ktor.server.engine.b> r7 = io.ktor.server.engine.b.class
            r6[r17] = r7
            java.lang.Class<io.ktor.util.pipeline.c> r7 = io.ktor.util.pipeline.c.class
            r8 = 2
            r6[r8] = r7
            r7 = 3
            java.lang.Class<io.ktor.http.v> r8 = io.ktor.http.v.class
            r6[r7] = r8
            r7 = 4
            java.lang.Class<kotlin.jvm.functions.l> r8 = kotlin.jvm.functions.l.class
            r6[r7] = r8
            r7 = 5
            java.lang.Class<org.slf4j.b> r8 = org.slf4j.b.class
            r6[r7] = r8
            r7 = 6
            java.lang.Class<io.ktor.utils.io.i> r8 = io.ktor.utils.io.i.class
            r6[r7] = r8
            r7 = 7
            java.lang.Class<io.ktor.utils.io.core.w> r8 = io.ktor.utils.io.core.w.class
            r6[r7] = r8
            java.util.List r6 = kotlin.collections.q.j(r6)
            java.util.HashSet r7 = new java.util.HashSet
            r7.<init>()
            r8 = 0
            r9 = r6
            r10 = 0
            java.util.Iterator r11 = r9.iterator()
        L_0x0111:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0147
            java.lang.Object r12 = r11.next()
            r13 = r12
            r14 = 0
            r18 = r13
            java.lang.Class r18 = (java.lang.Class) r18
            r19 = 0
            java.security.ProtectionDomain r5 = r18.getProtectionDomain()
            r21 = r6
            java.lang.String r6 = "it.protectionDomain"
            kotlin.jvm.internal.k.b(r5, r6)
            java.security.CodeSource r5 = r5.getCodeSource()
            java.lang.String r6 = "it.protectionDomain.codeSource"
            kotlin.jvm.internal.k.b(r5, r6)
            java.net.URL r5 = r5.getLocation()
            if (r5 == 0) goto L_0x0142
            r6 = 0
            r7.add(r5)
            goto L_0x0143
        L_0x0142:
        L_0x0143:
            r6 = r21
            r5 = 0
            goto L_0x0111
        L_0x0147:
            r21 = r6
            r5 = r7
            r6 = r3
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r9 = r6
            r10 = 0
            java.util.Iterator r11 = r9.iterator()
        L_0x0158:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x020b
            java.lang.Object r12 = r11.next()
            r13 = r12
            java.net.URL r13 = (java.net.URL) r13
            r14 = 0
            boolean r18 = r5.contains(r13)
            if (r18 != 0) goto L_0x01ec
            r18 = r2
            r19 = 0
            boolean r21 = r18.isEmpty()
            if (r21 == 0) goto L_0x0184
            r23 = r2
            r25 = r3
            r26 = r5
            r27 = r6
            r28 = r7
            r7 = 0
            goto L_0x01ce
        L_0x0184:
            java.util.Iterator r21 = r18.iterator()
        L_0x0188:
            boolean r22 = r21.hasNext()
            if (r22 == 0) goto L_0x01c3
            java.lang.Object r22 = r21.next()
            r23 = r2
            r2 = r22
            java.lang.String r2 = (java.lang.String) r2
            r24 = 0
            r25 = r3
            java.lang.String r3 = r13.toString()
            r26 = r5
            java.lang.String r5 = "url.toString()"
            kotlin.jvm.internal.k.b(r3, r5)
            r27 = r6
            r28 = r7
            r5 = 0
            r6 = 2
            r7 = 0
            boolean r2 = kotlin.text.x.S(r3, r2, r7, r6, r5)
            if (r2 == 0) goto L_0x01b8
            r7 = r17
            goto L_0x01ce
        L_0x01b8:
            r2 = r23
            r3 = r25
            r5 = r26
            r6 = r27
            r7 = r28
            goto L_0x0188
        L_0x01c3:
            r23 = r2
            r25 = r3
            r26 = r5
            r27 = r6
            r28 = r7
            r7 = 0
        L_0x01ce:
            if (r7 == 0) goto L_0x01e8
            java.lang.String r2 = r13.getPath()
            if (r2 == 0) goto L_0x01d7
            goto L_0x01d9
        L_0x01d7:
            java.lang.String r2 = ""
        L_0x01d9:
            kotlin.jvm.internal.k.b(r4, r15)
            r3 = 0
            r5 = 2
            r7 = 0
            boolean r2 = kotlin.text.w.N(r2, r4, r7, r5, r3)
            if (r2 != 0) goto L_0x01f9
            r2 = r17
            goto L_0x01fa
        L_0x01e8:
            r3 = 0
            r5 = 2
            r7 = 0
            goto L_0x01f9
        L_0x01ec:
            r23 = r2
            r25 = r3
            r26 = r5
            r27 = r6
            r28 = r7
            r3 = 0
            r5 = 2
            r7 = 0
        L_0x01f9:
            r2 = r7
        L_0x01fa:
            if (r2 == 0) goto L_0x01ff
            r8.add(r12)
        L_0x01ff:
            r2 = r23
            r3 = r25
            r5 = r26
            r6 = r27
            r7 = r28
            goto L_0x0158
        L_0x020b:
            r23 = r2
            r25 = r3
            r26 = r5
            r27 = r6
            r28 = r7
            r2 = r8
            boolean r3 = r2.isEmpty()
            if (r3 == 0) goto L_0x0226
            org.slf4j.b r3 = r29.f()
            java.lang.String r5 = "No ktor.deployment.watch patterns match classpath entries, automatic reload is not active"
            r3.info(r5)
            return r1
        L_0x0226:
            r0.B(r2)
            io.ktor.server.engine.u r3 = new io.ktor.server.engine.u
            r3.<init>(r2, r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.e.q():java.lang.ClassLoader");
    }

    private final void A(io.ktor.application.j<io.ktor.application.a> event, io.ktor.application.a application) {
        try {
            b().a(event, application);
        } catch (Throwable e2) {
            f().error("One or more of the handlers thrown an exception", e2);
        }
    }

    private final void t() {
        io.ktor.application.a currentApplication = this.e;
        ClassLoader applicationClassLoader = this.f;
        ClassLoader classLoader = null;
        this.e = null;
        this.f = null;
        if (currentApplication != null) {
            A(i.e(), currentApplication);
            try {
                currentApplication.D();
                if (applicationClassLoader instanceof u) {
                    classLoader = applicationClassLoader;
                }
                u uVar = (u) classLoader;
                if (uVar != null) {
                    uVar.close();
                }
            } catch (Throwable e2) {
                f().error("Failed to destroy application instance.", e2);
            }
            A(i.d(), currentApplication);
        }
        for (WatchKey it : this.h) {
            it.cancel();
        }
        this.h = new ArrayList();
    }

    private final void B(List<URL> urls) {
        HashSet<Path> paths = new HashSet<>();
        for (URL url : urls) {
            String path = url.getPath();
            if (path != null) {
                Path folder = new File(URLDecoder.decode(path, "utf-8")).toPath();
                if (Files.exists(folder, new LinkOption[0])) {
                    f visitor = new f(paths);
                    if (Files.isDirectory(folder, new LinkOption[0])) {
                        Files.walkFileTree(folder, visitor);
                    }
                }
            }
        }
        for (Path path2 : paths) {
            org.slf4j.b f2 = f();
            f2.debug("Watching " + path2 + " for changes.");
        }
        WatchEvent.Modifier it = x();
        WatchEvent.Modifier[] modifiers = it != null ? new WatchEvent.Modifier[]{it} : new WatchEvent.Modifier[0];
        Iterable<Path> $this$mapTo$iv$iv = paths;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Path it2 : $this$mapTo$iv$iv) {
            arrayList.add(it2.register(w(), new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY}, (WatchEvent.Modifier[]) Arrays.copyOf(modifiers, modifiers.length)));
        }
        this.h = arrayList;
    }

    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class f extends SimpleFileVisitor<Path> {
        final /* synthetic */ HashSet a;

        f(HashSet $captured_local_variable$0) {
            this.a = $captured_local_variable$0;
        }

        @NotNull
        /* renamed from: a */
        public FileVisitResult preVisitDirectory(@NotNull Path dir, @NotNull BasicFileAttributes attrs) {
            k.f(dir, "dir");
            k.f(attrs, "attrs");
            this.a.add(dir);
            return FileVisitResult.CONTINUE;
        }

        @NotNull
        /* renamed from: b */
        public FileVisitResult visitFile(@NotNull Path file, @NotNull BasicFileAttributes attrs) {
            k.f(file, "file");
            k.f(attrs, "attrs");
            Path dir = file.getParent();
            if (dir != null) {
                this.a.add(dir);
            }
            return FileVisitResult.CONTINUE;
        }
    }

    public void start() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.g;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            n<io.ktor.application.a, ClassLoader> p2 = p();
            this.e = p2.component1();
            this.f = p2.component2();
            x xVar = x.a;
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        } catch (Throwable th) {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void stop() {
        /*
            r5 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = r5.g
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L_0x0012
            int r2 = r0.getReadHoldCount()
            goto L_0x0013
        L_0x0012:
            r2 = r3
        L_0x0013:
            r4 = r3
        L_0x0014:
            if (r4 >= r2) goto L_0x001c
            r1.unlock()
            int r4 = r4 + 1
            goto L_0x0014
        L_0x001c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r4 = 0
            r5.t()     // Catch:{ all -> 0x0047 }
            kotlin.x r4 = kotlin.x.a     // Catch:{ all -> 0x0047 }
        L_0x002a:
            if (r3 >= r2) goto L_0x0032
            r1.lock()
            int r3 = r3 + 1
            goto L_0x002a
        L_0x0032:
            r0.unlock()
            java.util.List<java.lang.String> r0 = r5.i
            boolean r0 = r0.isEmpty()
            r0 = r0 ^ 1
            if (r0 == 0) goto L_0x0046
            java.nio.file.WatchService r0 = r5.w()
            r0.close()
        L_0x0046:
            return
        L_0x0047:
            r4 = move-exception
        L_0x0048:
            if (r3 >= r2) goto L_0x0050
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0048
        L_0x0050:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.e.stop():void");
    }

    private final io.ktor.application.a y(ClassLoader classLoader) {
        io.ktor.application.a application = new io.ktor.application.a(this);
        A(i.b(), application);
        l(new C0271e(this, classLoader, application));
        if (this.i.isEmpty()) {
            for (l it : this.q) {
                it.invoke(application);
            }
        }
        A(i.a(), application);
        return application;
    }

    /* renamed from: io.ktor.server.engine.e$e  reason: collision with other inner class name */
    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class C0271e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ io.ktor.application.a $application;
        final /* synthetic */ ClassLoader $classLoader;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0271e(e eVar, ClassLoader classLoader, io.ktor.application.a aVar) {
            super(0);
            this.this$0 = eVar;
            this.$classLoader = classLoader;
            this.$application = aVar;
        }

        public final void invoke() {
            Iterable<String> $this$forEach$iv = this.this$0.j;
            if ($this$forEach$iv != null) {
                for (String fqName : $this$forEach$iv) {
                    this.this$0.m(fqName, new a(fqName, this));
                }
            }
        }

        /* renamed from: io.ktor.server.engine.e$e$a */
        /* compiled from: ApplicationEngineEnvironmentReloading.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ String $fqName;
            final /* synthetic */ C0271e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, C0271e eVar) {
                super(0);
                this.$fqName = str;
                this.this$0 = eVar;
            }

            public final void invoke() {
                C0271e eVar = this.this$0;
                eVar.this$0.u(eVar.$classLoader, this.$fqName, eVar.$application);
            }
        }
    }

    private final void l(kotlin.jvm.functions.a<x> block) {
        try {
            block.invoke();
            ThreadLocal<List<String>> threadLocal = a;
            List it = threadLocal.get();
            if (it != null && it.isEmpty()) {
                threadLocal.remove();
            }
        } catch (Throwable th) {
            List it2 = a.get();
            if (it2 != null && it2.isEmpty()) {
                a.remove();
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public final void m(String fqName, kotlin.jvm.functions.a<x> block) {
        ThreadLocal<List<String>> threadLocal = a;
        List<String> list = threadLocal.get();
        if (list == null) {
            ArrayList arrayList = new ArrayList(1);
            threadLocal.set(arrayList);
            list = arrayList;
        }
        List modules = list;
        if (!modules.contains(fqName)) {
            modules.add(fqName);
            try {
                block.invoke();
            } finally {
                modules.remove(fqName);
            }
        } else {
            throw new IllegalStateException("Module startup is already in progress for " + "function " + fqName + " (recursive module startup from module main?)");
        }
    }

    /* access modifiers changed from: private */
    public final void u(ClassLoader classLoader, String fqName, io.ktor.application.a application) {
        boolean z;
        String str = fqName;
        io.ktor.application.a aVar = application;
        char[] charArray = ".#".toCharArray();
        k.b(charArray, "(this as java.lang.String).toCharArray()");
        int idx = kotlin.text.x.m0(fqName, charArray, 0, false, 6, (Object) null);
        int i2 = 0;
        if (idx == -1) {
            ClassLoader classLoader2 = classLoader;
            int i3 = idx;
        } else if (str != null) {
            int i4 = 0;
            String className = str.substring(0, idx);
            k.b(className, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String functionName = str.substring(idx + 1);
            k.b(functionName, "(this as java.lang.String).substring(startIndex)");
            Class clazz = z(classLoader, className);
            if (clazz != null) {
                Method[] methods = clazz.getMethods();
                k.b(methods, "clazz.methods");
                ArrayList arrayList = new ArrayList();
                Method[] methodArr = methods;
                int length = methodArr.length;
                while (true) {
                    int idx2 = idx;
                    int i5 = i2;
                    if (i4 >= length) {
                        break;
                    }
                    Method it = methodArr[i4];
                    int i6 = length;
                    Method it2 = it;
                    k.b(it2, "it");
                    if (k.a(it2.getName(), functionName) && Modifier.isStatic(it2.getModifiers())) {
                        arrayList.add(it);
                    }
                    i4++;
                    idx = idx2;
                    i2 = i5;
                    length = i6;
                }
                Iterable<Method> $this$mapNotNull$iv = arrayList;
                ArrayList arrayList2 = new ArrayList();
                for (Method it3 : $this$mapNotNull$iv) {
                    Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                    k.b(it3, "it");
                    Object it$iv$iv = kotlin.reflect.jvm.d.d(it3);
                    if (it$iv$iv != null) {
                        arrayList2.add(it$iv$iv);
                    }
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                }
                Iterable $this$filter$iv = arrayList2;
                List arrayList3 = new ArrayList();
                for (Object element$iv$iv : arrayList2) {
                    Iterable $this$filter$iv2 = $this$filter$iv;
                    if (d.e((kotlin.reflect.f) element$iv$iv)) {
                        arrayList3.add(element$iv$iv);
                    }
                    $this$filter$iv = $this$filter$iv2;
                }
                List staticFunctions = arrayList3;
                kotlin.reflect.f moduleFunction = n(staticFunctions);
                if (moduleFunction != null) {
                    int i7 = false;
                    List<j> parameters = moduleFunction.getParameters();
                    if (!(parameters instanceof Collection) || !parameters.isEmpty()) {
                        Iterator<T> it4 = parameters.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                int i8 = i7;
                                z = true;
                                break;
                            }
                            List staticFunctions2 = staticFunctions;
                            int i9 = i7;
                            if (((j) it4.next()).h() == j.a.INSTANCE) {
                                z = false;
                                break;
                            } else {
                                staticFunctions = staticFunctions2;
                                i7 = i9;
                            }
                        }
                    } else {
                        ArrayList arrayList4 = staticFunctions;
                        z = true;
                    }
                    if (z) {
                        o((Object) null, moduleFunction, aVar);
                        return;
                    }
                }
                if (l.class.isAssignableFrom(clazz)) {
                    Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
                    k.b(declaredConstructors, "clazz.declaredConstructors");
                    Constructor constructor = (Constructor) kotlin.collections.l.J(declaredConstructors);
                    k.b(constructor, "constructor");
                    if (constructor.getParameterCount() == 0) {
                        constructor.setAccessible(true);
                        Object newInstance = constructor.newInstance(new Object[0]);
                        if (newInstance != null) {
                            ((l) e0.e(newInstance, 1)).invoke(aVar);
                            return;
                        }
                        throw new TypeCastException("null cannot be cast to non-null type (io.ktor.application.Application) -> kotlin.Unit");
                    }
                    throw new RuntimeException("Module function with captured variables cannot be instantiated '" + str + '\'');
                }
                kotlin.reflect.c kclass = d.i(clazz);
                if (kclass != null) {
                    int i10 = 0;
                    Iterable $this$filterTo$iv$iv = kotlin.reflect.full.b.b(kclass);
                    ArrayList arrayList5 = new ArrayList();
                    for (T next : $this$filterTo$iv$iv) {
                        kotlin.reflect.f it5 = (kotlin.reflect.f) next;
                        int i11 = i10;
                        if (k.a(it5.getName(), functionName) && d.e(it5)) {
                            arrayList5.add(next);
                        }
                        i10 = i11;
                    }
                    int i12 = i10;
                    kotlin.reflect.f moduleFunction2 = n(arrayList5);
                    if (moduleFunction2 != null) {
                        o(r(kclass, aVar), moduleFunction2, aVar);
                        return;
                    }
                }
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        throw new ClassNotFoundException("Module function cannot be found for the fully qualified name '" + str + '\'');
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0066 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object r(kotlin.reflect.c<?> r21, io.ktor.application.a r22) {
        /*
            r20 = this;
            r0 = r20
            java.lang.Object r1 = r21.j()
            if (r1 == 0) goto L_0x0009
            return r1
        L_0x0009:
            java.util.Collection r2 = r21.f()
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = r2
            r6 = 0
            java.util.Iterator r7 = r5.iterator()
        L_0x0019:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0070
            java.lang.Object r8 = r7.next()
            r9 = r8
            kotlin.reflect.f r9 = (kotlin.reflect.f) r9
            r10 = 0
            java.util.List r11 = r9.getParameters()
            r12 = 0
            boolean r13 = r11 instanceof java.util.Collection
            if (r13 == 0) goto L_0x0038
            boolean r13 = r11.isEmpty()
            if (r13 == 0) goto L_0x0038
            r14 = 1
            goto L_0x0069
        L_0x0038:
            java.util.Iterator r13 = r11.iterator()
        L_0x003c:
            boolean r16 = r13.hasNext()
            if (r16 == 0) goto L_0x0068
            java.lang.Object r16 = r13.next()
            r14 = r16
            kotlin.reflect.j r14 = (kotlin.reflect.j) r14
            r17 = 0
            boolean r18 = r14.m()
            if (r18 != 0) goto L_0x0063
            io.ktor.server.engine.e$a r15 = d
            boolean r19 = r15.g(r14)
            if (r19 != 0) goto L_0x0063
            boolean r15 = r15.f(r14)
            if (r15 == 0) goto L_0x0061
            goto L_0x0063
        L_0x0061:
            r14 = 0
            goto L_0x0064
        L_0x0063:
            r14 = 1
        L_0x0064:
            if (r14 != 0) goto L_0x003c
            r14 = 0
            goto L_0x0069
        L_0x0068:
            r14 = 1
        L_0x0069:
            if (r14 == 0) goto L_0x0019
            r4.add(r8)
            goto L_0x0019
        L_0x0070:
            r2 = r4
            kotlin.reflect.f r3 = r0.n(r2)
            if (r3 == 0) goto L_0x0080
            r4 = 0
            r5 = r22
            java.lang.Object r4 = r0.o(r4, r3, r5)
            return r4
        L_0x0080:
            r5 = r22
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "There are no applicable constructors found in class "
            r4.append(r6)
            r6 = r21
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.e.r(kotlin.reflect.c, io.ktor.application.a):java.lang.Object");
    }

    private final <R> kotlin.reflect.f<R> n(@NotNull List<? extends kotlin.reflect.f<? extends R>> list) {
        return (kotlin.reflect.f) y.f0(y.u0(list, kotlin.comparisons.a.b(b.INSTANCE, c.INSTANCE, d.INSTANCE)));
    }

    private final <R> R o(Object instance, kotlin.reflect.f<? extends R> entryPoint, io.ktor.application.a application) {
        io.ktor.application.a aVar;
        Iterable $this$filterNotTo$iv$iv = entryPoint.getParameters();
        ArrayList arrayList = new ArrayList();
        for (T next : $this$filterNotTo$iv$iv) {
            if (!((j) next).m()) {
                arrayList.add(next);
            }
        }
        Iterable $this$associateByTo$iv$iv = arrayList;
        Map destination$iv$iv = new LinkedHashMap(kotlin.ranges.n.b(k0.b(r.r($this$associateByTo$iv$iv, 10)), 16));
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            j jVar = (j) element$iv$iv;
            j p2 = (j) element$iv$iv;
            if (p2.h() == j.a.INSTANCE) {
                aVar = instance;
            } else {
                a aVar2 = d;
                if (aVar2.g(p2)) {
                    aVar = this;
                } else if (aVar2.f(p2)) {
                    aVar = application;
                } else {
                    ClassLoader classLoader = null;
                    if (kotlin.text.x.S(p2.getType().toString(), "Application", false, 2, (Object) null)) {
                        Type b2 = kotlin.reflect.jvm.d.b(p2.getType());
                        if (!(b2 instanceof Class)) {
                            b2 = null;
                        }
                        Class cls = (Class) b2;
                        if (cls != null) {
                            classLoader = cls.getClassLoader();
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("Parameter type ");
                        sb.append(p2.getType());
                        sb.append(":{");
                        sb.append(classLoader);
                        sb.append("} is not supported.");
                        sb.append("Application is loaded as ");
                        Class<io.ktor.application.a> cls2 = c;
                        sb.append(cls2);
                        sb.append(":{");
                        sb.append(cls2.getClassLoader());
                        sb.append('}');
                        throw new IllegalArgumentException(sb.toString());
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Parameter type '");
                    sb2.append(p2.getType());
                    sb2.append("' of parameter '");
                    String name = p2.getName();
                    if (name == null) {
                        name = "<receiver>";
                    }
                    sb2.append(name);
                    sb2.append("' is not supported");
                    throw new IllegalArgumentException(sb2.toString());
                }
            }
            destination$iv$iv.put(jVar, aVar);
        }
        return entryPoint.callBy(destination$iv$iv);
    }

    private final Class<?> z(@NotNull ClassLoader $this$loadClassOrNull, String name) {
        try {
            return $this$loadClassOrNull.loadClass(name);
        } catch (ClassNotFoundException e2) {
            return null;
        }
    }

    private final WatchEvent.Modifier x() {
        try {
            Class c2 = Class.forName("com.sun.nio.file.SensitivityWatchEventModifier");
            Object obj = c2.getField("HIGH").get(c2);
            if (!(obj instanceof WatchEvent.Modifier)) {
                obj = null;
            }
            return (WatchEvent.Modifier) obj;
        } catch (Exception e2) {
            return null;
        }
    }

    /* compiled from: ApplicationEngineEnvironmentReloading.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final boolean h(j p, Class<?> type) {
            Type b = kotlin.reflect.jvm.d.b(p.getType());
            if (!(b instanceof Class)) {
                b = null;
            }
            Class it = (Class) b;
            if (it != null) {
                return type.isAssignableFrom(it);
            }
            return false;
        }

        /* access modifiers changed from: private */
        public final boolean g(j p) {
            return h(p, e.b);
        }

        /* access modifiers changed from: private */
        public final boolean f(j p) {
            return h(p, e.c);
        }

        /* access modifiers changed from: private */
        public final boolean e(@NotNull kotlin.reflect.f<?> $this$isApplicableFunction) {
            j it;
            if ($this$isApplicableFunction.isOperator() || $this$isApplicableFunction.isInfix() || $this$isApplicableFunction.isInline() || $this$isApplicableFunction.isAbstract() || $this$isApplicableFunction.isSuspend()) {
                return false;
            }
            j it2 = kotlin.reflect.full.a.a($this$isApplicableFunction);
            if (it2 != null) {
                a aVar = e.d;
                if (!aVar.f(it2) && !aVar.g(it2)) {
                    return false;
                }
            }
            Method it3 = kotlin.reflect.jvm.d.a($this$isApplicableFunction);
            if (it3 != null) {
                if (it3.isSynthetic()) {
                    return false;
                }
                if (Modifier.isStatic(it3.getModifiers()) && $this$isApplicableFunction.getParameters().isEmpty()) {
                    return false;
                }
            }
            List<j> parameters = $this$isApplicableFunction.getParameters();
            if ((parameters instanceof Collection) && parameters.isEmpty()) {
                return true;
            }
            for (j it4 : parameters) {
                a aVar2 = e.d;
                if (aVar2.f(it4) || aVar2.g(it4) || it4.h() == j.a.INSTANCE || it4.m()) {
                    it = 1;
                    continue;
                } else {
                    it = null;
                    continue;
                }
                if (it == null) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0016, code lost:
            if (r5 != false) goto L_0x001a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final kotlin.reflect.c<?> i(@org.jetbrains.annotations.NotNull java.lang.Class<?> r7) {
            /*
                r6 = this;
                java.lang.Class<kotlin.l> r0 = kotlin.l.class
                java.lang.annotation.Annotation r0 = r7.getAnnotation(r0)
                kotlin.l r0 = (kotlin.l) r0
                r1 = 0
                if (r0 == 0) goto L_0x0019
                r2 = r0
                r3 = 0
                int r4 = r2.k()
                r5 = 1
                if (r4 != r5) goto L_0x0015
                goto L_0x0016
            L_0x0015:
                r5 = 0
            L_0x0016:
                if (r5 == 0) goto L_0x0019
                goto L_0x001a
            L_0x0019:
                r0 = r1
            L_0x001a:
                if (r0 == 0) goto L_0x0021
                kotlin.reflect.c r0 = kotlin.jvm.a.e(r7)
                return r0
            L_0x0021:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.e.a.i(java.lang.Class):kotlin.reflect.c");
        }
    }
}
