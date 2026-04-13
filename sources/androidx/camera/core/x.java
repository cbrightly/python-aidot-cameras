package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class x implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageCapture.CaptureCallbackChecker a;
    public final /* synthetic */ ImageCapture.CaptureCallbackChecker.CaptureResultChecker b;
    public final /* synthetic */ long c;
    public final /* synthetic */ long d;
    public final /* synthetic */ Object e;

    public /* synthetic */ x(ImageCapture.CaptureCallbackChecker captureCallbackChecker, ImageCapture.CaptureCallbackChecker.CaptureResultChecker captureResultChecker, long j, long j2, Object obj) {
        this.a = captureCallbackChecker;
        this.b = captureResultChecker;
        this.c = j;
        this.d = j2;
        this.e = obj;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.a(this.b, this.c, this.d, this.e, completer);
    }
}
