package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class c implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ CameraRepository a;

    public /* synthetic */ c(CameraRepository cameraRepository) {
        this.a = cameraRepository;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.a(completer);
    }
}
