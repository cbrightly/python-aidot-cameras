package androidx.camera.core.impl;

import androidx.camera.core.impl.UseCaseAttachState;

/* compiled from: lambda */
public final /* synthetic */ class s implements UseCaseAttachState.AttachStateFilter {
    public static final /* synthetic */ s a = new s();

    private /* synthetic */ s() {
    }

    public final boolean filter(UseCaseAttachState.UseCaseAttachInfo useCaseAttachInfo) {
        return UseCaseAttachState.lambda$getActiveAndAttachedSessionConfigs$1(useCaseAttachInfo);
    }
}
