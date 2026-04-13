package androidx.camera.view;

import androidx.camera.core.SurfaceRequest;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class u implements Consumer {
    public final /* synthetic */ CallbackToFutureAdapter.Completer a;

    public /* synthetic */ u(CallbackToFutureAdapter.Completer completer) {
        this.a = completer;
    }

    public final void accept(Object obj) {
        this.a.set((SurfaceRequest.Result) obj);
    }
}
