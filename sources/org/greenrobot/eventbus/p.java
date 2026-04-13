package org.greenrobot.eventbus;

/* compiled from: Subscription */
public final class p {
    final Object a;
    final n b;
    volatile boolean c = true;

    p(Object subscriber, n subscriberMethod) {
        this.a = subscriber;
        this.b = subscriberMethod;
    }

    public boolean equals(Object other) {
        if (!(other instanceof p)) {
            return false;
        }
        p otherSubscription = (p) other;
        if (this.a != otherSubscription.a || !this.b.equals(otherSubscription.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.a.hashCode() + this.b.f.hashCode();
    }
}
