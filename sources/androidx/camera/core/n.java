package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class n implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ CameraX a;

    public /* synthetic */ n(CameraX cameraX) {
        this.a = cameraX;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return CameraX.lambda$shutdownLocked$5(this.a, completer);
    }
}
