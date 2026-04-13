package io.ktor.network.selector;

import java.nio.channels.SelectableChannel;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: Selectable.kt */
public class l implements k {
    @NotNull
    private static final AtomicIntegerFieldUpdater<l> c;
    public static final a d = new a((DefaultConstructorMarker) null);
    @NotNull
    private final g f = new g();
    private volatile int interestedOps;
    @NotNull
    private final SelectableChannel q;

    public l(@NotNull SelectableChannel channel) {
        k.f(channel, "channel");
        this.q = channel;
    }

    @NotNull
    public SelectableChannel getChannel() {
        return this.q;
    }

    @NotNull
    public g D() {
        return this.f;
    }

    public int X() {
        return this.interestedOps;
    }

    public void i(int i) {
        this.interestedOps = i;
    }

    public void Z(@NotNull j interest, boolean state) {
        int before;
        k.f(interest, "interest");
        int flag = interest.getFlag();
        do {
            before = X();
        } while (!c.compareAndSet(this, before, state ? before | flag : (~flag) & before));
    }

    public void close() {
        i(0);
        g this_$iv = D();
        for (j interest$iv : j.Companion.a()) {
            n $this$run$iv = this_$iv.l(interest$iv);
            if ($this$run$iv != null) {
                j jVar = interest$iv;
                ClosedChannelCancellationException closedChannelCancellationException = new ClosedChannelCancellationException();
                o.a aVar = o.Companion;
                $this$run$iv.resumeWith(o.m17constructorimpl(p.a(closedChannelCancellationException)));
            }
        }
    }

    public void dispose() {
        close();
    }

    /* compiled from: Selectable.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        AtomicIntegerFieldUpdater<l> newUpdater = AtomicIntegerFieldUpdater.newUpdater(l.class, "interestedOps");
        if (newUpdater == null) {
            k.n();
        }
        c = newUpdater;
    }
}
