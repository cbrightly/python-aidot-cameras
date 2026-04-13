package androidx.camera.core;

import android.util.Size;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.SessionConfig;

/* compiled from: lambda */
public final /* synthetic */ class z0 implements SessionConfig.ErrorListener {
    public final /* synthetic */ Preview a;
    public final /* synthetic */ String b;
    public final /* synthetic */ PreviewConfig c;
    public final /* synthetic */ Size d;

    public /* synthetic */ z0(Preview preview, String str, PreviewConfig previewConfig, Size size) {
        this.a = preview;
        this.b = str;
        this.c = previewConfig;
        this.d = size;
    }

    public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        this.a.a(this.b, this.c, this.d, sessionConfig, sessionError);
    }
}
