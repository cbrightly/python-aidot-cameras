package io.ktor.utils.io.internal;

import io.ktor.utils.io.a;
import io.ktor.utils.io.b0;
import io.ktor.utils.io.core.a0;
import java.nio.ByteBuffer;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WriteSessionImpl.kt */
public final class o implements b0 {
    private int a;
    private io.ktor.utils.io.a b;
    private ByteBuffer c;
    private a0 d;
    private k e = this.b.f0().b;

    @f(c = "io.ktor.utils.io.internal.WriteSessionImpl", f = "WriteSessionImpl.kt", l = {90}, m = "tryAwaitJoinSwitch")
    /* compiled from: WriteSessionImpl.kt */
    public static final class a extends d {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ o this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(o oVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = oVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.g(0, (a.c) null, this);
        }
    }

    public o(@NotNull io.ktor.utils.io.a channel) {
        k.f(channel, "channel");
        this.b = channel.T0();
        a0.c cVar = a0.I4;
        this.c = cVar.a().n();
        this.d = cVar.a();
    }

    public final void d() {
        io.ktor.utils.io.a T0 = this.b.T0();
        this.b = T0;
        ByteBuffer g1 = T0.g1();
        if (g1 != null) {
            this.c = g1;
            a0 a0Var = new a0(this.b.f0().a);
            this.d = a0Var;
            a0Var.n1(this.c);
            this.e = this.b.f0().b;
        }
    }

    public final void e() {
        int i = this.a;
        if (i > 0) {
            this.e.a(i);
            this.a = 0;
        }
        this.b.W0();
        this.b.l1();
    }

    @Nullable
    public a0 a(int min) {
        int m = this.a + this.e.m(0);
        this.a = m;
        if (m < min) {
            return null;
        }
        this.b.u0(this.c, m);
        if (this.c.remaining() < min) {
            return null;
        }
        this.d.n1(this.c);
        return this.d;
    }

    public void b(int n) {
        int i;
        if (n < 0 || n > (i = this.a)) {
            h(n);
            throw null;
        }
        this.a = i - n;
        this.b.c0(this.c, this.e, n);
    }

    private final Void h(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Written bytes count shouldn't be negative: " + n);
        }
        throw new IllegalStateException("Unable to mark " + n + " bytes as written: only " + this.a + " were pre-locked.");
    }

    @Nullable
    public Object c(int n, @NotNull kotlin.coroutines.d<? super x> $completion) {
        a.c joining = this.b.l0();
        if (joining != null) {
            Object g = g(n, joining, $completion);
            return g == c.d() ? g : x.a;
        }
        int i = this.a;
        if (i >= n) {
            return x.a;
        }
        if (i > 0) {
            this.e.a(i);
            this.a = 0;
        }
        Object n1 = this.b.n1(n, $completion);
        return n1 == c.d() ? n1 : x.a;
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object g(int r6, @org.jetbrains.annotations.NotNull io.ktor.utils.io.a.c r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof io.ktor.utils.io.internal.o.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.utils.io.internal.o$a r0 = (io.ktor.utils.io.internal.o.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.internal.o$a r0 = new io.ktor.utils.io.internal.o$a
            r0.<init>(r5, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003a;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$1
            r7 = r2
            io.ktor.utils.io.a$c r7 = (io.ktor.utils.io.a.c) r7
            int r6 = r0.I$0
            java.lang.Object r2 = r0.L$0
            io.ktor.utils.io.internal.o r2 = (io.ktor.utils.io.internal.o) r2
            kotlin.p.b(r1)
            goto L_0x006a
        L_0x003a:
            kotlin.p.b(r1)
            int r3 = r5.a
            if (r3 <= 0) goto L_0x0049
            io.ktor.utils.io.internal.k r4 = r5.e
            r4.a(r3)
            r3 = 0
            r5.a = r3
        L_0x0049:
            r5.f()
            io.ktor.utils.io.a r3 = r5.b
            r3.W0()
            io.ktor.utils.io.a r3 = r5.b
            r3.l1()
            io.ktor.utils.io.a r3 = r5.b
            r0.L$0 = r5
            r0.I$0 = r6
            r0.L$1 = r7
            r4 = 1
            r0.label = r4
            java.lang.Object r3 = r3.n1(r6, r0)
            if (r3 != r2) goto L_0x0069
            return r2
        L_0x0069:
            r2 = r5
        L_0x006a:
            io.ktor.utils.io.a r3 = r2.b
            io.ktor.utils.io.a r3 = r3.T0()
            r2.b = r3
            java.nio.ByteBuffer r3 = r3.g1()
            if (r3 == 0) goto L_0x0099
            r2.c = r3
            io.ktor.utils.io.core.a0 r3 = new io.ktor.utils.io.core.a0
            io.ktor.utils.io.a r4 = r2.b
            io.ktor.utils.io.internal.f r4 = r4.f0()
            java.nio.ByteBuffer r4 = r4.a
            r3.<init>(r4)
            r2.d = r3
            java.nio.ByteBuffer r4 = r2.c
            r3.n1(r4)
            io.ktor.utils.io.a r3 = r2.b
            io.ktor.utils.io.internal.f r3 = r3.f0()
            io.ktor.utils.io.internal.k r3 = r3.b
            r2.e = r3
        L_0x0099:
            kotlin.x r3 = kotlin.x.a
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.o.g(int, io.ktor.utils.io.a$c, kotlin.coroutines.d):java.lang.Object");
    }

    public void f() {
        this.b.flush();
    }
}
