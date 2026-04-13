package coil.request;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: lambda */
public final /* synthetic */ class a implements LifecycleOwner {
    public static final /* synthetic */ a c = new a();

    private /* synthetic */ a() {
    }

    public final Lifecycle getLifecycle() {
        return GlobalLifecycle.b();
    }
}
