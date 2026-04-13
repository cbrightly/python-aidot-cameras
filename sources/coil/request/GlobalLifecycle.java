package coil.request;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: GlobalLifecycle.kt */
public final class GlobalLifecycle extends Lifecycle {
    @NotNull
    public static final GlobalLifecycle a = new GlobalLifecycle();
    @NotNull
    private static final LifecycleOwner b = a.c;

    private GlobalLifecycle() {
    }

    /* access modifiers changed from: private */
    public static final Lifecycle b() {
        return a;
    }

    public void addObserver(@NotNull LifecycleObserver observer) {
        k.e(observer, "observer");
        if (observer instanceof DefaultLifecycleObserver) {
            LifecycleOwner lifecycleOwner = b;
            ((DefaultLifecycleObserver) observer).onCreate(lifecycleOwner);
            ((DefaultLifecycleObserver) observer).onStart(lifecycleOwner);
            ((DefaultLifecycleObserver) observer).onResume(lifecycleOwner);
            return;
        }
        throw new IllegalArgumentException((observer + " must implement androidx.lifecycle.DefaultLifecycleObserver.").toString());
    }

    public void removeObserver(@NotNull LifecycleObserver observer) {
        k.e(observer, "observer");
    }

    @NotNull
    public Lifecycle.State getCurrentState() {
        return Lifecycle.State.RESUMED;
    }

    @NotNull
    public String toString() {
        return "coil.request.GlobalLifecycle";
    }
}
