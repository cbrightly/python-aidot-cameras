package androidx.camera.core;

import android.util.Size;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.SessionConfig;

/* compiled from: lambda */
public final /* synthetic */ class e0 implements SessionConfig.ErrorListener {
    public final /* synthetic */ ImageCapture a;
    public final /* synthetic */ String b;
    public final /* synthetic */ ImageCaptureConfig c;
    public final /* synthetic */ Size d;

    public /* synthetic */ e0(ImageCapture imageCapture, String str, ImageCaptureConfig imageCaptureConfig, Size size) {
        this.a = imageCapture;
        this.b = str;
        this.c = imageCaptureConfig;
        this.d = size;
    }

    public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        this.a.b(this.b, this.c, this.d, sessionConfig, sessionError);
    }
}
