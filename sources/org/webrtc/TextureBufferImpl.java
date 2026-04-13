package org.webrtc;

import android.graphics.Matrix;
import android.os.Handler;
import org.webrtc.VideoFrame;

public class TextureBufferImpl implements VideoFrame.TextureBuffer {
    private final int height;
    private final int id;
    private final RefCountDelegate refCountDelegate;
    /* access modifiers changed from: private */
    public final RefCountMonitor refCountMonitor;
    private final Handler toI420Handler;
    private final Matrix transformMatrix;
    private final VideoFrame.TextureBuffer.Type type;
    private final int unscaledHeight;
    private final int unscaledWidth;
    private final int width;
    private final YuvConverter yuvConverter;

    public interface RefCountMonitor {
        void onDestroy(TextureBufferImpl textureBufferImpl);

        void onRelease(TextureBufferImpl textureBufferImpl);

        void onRetain(TextureBufferImpl textureBufferImpl);
    }

    public /* synthetic */ int getBufferType() {
        return x0.a(this);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextureBufferImpl(int r13, int r14, org.webrtc.VideoFrame.TextureBuffer.Type r15, int r16, android.graphics.Matrix r17, android.os.Handler r18, org.webrtc.YuvConverter r19, @androidx.annotation.Nullable java.lang.Runnable r20) {
        /*
            r12 = this;
            org.webrtc.TextureBufferImpl$1 r10 = new org.webrtc.TextureBufferImpl$1
            r11 = r20
            r10.<init>(r11)
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r13
            r4 = r14
            r5 = r15
            r6 = r16
            r7 = r17
            r8 = r18
            r9 = r19
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.TextureBufferImpl.<init>(int, int, org.webrtc.VideoFrame$TextureBuffer$Type, int, android.graphics.Matrix, android.os.Handler, org.webrtc.YuvConverter, java.lang.Runnable):void");
    }

    TextureBufferImpl(int width2, int height2, VideoFrame.TextureBuffer.Type type2, int id2, Matrix transformMatrix2, Handler toI420Handler2, YuvConverter yuvConverter2, RefCountMonitor refCountMonitor2) {
        this(width2, height2, width2, height2, type2, id2, transformMatrix2, toI420Handler2, yuvConverter2, refCountMonitor2);
    }

    private TextureBufferImpl(int unscaledWidth2, int unscaledHeight2, int width2, int height2, VideoFrame.TextureBuffer.Type type2, int id2, Matrix transformMatrix2, Handler toI420Handler2, YuvConverter yuvConverter2, RefCountMonitor refCountMonitor2) {
        this.unscaledWidth = unscaledWidth2;
        this.unscaledHeight = unscaledHeight2;
        this.width = width2;
        this.height = height2;
        this.type = type2;
        this.id = id2;
        this.transformMatrix = transformMatrix2;
        this.toI420Handler = toI420Handler2;
        this.yuvConverter = yuvConverter2;
        this.refCountDelegate = new RefCountDelegate(new d0(this, refCountMonitor2));
        this.refCountMonitor = refCountMonitor2;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ void a(RefCountMonitor refCountMonitor2) {
        refCountMonitor2.onDestroy(this);
    }

    public VideoFrame.TextureBuffer.Type getType() {
        return this.type;
    }

    public int getTextureId() {
        return this.id;
    }

    public Matrix getTransformMatrix() {
        return this.transformMatrix;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public VideoFrame.I420Buffer toI420() {
        return (VideoFrame.I420Buffer) ThreadUtils.invokeAtFrontUninterruptibly(this.toI420Handler, new e0(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$toI420$1 */
    public /* synthetic */ VideoFrame.I420Buffer b() {
        return this.yuvConverter.convert(this);
    }

    public void retain() {
        this.refCountMonitor.onRetain(this);
        this.refCountDelegate.retain();
    }

    public void release() {
        this.refCountMonitor.onRelease(this);
        this.refCountDelegate.release();
    }

    public VideoFrame.Buffer cropAndScale(int cropX, int cropY, int cropWidth, int cropHeight, int scaleWidth, int scaleHeight) {
        Matrix cropAndScaleMatrix = new Matrix();
        int i = this.height;
        cropAndScaleMatrix.preTranslate(((float) cropX) / ((float) this.width), ((float) (i - (cropY + cropHeight))) / ((float) i));
        cropAndScaleMatrix.preScale(((float) cropWidth) / ((float) this.width), ((float) cropHeight) / ((float) this.height));
        return applyTransformMatrix(cropAndScaleMatrix, Math.round(((float) (this.unscaledWidth * cropWidth)) / ((float) this.width)), Math.round(((float) (this.unscaledHeight * cropHeight)) / ((float) this.height)), scaleWidth, scaleHeight);
    }

    public int getUnscaledWidth() {
        return this.unscaledWidth;
    }

    public int getUnscaledHeight() {
        return this.unscaledHeight;
    }

    public Handler getToI420Handler() {
        return this.toI420Handler;
    }

    public YuvConverter getYuvConverter() {
        return this.yuvConverter;
    }

    public TextureBufferImpl applyTransformMatrix(Matrix transformMatrix2, int newWidth, int newHeight) {
        return applyTransformMatrix(transformMatrix2, newWidth, newHeight, newWidth, newHeight);
    }

    private TextureBufferImpl applyTransformMatrix(Matrix transformMatrix2, int unscaledWidth2, int unscaledHeight2, int scaledWidth, int scaledHeight) {
        Matrix newMatrix = new Matrix(this.transformMatrix);
        newMatrix.preConcat(transformMatrix2);
        retain();
        return new TextureBufferImpl(unscaledWidth2, unscaledHeight2, scaledWidth, scaledHeight, this.type, this.id, newMatrix, this.toI420Handler, this.yuvConverter, new RefCountMonitor() {
            public void onRetain(TextureBufferImpl textureBuffer) {
                TextureBufferImpl.this.refCountMonitor.onRetain(TextureBufferImpl.this);
            }

            public void onRelease(TextureBufferImpl textureBuffer) {
                TextureBufferImpl.this.refCountMonitor.onRelease(TextureBufferImpl.this);
            }

            public void onDestroy(TextureBufferImpl textureBuffer) {
                TextureBufferImpl.this.release();
            }
        });
    }
}
