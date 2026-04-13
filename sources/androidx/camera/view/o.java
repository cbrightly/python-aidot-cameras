package androidx.camera.view;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class o implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ TextureViewImplementation a;

    public /* synthetic */ o(TextureViewImplementation textureViewImplementation) {
        this.a = textureViewImplementation;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.d(completer);
    }
}
