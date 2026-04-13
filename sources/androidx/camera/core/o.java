package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class o implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ CameraX a;

    public /* synthetic */ o(CameraX cameraX) {
        this.a = cameraX;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.e(completer);
    }
}
