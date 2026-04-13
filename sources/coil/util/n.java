package coil.util;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import coil.e;
import coil.network.b;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SystemCallbacks.kt */
public final class n implements ComponentCallbacks2, b.C0007b {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Context d;
    @NotNull
    private final WeakReference<e> f;
    @NotNull
    private final b q;
    private volatile boolean x;
    @NotNull
    private final AtomicBoolean y = new AtomicBoolean(false);

    public n(@NotNull e imageLoader, @NotNull Context context, boolean isNetworkObserverEnabled) {
        k.e(imageLoader, "imageLoader");
        k.e(context, "context");
        this.d = context;
        this.f = new WeakReference<>(imageLoader);
        b a2 = b.a.a(context, isNetworkObserverEnabled, this, imageLoader.j());
        this.q = a2;
        this.x = a2.a();
        context.registerComponentCallbacks(this);
    }

    public final boolean b() {
        return this.x;
    }

    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        k.e(newConfig, "newConfig");
        if (((e) this.f.get()) == null) {
            c();
        }
    }

    public void onTrimMemory(int level) {
        x xVar;
        e eVar = (e) this.f.get();
        if (eVar == null) {
            xVar = null;
        } else {
            eVar.n(level);
            xVar = x.a;
        }
        if (xVar == null) {
            c();
        }
    }

    public void onLowMemory() {
        onTrimMemory(80);
    }

    public void a(boolean isOnline) {
        e imageLoader = (e) this.f.get();
        if (imageLoader == null) {
            c();
            return;
        }
        this.x = isOnline;
        m $this$log$iv = imageLoader.j();
        if ($this$log$iv != null && $this$log$iv.b() <= 4) {
            $this$log$iv.a("NetworkObserver", 4, isOnline ? "ONLINE" : "OFFLINE", (Throwable) null);
        }
    }

    public final void c() {
        if (!this.y.getAndSet(true)) {
            this.d.unregisterComponentCallbacks(this);
            this.q.shutdown();
        }
    }

    /* compiled from: SystemCallbacks.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
