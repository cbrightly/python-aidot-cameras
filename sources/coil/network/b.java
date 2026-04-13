package coil.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import androidx.annotation.MainThread;
import androidx.core.content.ContextCompat;
import coil.util.g;
import coil.util.m;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NetworkObserver.kt */
public interface b {
    @NotNull
    public static final a a = a.a;

    /* renamed from: coil.network.b$b  reason: collision with other inner class name */
    /* compiled from: NetworkObserver.kt */
    public interface C0007b {
        @MainThread
        void a(boolean z);
    }

    boolean a();

    void shutdown();

    /* compiled from: NetworkObserver.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        @NotNull
        public final b a(@NotNull Context context, boolean isEnabled, @NotNull C0007b listener, @Nullable m logger) {
            k.e(context, "context");
            k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
            if (!isEnabled) {
                return a.b;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) ContextCompat.getSystemService(context, ConnectivityManager.class);
            if (connectivityManager != null) {
                if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") == 0) {
                    try {
                        if (Build.VERSION.SDK_INT >= 21) {
                            return new c(connectivityManager, listener);
                        }
                        return new NetworkObserverApi14(context, connectivityManager, listener);
                    } catch (Exception e) {
                        if (logger != null) {
                            g.a(logger, "NetworkObserver", new RuntimeException("Failed to register network observer.", e));
                        }
                        return a.b;
                    }
                }
            }
            if (logger != null) {
                m $this$log$iv = logger;
                if ($this$log$iv.b() <= 5) {
                    $this$log$iv.a("NetworkObserver", 5, "Unable to register network observer.", (Throwable) null);
                }
            }
            return a.b;
        }
    }
}
