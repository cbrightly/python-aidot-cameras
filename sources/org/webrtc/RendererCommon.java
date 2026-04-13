package org.webrtc;

import android.graphics.Point;
import android.opengl.Matrix;
import android.view.View;

public class RendererCommon {
    private static float BALANCED_VISIBLE_FRACTION = 0.5625f;

    public interface GlDrawer {
        void drawOes(int i, float[] fArr, int i2, int i3, int i4, int i5, int i6, int i7);

        void drawRgb(int i, float[] fArr, int i2, int i3, int i4, int i5, int i6, int i7);

        void drawYuv(int[] iArr, float[] fArr, int i, int i2, int i3, int i4, int i5, int i6);

        void release();
    }

    public interface RendererEvents {
        void onFirstFrameRendered();

        void onFrameResolutionChanged(int i, int i2, int i3);
    }

    public enum ScalingType {
        SCALE_ASPECT_FIT,
        SCALE_ASPECT_FILL,
        SCALE_ASPECT_BALANCED
    }

    public static class VideoLayoutMeasure {
        private float visibleFractionMatchOrientation;
        private float visibleFractionMismatchOrientation;

        public VideoLayoutMeasure() {
            ScalingType scalingType = ScalingType.SCALE_ASPECT_BALANCED;
            this.visibleFractionMatchOrientation = RendererCommon.convertScalingTypeToVisibleFraction(scalingType);
            this.visibleFractionMismatchOrientation = RendererCommon.convertScalingTypeToVisibleFraction(scalingType);
        }

        public void setScalingType(ScalingType scalingType) {
            setScalingType(scalingType, scalingType);
        }

        public void setScalingType(ScalingType scalingTypeMatchOrientation, ScalingType scalingTypeMismatchOrientation) {
            this.visibleFractionMatchOrientation = RendererCommon.convertScalingTypeToVisibleFraction(scalingTypeMatchOrientation);
            this.visibleFractionMismatchOrientation = RendererCommon.convertScalingTypeToVisibleFraction(scalingTypeMismatchOrientation);
        }

        public void setVisibleFraction(float visibleFractionMatchOrientation2, float visibleFractionMismatchOrientation2) {
            this.visibleFractionMatchOrientation = visibleFractionMatchOrientation2;
            this.visibleFractionMismatchOrientation = visibleFractionMismatchOrientation2;
        }

        public Point measure(int widthSpec, int heightSpec, int frameWidth, int frameHeight) {
            float visibleFraction;
            int maxWidth = View.getDefaultSize(Integer.MAX_VALUE, widthSpec);
            int maxHeight = View.getDefaultSize(Integer.MAX_VALUE, heightSpec);
            if (frameWidth == 0 || frameHeight == 0 || maxWidth == 0 || maxHeight == 0) {
                return new Point(maxWidth, maxHeight);
            }
            float frameAspect = ((float) frameWidth) / ((float) frameHeight);
            float displayAspect = ((float) maxWidth) / ((float) maxHeight);
            boolean z = true;
            boolean z2 = frameAspect > 1.0f;
            if (displayAspect <= 1.0f) {
                z = false;
            }
            if (z2 == z) {
                visibleFraction = this.visibleFractionMatchOrientation;
            } else {
                visibleFraction = this.visibleFractionMismatchOrientation;
            }
            Point layoutSize = RendererCommon.getDisplaySize(visibleFraction, frameAspect, maxWidth, maxHeight);
            if (View.MeasureSpec.getMode(widthSpec) == 1073741824) {
                layoutSize.x = maxWidth;
            }
            if (View.MeasureSpec.getMode(heightSpec) == 1073741824) {
                layoutSize.y = maxHeight;
            }
            return layoutSize;
        }
    }

    public static float[] getLayoutMatrix(boolean mirror, float videoAspectRatio, float displayAspectRatio) {
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        if (displayAspectRatio > videoAspectRatio) {
            scaleY = videoAspectRatio / displayAspectRatio;
        } else {
            scaleX = displayAspectRatio / videoAspectRatio;
        }
        if (mirror) {
            scaleX *= -1.0f;
        }
        float[] matrix = new float[16];
        Matrix.setIdentityM(matrix, 0);
        Matrix.scaleM(matrix, 0, scaleX, scaleY, 1.0f);
        adjustOrigin(matrix);
        return matrix;
    }

    public static android.graphics.Matrix convertMatrixToAndroidGraphicsMatrix(float[] matrix4x4) {
        float[] values = {matrix4x4[0], matrix4x4[4], matrix4x4[12], matrix4x4[1], matrix4x4[5], matrix4x4[13], matrix4x4[3], matrix4x4[7], matrix4x4[15]};
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setValues(values);
        return matrix;
    }

    public static float[] convertMatrixFromAndroidGraphicsMatrix(android.graphics.Matrix matrix) {
        float[] values = new float[9];
        matrix.getValues(values);
        return new float[]{values[0], values[3], 0.0f, values[6], values[1], values[4], 0.0f, values[7], 0.0f, 0.0f, 1.0f, 0.0f, values[2], values[5], 0.0f, values[8]};
    }

    public static Point getDisplaySize(ScalingType scalingType, float videoAspectRatio, int maxDisplayWidth, int maxDisplayHeight) {
        return getDisplaySize(convertScalingTypeToVisibleFraction(scalingType), videoAspectRatio, maxDisplayWidth, maxDisplayHeight);
    }

    private static void adjustOrigin(float[] matrix) {
        matrix[12] = matrix[12] - ((matrix[0] + matrix[4]) * 0.5f);
        matrix[13] = matrix[13] - ((matrix[1] + matrix[5]) * 0.5f);
        matrix[12] = matrix[12] + 0.5f;
        matrix[13] = matrix[13] + 0.5f;
    }

    /* renamed from: org.webrtc.RendererCommon$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$webrtc$RendererCommon$ScalingType;

        static {
            int[] iArr = new int[ScalingType.values().length];
            $SwitchMap$org$webrtc$RendererCommon$ScalingType = iArr;
            try {
                iArr[ScalingType.SCALE_ASPECT_FIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$webrtc$RendererCommon$ScalingType[ScalingType.SCALE_ASPECT_FILL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$webrtc$RendererCommon$ScalingType[ScalingType.SCALE_ASPECT_BALANCED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static float convertScalingTypeToVisibleFraction(ScalingType scalingType) {
        switch (AnonymousClass1.$SwitchMap$org$webrtc$RendererCommon$ScalingType[scalingType.ordinal()]) {
            case 1:
                return 1.0f;
            case 2:
                return 0.0f;
            case 3:
                return BALANCED_VISIBLE_FRACTION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Point getDisplaySize(float minVisibleFraction, float videoAspectRatio, int maxDisplayWidth, int maxDisplayHeight) {
        return (minVisibleFraction == 0.0f || videoAspectRatio == 0.0f) ? new Point(maxDisplayWidth, maxDisplayHeight) : new Point(Math.min(maxDisplayWidth, Math.round((((float) maxDisplayHeight) / minVisibleFraction) * videoAspectRatio)), Math.min(maxDisplayHeight, Math.round((((float) maxDisplayWidth) / minVisibleFraction) / videoAspectRatio)));
    }
}
