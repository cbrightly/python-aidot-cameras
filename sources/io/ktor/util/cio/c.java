package io.ktor.util.cio;

import io.ktor.utils.io.b0;
import io.ktor.utils.io.i;
import io.ktor.utils.io.q;
import io.ktor.utils.io.z;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.y;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.p0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileChannels.kt */
public final class c {
    public static /* synthetic */ i b(File file, long j, long j2, g gVar, int i, Object obj) {
        long j3;
        i0 i0Var;
        long j4 = (i & 1) != 0 ? 0 : j;
        if ((i & 2) != 0) {
            j3 = -1;
        } else {
            j3 = j2;
        }
        if ((i & 4) != 0) {
            i0Var = d1.b();
        } else {
            i0Var = gVar;
        }
        return a(file, j4, j3, i0Var);
    }

    @NotNull
    public static final i a(@NotNull File $this$readChannel, long start, long endInclusive, @NotNull g coroutineContext) {
        File file = $this$readChannel;
        g gVar = coroutineContext;
        k.f(file, "$this$readChannel");
        k.f(gVar, "coroutineContext");
        return q.e(p0.a(coroutineContext), new n0("file-reader").plus(gVar), false, new a(start, endInclusive, $this$readChannel.length(), new RandomAccessFile(file, "r"), (d) null)).getChannel();
    }

    @f(c = "io.ktor.util.cio.FileChannelsKt$readChannel$1", f = "FileChannels.kt", l = {45, 64}, m = "invokeSuspend")
    /* compiled from: FileChannels.kt */
    public static final class a extends l implements p<z, d<? super x>, Object> {
        final /* synthetic */ long $endInclusive;
        final /* synthetic */ RandomAccessFile $file;
        final /* synthetic */ long $fileLength;
        final /* synthetic */ long $start;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        private z p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(long j, long j2, long j3, RandomAccessFile randomAccessFile, d dVar) {
            super(2, dVar);
            this.$start = j;
            this.$endInclusive = j2;
            this.$fileLength = j3;
            this.$file = randomAccessFile;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            k.f(dVar, "completion");
            a aVar = new a(this.$start, this.$endInclusive, this.$fileLength, this.$file, dVar);
            z zVar = (z) obj;
            aVar.p$ = (z) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((a) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: java.io.RandomAccessFile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v20, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: java.io.Closeable} */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
            r0 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f9, code lost:
            r2.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ff, code lost:
            return r0;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r17) {
            /*
                r16 = this;
                r1 = r16
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r1.label
                r3 = 0
                r4 = 0
                switch(r2) {
                    case 0: goto L_0x005e;
                    case 1: goto L_0x003b;
                    case 2: goto L_0x0015;
                    default: goto L_0x000d;
                }
            L_0x000d:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L_0x0015:
                r0 = r3
                r2 = r4
                r5 = r3
                r6 = r4
                r7 = r4
                r8 = r4
                java.lang.Object r9 = r1.L$4
                r7 = r9
                kotlin.jvm.internal.y r7 = (kotlin.jvm.internal.y) r7
                java.lang.Object r9 = r1.L$3
                r6 = r9
                java.nio.channels.FileChannel r6 = (java.nio.channels.FileChannel) r6
                java.lang.Object r9 = r1.L$2
                r4 = r9
                java.io.RandomAccessFile r4 = (java.io.RandomAccessFile) r4
                int r9 = r1.I$0
                java.lang.Object r0 = r1.L$1
                r2 = r0
                java.io.Closeable r2 = (java.io.Closeable) r2
                java.lang.Object r0 = r1.L$0
                r8 = r0
                io.ktor.utils.io.z r8 = (io.ktor.utils.io.z) r8
                kotlin.p.b(r17)     // Catch:{ all -> 0x005b }
                goto L_0x00f6
            L_0x003b:
                r0 = r3
                r2 = r4
                r5 = r3
                r6 = r4
                r7 = r4
                java.lang.Object r8 = r1.L$3
                r6 = r8
                java.nio.channels.FileChannel r6 = (java.nio.channels.FileChannel) r6
                java.lang.Object r8 = r1.L$2
                r4 = r8
                java.io.RandomAccessFile r4 = (java.io.RandomAccessFile) r4
                int r9 = r1.I$0
                java.lang.Object r0 = r1.L$1
                r2 = r0
                java.io.Closeable r2 = (java.io.Closeable) r2
                java.lang.Object r0 = r1.L$0
                r8 = r0
                io.ktor.utils.io.z r8 = (io.ktor.utils.io.z) r8
                kotlin.p.b(r17)     // Catch:{ all -> 0x005b }
                goto L_0x00c5
            L_0x005b:
                r0 = move-exception
                goto L_0x0104
            L_0x005e:
                kotlin.p.b(r17)
                io.ktor.utils.io.z r8 = r1.p$
                long r5 = r1.$start
                r9 = 0
                int r2 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
                r5 = 1
                if (r2 < 0) goto L_0x006e
                r2 = r5
                goto L_0x006f
            L_0x006e:
                r2 = r3
            L_0x006f:
                if (r2 == 0) goto L_0x0147
                long r6 = r1.$endInclusive
                long r11 = r1.$fileLength
                r13 = 1
                long r11 = r11 - r13
                int r2 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
                if (r2 > 0) goto L_0x007d
                r3 = r5
            L_0x007d:
                if (r3 == 0) goto L_0x011b
                java.io.RandomAccessFile r2 = r1.$file
                r3 = r2
                r6 = 0
                r7 = 0
                r11 = r3
                r12 = 0
                java.nio.channels.FileChannel r2 = r2.getChannel()     // Catch:{ all -> 0x0100 }
                java.lang.String r13 = "file.channel"
                kotlin.jvm.internal.k.b(r2, r13)     // Catch:{ all -> 0x0100 }
                long r13 = r1.$start     // Catch:{ all -> 0x0100 }
                int r9 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
                if (r9 <= 0) goto L_0x0099
                r2.position(r13)     // Catch:{ all -> 0x0100 }
            L_0x0099:
                long r9 = r1.$endInclusive     // Catch:{ all -> 0x0100 }
                r13 = -1
                int r9 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
                if (r9 != 0) goto L_0x00c6
                io.ktor.utils.io.l r9 = r8.getChannel()     // Catch:{ all -> 0x0100 }
                io.ktor.util.cio.c$a$a r10 = new io.ktor.util.cio.c$a$a     // Catch:{ all -> 0x0100 }
                r10.<init>(r2, r4, r1, r8)     // Catch:{ all -> 0x0100 }
                r1.L$0 = r8     // Catch:{ all -> 0x0100 }
                r1.L$1 = r3     // Catch:{ all -> 0x0100 }
                r1.I$0 = r7     // Catch:{ all -> 0x0100 }
                r1.L$2 = r11     // Catch:{ all -> 0x0100 }
                r1.L$3 = r2     // Catch:{ all -> 0x0100 }
                r1.label = r5     // Catch:{ all -> 0x0100 }
                java.lang.Object r4 = r9.n(r10, r1)     // Catch:{ all -> 0x0100 }
                if (r4 != r0) goto L_0x00be
                return r0
            L_0x00be:
                r9 = r7
                r4 = r11
                r5 = r12
                r15 = r6
                r6 = r2
                r2 = r3
                r3 = r15
            L_0x00c5:
                goto L_0x00f7
            L_0x00c6:
                kotlin.jvm.internal.y r4 = new kotlin.jvm.internal.y     // Catch:{ all -> 0x0100 }
                r4.<init>()     // Catch:{ all -> 0x0100 }
                long r9 = r1.$start     // Catch:{ all -> 0x0100 }
                r4.element = r9     // Catch:{ all -> 0x0100 }
                io.ktor.utils.io.l r5 = r8.getChannel()     // Catch:{ all -> 0x0100 }
                io.ktor.util.cio.c$a$b r9 = new io.ktor.util.cio.c$a$b     // Catch:{ all -> 0x0100 }
                r9.<init>(r4, r2, r1, r8)     // Catch:{ all -> 0x0100 }
                r1.L$0 = r8     // Catch:{ all -> 0x0100 }
                r1.L$1 = r3     // Catch:{ all -> 0x0100 }
                r1.I$0 = r7     // Catch:{ all -> 0x0100 }
                r1.L$2 = r11     // Catch:{ all -> 0x0100 }
                r1.L$3 = r2     // Catch:{ all -> 0x0100 }
                r1.L$4 = r4     // Catch:{ all -> 0x0100 }
                r10 = 2
                r1.label = r10     // Catch:{ all -> 0x0100 }
                java.lang.Object r5 = r5.z(r9, r1)     // Catch:{ all -> 0x0100 }
                if (r5 != r0) goto L_0x00ee
                return r0
            L_0x00ee:
                r9 = r7
                r5 = r12
                r7 = r4
                r4 = r11
                r15 = r6
                r6 = r2
                r2 = r3
                r3 = r15
            L_0x00f6:
            L_0x00f7:
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x005b }
                r2.close()
                return r0
            L_0x0100:
                r0 = move-exception
                r2 = r3
                r3 = r6
                r9 = r7
            L_0x0104:
                r4 = r0
                r5 = 1
                r2.close()     // Catch:{ all -> 0x010b }
                goto L_0x0111
            L_0x010b:
                r0 = move-exception
                r6 = r0
                r0 = r6
                io.ktor.utils.io.core.t.a(r4, r0)     // Catch:{ all -> 0x0114 }
            L_0x0111:
                throw r4     // Catch:{ all -> 0x0114 }
            L_0x0114:
                r0 = move-exception
                if (r5 != 0) goto L_0x011a
                r2.close()
            L_0x011a:
                throw r0
            L_0x011b:
                r0 = 0
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "endInclusive points to the position out of the file: file size = "
                r2.append(r3)
                java.io.RandomAccessFile r3 = r1.$file
                long r3 = r3.length()
                r2.append(r3)
                java.lang.String r3 = ", endInclusive = "
                r2.append(r3)
                long r3 = r1.$endInclusive
                r2.append(r3)
                java.lang.String r0 = r2.toString()
                java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                throw r2
            L_0x0147:
                r0 = 0
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "start position shouldn't be negative but it is "
                r2.append(r3)
                long r3 = r1.$start
                r2.append(r3)
                java.lang.String r0 = r2.toString()
                java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.util.cio.c.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* renamed from: io.ktor.util.cio.c$a$a  reason: collision with other inner class name */
        /* compiled from: FileChannels.kt */
        public static final class C0274a extends l implements p<b0, d<? super x>, Object> {
            final /* synthetic */ FileChannel $fileChannel;
            final /* synthetic */ z $this_writer$inlined;
            Object L$0;
            Object L$1;
            int label;
            private b0 p$;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0274a(FileChannel fileChannel, d dVar, a aVar, z zVar) {
                super(2, dVar);
                this.$fileChannel = fileChannel;
                this.this$0 = aVar;
                this.$this_writer$inlined = zVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                k.f(dVar, "completion");
                C0274a aVar = new C0274a(this.$fileChannel, dVar, this.this$0, this.$this_writer$inlined);
                b0 b0Var = (b0) obj;
                aVar.p$ = (b0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0274a) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            /* Debug info: failed to restart local var, previous not found, register: 6 */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: io.ktor.utils.io.b0} */
            /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
                	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
                	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
                */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Removed duplicated region for block: B:13:0x004a  */
            /* JADX WARNING: Removed duplicated region for block: B:17:0x0056 A[LOOP:0: B:6:0x0028->B:17:0x0056, LOOP_END] */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0053 A[SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0030 A[SYNTHETIC] */
            @org.jetbrains.annotations.Nullable
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
                /*
                    r6 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r6.label
                    switch(r1) {
                        case 0: goto L_0x0022;
                        case 1: goto L_0x0011;
                        default: goto L_0x0009;
                    }
                L_0x0009:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0011:
                    r1 = 0
                    r2 = r1
                    java.lang.Object r3 = r6.L$1
                    r2 = r3
                    io.ktor.utils.io.core.a0 r2 = (io.ktor.utils.io.core.a0) r2
                    java.lang.Object r3 = r6.L$0
                    r1 = r3
                    io.ktor.utils.io.b0 r1 = (io.ktor.utils.io.b0) r1
                    kotlin.p.b(r7)
                    r3 = r6
                    goto L_0x0048
                L_0x0022:
                    kotlin.p.b(r7)
                    io.ktor.utils.io.b0 r1 = r6.p$
                    r2 = r6
                L_0x0028:
                    r3 = 1
                    io.ktor.utils.io.core.a0 r4 = r1.a(r3)
                    if (r4 != 0) goto L_0x004a
                    io.ktor.utils.io.z r5 = r2.$this_writer$inlined
                    io.ktor.utils.io.l r5 = r5.getChannel()
                    r5.flush()
                    r2.L$0 = r1
                    r2.L$1 = r4
                    r2.label = r3
                    java.lang.Object r3 = r1.c(r3, r2)
                    if (r3 != r0) goto L_0x0046
                    return r0
                L_0x0046:
                    r3 = r2
                    r2 = r4
                L_0x0048:
                    r2 = r3
                    goto L_0x0028
                L_0x004a:
                    java.nio.channels.FileChannel r3 = r2.$fileChannel
                    int r3 = io.ktor.util.e.a(r3, r4)
                    r5 = -1
                    if (r3 != r5) goto L_0x0056
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0056:
                    r1.b(r3)
                    goto L_0x0028
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.util.cio.c.a.C0274a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* compiled from: FileChannels.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<ByteBuffer, Boolean> {
            final /* synthetic */ FileChannel $fileChannel;
            final /* synthetic */ y $position;
            final /* synthetic */ z $this_writer$inlined;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(y yVar, FileChannel fileChannel, a aVar, z zVar) {
                super(1);
                this.$position = yVar;
                this.$fileChannel = fileChannel;
                this.this$0 = aVar;
                this.$this_writer$inlined = zVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((ByteBuffer) obj));
            }

            public final boolean invoke(@NotNull ByteBuffer buffer) {
                int i;
                k.f(buffer, "buffer");
                long fileRemaining = (this.this$0.$endInclusive - this.$position.element) + 1;
                if (fileRemaining < ((long) buffer.remaining())) {
                    int l = buffer.limit();
                    buffer.limit(buffer.position() + ((int) fileRemaining));
                    i = this.$fileChannel.read(buffer);
                    buffer.limit(l);
                } else {
                    i = this.$fileChannel.read(buffer);
                }
                int rc = i;
                if (rc > 0) {
                    this.$position.element += (long) rc;
                }
                return rc != -1 && this.$position.element <= this.this$0.$endInclusive;
            }
        }
    }
}
