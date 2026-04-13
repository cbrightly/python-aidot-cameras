package coil.network;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import androidx.annotation.RequiresApi;
import coil.network.b;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

@RequiresApi(21)
@SuppressLint({"MissingPermission"})
/* compiled from: NetworkObserver.kt */
public final class c implements b {
    @NotNull
    private final ConnectivityManager b;
    @NotNull
    private final b.C0007b c;
    @NotNull
    private final a d;

    public c(@NotNull ConnectivityManager connectivityManager, @NotNull b.C0007b listener) {
        k.e(connectivityManager, "connectivityManager");
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.b = connectivityManager;
        this.c = listener;
        a aVar = new a(this);
        this.d = aVar;
        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().addCapability(12).build(), aVar);
    }

    /* compiled from: NetworkObserver.kt */
    public static final class a extends ConnectivityManager.NetworkCallback {
        final /* synthetic */ c a;

        a(c $receiver) {
            this.a = $receiver;
        }

        public void onAvailable(@NotNull Network network) {
            k.e(network, "network");
            this.a.d(network, true);
        }

        public void onLost(@NotNull Network network) {
            k.e(network, "network");
            this.a.d(network, false);
        }
    }

    public boolean a() {
        Network[] allNetworks = this.b.getAllNetworks();
        k.d(allNetworks, "connectivityManager.allNetworks");
        for (Network it : allNetworks) {
            k.d(it, "it");
            if (c(it)) {
                return true;
            }
        }
        return false;
    }

    public void shutdown() {
        this.b.unregisterNetworkCallback(this.d);
    }

    /* access modifiers changed from: private */
    public final void d(Network network, boolean isOnline) {
        boolean z;
        Network[] allNetworks = this.b.getAllNetworks();
        k.d(allNetworks, "connectivityManager.allNetworks");
        int length = allNetworks.length;
        boolean isAnyOnline = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Network it = allNetworks[i];
            if (k.a(it, network)) {
                z = isOnline;
            } else {
                k.d(it, "it");
                z = c(it);
            }
            if (z) {
                isAnyOnline = true;
                break;
            }
            i++;
        }
        this.c.a(isAnyOnline);
    }

    private final boolean c(Network $this$isOnline) {
        NetworkCapabilities capabilities = this.b.getNetworkCapabilities($this$isOnline);
        return capabilities != null && capabilities.hasCapability(12);
    }
}
