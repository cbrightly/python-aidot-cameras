package androidx.camera.core;

import androidx.annotation.GuardedBy;

public final class SingleCloseImageProxy extends ForwardingImageProxy {
    @GuardedBy("this")
    private boolean mClosed = false;

    SingleCloseImageProxy(ImageProxy image) {
        super(image);
    }

    public synchronized void close() {
        if (!this.mClosed) {
            this.mClosed = true;
            super.close();
        }
    }
}
