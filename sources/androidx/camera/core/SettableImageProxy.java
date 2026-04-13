package androidx.camera.core;

import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class SettableImageProxy extends ForwardingImageProxy {
    @Nullable
    private Rect mCropRect;
    private final int mHeight;
    private final ImageInfo mImageInfo;
    private final int mWidth;

    SettableImageProxy(ImageProxy imageProxy, ImageInfo imageInfo) {
        this(imageProxy, (Size) null, imageInfo);
    }

    SettableImageProxy(ImageProxy imageProxy, @Nullable Size resolution, ImageInfo imageInfo) {
        super(imageProxy);
        if (resolution == null) {
            this.mWidth = super.getWidth();
            this.mHeight = super.getHeight();
        } else {
            this.mWidth = resolution.getWidth();
            this.mHeight = resolution.getHeight();
        }
        this.mImageInfo = imageInfo;
    }

    @NonNull
    public synchronized Rect getCropRect() {
        if (this.mCropRect == null) {
            return new Rect(0, 0, getWidth(), getHeight());
        }
        return new Rect(this.mCropRect);
    }

    public synchronized void setCropRect(@Nullable Rect cropRect) {
        if (cropRect != null) {
            cropRect = new Rect(cropRect);
            if (!cropRect.intersect(0, 0, getWidth(), getHeight())) {
                cropRect.setEmpty();
            }
        }
        this.mCropRect = cropRect;
    }

    public synchronized int getWidth() {
        return this.mWidth;
    }

    public synchronized int getHeight() {
        return this.mHeight;
    }

    @NonNull
    public ImageInfo getImageInfo() {
        return this.mImageInfo;
    }
}
