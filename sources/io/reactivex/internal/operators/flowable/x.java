package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.functions.f;
import io.reactivex.internal.operators.flowable.FlowableRepeatWhen;
import io.reactivex.internal.operators.flowable.FlowableRetryWhen;
import io.reactivex.internal.subscriptions.c;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Publisher;
import org.reactivestreams.b;

/* compiled from: FlowableRetryWhen */
public final class x<T> extends a<T, T> {
    final f<? super e<Throwable>, ? extends org.reactivestreams.a<?>> f;

    public x(e<T> source, f<? super e<Throwable>, ? extends org.reactivestreams.a<?>> handler) {
        super(source);
        this.f = handler;
    }

    public void L(b<? super T> s) {
        SerializedSubscriber<T> z = new io.reactivex.subscribers.b<>(s);
        FlowableProcessor<Throwable> processor = io.reactivex.processors.e.Y(8).W();
        try {
            Publisher<?> when = (org.reactivestreams.a) io.reactivex.internal.functions.b.e(this.f.apply(processor), "handler returned a null Publisher");
            FlowableRepeatWhen.WhenReceiver<T, Throwable> receiver = new v<>(this.d);
            FlowableRetryWhen.RetryWhenSubscriber<T> subscriber = new a<>(z, processor, receiver);
            receiver.subscriber = subscriber;
            s.onSubscribe(subscriber);
            when.a(receiver);
            receiver.onNext(0);
        } catch (Throwable ex) {
            io.reactivex.exceptions.a.b(ex);
            c.error(ex, s);
        }
    }

    /* compiled from: FlowableRetryWhen */
    public static final class a<T> extends w<T, Throwable> {
        private static final long serialVersionUID = -2680129890138081029L;

        a(b<? super T> actual, io.reactivex.processors.a<Throwable> processor, org.reactivestreams.c receiver) {
            super(actual, processor, receiver);
        }

        public void onError(Throwable t) {
            again(t);
        }

        public void onComplete() {
            this.receiver.cancel();
            this.downstream.onComplete();
        }
    }
}
