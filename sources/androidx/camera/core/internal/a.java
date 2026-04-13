package androidx.camera.core.internal;

import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ List c;

    public /* synthetic */ a(List list) {
        this.c = list;
    }

    public final void run() {
        CameraUseCaseAdapter.lambda$notifyAttachedUseCasesChange$0(this.c);
    }
}
