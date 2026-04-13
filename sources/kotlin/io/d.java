package kotlin.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.sequences.h;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileTreeWalk.kt */
public final class d implements h<File> {
    /* access modifiers changed from: private */
    public final File a;
    /* access modifiers changed from: private */
    public final f b;
    /* access modifiers changed from: private */
    public final l<File, Boolean> c;
    /* access modifiers changed from: private */
    public final l<File, x> d;
    /* access modifiers changed from: private */
    public final p<File, IOException, x> e;
    /* access modifiers changed from: private */
    public final int f;

    private d(File start, f direction, l<? super File, Boolean> onEnter, l<? super File, x> onLeave, p<? super File, ? super IOException, x> onFail, int maxDepth) {
        this.a = start;
        this.b = direction;
        this.c = onEnter;
        this.d = onLeave;
        this.e = onFail;
        this.f = maxDepth;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* synthetic */ d(java.io.File r8, kotlin.io.f r9, kotlin.jvm.functions.l r10, kotlin.jvm.functions.l r11, kotlin.jvm.functions.p r12, int r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r15 = r14 & 2
            if (r15 == 0) goto L_0x0008
            kotlin.io.f r9 = kotlin.io.f.TOP_DOWN
            r2 = r9
            goto L_0x0009
        L_0x0008:
            r2 = r9
        L_0x0009:
            r9 = r14 & 32
            if (r9 == 0) goto L_0x0012
            r13 = 2147483647(0x7fffffff, float:NaN)
            r6 = r13
            goto L_0x0013
        L_0x0012:
            r6 = r13
        L_0x0013:
            r0 = r7
            r1 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.d.<init>(java.io.File, kotlin.io.f, kotlin.jvm.functions.l, kotlin.jvm.functions.l, kotlin.jvm.functions.p, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public d(@NotNull File start, @NotNull f direction) {
        this(start, direction, (l) null, (l) null, (p) null, 0, 32, (DefaultConstructorMarker) null);
        k.e(start, "start");
        k.e(direction, "direction");
    }

    @NotNull
    public Iterator<File> iterator() {
        return new b();
    }

    /* compiled from: FileTreeWalk.kt */
    public static abstract class c {
        @NotNull
        private final File a;

        @Nullable
        public abstract File b();

        public c(@NotNull File root) {
            k.e(root, "root");
            this.a = root;
        }

        @NotNull
        public final File a() {
            return this.a;
        }
    }

    /* compiled from: FileTreeWalk.kt */
    public static abstract class a extends c {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull File rootDir) {
            super(rootDir);
            k.e(rootDir, "rootDir");
            if (!rootDir.isDirectory()) {
                throw new AssertionError("rootDir must be verified to be directory beforehand.");
            }
        }
    }

    /* compiled from: FileTreeWalk.kt */
    public final class b extends kotlin.collections.c<File> {
        private final ArrayDeque<c> f;

        public b() {
            ArrayDeque<c> arrayDeque = new ArrayDeque<>();
            this.f = arrayDeque;
            if (d.this.a.isDirectory()) {
                arrayDeque.push(h(d.this.a));
            } else if (d.this.a.isFile()) {
                arrayDeque.push(new C0325b(this, d.this.a));
            } else {
                e();
            }
        }

        /* access modifiers changed from: protected */
        public void b() {
            File nextFile = i();
            if (nextFile != null) {
                f(nextFile);
            } else {
                e();
            }
        }

        private final a h(File root) {
            switch (e.a[d.this.b.ordinal()]) {
                case 1:
                    return new c(this, root);
                case 2:
                    return new a(this, root);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        private final File i() {
            File file;
            while (true) {
                c topState = this.f.peek();
                if (topState == null) {
                    return null;
                }
                file = topState.b();
                if (file == null) {
                    this.f.pop();
                } else if (k.a(file, topState.a()) || !file.isDirectory() || this.f.size() >= d.this.f) {
                    return file;
                } else {
                    this.f.push(h(file));
                }
            }
            return file;
        }

        /* compiled from: FileTreeWalk.kt */
        public final class a extends a {
            private boolean b;
            private File[] c;
            private int d;
            private boolean e;
            final /* synthetic */ b f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull b this$0, File rootDir) {
                super(rootDir);
                k.e(rootDir, "rootDir");
                this.f = this$0;
            }

            @Nullable
            public File b() {
                if (!this.e && this.c == null) {
                    l d2 = d.this.c;
                    if (d2 != null && !((Boolean) d2.invoke(a())).booleanValue()) {
                        return null;
                    }
                    File[] listFiles = a().listFiles();
                    this.c = listFiles;
                    if (listFiles == null) {
                        p e2 = d.this.e;
                        if (e2 != null) {
                            x xVar = (x) e2.invoke(a(), new AccessDeniedException(a(), (File) null, "Cannot list files in a directory", 2, (DefaultConstructorMarker) null));
                        }
                        this.e = true;
                    }
                }
                File[] fileArr = this.c;
                if (fileArr != null) {
                    int i = this.d;
                    k.c(fileArr);
                    if (i < fileArr.length) {
                        File[] fileArr2 = this.c;
                        k.c(fileArr2);
                        int i2 = this.d;
                        this.d = i2 + 1;
                        return fileArr2[i2];
                    }
                }
                if (!this.b) {
                    this.b = true;
                    return a();
                }
                l f2 = d.this.d;
                if (f2 != null) {
                    x xVar2 = (x) f2.invoke(a());
                }
                return null;
            }
        }

        /* compiled from: FileTreeWalk.kt */
        public final class c extends a {
            private boolean b;
            private File[] c;
            private int d;
            final /* synthetic */ b e;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public c(@NotNull b this$0, File rootDir) {
                super(rootDir);
                k.e(rootDir, "rootDir");
                this.e = this$0;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:27:0x0085, code lost:
                if (r0.length == 0) goto L_0x0087;
             */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.io.File b() {
                /*
                    r10 = this;
                    boolean r0 = r10.b
                    r1 = 0
                    if (r0 != 0) goto L_0x0028
                    kotlin.io.d$b r0 = r10.e
                    kotlin.io.d r0 = kotlin.io.d.this
                    kotlin.jvm.functions.l r0 = r0.c
                    if (r0 == 0) goto L_0x0020
                    java.io.File r2 = r10.a()
                    java.lang.Object r0 = r0.invoke(r2)
                    java.lang.Boolean r0 = (java.lang.Boolean) r0
                    boolean r0 = r0.booleanValue()
                    if (r0 != 0) goto L_0x0020
                    return r1
                L_0x0020:
                    r0 = 1
                    r10.b = r0
                    java.io.File r0 = r10.a()
                    return r0
                L_0x0028:
                    java.io.File[] r0 = r10.c
                    if (r0 == 0) goto L_0x004a
                    int r2 = r10.d
                    kotlin.jvm.internal.k.c(r0)
                    int r0 = r0.length
                    if (r2 >= r0) goto L_0x0035
                    goto L_0x004a
                L_0x0035:
                    kotlin.io.d$b r0 = r10.e
                    kotlin.io.d r0 = kotlin.io.d.this
                    kotlin.jvm.functions.l r0 = r0.d
                    if (r0 == 0) goto L_0x0049
                    java.io.File r2 = r10.a()
                    java.lang.Object r0 = r0.invoke(r2)
                    kotlin.x r0 = (kotlin.x) r0
                L_0x0049:
                    return r1
                L_0x004a:
                    java.io.File[] r0 = r10.c
                    if (r0 != 0) goto L_0x009c
                    java.io.File r0 = r10.a()
                    java.io.File[] r0 = r0.listFiles()
                    r10.c = r0
                    if (r0 != 0) goto L_0x007d
                    kotlin.io.d$b r0 = r10.e
                    kotlin.io.d r0 = kotlin.io.d.this
                    kotlin.jvm.functions.p r0 = r0.e
                    if (r0 == 0) goto L_0x007d
                    java.io.File r2 = r10.a()
                    kotlin.io.AccessDeniedException r9 = new kotlin.io.AccessDeniedException
                    java.io.File r4 = r10.a()
                    r5 = 0
                    r7 = 2
                    r8 = 0
                    java.lang.String r6 = "Cannot list files in a directory"
                    r3 = r9
                    r3.<init>(r4, r5, r6, r7, r8)
                    java.lang.Object r0 = r0.invoke(r2, r9)
                    kotlin.x r0 = (kotlin.x) r0
                L_0x007d:
                    java.io.File[] r0 = r10.c
                    if (r0 == 0) goto L_0x0087
                    kotlin.jvm.internal.k.c(r0)
                    int r0 = r0.length
                    if (r0 != 0) goto L_0x009c
                L_0x0087:
                    kotlin.io.d$b r0 = r10.e
                    kotlin.io.d r0 = kotlin.io.d.this
                    kotlin.jvm.functions.l r0 = r0.d
                    if (r0 == 0) goto L_0x009b
                    java.io.File r2 = r10.a()
                    java.lang.Object r0 = r0.invoke(r2)
                    kotlin.x r0 = (kotlin.x) r0
                L_0x009b:
                    return r1
                L_0x009c:
                    java.io.File[] r0 = r10.c
                    kotlin.jvm.internal.k.c(r0)
                    int r1 = r10.d
                    int r2 = r1 + 1
                    r10.d = r2
                    r0 = r0[r1]
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.io.d.b.c.b():java.io.File");
            }
        }

        /* renamed from: kotlin.io.d$b$b  reason: collision with other inner class name */
        /* compiled from: FileTreeWalk.kt */
        public final class C0325b extends c {
            private boolean b;
            final /* synthetic */ b c;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0325b(@NotNull b this$0, File rootFile) {
                super(rootFile);
                k.e(rootFile, "rootFile");
                this.c = this$0;
                if (!rootFile.isFile()) {
                    throw new AssertionError("rootFile must be verified to be file beforehand.");
                }
            }

            @Nullable
            public File b() {
                if (this.b) {
                    return null;
                }
                this.b = true;
                return a();
            }
        }
    }
}
