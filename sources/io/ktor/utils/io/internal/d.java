package io.ktor.utils.io.internal;

import io.ktor.utils.io.internal.f;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ObjectPool.kt */
public final class d {
    private static final int a = n.a("BufferSize", 4096);
    private static final int b;
    private static final int c;
    @NotNull
    private static final io.ktor.utils.io.pool.d<ByteBuffer> d;
    @NotNull
    private static final io.ktor.utils.io.pool.d<f.c> e;
    @NotNull
    private static final io.ktor.utils.io.pool.d<f.c> f = new a();

    static {
        int a2 = n.a("BufferPoolSize", 2048);
        b = a2;
        int a3 = n.a("BufferObjectPoolSize", 1024);
        c = a3;
        d = new c(a2);
        e = new b(a3);
    }

    public static final int a() {
        return a;
    }

    /* compiled from: ObjectPool.kt */
    public static final class c extends io.ktor.utils.io.pool.b<ByteBuffer> {
        c(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: t */
        public ByteBuffer l() {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(d.a());
            k.b(allocateDirect, "ByteBuffer.allocateDirect(BUFFER_SIZE)");
            return allocateDirect;
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: s */
        public ByteBuffer g(@NotNull ByteBuffer instance) {
            k.f(instance, "instance");
            instance.clear();
            return instance;
        }

        /* access modifiers changed from: protected */
        /* renamed from: u */
        public void r(@NotNull ByteBuffer instance) {
            k.f(instance, "instance");
            if (!(instance.capacity() == d.a())) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }
    }

    @NotNull
    public static final io.ktor.utils.io.pool.d<ByteBuffer> c() {
        return d;
    }

    /* compiled from: ObjectPool.kt */
    public static final class b extends io.ktor.utils.io.pool.b<f.c> {
        b(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: t */
        public f.c l() {
            return new f.c(d.c().p0(), 0, 2, (DefaultConstructorMarker) null);
        }

        /* access modifiers changed from: protected */
        /* renamed from: s */
        public void i(@NotNull f.c instance) {
            k.f(instance, "instance");
            d.c().N0(instance.a);
        }
    }

    @NotNull
    public static final io.ktor.utils.io.pool.d<f.c> b() {
        return e;
    }

    /* compiled from: ObjectPool.kt */
    public static final class a extends io.ktor.utils.io.pool.c<f.c> {
        a() {
        }

        @NotNull
        /* renamed from: a */
        public f.c p0() {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(d.a());
            k.b(allocateDirect, "ByteBuffer.allocateDirect(BUFFER_SIZE)");
            return new f.c(allocateDirect, 0, 2, (DefaultConstructorMarker) null);
        }
    }
}
