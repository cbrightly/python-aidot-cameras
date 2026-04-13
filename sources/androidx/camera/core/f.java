package androidx.camera.core;

import android.content.Context;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class f implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ CameraX a;
    public final /* synthetic */ Context b;

    public /* synthetic */ f(CameraX cameraX, Context context) {
        this.a = cameraX;
        this.b = context;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return CameraX.lambda$initializeInstanceLocked$3(this.a, this.b, completer);
    }
}
