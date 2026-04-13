package io.ktor.utils.io.core;

import io.ktor.utils.io.bits.a;
import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.pool.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferFactory.kt */
public final class u extends b<a0> {
    private final a a1;
    private final int p0;

    public u() {
        this(0, 0, (a) null, 7, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public u(int bufferSize, int capacity, @NotNull a allocator) {
        super(capacity);
        k.f(allocator, "allocator");
        this.p0 = bufferSize;
        this.a1 = allocator;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ u(int i, int i2, a aVar, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 4096 : i, (i3 & 2) != 0 ? 1000 : i2, (i3 & 4) != 0 ? io.ktor.utils.io.bits.b.a : aVar);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: u */
    public a0 l() {
        return new a0(this.a1.b(this.p0), (io.ktor.utils.io.core.internal.a) null, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: protected */
    /* renamed from: t */
    public void i(@NotNull a0 instance) {
        k.f(instance, "instance");
        this.a1.a(instance.n());
        super.i(instance);
        instance.h1();
    }

    /* access modifiers changed from: protected */
    /* renamed from: v */
    public void r(@NotNull a0 instance) {
        k.f(instance, "instance");
        super.r(instance);
        a0.c cVar = a0.I4;
        if (instance != cVar.a()) {
            boolean z = true;
            if (instance != cVar.a()) {
                if (instance != c.c.a()) {
                    if (instance != io.ktor.utils.io.core.internal.a.z4.a()) {
                        if (instance.d1() == 0) {
                            if (instance.b1() == null) {
                                if (instance.c1() != null) {
                                    z = false;
                                }
                                if (!z) {
                                    throw new IllegalStateException("Recycled instance shouldn't be a view or another buffer.".toString());
                                }
                                return;
                            }
                            throw new IllegalStateException("Recycled instance shouldn't be a part of a chain.".toString());
                        }
                        throw new IllegalStateException("Unable to clear buffer: it is still in use.".toString());
                    }
                    throw new IllegalStateException("Empty instance couldn't be recycled".toString());
                }
                throw new IllegalStateException("Empty instance couldn't be recycled".toString());
            }
            throw new IllegalStateException("Empty instance couldn't be recycled".toString());
        }
        throw new IllegalStateException("IoBuffer.Empty couldn't be recycled".toString());
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: s */
    public a0 g(@NotNull a0 instance) {
        k.f(instance, "instance");
        Object g = super.g(instance);
        a0 $this$apply = (a0) g;
        $this$apply.i1();
        $this$apply.E();
        return (a0) g;
    }
}
