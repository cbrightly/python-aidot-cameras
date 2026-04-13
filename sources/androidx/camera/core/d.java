package androidx.camera.core;

import android.content.Context;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class d implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ CameraX a;
    public final /* synthetic */ Context b;

    public /* synthetic */ d(CameraX cameraX, Context context) {
        this.a = cameraX;
        this.b = context;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.c(this.b, completer);
    }
}
