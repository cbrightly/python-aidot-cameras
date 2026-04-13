package org.greenrobot.eventbus;

/* compiled from: AsyncPoster */
public class a implements Runnable, k {
    private final j c = new j();
    private final c d;

    a(c eventBus) {
        this.d = eventBus;
    }

    public void a(p subscription, Object event) {
        this.c.a(i.a(subscription, event));
        this.d.d().execute(this);
    }

    public void run() {
        i pendingPost = this.c.b();
        if (pendingPost != null) {
            this.d.g(pendingPost);
            return;
        }
        throw new IllegalStateException("No pending post available");
    }
}
