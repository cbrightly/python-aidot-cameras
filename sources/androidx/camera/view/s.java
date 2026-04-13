package androidx.camera.view;

import android.view.Surface;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class s implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ TextureViewImplementation a;
    public final /* synthetic */ Surface b;

    public /* synthetic */ s(TextureViewImplementation textureViewImplementation, Surface surface) {
        this.a = textureViewImplementation;
        this.b = surface;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.b(this.b, completer);
    }
}
