package androidx.camera.core;

import android.graphics.Rect;
import android.media.Image;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.TagBundle;
import java.nio.ByteBuffer;

public final class AndroidImageProxy implements ImageProxy {
    @GuardedBy("this")
    private final Image mImage;
    private final ImageInfo mImageInfo;
    @GuardedBy("this")
    private final PlaneProxy[] mPlanes;

    AndroidImageProxy(Image image) {
        this.mImage = image;
        Image.Plane[] originalPlanes = image.getPlanes();
        if (originalPlanes != null) {
            this.mPlanes = new PlaneProxy[originalPlanes.length];
            for (int i = 0; i < originalPlanes.length; i++) {
                this.mPlanes[i] = new PlaneProxy(originalPlanes[i]);
            }
        } else {
            this.mPlanes = new PlaneProxy[0];
        }
        this.mImageInfo = ImmutableImageInfo.create(TagBundle.emptyBundle(), image.getTimestamp(), 0);
    }

    public synchronized void close() {
        this.mImage.close();
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
        return this.mPlanes;
    }

    public static final class PlaneProxy implements ImageProxy.PlaneProxy {
        @GuardedBy("this")
        private final Image.Plane mPlane;

        PlaneProxy(Image.Plane plane) {
            this.mPlane = plane;
        }

        public synchronized int getRowStride() {
            return this.mPlane.getRowStride();
        }

        public synchronized int getPixelStride() {
            return this.mPlane.getPixelStride();
        }

        @NonNull
        public synchronized ByteBuffer getBuffer() {
            return this.mPlane.getBuffer();
        }
    }

    @NonNull
    public ImageInfo getImageInfo() {
        return this.mImageInfo;
    }

    @ExperimentalGetImage
    public synchronized Image getImage() {
        return this.mImage;
    }
}
