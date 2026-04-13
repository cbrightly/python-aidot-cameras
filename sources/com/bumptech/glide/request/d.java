package com.bumptech.glide.request;

/* compiled from: RequestCoordinator */
public interface d {
    boolean a();

    boolean b(c cVar);

    boolean c(c cVar);

    void d(c cVar);

    void f(c cVar);

    d getRoot();

    boolean i(c cVar);

    /* compiled from: RequestCoordinator */
    public enum a {
        RUNNING(false),
        PAUSED(false),
        CLEARED(false),
        SUCCESS(true),
        FAILED(true);
        
        private final boolean isComplete;

        private a(boolean isComplete2) {
            this.isComplete = isComplete2;
        }

        /* access modifiers changed from: package-private */
        public boolean isComplete() {
            return this.isComplete;
        }
    }
}
