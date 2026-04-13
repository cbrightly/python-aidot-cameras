package coil.util;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.d;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;

/* renamed from: coil.util.-Lifecycles$observeStarted$2$1  reason: invalid class name */
/* compiled from: Lifecycles.kt */
public final class Lifecycles$observeStarted$2$1 implements DefaultLifecycleObserver {
    final /* synthetic */ n<x> c;

    public /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        d.a(this, lifecycleOwner);
    }

    public /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        d.b(this, lifecycleOwner);
    }

    public /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        d.c(this, lifecycleOwner);
    }

    public /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        d.d(this, lifecycleOwner);
    }

    public /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        d.f(this, lifecycleOwner);
    }

    Lifecycles$observeStarted$2$1(n<? super x> $continuation) {
        this.c = $continuation;
    }

    public void onStart(@NotNull LifecycleOwner owner) {
        k.e(owner, "owner");
        n<x> nVar = this.c;
        x xVar = x.a;
        o.a aVar = o.Companion;
        nVar.resumeWith(o.m17constructorimpl(xVar));
    }
}
