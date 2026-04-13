package androidx.camera.view;

import androidx.camera.core.impl.utils.futures.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class g implements AsyncFunction {
    public final /* synthetic */ PreviewStreamStateObserver a;

    public /* synthetic */ g(PreviewStreamStateObserver previewStreamStateObserver) {
        this.a = previewStreamStateObserver;
    }

    public final ListenableFuture apply(Object obj) {
        return this.a.a((Void) obj);
    }
}
