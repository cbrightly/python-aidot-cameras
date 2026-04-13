package io.reactivex.subscribers;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.disposables.b;
import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.internal.util.d;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.c;

/* compiled from: DisposableSubscriber */
public abstract class a<T> implements h<T>, b {
    final AtomicReference<c> upstream = new AtomicReference<>();

    public final void onSubscribe(c s) {
        if (d.c(this.upstream, s, getClass())) {
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        this.upstream.get().request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    /* access modifiers changed from: protected */
    public final void request(long n) {
        this.upstream.get().request(n);
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        dispose();
    }

    public final boolean isDisposed() {
        return this.upstream.get() == f.CANCELLED;
    }

    public final void dispose() {
        f.cancel(this.upstream);
    }
}
