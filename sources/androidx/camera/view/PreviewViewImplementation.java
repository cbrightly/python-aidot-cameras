package androidx.camera.view;

import android.graphics.Bitmap;
import android.util.Size;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.SurfaceRequest;
import com.google.common.util.concurrent.ListenableFuture;

public abstract class PreviewViewImplementation {
    @NonNull
    FrameLayout mParent;
    @NonNull
    private final PreviewTransformation mPreviewTransform;
    @Nullable
    Size mResolution;

    public interface OnSurfaceNotInUseListener {
        void onSurfaceNotInUse();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract View getPreview();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract Bitmap getPreviewBitmap();

    /* access modifiers changed from: package-private */
    public abstract void initializePreview();

    /* access modifiers changed from: package-private */
    public abstract void onAttachedToWindow();

    /* access modifiers changed from: package-private */
    public abstract void onDetachedFromWindow();

    /* access modifiers changed from: package-private */
    public abstract void onSurfaceRequested(@NonNull SurfaceRequest surfaceRequest, @Nullable OnSurfaceNotInUseListener onSurfaceNotInUseListener);

    /* access modifiers changed from: package-private */
    @NonNull
    public abstract ListenableFuture<Void> waitForNextFrame();

    PreviewViewImplementation(@NonNull FrameLayout parent, @NonNull PreviewTransformation previewTransform) {
        this.mParent = parent;
        this.mPreviewTransform = previewTransform;
    }

    /* access modifiers changed from: package-private */
    public void redrawPreview() {
        View preview = getPreview();
        if (preview != null) {
            this.mPreviewTransform.transformView(new Size(this.mParent.getWidth(), this.mParent.getHeight()), this.mParent.getLayoutDirection(), preview);
        }
    }

    /* access modifiers changed from: package-private */
    public void onSurfaceProvided() {
        redrawPreview();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Bitmap getBitmap() {
        Bitmap bitmap = getPreviewBitmap();
        if (bitmap == null) {
            return null;
        }
        return this.mPreviewTransform.createTransformedBitmap(bitmap, new Size(this.mParent.getWidth(), this.mParent.getHeight()), this.mParent.getLayoutDirection());
    }
}
