package org.greenrobot.eventbus;

/* compiled from: SubscriberExceptionEvent */
public final class m {
    public final c a;
    public final Throwable b;
    public final Object c;
    public final Object d;

    public m(c eventBus, Throwable throwable, Object causingEvent, Object causingSubscriber) {
        this.a = eventBus;
        this.b = throwable;
        this.c = causingEvent;
        this.d = causingSubscriber;
    }
}
