package androidx.camera.core.impl;

import androidx.camera.core.impl.UseCaseAttachState;

/* compiled from: lambda */
public final /* synthetic */ class r implements UseCaseAttachState.AttachStateFilter {
    public static final /* synthetic */ r a = new r();

    private /* synthetic */ r() {
    }

    public final boolean filter(UseCaseAttachState.UseCaseAttachInfo useCaseAttachInfo) {
        return useCaseAttachInfo.getAttached();
    }
}
