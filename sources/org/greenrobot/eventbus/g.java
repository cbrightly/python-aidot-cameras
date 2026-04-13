package org.greenrobot.eventbus;

import android.os.Looper;

/* compiled from: MainThreadSupport */
public interface g {
    k a(c cVar);

    boolean b();

    /* compiled from: MainThreadSupport */
    public static class a implements g {
        private final Looper a;

        public a(Looper looper) {
            this.a = looper;
        }

        public boolean b() {
            return this.a == Looper.myLooper();
        }

        public k a(c eventBus) {
            return new e(eventBus, this.a, 10);
        }
    }
}
