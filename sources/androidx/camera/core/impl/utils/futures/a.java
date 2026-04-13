package androidx.camera.core.impl.utils.futures;

import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class a implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ListenableFuture a;

    public /* synthetic */ a(ListenableFuture listenableFuture) {
        this.a = listenableFuture;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return Futures.propagateTransform(false, this.a, Futures.IDENTITY_FUNCTION, completer, CameraXExecutors.directExecutor());
    }
}
