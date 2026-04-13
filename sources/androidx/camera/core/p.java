package androidx.camera.core;

import android.util.Size;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.core.impl.SessionConfig;

/* compiled from: lambda */
public final /* synthetic */ class p implements SessionConfig.ErrorListener {
    public final /* synthetic */ ImageAnalysis a;
    public final /* synthetic */ String b;
    public final /* synthetic */ ImageAnalysisConfig c;
    public final /* synthetic */ Size d;

    public /* synthetic */ p(ImageAnalysis imageAnalysis, String str, ImageAnalysisConfig imageAnalysisConfig, Size size) {
        this.a = imageAnalysis;
        this.b = str;
        this.c = imageAnalysisConfig;
        this.d = size;
    }

    public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        this.a.a(this.b, this.c, this.d, sessionConfig, sessionError);
    }
}
