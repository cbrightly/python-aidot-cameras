package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: lambda */
public final /* synthetic */ class n1 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ AtomicReference a;
    public final /* synthetic */ String b;

    public /* synthetic */ n1(AtomicReference atomicReference, String str) {
        this.a = atomicReference;
        this.b = str;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.set(completer);
    }
}
