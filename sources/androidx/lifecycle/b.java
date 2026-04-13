package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlinx.coroutines.z1;

/* compiled from: lambda */
public final /* synthetic */ class b implements LifecycleEventObserver {
    public final /* synthetic */ LifecycleController c;
    public final /* synthetic */ z1 d;

    public /* synthetic */ b(LifecycleController lifecycleController, z1 z1Var) {
        this.c = lifecycleController;
        this.d = z1Var;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        LifecycleController.m1observer$lambda0(this.c, this.d, lifecycleOwner, event);
    }
}
