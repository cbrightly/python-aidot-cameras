package zendesk.messaging.android.internal;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.flow.c;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.z;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ProcessLifecycleObserver.kt */
public final class ProcessLifecycleObserver implements LifecycleObserver {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    @NotNull
    private q<Boolean> d = z.a(false);

    @NotNull
    public final c<Boolean> a() {
        return this.d;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public final void onAppForegrounded() {
        this.d.setValue(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public final void onAppBackgrounded() {
        this.d.setValue(false);
    }

    /* compiled from: ProcessLifecycleObserver.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final ProcessLifecycleObserver a() {
            ProcessLifecycleObserver it = new ProcessLifecycleObserver();
            ProcessLifecycleOwner.get().getLifecycle().addObserver(it);
            return it;
        }

        @NotNull
        public final o0 b() {
            LifecycleOwner lifecycleOwner = ProcessLifecycleOwner.get();
            k.d(lifecycleOwner, "get()");
            return LifecycleOwnerKt.getLifecycleScope(lifecycleOwner);
        }
    }
}
