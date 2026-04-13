package coil.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import coil.network.b;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

@SuppressLint({"MissingPermission"})
/* compiled from: NetworkObserver.kt */
public final class NetworkObserverApi14 implements b {
    @NotNull
    private final Context b;
    @NotNull
    private final ConnectivityManager c;
    @NotNull
    private final NetworkObserverApi14$connectionReceiver$1 d;

    public NetworkObserverApi14(@NotNull Context context, @NotNull ConnectivityManager connectivityManager, @NotNull b.C0007b listener) {
        k.e(context, "context");
        k.e(connectivityManager, "connectivityManager");
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.b = context;
        this.c = connectivityManager;
        NetworkObserverApi14$connectionReceiver$1 networkObserverApi14$connectionReceiver$1 = new NetworkObserverApi14$connectionReceiver$1(listener, this);
        this.d = networkObserverApi14$connectionReceiver$1;
        context.registerReceiver(networkObserverApi14$connectionReceiver$1, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public boolean a() {
        NetworkInfo activeNetworkInfo = this.c.getActiveNetworkInfo();
        return k.a(activeNetworkInfo == null ? null : Boolean.valueOf(activeNetworkInfo.isConnectedOrConnecting()), true);
    }

    public void shutdown() {
        this.b.unregisterReceiver(this.d);
    }
}
