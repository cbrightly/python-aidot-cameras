package androidx.camera.core;

import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.impl.ImageReaderProxy;
import java.util.concurrent.Executor;

public class SafeCloseImageReaderProxy implements ImageReaderProxy {
    private ForwardingImageProxy.OnImageCloseListener mImageCloseListener = new g1(this);
    @GuardedBy("mLock")
    private final ImageReaderProxy mImageReaderProxy;
    @GuardedBy("mLock")
    private volatile boolean mIsClosed = false;
    private final Object mLock = new Object();
    @GuardedBy("mLock")
    private volatile int mOutstandingImages = 0;
    @Nullable
    private final Surface mSurface;

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ void a(ImageProxy image) {
        synchronized (this.mLock) {
            this.mOutstandingImages--;
            if (this.mIsClosed && this.mOutstandingImages == 0) {
                close();
            }
        }
    }

    SafeCloseImageReaderProxy(@NonNull ImageReaderProxy imageReaderProxy) {
        this.mImageReaderProxy = imageReaderProxy;
        this.mSurface = imageReaderProxy.getSurface();
    }

    @Nullable
    public ImageProxy acquireLatestImage() {
        ImageProxy wrapImageProxy;
        synchronized (this.mLock) {
            wrapImageProxy = wrapImageProxy(this.mImageReaderProxy.acquireLatestImage());
        }
        return wrapImageProxy;
    }

    @Nullable
    public ImageProxy acquireNextImage() {
        ImageProxy wrapImageProxy;
        synchronized (this.mLock) {
            wrapImageProxy = wrapImageProxy(this.mImageReaderProxy.acquireNextImage());
        }
        return wrapImageProxy;
    }

    public void close() {
        synchronized (this.mLock) {
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
            }
            this.mImageReaderProxy.close();
        }
    }

    @GuardedBy("mLock")
    @Nullable
    private ImageProxy wrapImageProxy(@Nullable ImageProxy imageProxy) {
        synchronized (this.mLock) {
            if (imageProxy == null) {
                return null;
            }
            this.mOutstandingImages++;
            SingleCloseImageProxy singleCloseImageProxy = new SingleCloseImageProxy(imageProxy);
            singleCloseImageProxy.addOnImageCloseListener(this.mImageCloseListener);
            return singleCloseImageProxy;
        }
    }

    /* access modifiers changed from: package-private */
    @GuardedBy("mLock")
    public void safeClose() {
        synchronized (this.mLock) {
            this.mIsClosed = true;
            this.mImageReaderProxy.clearOnImageAvailableListener();
            if (this.mOutstandingImages == 0) {
                close();
            }
        }
    }

    public int getHeight() {
        int height;
        synchronized (this.mLock) {
            height = this.mImageReaderProxy.getHeight();
        }
        return height;
    }

    public int getWidth() {
        int width;
        synchronized (this.mLock) {
            width = this.mImageReaderProxy.getWidth();
        }
        return width;
    }

    public int getImageFormat() {
        int imageFormat;
        synchronized (this.mLock) {
            imageFormat = this.mImageReaderProxy.getImageFormat();
        }
        return imageFormat;
    }

    public int getMaxImages() {
        int maxImages;
        synchronized (this.mLock) {
            maxImages = this.mImageReaderProxy.getMaxImages();
        }
        return maxImages;
    }

    @Nullable
    public Surface getSurface() {
        Surface surface;
        synchronized (this.mLock) {
            surface = this.mImageReaderProxy.getSurface();
        }
        return surface;
    }

    public void setOnImageAvailableListener(@NonNull ImageReaderProxy.OnImageAvailableListener listener, @NonNull Executor executor) {
        synchronized (this.mLock) {
            this.mImageReaderProxy.setOnImageAvailableListener(new f1(this, listener), executor);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setOnImageAvailableListener$1 */
    public /* synthetic */ void b(ImageReaderProxy.OnImageAvailableListener listener, ImageReaderProxy imageReader) {
        listener.onImageAvailable(this);
    }

    public void clearOnImageAvailableListener() {
        synchronized (this.mLock) {
            this.mImageReaderProxy.clearOnImageAvailableListener();
        }
    }
}
