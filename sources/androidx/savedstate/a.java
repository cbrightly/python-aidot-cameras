package androidx.savedstate;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: lambda */
public final /* synthetic */ class a implements LifecycleEventObserver {
    public final /* synthetic */ SavedStateRegistry c;

    public /* synthetic */ a(SavedStateRegistry savedStateRegistry) {
        this.c = savedStateRegistry;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        SavedStateRegistry.m3performAttach$lambda4(this.c, lifecycleOwner, event);
    }
}
