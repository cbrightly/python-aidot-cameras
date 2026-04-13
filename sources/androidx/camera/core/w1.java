package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: lambda */
public final /* synthetic */ class w1 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ AtomicReference a;

    public /* synthetic */ w1(AtomicReference atomicReference) {
        this.a = atomicReference;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.set(completer);
    }
}
