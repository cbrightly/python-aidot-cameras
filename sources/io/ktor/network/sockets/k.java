package io.ktor.network.sockets;

import io.ktor.network.selector.l;
import io.ktor.network.selector.m;
import io.ktor.network.sockets.q;
import io.ktor.utils.io.f;
import io.ktor.utils.io.pool.d;
import io.ktor.utils.io.v;
import io.ktor.utils.io.y;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.g;
import kotlin.x;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NIOSocket.kt */
public abstract class k<S extends SelectableChannel & ByteChannel> extends l implements o0, c, e, o0 {
    @NotNull
    private final S a1;
    @Nullable
    private final d<ByteBuffer> a2;
    @NotNull
    private final z p0 = e2.b((z1) null, 1, (Object) null);
    @NotNull
    private final m p1;
    /* access modifiers changed from: private */
    public final q.e p2;
    private final AtomicBoolean x = new AtomicBoolean();
    private final AtomicReference<v> y = new AtomicReference<>();
    private final AtomicReference<y> z = new AtomicReference<>();

    /* compiled from: NIOSocket.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar) {
            super(1);
            this.this$0 = kVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.this$0.t();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k(@NotNull S channel, @NotNull m selector, @Nullable d<ByteBuffer> pool, @Nullable q.e socketOptions) {
        super(channel);
        kotlin.jvm.internal.k.f(channel, "channel");
        kotlin.jvm.internal.k.f(selector, "selector");
        this.a1 = channel;
        this.p1 = selector;
        this.a2 = pool;
        this.p2 = socketOptions;
    }

    @NotNull
    public S getChannel() {
        return this.a1;
    }

    @NotNull
    public final m I() {
        return this.p1;
    }

    @Nullable
    public final d<ByteBuffer> z() {
        return this.a2;
    }

    @NotNull
    /* renamed from: J */
    public z L0() {
        return this.p0;
    }

    @NotNull
    public g getCoroutineContext() {
        return L0();
    }

    /* compiled from: NIOSocket.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<y> {
        final /* synthetic */ f $channel;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(k kVar, f fVar) {
            super(0);
            this.this$0 = kVar;
            this.$channel = fVar;
        }

        @NotNull
        public final y invoke() {
            if (this.this$0.z() != null) {
                k kVar = this.this$0;
                k kVar2 = this.this$0;
                return h.b(kVar, this.$channel, (ReadableByteChannel) kVar.getChannel(), kVar2, kVar2.I(), this.this$0.z(), this.this$0.p2);
            }
            k kVar3 = this.this$0;
            k kVar4 = this.this$0;
            return h.a(kVar3, this.$channel, (ReadableByteChannel) kVar3.getChannel(), kVar4, kVar4.I(), this.this$0.p2);
        }
    }

    @NotNull
    public final y a(@NotNull f channel) {
        kotlin.jvm.internal.k.f(channel, "channel");
        return (y) s("reading", channel, this.z, new b(this, channel));
    }

    /* compiled from: NIOSocket.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<v> {
        final /* synthetic */ f $channel;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(k kVar, f fVar) {
            super(0);
            this.this$0 = kVar;
            this.$channel = fVar;
        }

        @NotNull
        public final v invoke() {
            k kVar = this.this$0;
            k kVar2 = this.this$0;
            return i.a(kVar, this.$channel, (WritableByteChannel) kVar.getChannel(), kVar2, kVar2.I(), this.this$0.p2);
        }
    }

    @NotNull
    public final v g(@NotNull f channel) {
        kotlin.jvm.internal.k.f(channel, "channel");
        return (v) s("writing", channel, this.y, new c(this, channel));
    }

    public void dispose() {
        close();
    }

    public void close() {
        io.ktor.utils.io.l channel;
        if (this.x.compareAndSet(false, true)) {
            v vVar = this.y.get();
            if (!(vVar == null || (channel = vVar.getChannel()) == null)) {
                io.ktor.utils.io.m.a(channel);
            }
            y yVar = this.z.get();
            if (yVar != null) {
                z1.a.a(yVar, (CancellationException) null, 1, (Object) null);
            }
            t();
        }
    }

    private final <J extends z1> J s(String name, f channel, AtomicReference<J> ref, kotlin.jvm.functions.a<? extends J> producer) {
        if (!this.x.get()) {
            z1 j = (z1) producer.invoke();
            if (!ref.compareAndSet((Object) null, j)) {
                IllegalStateException e = new IllegalStateException(name + " channel has been already set");
                z1.a.a(j, (CancellationException) null, 1, (Object) null);
                throw e;
            } else if (!this.x.get()) {
                channel.s(j);
                j.t(new a(this));
                return j;
            } else {
                ClosedChannelException e2 = new ClosedChannelException();
                z1.a.a(j, (CancellationException) null, 1, (Object) null);
                channel.d(e2);
                throw e2;
            }
        } else {
            ClosedChannelException e3 = new ClosedChannelException();
            channel.d(e3);
            throw e3;
        }
    }

    private final Throwable r() {
        try {
            ((ByteChannel) getChannel()).close();
            super.close();
            t = null;
        } catch (Throwable th) {
            t = th;
        }
        this.p1.c(this);
        return t;
    }

    /* access modifiers changed from: private */
    public final void t() {
        if (this.x.get() && v(this.y) && v(this.z)) {
            Throwable e1 = x(this.y);
            Throwable e2 = x(this.z);
            Throwable combined = u(u(e1, e2), r());
            z J = L0();
            if (combined == null) {
                J.complete();
            } else {
                J.a(combined);
            }
        }
    }

    private final Throwable u(Throwable e1, Throwable e2) {
        if (e1 == null) {
            return e2;
        }
        if (!(e2 == null || e1 == e2)) {
            kotlin.b.a(e1, e2);
        }
        return e1;
    }

    private final boolean v(@NotNull AtomicReference<? extends z1> $this$completedOrNotStarted) {
        z1 it = (z1) $this$completedOrNotStarted.get();
        return it == null || it.I();
    }

    private final Throwable x(@NotNull AtomicReference<? extends z1> $this$exception) {
        CancellationException n;
        z1 it = (z1) $this$exception.get();
        if (it == null) {
            return null;
        }
        if (!it.isCancelled()) {
            it = null;
        }
        if (it == null || (n = it.n()) == null) {
            return null;
        }
        return n.getCause();
    }
}
