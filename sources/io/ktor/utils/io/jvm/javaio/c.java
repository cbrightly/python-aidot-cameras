package io.ktor.utils.io.jvm.javaio;

import io.ktor.utils.io.i;
import java.io.InputStream;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Blocking.kt */
public final class c extends InputStream {
    private final a c;
    private byte[] d;
    /* access modifiers changed from: private */
    public final i f;

    /* compiled from: Blocking.kt */
    public static final class a extends a {
        final /* synthetic */ c g;
        final /* synthetic */ z1 h;

        @f(c = "io.ktor.utils.io.jvm.javaio.InputAdapter$loop$1", f = "Blocking.kt", l = {282, 31}, m = "loop")
        /* renamed from: io.ktor.utils.io.jvm.javaio.c$a$a  reason: collision with other inner class name */
        /* compiled from: Blocking.kt */
        public static final class C0292a extends d {
            int I$0;
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0292a(a aVar, kotlin.coroutines.d dVar) {
                super(dVar);
                this.this$0 = aVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.g(this);
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(c $outer, z1 $captured_local_variable$1, z1 $super_call_param$2) {
            super($super_call_param$2);
            this.g = $outer;
            this.h = $captured_local_variable$1;
        }

        /* Debug info: failed to restart local var, previous not found, register: 18 */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0049  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x005e  */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x00f3  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00f9  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object g(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r19) {
            /*
                r18 = this;
                r0 = r19
                boolean r1 = r0 instanceof io.ktor.utils.io.jvm.javaio.c.a.C0292a
                if (r1 == 0) goto L_0x0017
                r1 = r0
                io.ktor.utils.io.jvm.javaio.c$a$a r1 = (io.ktor.utils.io.jvm.javaio.c.a.C0292a) r1
                int r2 = r1.label
                r3 = -2147483648(0xffffffff80000000, float:-0.0)
                r4 = r2 & r3
                if (r4 == 0) goto L_0x0017
                int r2 = r2 - r3
                r1.label = r2
                r2 = r18
                goto L_0x001e
            L_0x0017:
                io.ktor.utils.io.jvm.javaio.c$a$a r1 = new io.ktor.utils.io.jvm.javaio.c$a$a
                r2 = r18
                r1.<init>(r2, r0)
            L_0x001e:
                java.lang.Object r3 = r1.result
                java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
                int r5 = r1.label
                r6 = 0
                r7 = 0
                switch(r5) {
                    case 0: goto L_0x005e;
                    case 1: goto L_0x0049;
                    case 2: goto L_0x0033;
                    default: goto L_0x002b;
                }
            L_0x002b:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
                r1.<init>(r3)
                throw r1
            L_0x0033:
                r5 = r6
                r6 = r7
                java.lang.Object r8 = r1.L$1
                r6 = r8
                byte[] r6 = (byte[]) r6
                int r5 = r1.I$0
                java.lang.Object r8 = r1.L$0
                io.ktor.utils.io.jvm.javaio.c$a r8 = (io.ktor.utils.io.jvm.javaio.c.a) r8
                kotlin.p.b(r3)
                r7 = r6
                r6 = r5
                r5 = r4
                r4 = r3
                goto L_0x00ea
            L_0x0049:
                r5 = r7
                r8 = r6
                java.lang.Object r9 = r1.L$1
                r5 = r9
                io.ktor.utils.io.jvm.javaio.a r5 = (io.ktor.utils.io.jvm.javaio.a) r5
                int r6 = r1.I$0
                java.lang.Object r9 = r1.L$0
                io.ktor.utils.io.jvm.javaio.c$a r9 = (io.ktor.utils.io.jvm.javaio.c.a) r9
                kotlin.p.b(r3)
                r5 = r4
                r8 = r9
                r4 = r3
                goto L_0x00c2
            L_0x005e:
                kotlin.p.b(r3)
                r5 = 0
                r6 = r2
            L_0x0063:
                r8 = r6
                r9 = 0
                r8.result = r5
                r1.L$0 = r6
                r1.I$0 = r5
                r1.L$1 = r8
                r10 = 1
                r1.label = r10
                r10 = r1
                r11 = 0
                r12 = r7
                r13 = r8
                r14 = 0
            L_0x0076:
                java.lang.Object r15 = r13.state
                r19 = r15
                r16 = 0
                r7 = r19
                r19 = r0
                boolean r0 = r7 instanceof java.lang.Thread
                if (r0 == 0) goto L_0x008e
                r0 = r7
                java.lang.Thread r0 = (java.lang.Thread) r0
                kotlin.coroutines.d r12 = kotlin.coroutines.intrinsics.b.c(r10)
                goto L_0x009d
            L_0x008e:
                boolean r0 = kotlin.jvm.internal.k.a(r7, r8)
                if (r0 == 0) goto L_0x0111
                kotlin.coroutines.d r0 = kotlin.coroutines.intrinsics.b.c(r10)
                r17 = r12
                r12 = r0
                r0 = r17
            L_0x009d:
                r7 = r12
                java.util.concurrent.atomic.AtomicReferenceFieldUpdater r12 = io.ktor.utils.io.jvm.javaio.a.a
                boolean r12 = r12.compareAndSet(r13, r15, r7)
                if (r12 == 0) goto L_0x010b
                if (r0 == 0) goto L_0x00ab
                java.util.concurrent.locks.LockSupport.unpark(r0)
            L_0x00ab:
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                java.lang.Object r7 = kotlin.coroutines.intrinsics.c.d()
                if (r0 != r7) goto L_0x00b8
                kotlin.coroutines.jvm.internal.h.c(r1)
            L_0x00b8:
                if (r0 != r4) goto L_0x00bb
                return r4
            L_0x00bb:
                r8 = r6
                r6 = r5
                r5 = r4
                r4 = r3
                r3 = r0
                r0 = r19
            L_0x00c2:
                if (r3 == 0) goto L_0x0103
                byte[] r3 = (byte[]) r3
                io.ktor.utils.io.jvm.javaio.c r7 = r8.g
                io.ktor.utils.io.i r7 = r7.f
                int r9 = r8.e()
                int r10 = r8.d()
                r1.L$0 = r8
                r1.I$0 = r6
                r1.L$1 = r3
                r11 = 2
                r1.label = r11
                java.lang.Object r7 = r7.j(r3, r9, r10, r1)
                if (r7 != r5) goto L_0x00e5
                return r5
            L_0x00e5:
                r17 = r7
                r7 = r3
                r3 = r17
            L_0x00ea:
                java.lang.Number r3 = (java.lang.Number) r3
                int r3 = r3.intValue()
                r6 = -1
                if (r3 != r6) goto L_0x00f9
                r8.c(r3)
                kotlin.x r5 = kotlin.x.a
                return r5
            L_0x00f9:
                r6 = r8
                r7 = 0
                r17 = r5
                r5 = r3
                r3 = r4
                r4 = r17
                goto L_0x0063
            L_0x0103:
                kotlin.TypeCastException r3 = new kotlin.TypeCastException
                java.lang.String r5 = "null cannot be cast to non-null type kotlin.ByteArray"
                r3.<init>(r5)
                throw r3
            L_0x010b:
                r12 = r0
                r7 = 0
                r0 = r19
                goto L_0x0076
            L_0x0111:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r4 = "Already suspended or in finished state"
                r0.<init>(r4)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.jvm.javaio.c.a.g(kotlin.coroutines.d):java.lang.Object");
        }
    }

    public c(@Nullable z1 parent, @NotNull i channel) {
        k.f(channel, "channel");
        this.f = channel;
        this.c = new a(this, parent, parent);
    }

    public int available() {
        return this.f.e();
    }

    public synchronized int read() {
        byte[] buffer = this.d;
        if (buffer == null) {
            buffer = new byte[1];
            this.d = buffer;
        }
        int rc = this.c.k(buffer, 0, 1);
        if (rc == -1) {
            return -1;
        }
        if (rc == 1) {
            return buffer[0] & 255;
        }
        throw new IllegalStateException(("rc should be 1 or -1 but got " + rc).toString());
    }

    public synchronized int read(@Nullable byte[] b, int off, int len) {
        a aVar;
        aVar = this.c;
        if (b == null) {
            k.n();
        }
        return aVar.k(b, off, len);
    }

    public synchronized void close() {
        super.close();
        io.ktor.utils.io.k.a(this.f);
        this.c.i();
    }
}
