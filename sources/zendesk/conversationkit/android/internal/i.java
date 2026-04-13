package zendesk.conversationkit.android.internal;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.c;

/* compiled from: ConnectivityObserver.kt */
public final class i extends ConnectivityManager.NetworkCallback {
    @Nullable
    private final ConnectivityManager a;
    @NotNull
    private final o0 b;
    @NotNull
    private d c = new c0();

    public i(@Nullable ConnectivityManager connectivityManager, @NotNull o0 coroutineScope) {
        k.e(coroutineScope, "coroutineScope");
        this.a = connectivityManager;
        this.b = coroutineScope;
    }

    @NotNull
    public final d a() {
        return this.c;
    }

    public final void b(@NotNull d actionDispatcher) {
        k.e(actionDispatcher, "actionDispatcher");
        this.c = actionDispatcher;
        NetworkRequest networkRequest = new NetworkRequest.Builder().addCapability(12).build();
        ConnectivityManager connectivityManager = this.a;
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(networkRequest, this);
        }
    }

    @f(c = "zendesk.conversationkit.android.internal.ConnectivityObserver$onAvailable$1", f = "ConnectivityObserver.kt", l = {41}, m = "invokeSuspend")
    /* compiled from: ConnectivityObserver.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(i iVar, d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = iVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new a(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    d a = this.this$0.a();
                    c.k kVar = new c.k(zendesk.conversationkit.android.a.CONNECTED);
                    this.label = 1;
                    if (a.a(kVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public void onAvailable(@NotNull Network network) {
        k.e(network, "network");
        z1 unused = j.d(this.b, (g) null, (q0) null, new a(this, (d<? super a>) null), 3, (Object) null);
    }

    @f(c = "zendesk.conversationkit.android.internal.ConnectivityObserver$onLost$1", f = "ConnectivityObserver.kt", l = {49}, m = "invokeSuspend")
    /* compiled from: ConnectivityObserver.kt */
    public static final class b extends l implements p<o0, d<? super x>, Object> {
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(i iVar, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = iVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new b(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    d a = this.this$0.a();
                    c.k kVar = new c.k(zendesk.conversationkit.android.a.DISCONNECTED);
                    this.label = 1;
                    if (a.a(kVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public void onLost(@NotNull Network network) {
        k.e(network, "network");
        z1 unused = j.d(this.b, (g) null, (q0) null, new b(this, (d<? super b>) null), 3, (Object) null);
    }
}
