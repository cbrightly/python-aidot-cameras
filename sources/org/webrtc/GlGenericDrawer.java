package org.webrtc;

import android.opengl.GLES20;
import androidx.annotation.Nullable;
import java.nio.FloatBuffer;
import org.webrtc.RendererCommon;

public class GlGenericDrawer implements RendererCommon.GlDrawer {
    private static final String DEFAULT_VERTEX_SHADER_STRING = "varying vec2 tc;\nattribute vec4 in_pos;\nattribute vec4 in_tc;\nuniform mat4 tex_mat;\nvoid main() {\n  gl_Position = in_pos;\n  tc = (tex_mat * in_tc).xy;\n}\n";
    private static final FloatBuffer FULL_RECTANGLE_BUFFER = GlUtil.createFloatBuffer(new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f});
    private static final FloatBuffer FULL_RECTANGLE_TEXTURE_BUFFER = GlUtil.createFloatBuffer(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
    private static final String INPUT_TEXTURE_COORDINATE_NAME = "in_tc";
    private static final String INPUT_VERTEX_COORDINATE_NAME = "in_pos";
    private static final String TEXTURE_MATRIX_NAME = "tex_mat";
    @Nullable
    private GlShader currentShader;
    @Nullable
    private ShaderType currentShaderType;
    private final String genericFragmentSource;
    private int inPosLocation;
    private int inTcLocation;
    private final ShaderCallbacks shaderCallbacks;
    private int texMatrixLocation;
    private final String vertexShader;

    public interface ShaderCallbacks {
        void onNewShader(GlShader glShader);

        void onPrepareShader(GlShader glShader, float[] fArr, int i, int i2, int i3, int i4);
    }

    public enum ShaderType {
        OES,
        RGB,
        YUV
    }

    static String createFragmentShaderString(String genericFragmentSource2, ShaderType shaderType) {
        StringBuilder stringBuilder = new StringBuilder();
        ShaderType shaderType2 = ShaderType.OES;
        if (shaderType == shaderType2) {
            stringBuilder.append("#extension GL_OES_EGL_image_external : require\n");
        }
        stringBuilder.append("precision mediump float;\n");
        stringBuilder.append("varying vec2 tc;\n");
        if (shaderType == ShaderType.YUV) {
            stringBuilder.append("uniform sampler2D y_tex;\n");
            stringBuilder.append("uniform sampler2D u_tex;\n");
            stringBuilder.append("uniform sampler2D v_tex;\n");
            stringBuilder.append("vec4 sample(vec2 p) {\n");
            stringBuilder.append("  float y = texture2D(y_tex, p).r * 1.16438;\n");
            stringBuilder.append("  float u = texture2D(u_tex, p).r;\n");
            stringBuilder.append("  float v = texture2D(v_tex, p).r;\n");
            stringBuilder.append("  return vec4(y + 1.59603 * v - 0.874202,\n");
            stringBuilder.append("    y - 0.391762 * u - 0.812968 * v + 0.531668,\n");
            stringBuilder.append("    y + 2.01723 * u - 1.08563, 1);\n");
            stringBuilder.append("}\n");
            stringBuilder.append(genericFragmentSource2);
        } else {
            String samplerName = shaderType == shaderType2 ? "samplerExternalOES" : "sampler2D";
            stringBuilder.append("uniform ");
            stringBuilder.append(samplerName);
            stringBuilder.append(" tex;\n");
            stringBuilder.append(genericFragmentSource2.replace("sample(", "texture2D(tex, "));
        }
        return stringBuilder.toString();
    }

    public GlGenericDrawer(String genericFragmentSource2, ShaderCallbacks shaderCallbacks2) {
        this(DEFAULT_VERTEX_SHADER_STRING, genericFragmentSource2, shaderCallbacks2);
    }

    public GlGenericDrawer(String vertexShader2, String genericFragmentSource2, ShaderCallbacks shaderCallbacks2) {
        this.vertexShader = vertexShader2;
        this.genericFragmentSource = genericFragmentSource2;
        this.shaderCallbacks = shaderCallbacks2;
    }

    /* access modifiers changed from: package-private */
    public GlShader createShader(ShaderType shaderType) {
        return new GlShader(this.vertexShader, createFragmentShaderString(this.genericFragmentSource, shaderType));
    }

    public void drawOes(int oesTextureId, float[] texMatrix, int frameWidth, int frameHeight, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        prepareShader(ShaderType.OES, texMatrix, frameWidth, frameHeight, viewportWidth, viewportHeight);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, oesTextureId);
        GLES20.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glBindTexture(36197, 0);
    }

    public void drawRgb(int textureId, float[] texMatrix, int frameWidth, int frameHeight, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        prepareShader(ShaderType.RGB, texMatrix, frameWidth, frameHeight, viewportWidth, viewportHeight);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, textureId);
        GLES20.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glBindTexture(3553, 0);
    }

    public void drawYuv(int[] yuvTextures, float[] texMatrix, int frameWidth, int frameHeight, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        prepareShader(ShaderType.YUV, texMatrix, frameWidth, frameHeight, viewportWidth, viewportHeight);
        for (int i = 0; i < 3; i++) {
            GLES20.glActiveTexture(33984 + i);
            GLES20.glBindTexture(3553, yuvTextures[i]);
        }
        GLES20.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        GLES20.glDrawArrays(5, 0, 4);
        for (int i2 = 0; i2 < 3; i2++) {
            GLES20.glActiveTexture(i2 + 33984);
            GLES20.glBindTexture(3553, 0);
        }
    }

    private void prepareShader(ShaderType shaderType, float[] texMatrix, int frameWidth, int frameHeight, int viewportWidth, int viewportHeight) {
        GlShader shader;
        ShaderType shaderType2 = shaderType;
        if (shaderType.equals(this.currentShaderType)) {
            shader = this.currentShader;
        } else {
            this.currentShaderType = null;
            GlShader glShader = this.currentShader;
            if (glShader != null) {
                glShader.release();
                this.currentShader = null;
            }
            shader = createShader(shaderType);
            this.currentShaderType = shaderType2;
            this.currentShader = shader;
            shader.useProgram();
            if (shaderType2 == ShaderType.YUV) {
                GLES20.glUniform1i(shader.getUniformLocation("y_tex"), 0);
                GLES20.glUniform1i(shader.getUniformLocation("u_tex"), 1);
                GLES20.glUniform1i(shader.getUniformLocation("v_tex"), 2);
            } else {
                GLES20.glUniform1i(shader.getUniformLocation("tex"), 0);
            }
            GlUtil.checkNoGLES2Error("Create shader");
            this.shaderCallbacks.onNewShader(shader);
            this.texMatrixLocation = shader.getUniformLocation(TEXTURE_MATRIX_NAME);
            this.inPosLocation = shader.getAttribLocation(INPUT_VERTEX_COORDINATE_NAME);
            this.inTcLocation = shader.getAttribLocation(INPUT_TEXTURE_COORDINATE_NAME);
        }
        shader.useProgram();
        GLES20.glEnableVertexAttribArray(this.inPosLocation);
        GLES20.glVertexAttribPointer(this.inPosLocation, 2, 5126, false, 0, FULL_RECTANGLE_BUFFER);
        GLES20.glEnableVertexAttribArray(this.inTcLocation);
        GLES20.glVertexAttribPointer(this.inTcLocation, 2, 5126, false, 0, FULL_RECTANGLE_TEXTURE_BUFFER);
        GLES20.glUniformMatrix4fv(this.texMatrixLocation, 1, false, texMatrix, 0);
        this.shaderCallbacks.onPrepareShader(shader, texMatrix, frameWidth, frameHeight, viewportWidth, viewportHeight);
        GlUtil.checkNoGLES2Error("Prepare shader");
    }

    public void release() {
        GlShader glShader = this.currentShader;
        if (glShader != null) {
            glShader.release();
            this.currentShader = null;
            this.currentShaderType = null;
        }
    }
}
