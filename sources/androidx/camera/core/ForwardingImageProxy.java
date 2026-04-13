package androidx.camera.core;

import android.graphics.Rect;
import android.media.Image;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;
import java.util.HashSet;
import java.util.Set;

public abstract class ForwardingImageProxy implements ImageProxy {
    @GuardedBy("this")
    protected final ImageProxy mImage;
    @GuardedBy("this")
    private final Set<OnImageCloseListener> mOnImageCloseListeners = new HashSet();

    public interface OnImageCloseListener {
        void onImageClose(ImageProxy imageProxy);
    }

    protected ForwardingImageProxy(ImageProxy image) {
        this.mImage = image;
    }

    public void close() {
        synchronized (this) {
            this.mImage.close();
        }
        notifyOnImageCloseListeners();
    }

    @NonNull
    public synchronized Rect getCropRect() {
        return this.mImage.getCropRect();
    }

    public synchronized void setCropRect(@Nullable Rect rect) {
        this.mImage.setCropRect(rect);
    }

    public synchronized int getFormat() {
        return this.mImage.getFormat();
    }

    public synchronized int getHeight() {
        return this.mImage.getHeight();
    }

    public synchronized int getWidth() {
        return this.mImage.getWidth();
    }

    @NonNull
    public synchronized ImageProxy.PlaneProxy[] getPlanes() {
        return this.mImage.getPlanes();
    }

    @NonNull
    public synchronized ImageInfo getImageInfo() {
        return this.mImage.getImageInfo();
    }

    @ExperimentalGetImage
    public synchronized Image getImage() {
        return this.mImage.getImage();
    }

    /* access modifiers changed from: package-private */
    public synchronized void addOnImageCloseListener(OnImageCloseListener listener) {
        this.mOnImageCloseListeners.add(listener);
    }

    /* access modifiers changed from: protected */
    public void notifyOnImageCloseListeners() {
        Set<OnImageCloseListener> onImageCloseListeners;
        synchronized (this) {
            onImageCloseListeners = new HashSet<>(this.mOnImageCloseListeners);
        }
        for (OnImageCloseListener listener : onImageCloseListeners) {
            listener.onImageClose(this);
        }
    }
}
