package androidx.camera.view;

import androidx.arch.core.util.Function;

/* compiled from: lambda */
public final /* synthetic */ class e implements Function {
    public final /* synthetic */ PreviewStreamStateObserver a;

    public /* synthetic */ e(PreviewStreamStateObserver previewStreamStateObserver) {
        this.a = previewStreamStateObserver;
    }

    public final Object apply(Object obj) {
        this.a.b((Void) obj);
        return null;
    }
}
