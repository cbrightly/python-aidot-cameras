package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class c1 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ProcessingImageReader a;

    public /* synthetic */ c1(ProcessingImageReader processingImageReader) {
        this.a = processingImageReader;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.a(completer);
    }
}
