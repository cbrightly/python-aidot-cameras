package androidx.lifecycle;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;

/* compiled from: lambda */
public final /* synthetic */ class c implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ SavedStateHandle a;

    public /* synthetic */ c(SavedStateHandle savedStateHandle) {
        this.a = savedStateHandle;
    }

    public final Bundle saveState() {
        return SavedStateHandle.m2savedStateProvider$lambda0(this.a);
    }
}
