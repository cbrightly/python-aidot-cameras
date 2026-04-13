package org.greenrobot.eventbus;

/* compiled from: PendingPostQueue */
public final class j {
    private i a;
    private i b;

    j() {
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(i pendingPost) {
        if (pendingPost != null) {
            try {
                i iVar = this.b;
                if (iVar != null) {
                    iVar.d = pendingPost;
                    this.b = pendingPost;
                } else if (this.a == null) {
                    this.b = pendingPost;
                    this.a = pendingPost;
                } else {
                    throw new IllegalStateException("Head present, but no tail");
                }
                notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        } else {
            throw new NullPointerException("null cannot be enqueued");
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized i b() {
        i pendingPost;
        i iVar = this.a;
        pendingPost = iVar;
        if (iVar != null) {
            i iVar2 = iVar.d;
            this.a = iVar2;
            if (iVar2 == null) {
                this.b = null;
            }
        }
        return pendingPost;
    }

    /* access modifiers changed from: package-private */
    public synchronized i c(int maxMillisToWait) {
        if (this.a == null) {
            wait((long) maxMillisToWait);
        }
        return b();
    }
}
