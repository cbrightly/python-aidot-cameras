package androidx.camera.core;

import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureStage;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class h0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageCapture a;
    public final /* synthetic */ CaptureConfig.Builder b;
    public final /* synthetic */ List c;
    public final /* synthetic */ CaptureStage d;

    public /* synthetic */ h0(ImageCapture imageCapture, CaptureConfig.Builder builder, List list, CaptureStage captureStage) {
        this.a = imageCapture;
        this.b = builder;
        this.c = list;
        this.d = captureStage;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.c(this.b, this.c, this.d, completer);
    }
}
