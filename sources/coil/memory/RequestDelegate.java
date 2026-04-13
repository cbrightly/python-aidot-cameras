package coil.memory;

import androidx.annotation.MainThread;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestDelegate.kt */
public abstract class RequestDelegate implements DefaultLifecycleObserver {
    public /* synthetic */ RequestDelegate(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        d.a(this, lifecycleOwner);
    }

    public /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        d.c(this, lifecycleOwner);
    }

    public /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        d.d(this, lifecycleOwner);
    }

    public /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
        d.e(this, lifecycleOwner);
    }

    public /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        d.f(this, lifecycleOwner);
    }

    private RequestDelegate() {
    }

    @MainThread
    public void a() {
    }

    @MainThread
    public void b() {
    }

    @MainThread
    public void onDestroy(@NotNull LifecycleOwner owner) {
        k.e(owner, "owner");
        b();
    }
}
