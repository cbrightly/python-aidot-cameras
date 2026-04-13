package androidx.camera.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.ExperimentalUseCaseGroup;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.view.PreviewView;
import androidx.camera.view.internal.compat.quirk.DeviceQuirks;
import androidx.camera.view.internal.compat.quirk.PreviewOneThirdWiderQuirk;
import androidx.core.util.Preconditions;

public final class PreviewTransformation {
    private static final PreviewView.ScaleType DEFAULT_SCALE_TYPE = PreviewView.ScaleType.FILL_CENTER;
    private static final String TAG = "PreviewTransform";
    private boolean mIsFrontCamera;
    private int mPreviewRotationDegrees;
    private Size mResolution;
    private PreviewView.ScaleType mScaleType = DEFAULT_SCALE_TYPE;
    private Rect mSurfaceCropRect;
    private int mTargetRotation;
    private Rect mViewportRect;

    PreviewTransformation() {
    }

    /* access modifiers changed from: package-private */
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public void setTransformationInfo(@NonNull SurfaceRequest.TransformationInfo transformationInfo, Size resolution, boolean isFrontCamera) {
        Logger.d(TAG, "Transformation info set: " + transformationInfo + " " + resolution + " " + isFrontCamera);
        this.mSurfaceCropRect = getCorrectedCropRect(transformationInfo.getCropRect());
        this.mViewportRect = transformationInfo.getCropRect();
        this.mPreviewRotationDegrees = transformationInfo.getRotationDegrees();
        this.mTargetRotation = transformationInfo.getTargetRotation();
        this.mResolution = resolution;
        this.mIsFrontCamera = isFrontCamera;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Matrix getTextureViewCorrectionMatrix() {
        Preconditions.checkState(isTransformationInfoReady());
        RectF surfaceRect = new RectF(0.0f, 0.0f, (float) this.mResolution.getWidth(), (float) this.mResolution.getHeight());
        return TransformUtils.getRectToRect(surfaceRect, surfaceRect, -TransformUtils.surfaceRotationToRotationDegrees(this.mTargetRotation));
    }

    /* access modifiers changed from: package-private */
    public void transformView(Size previewViewSize, int layoutDirection, @NonNull View preview) {
        if (previewViewSize.getHeight() == 0 || previewViewSize.getWidth() == 0) {
            Logger.w(TAG, "Transform not applied due to PreviewView size: " + previewViewSize);
        } else if (isTransformationInfoReady()) {
            if (preview instanceof TextureView) {
                ((TextureView) preview).setTransform(getTextureViewCorrectionMatrix());
            } else {
                Display display = preview.getDisplay();
                if (!(display == null || display.getRotation() == this.mTargetRotation)) {
                    Logger.e(TAG, "Non-display rotation not supported with SurfaceView / PERFORMANCE mode.");
                }
            }
            RectF surfaceRectInPreviewView = getTransformedSurfaceRect(previewViewSize, layoutDirection);
            preview.setPivotX(0.0f);
            preview.setPivotY(0.0f);
            preview.setScaleX(surfaceRectInPreviewView.width() / ((float) this.mResolution.getWidth()));
            preview.setScaleY(surfaceRectInPreviewView.height() / ((float) this.mResolution.getHeight()));
            preview.setTranslationX(surfaceRectInPreviewView.left - ((float) preview.getLeft()));
            preview.setTranslationY(surfaceRectInPreviewView.top - ((float) preview.getTop()));
        }
    }

    /* access modifiers changed from: package-private */
    public void setScaleType(PreviewView.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    /* access modifiers changed from: package-private */
    public PreviewView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    private RectF getTransformedSurfaceRect(Size previewViewSize, int layoutDirection) {
        Preconditions.checkState(isTransformationInfoReady());
        Matrix surfaceToPreviewView = getSurfaceToPreviewViewMatrix(previewViewSize, layoutDirection);
        RectF rect = new RectF(0.0f, 0.0f, (float) this.mResolution.getWidth(), (float) this.mResolution.getHeight());
        surfaceToPreviewView.mapRect(rect);
        return rect;
    }

    /* access modifiers changed from: package-private */
    public Matrix getSurfaceToPreviewViewMatrix(Size previewViewSize, int layoutDirection) {
        RectF previewViewCropRect;
        Preconditions.checkState(isTransformationInfoReady());
        if (isViewportAspectRatioMatchPreviewView(previewViewSize)) {
            previewViewCropRect = new RectF(0.0f, 0.0f, (float) previewViewSize.getWidth(), (float) previewViewSize.getHeight());
        } else {
            previewViewCropRect = getPreviewViewViewportRectForMismatchedAspectRatios(previewViewSize, layoutDirection);
        }
        Matrix matrix = TransformUtils.getRectToRect(new RectF(this.mSurfaceCropRect), previewViewCropRect, this.mPreviewRotationDegrees);
        if (this.mIsFrontCamera) {
            if (TransformUtils.is90or270(this.mPreviewRotationDegrees)) {
                matrix.preScale(1.0f, -1.0f, (float) this.mSurfaceCropRect.centerX(), (float) this.mSurfaceCropRect.centerY());
            } else {
                matrix.preScale(-1.0f, 1.0f, (float) this.mSurfaceCropRect.centerX(), (float) this.mSurfaceCropRect.centerY());
            }
        }
        return matrix;
    }

    private Rect getCorrectedCropRect(Rect surfaceCropRect) {
        PreviewOneThirdWiderQuirk quirk = (PreviewOneThirdWiderQuirk) DeviceQuirks.get(PreviewOneThirdWiderQuirk.class);
        if (quirk == null) {
            return surfaceCropRect;
        }
        RectF cropRectF = new RectF(surfaceCropRect);
        Matrix correction = new Matrix();
        correction.setScale(quirk.getCropRectScaleX(), 1.0f, (float) surfaceCropRect.centerX(), (float) surfaceCropRect.centerY());
        correction.mapRect(cropRectF);
        Rect correctRect = new Rect();
        cropRectF.round(correctRect);
        return correctRect;
    }

    /* access modifiers changed from: package-private */
    public RectF getPreviewViewViewportRectForMismatchedAspectRatios(Size previewViewSize, int layoutDirection) {
        RectF previewViewRect = new RectF(0.0f, 0.0f, (float) previewViewSize.getWidth(), (float) previewViewSize.getHeight());
        Size rotatedViewportSize = getRotatedViewportSize();
        RectF rotatedViewportRect = new RectF(0.0f, 0.0f, (float) rotatedViewportSize.getWidth(), (float) rotatedViewportSize.getHeight());
        Matrix matrix = new Matrix();
        setMatrixRectToRect(matrix, rotatedViewportRect, previewViewRect, this.mScaleType);
        matrix.mapRect(rotatedViewportRect);
        if (layoutDirection == 1) {
            return flipHorizontally(rotatedViewportRect, ((float) previewViewSize.getWidth()) / 2.0f);
        }
        return rotatedViewportRect;
    }

    /* renamed from: androidx.camera.view.PreviewTransformation$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$view$PreviewView$ScaleType;

        static {
            int[] iArr = new int[PreviewView.ScaleType.values().length];
            $SwitchMap$androidx$camera$view$PreviewView$ScaleType = iArr;
            try {
                iArr[PreviewView.ScaleType.FIT_CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[PreviewView.ScaleType.FILL_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[PreviewView.ScaleType.FIT_END.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[PreviewView.ScaleType.FILL_END.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[PreviewView.ScaleType.FIT_START.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[PreviewView.ScaleType.FILL_START.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private static void setMatrixRectToRect(Matrix matrix, RectF source, RectF destination, PreviewView.ScaleType scaleType) {
        Matrix.ScaleToFit matrixScaleType;
        switch (AnonymousClass1.$SwitchMap$androidx$camera$view$PreviewView$ScaleType[scaleType.ordinal()]) {
            case 1:
            case 2:
                matrixScaleType = Matrix.ScaleToFit.CENTER;
                break;
            case 3:
            case 4:
                matrixScaleType = Matrix.ScaleToFit.END;
                break;
            case 5:
            case 6:
                matrixScaleType = Matrix.ScaleToFit.START;
                break;
            default:
                Logger.e(TAG, "Unexpected crop rect: " + scaleType);
                matrixScaleType = Matrix.ScaleToFit.FILL;
                break;
        }
        if (scaleType == PreviewView.ScaleType.FIT_CENTER || scaleType == PreviewView.ScaleType.FIT_START || scaleType == PreviewView.ScaleType.FIT_END) {
            matrix.setRectToRect(source, destination, matrixScaleType);
            return;
        }
        matrix.setRectToRect(destination, source, matrixScaleType);
        matrix.invert(matrix);
    }

    private static RectF flipHorizontally(RectF original, float flipLineX) {
        return new RectF((flipLineX + flipLineX) - original.right, original.top, (flipLineX + flipLineX) - original.left, original.bottom);
    }

    private Size getRotatedViewportSize() {
        if (TransformUtils.is90or270(this.mPreviewRotationDegrees)) {
            return new Size(this.mViewportRect.height(), this.mViewportRect.width());
        }
        return new Size(this.mViewportRect.width(), this.mViewportRect.height());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isViewportAspectRatioMatchPreviewView(Size previewViewSize) {
        return TransformUtils.isAspectRatioMatchingWithRoundingError(previewViewSize, true, getRotatedViewportSize(), false);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Rect getSurfaceCropRect() {
        return this.mSurfaceCropRect;
    }

    /* access modifiers changed from: package-private */
    public Bitmap createTransformedBitmap(@NonNull Bitmap original, Size previewViewSize, int layoutDirection) {
        if (!isTransformationInfoReady()) {
            return original;
        }
        Matrix textureViewCorrection = getTextureViewCorrectionMatrix();
        RectF surfaceRectInPreviewView = getTransformedSurfaceRect(previewViewSize, layoutDirection);
        Bitmap transformed = Bitmap.createBitmap(previewViewSize.getWidth(), previewViewSize.getHeight(), original.getConfig());
        Canvas canvas = new Canvas(transformed);
        Matrix canvasTransform = new Matrix();
        canvasTransform.postConcat(textureViewCorrection);
        canvasTransform.postScale(surfaceRectInPreviewView.width() / ((float) this.mResolution.getWidth()), surfaceRectInPreviewView.height() / ((float) this.mResolution.getHeight()));
        canvasTransform.postTranslate(surfaceRectInPreviewView.left, surfaceRectInPreviewView.top);
        canvas.drawBitmap(original, canvasTransform, new Paint(7));
        return transformed;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Matrix getPreviewViewToNormalizedSurfaceMatrix(Size previewViewSize, int layoutDirection) {
        if (!isTransformationInfoReady()) {
            return null;
        }
        Matrix matrix = new Matrix();
        getSurfaceToPreviewViewMatrix(previewViewSize, layoutDirection).invert(matrix);
        Matrix normalization = new Matrix();
        normalization.setRectToRect(new RectF(0.0f, 0.0f, (float) this.mResolution.getWidth(), (float) this.mResolution.getHeight()), new RectF(0.0f, 0.0f, 1.0f, 1.0f), Matrix.ScaleToFit.FILL);
        matrix.postConcat(normalization);
        return matrix;
    }

    private boolean isTransformationInfoReady() {
        return (this.mSurfaceCropRect == null || this.mResolution == null) ? false : true;
    }
}
