package org.webrtc;

import android.content.Context;
import android.graphics.Matrix;
import android.view.WindowManager;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import org.webrtc.VideoFrame;

/* compiled from: CameraSession */
public final /* synthetic */ class p0 {
    public static int b(Context context) {
        switch (((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                return 0;
        }
    }

    public static VideoFrame.TextureBuffer a(TextureBufferImpl buffer, boolean mirror, int rotation) {
        Matrix transformMatrix = new Matrix();
        transformMatrix.preTranslate(0.5f, 0.5f);
        if (mirror) {
            transformMatrix.preScale(-1.0f, 1.0f);
        }
        transformMatrix.preRotate((float) rotation);
        transformMatrix.preTranslate(-0.5f, -0.5f);
        return buffer.applyTransformMatrix(transformMatrix, buffer.getWidth(), buffer.getHeight());
    }
}
