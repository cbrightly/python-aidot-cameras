package org.webrtc;

import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLException;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import org.webrtc.GlGenericDrawer;
import org.webrtc.ThreadUtils;
import org.webrtc.VideoFrame;

public final class YuvConverter {
    private static final String FRAGMENT_SHADER = "uniform vec2 xUnit;\nuniform vec4 coeffs;\n\nvoid main() {\n  gl_FragColor.r = coeffs.a + dot(coeffs.rgb,\n      sample(tc - 1.5 * xUnit).rgb);\n  gl_FragColor.g = coeffs.a + dot(coeffs.rgb,\n      sample(tc - 0.5 * xUnit).rgb);\n  gl_FragColor.b = coeffs.a + dot(coeffs.rgb,\n      sample(tc + 0.5 * xUnit).rgb);\n  gl_FragColor.a = coeffs.a + dot(coeffs.rgb,\n      sample(tc + 1.5 * xUnit).rgb);\n}\n";
    private static final String TAG = "YuvConverter";
    private final GlGenericDrawer drawer;
    private final GlTextureFrameBuffer i420TextureFrameBuffer;
    private final ShaderCallbacks shaderCallbacks;
    private final ThreadUtils.ThreadChecker threadChecker;
    private final VideoFrameDrawer videoFrameDrawer;

    public static class ShaderCallbacks implements GlGenericDrawer.ShaderCallbacks {
        private static final float[] uCoeffs = {-0.148223f, -0.290993f, 0.439216f, 0.501961f};
        private static final float[] vCoeffs = {0.439216f, -0.367788f, -0.0714274f, 0.501961f};
        private static final float[] yCoeffs = {0.256788f, 0.504129f, 0.0979059f, 0.0627451f};
        private float[] coeffs;
        private int coeffsLoc;
        private float stepSize;
        private int xUnitLoc;

        private ShaderCallbacks() {
        }

        public void setPlaneY() {
            this.coeffs = yCoeffs;
            this.stepSize = 1.0f;
        }

        public void setPlaneU() {
            this.coeffs = uCoeffs;
            this.stepSize = 2.0f;
        }

        public void setPlaneV() {
            this.coeffs = vCoeffs;
            this.stepSize = 2.0f;
        }

        public void onNewShader(GlShader shader) {
            this.xUnitLoc = shader.getUniformLocation("xUnit");
            this.coeffsLoc = shader.getUniformLocation("coeffs");
        }

        public void onPrepareShader(GlShader shader, float[] texMatrix, int frameWidth, int frameHeight, int viewportWidth, int viewportHeight) {
            GLES20.glUniform4fv(this.coeffsLoc, 1, this.coeffs, 0);
            int i = this.xUnitLoc;
            float f = this.stepSize;
            GLES20.glUniform2f(i, (texMatrix[0] * f) / ((float) frameWidth), (f * texMatrix[1]) / ((float) frameWidth));
        }
    }

    public YuvConverter() {
        this(new VideoFrameDrawer());
    }

    public YuvConverter(VideoFrameDrawer videoFrameDrawer2) {
        ThreadUtils.ThreadChecker threadChecker2 = new ThreadUtils.ThreadChecker();
        this.threadChecker = threadChecker2;
        this.i420TextureFrameBuffer = new GlTextureFrameBuffer(6408);
        ShaderCallbacks shaderCallbacks2 = new ShaderCallbacks();
        this.shaderCallbacks = shaderCallbacks2;
        this.drawer = new GlGenericDrawer(FRAGMENT_SHADER, shaderCallbacks2);
        this.videoFrameDrawer = videoFrameDrawer2;
        threadChecker2.detachThread();
    }

    @Nullable
    public VideoFrame.I420Buffer convert(VideoFrame.TextureBuffer inputTextureBuffer) {
        try {
            return convertInternal(inputTextureBuffer);
        } catch (GLException e) {
            Logging.w(TAG, "Failed to convert TextureBuffer", e);
            return null;
        }
    }

    private VideoFrame.I420Buffer convertInternal(VideoFrame.TextureBuffer inputTextureBuffer) {
        VideoFrame.TextureBuffer preparedBuffer = (VideoFrame.TextureBuffer) this.videoFrameDrawer.prepareBufferForViewportSize(inputTextureBuffer, inputTextureBuffer.getWidth(), inputTextureBuffer.getHeight());
        int frameWidth = preparedBuffer.getWidth();
        int frameHeight = preparedBuffer.getHeight();
        int stride = ((frameWidth + 7) / 8) * 8;
        int uvHeight = (frameHeight + 1) / 2;
        int totalHeight = frameHeight + uvHeight;
        ByteBuffer i420ByteBuffer = JniCommon.nativeAllocateByteBuffer(stride * totalHeight);
        int viewportWidth = stride / 4;
        Matrix renderMatrix = new Matrix();
        renderMatrix.preTranslate(0.5f, 0.5f);
        renderMatrix.preScale(1.0f, -1.0f);
        renderMatrix.preTranslate(-0.5f, -0.5f);
        this.i420TextureFrameBuffer.setSize(viewportWidth, totalHeight);
        GLES20.glBindFramebuffer(36160, this.i420TextureFrameBuffer.getFrameBufferId());
        GlUtil.checkNoGLES2Error("glBindFramebuffer");
        this.shaderCallbacks.setPlaneY();
        VideoFrame.TextureBuffer textureBuffer = preparedBuffer;
        int i = frameWidth;
        int i2 = frameHeight;
        int viewportWidth2 = viewportWidth;
        ByteBuffer i420ByteBuffer2 = i420ByteBuffer;
        int i3 = totalHeight;
        VideoFrameDrawer.drawTexture(this.drawer, textureBuffer, renderMatrix, i, i2, 0, 0, viewportWidth2, frameHeight);
        this.shaderCallbacks.setPlaneU();
        Matrix matrix = renderMatrix;
        int i4 = frameHeight;
        int i5 = uvHeight;
        VideoFrameDrawer.drawTexture(this.drawer, textureBuffer, matrix, i, i2, 0, i4, viewportWidth2 / 2, i5);
        this.shaderCallbacks.setPlaneV();
        VideoFrameDrawer.drawTexture(this.drawer, textureBuffer, matrix, i, i2, viewportWidth2 / 2, i4, viewportWidth2 / 2, i5);
        GLES20.glReadPixels(0, 0, this.i420TextureFrameBuffer.getWidth(), this.i420TextureFrameBuffer.getHeight(), 6408, 5121, i420ByteBuffer2);
        GlUtil.checkNoGLES2Error("YuvConverter.convert");
        GLES20.glBindFramebuffer(36160, 0);
        int uPos = (stride * frameHeight) + 0;
        int vPos = uPos + (stride / 2);
        ByteBuffer i420ByteBuffer3 = i420ByteBuffer2;
        i420ByteBuffer3.position(0);
        i420ByteBuffer3.limit((stride * frameHeight) + 0);
        ByteBuffer dataY = i420ByteBuffer3.slice();
        i420ByteBuffer3.position(uPos);
        int uvSize = ((uvHeight - 1) * stride) + (stride / 2);
        i420ByteBuffer3.limit(uPos + uvSize);
        ByteBuffer dataU = i420ByteBuffer3.slice();
        i420ByteBuffer3.position(vPos);
        i420ByteBuffer3.limit(vPos + uvSize);
        ByteBuffer dataV = i420ByteBuffer3.slice();
        preparedBuffer.release();
        ByteBuffer byteBuffer = i420ByteBuffer3;
        int i6 = vPos;
        int i7 = uPos;
        return JavaI420Buffer.wrap(frameWidth, frameHeight, dataY, stride, dataU, stride, dataV, stride, new n0(i420ByteBuffer3));
    }

    public void release() {
        this.threadChecker.checkIsOnValidThread();
        this.drawer.release();
        this.i420TextureFrameBuffer.release();
        this.videoFrameDrawer.release();
        this.threadChecker.detachThread();
    }
}
