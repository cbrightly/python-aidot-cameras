package androidx.camera.core;

import android.content.Context;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class j implements AsyncFunction {
    public final /* synthetic */ CameraX a;
    public final /* synthetic */ Context b;

    public /* synthetic */ j(CameraX cameraX, Context context) {
        this.a = cameraX;
        this.b = context;
    }

    public final ListenableFuture apply(Object obj) {
        return this.a.initInternal(this.b);
    }
}
