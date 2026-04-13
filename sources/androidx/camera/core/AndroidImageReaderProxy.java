package androidx.camera.core;

import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import java.util.concurrent.Executor;

public final class AndroidImageReaderProxy implements ImageReaderProxy {
    @GuardedBy("this")
    private final ImageReader mImageReader;

    AndroidImageReaderProxy(ImageReader imageReader) {
        this.mImageReader = imageReader;
    }

    @Nullable
    public synchronized ImageProxy acquireLatestImage() {
        Image image;
        try {
            image = this.mImageReader.acquireLatestImage();
        } catch (RuntimeException e) {
            if (isImageReaderContextNotInitializedException(e)) {
                image = null;
            } else {
                throw e;
            }
        }
        if (image == null) {
            return null;
        }
        return new AndroidImageProxy(image);
    }

    @Nullable
    public synchronized ImageProxy acquireNextImage() {
        Image image;
        try {
            image = this.mImageReader.acquireNextImage();
        } catch (RuntimeException e) {
            if (isImageReaderContextNotInitializedException(e)) {
                image = null;
            } else {
                throw e;
            }
        }
        if (image == null) {
            return null;
        }
        return new AndroidImageProxy(image);
    }

    private boolean isImageReaderContextNotInitializedException(RuntimeException e) {
        return "ImageReaderContext is not initialized".equals(e.getMessage());
    }

    public synchronized void close() {
        this.mImageReader.close();
    }

    public synchronized int getHeight() {
        return this.mImageReader.getHeight();
    }

    public synchronized int getWidth() {
        return this.mImageReader.getWidth();
    }

    public synchronized int getImageFormat() {
        return this.mImageReader.getImageFormat();
    }

    public synchronized int getMaxImages() {
        return this.mImageReader.getMaxImages();
    }

    @Nullable
    public synchronized Surface getSurface() {
        return this.mImageReader.getSurface();
    }

    public synchronized void setOnImageAvailableListener(@NonNull ImageReaderProxy.OnImageAvailableListener listener, @NonNull Executor executor) {
        this.mImageReader.setOnImageAvailableListener(new b(this, executor, listener), MainThreadAsyncHandler.getInstance());
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setOnImageAvailableListener$0 */
    public /* synthetic */ void a(ImageReaderProxy.OnImageAvailableListener listener) {
        listener.onImageAvailable(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setOnImageAvailableListener$1 */
    public /* synthetic */ void b(Executor executor, ImageReaderProxy.OnImageAvailableListener listener, ImageReader imageReader) {
        executor.execute(new c(this, listener));
    }

    public synchronized void clearOnImageAvailableListener() {
        this.mImageReader.setOnImageAvailableListener((ImageReader.OnImageAvailableListener) null, (Handler) null);
    }
}
