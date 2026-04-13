package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: ResourceRecycler */
public class w {
    private boolean a;
    private final Handler b = new Handler(Looper.getMainLooper(), new a());

    w() {
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(t<?> resource, boolean forceNextFrame) {
        if (!this.a) {
            if (!forceNextFrame) {
                this.a = true;
                resource.recycle();
                this.a = false;
            }
        }
        this.b.obtainMessage(1, resource).sendToTarget();
    }

    /* compiled from: ResourceRecycler */
    public static final class a implements Handler.Callback {
        a() {
        }

        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            ((t) message.obj).recycle();
            return true;
        }
    }
}
