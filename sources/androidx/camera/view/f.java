package androidx.camera.view;

import androidx.camera.core.CameraInfo;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class f implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ PreviewStreamStateObserver a;
    public final /* synthetic */ CameraInfo b;
    public final /* synthetic */ List c;

    public /* synthetic */ f(PreviewStreamStateObserver previewStreamStateObserver, CameraInfo cameraInfo, List list) {
        this.a = previewStreamStateObserver;
        this.b = cameraInfo;
        this.c = list;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.c(this.b, this.c, completer);
    }
}
