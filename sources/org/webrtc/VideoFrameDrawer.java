package org.webrtc;

import android.graphics.Matrix;
import android.graphics.Point;
import android.opengl.GLES20;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import org.webrtc.RendererCommon;
import org.webrtc.VideoFrame;

public class VideoFrameDrawer {
    public static final String TAG = "VideoFrameDrawer";
    static final float[] srcPoints = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private final float[] dstPoints = new float[6];
    @Nullable
    private VideoFrame lastI420Frame;
    private int renderHeight;
    private final Matrix renderMatrix = new Matrix();
    private final Point renderSize = new Point();
    private int renderWidth;
    private final YuvUploader yuvUploader = new YuvUploader((AnonymousClass1) null);

    public static void drawTexture(RendererCommon.GlDrawer drawer, VideoFrame.TextureBuffer buffer, Matrix renderMatrix2, int frameWidth, int frameHeight, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        Matrix finalMatrix = new Matrix(buffer.getTransformMatrix());
        Matrix matrix = renderMatrix2;
        finalMatrix.preConcat(renderMatrix2);
        float[] finalGlMatrix = RendererCommon.convertMatrixFromAndroidGraphicsMatrix(finalMatrix);
        switch (AnonymousClass1.$SwitchMap$org$webrtc$VideoFrame$TextureBuffer$Type[buffer.getType().ordinal()]) {
            case 1:
                drawer.drawOes(buffer.getTextureId(), finalGlMatrix, frameWidth, frameHeight, viewportX, viewportY, viewportWidth, viewportHeight);
                return;
            case 2:
                drawer.drawRgb(buffer.getTextureId(), finalGlMatrix, frameWidth, frameHeight, viewportX, viewportY, viewportWidth, viewportHeight);
                return;
            default:
                throw new RuntimeException("Unknown texture type.");
        }
    }

    /* renamed from: org.webrtc.VideoFrameDrawer$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$webrtc$VideoFrame$TextureBuffer$Type;

        static {
            int[] iArr = new int[VideoFrame.TextureBuffer.Type.values().length];
            $SwitchMap$org$webrtc$VideoFrame$TextureBuffer$Type = iArr;
            try {
                iArr[VideoFrame.TextureBuffer.Type.OES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$webrtc$VideoFrame$TextureBuffer$Type[VideoFrame.TextureBuffer.Type.RGB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class YuvUploader {
        @Nullable
        private ByteBuffer copyBuffer;
        @Nullable
        private int[] yuvTextures;

        private YuvUploader() {
        }

        /* synthetic */ YuvUploader(AnonymousClass1 x0) {
            this();
        }

        @Nullable
        public int[] uploadYuvData(int width, int height, int[] strides, ByteBuffer[] planes) {
            ByteBuffer packedByteBuffer;
            ByteBuffer byteBuffer;
            int[] planeWidths = {width, width / 2, width / 2};
            int[] planeHeights = {height, height / 2, height / 2};
            int copyCapacityNeeded = 0;
            for (int i = 0; i < 3; i++) {
                if (strides[i] > planeWidths[i]) {
                    copyCapacityNeeded = Math.max(copyCapacityNeeded, planeWidths[i] * planeHeights[i]);
                }
            }
            if (copyCapacityNeeded > 0 && ((byteBuffer = this.copyBuffer) == null || byteBuffer.capacity() < copyCapacityNeeded)) {
                this.copyBuffer = ByteBuffer.allocateDirect(copyCapacityNeeded);
            }
            if (this.yuvTextures == null) {
                this.yuvTextures = new int[3];
                for (int i2 = 0; i2 < 3; i2++) {
                    this.yuvTextures[i2] = GlUtil.generateTexture(3553);
                }
            }
            for (int i3 = 0; i3 < 3; i3++) {
                GLES20.glActiveTexture(33984 + i3);
                GLES20.glBindTexture(3553, this.yuvTextures[i3]);
                if (strides[i3] == planeWidths[i3]) {
                    packedByteBuffer = planes[i3];
                } else {
                    YuvHelper.copyPlane(planes[i3], strides[i3], this.copyBuffer, planeWidths[i3], planeWidths[i3], planeHeights[i3]);
                    packedByteBuffer = this.copyBuffer;
                }
                GLES20.glTexImage2D(3553, 0, 6409, planeWidths[i3], planeHeights[i3], 0, 6409, 5121, packedByteBuffer);
            }
            return this.yuvTextures;
        }

        @Nullable
        public int[] uploadFromBuffer(VideoFrame.I420Buffer buffer) {
            return uploadYuvData(buffer.getWidth(), buffer.getHeight(), new int[]{buffer.getStrideY(), buffer.getStrideU(), buffer.getStrideV()}, new ByteBuffer[]{buffer.getDataY(), buffer.getDataU(), buffer.getDataV()});
        }

        @Nullable
        public int[] getYuvTextures() {
            return this.yuvTextures;
        }

        public void release() {
            this.copyBuffer = null;
            int[] iArr = this.yuvTextures;
            if (iArr != null) {
                GLES20.glDeleteTextures(3, iArr, 0);
                this.yuvTextures = null;
            }
        }
    }

    private static int distance(float x0, float y0, float x1, float y1) {
        return (int) Math.round(Math.hypot((double) (x1 - x0), (double) (y1 - y0)));
    }

    private void calculateTransformedRenderSize(int frameWidth, int frameHeight, @Nullable Matrix renderMatrix2) {
        if (renderMatrix2 == null) {
            this.renderWidth = frameWidth;
            this.renderHeight = frameHeight;
            return;
        }
        renderMatrix2.mapPoints(this.dstPoints, srcPoints);
        for (int i = 0; i < 3; i++) {
            float[] fArr = this.dstPoints;
            int i2 = (i * 2) + 0;
            fArr[i2] = fArr[i2] * ((float) frameWidth);
            int i3 = (i * 2) + 1;
            fArr[i3] = fArr[i3] * ((float) frameHeight);
        }
        float[] fArr2 = this.dstPoints;
        this.renderWidth = distance(fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
        float[] fArr3 = this.dstPoints;
        this.renderHeight = distance(fArr3[0], fArr3[1], fArr3[4], fArr3[5]);
    }

    public void drawFrame(VideoFrame frame, RendererCommon.GlDrawer drawer) {
        drawFrame(frame, drawer, (Matrix) null);
    }

    public void drawFrame(VideoFrame frame, RendererCommon.GlDrawer drawer, Matrix additionalRenderMatrix) {
        drawFrame(frame, drawer, additionalRenderMatrix, 0, 0, frame.getRotatedWidth(), frame.getRotatedHeight());
    }

    public void drawFrame(VideoFrame frame, RendererCommon.GlDrawer drawer, @Nullable Matrix additionalRenderMatrix, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        VideoFrame videoFrame = frame;
        Matrix matrix = additionalRenderMatrix;
        calculateTransformedRenderSize(frame.getRotatedWidth(), frame.getRotatedHeight(), matrix);
        if (this.renderWidth <= 0 || this.renderHeight <= 0) {
            Logging.w(TAG, "Illegal frame size: " + this.renderWidth + "x" + this.renderHeight);
            return;
        }
        boolean isTextureFrame = frame.getBuffer() instanceof VideoFrame.TextureBuffer;
        this.renderMatrix.reset();
        this.renderMatrix.preTranslate(0.5f, 0.5f);
        if (!isTextureFrame) {
            this.renderMatrix.preScale(1.0f, -1.0f);
        }
        this.renderMatrix.preRotate((float) frame.getRotation());
        this.renderMatrix.preTranslate(-0.5f, -0.5f);
        if (matrix != null) {
            this.renderMatrix.preConcat(matrix);
        }
        if (isTextureFrame) {
            this.lastI420Frame = null;
            drawTexture(drawer, (VideoFrame.TextureBuffer) frame.getBuffer(), this.renderMatrix, this.renderWidth, this.renderHeight, viewportX, viewportY, viewportWidth, viewportHeight);
            return;
        }
        if (videoFrame != this.lastI420Frame) {
            this.lastI420Frame = videoFrame;
            VideoFrame.I420Buffer i420Buffer = frame.getBuffer().toI420();
            this.yuvUploader.uploadFromBuffer(i420Buffer);
            i420Buffer.release();
        }
        drawer.drawYuv(this.yuvUploader.getYuvTextures(), RendererCommon.convertMatrixFromAndroidGraphicsMatrix(this.renderMatrix), this.renderWidth, this.renderHeight, viewportX, viewportY, viewportWidth, viewportHeight);
    }

    public VideoFrame.Buffer prepareBufferForViewportSize(VideoFrame.Buffer buffer, int width, int height) {
        buffer.retain();
        return buffer;
    }

    public void release() {
        this.yuvUploader.release();
        this.lastI420Frame = null;
    }
}
