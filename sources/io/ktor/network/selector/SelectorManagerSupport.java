package io.ktor.network.selector;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SelectorManagerSupport.kt */
public abstract class SelectorManagerSupport implements m {
    @NotNull
    private final SelectorProvider c;
    private int d;
    private int f;

    /* access modifiers changed from: protected */
    public abstract void x(@NotNull k kVar);

    /* compiled from: SelectorManagerSupport.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<Throwable, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
        }
    }

    public SelectorManagerSupport() {
        SelectorProvider provider = SelectorProvider.provider();
        k.b(provider, "SelectorProvider.provider()");
        this.c = provider;
    }

    @NotNull
    public final SelectorProvider a() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public final int s() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public final int r() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public final void z(int i) {
        this.f = i;
    }

    @Nullable
    public final Object g(@NotNull k selectable, @NotNull j interest, @NotNull d<? super x> $completion) {
        if ((selectable.X() & interest.getFlag()) != 0) {
            o oVar = new o(b.c($completion), 1);
            o cancellable$iv = oVar;
            oVar.f(a.INSTANCE);
            selectable.D().j(interest, oVar);
            if (!oVar.isCancelled()) {
                x(selectable);
            }
            Object t = cancellable$iv.t();
            if (t == c.d()) {
                h.c($completion);
            }
            return t == c.d() ? t : x.a;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    /* access modifiers changed from: protected */
    public final void v(@NotNull Set<SelectionKey> selectedKeys, @NotNull Set<? extends SelectionKey> keys) {
        k.f(selectedKeys, "selectedKeys");
        k.f(keys, "keys");
        int selectedCount = selectedKeys.size();
        this.d = keys.size() - selectedCount;
        this.f = 0;
        if (selectedCount > 0) {
            Iterator iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                u(iter.next());
                iter.remove();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void u(@NotNull SelectionKey key) {
        n $this$invokeForEachPresent;
        k.f(key, CacheEntity.KEY);
        try {
            int readyOps = key.readyOps();
            int interestOps = key.interestOps();
            k subj = t(key);
            if (subj == null) {
                key.cancel();
                this.f++;
                return;
            }
            x unit = x.a;
            g this_$iv = subj.D();
            int[] flags$iv = j.Companion.b();
            int length = flags$iv.length;
            for (int ordinal$iv = 0; ordinal$iv < length; ordinal$iv++) {
                if (!((flags$iv[ordinal$iv] & readyOps) == 0 || ($this$invokeForEachPresent = this_$iv.k(ordinal$iv)) == null)) {
                    o.a aVar = kotlin.o.Companion;
                    $this$invokeForEachPresent.resumeWith(kotlin.o.m17constructorimpl(unit));
                }
            }
            int newOps = (~readyOps) & interestOps;
            if (newOps != interestOps) {
                key.interestOps(newOps);
            }
            if (newOps != 0) {
                this.d++;
            }
        } catch (Throwable t) {
            key.cancel();
            this.f++;
            k subj2 = t(key);
            if (subj2 != null) {
                m(subj2, t);
                I(key, (k) null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void i(@NotNull Selector selector, @NotNull k s) {
        k.f(selector, "selector");
        k.f(s, "s");
        try {
            SelectableChannel channel = s.getChannel();
            SelectionKey key = channel.keyFor(selector);
            int ops = s.X();
            if (key == null) {
                if (ops != 0) {
                    channel.register(selector, ops, s);
                }
            } else if (key.interestOps() != ops) {
                key.interestOps(ops);
            }
            if (ops != 0) {
                this.d++;
            }
        } catch (Throwable t) {
            SelectionKey keyFor = s.getChannel().keyFor(selector);
            if (keyFor != null) {
                keyFor.cancel();
            }
            m(s, t);
        }
    }

    /* access modifiers changed from: protected */
    public final void m(@NotNull k attachment, @NotNull Throwable t) {
        k.f(attachment, "attachment");
        k.f(t, "t");
        g this_$iv = attachment.D();
        for (j interest$iv : j.Companion.a()) {
            n $this$run$iv = this_$iv.l(interest$iv);
            if ($this$run$iv != null) {
                j jVar = interest$iv;
                o.a aVar = kotlin.o.Companion;
                $this$run$iv.resumeWith(kotlin.o.m17constructorimpl(p.a(t)));
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void n(@NotNull Selector selector, @Nullable Throwable t) {
        k.f(selector, "selector");
        Throwable cause = t != null ? t : new ClosedSelectorCancellationException();
        Iterable<SelectionKey> $this$forEach$iv = selector.keys();
        k.b($this$forEach$iv, "selector.keys()");
        for (SelectionKey k : $this$forEach$iv) {
            try {
                k.b(k, "k");
                if (k.isValid()) {
                    k.interestOps(0);
                }
            } catch (CancelledKeyException e) {
            }
            Object attachment = k.attachment();
            if (!(attachment instanceof k)) {
                attachment = null;
            }
            k it = (k) attachment;
            if (it != null) {
                m(it, cause);
            }
            k.cancel();
        }
    }

    private final k t(@NotNull SelectionKey $this$subject) {
        Object attachment = $this$subject.attachment();
        if (!(attachment instanceof k)) {
            attachment = null;
        }
        return (k) attachment;
    }

    private final void I(@NotNull SelectionKey $this$subject, k newValue) {
        $this$subject.attach(newValue);
    }

    /* compiled from: SelectorManagerSupport.kt */
    public static final class ClosedSelectorCancellationException extends CancellationException {
        public ClosedSelectorCancellationException() {
            super("Closed selector");
        }
    }
}
