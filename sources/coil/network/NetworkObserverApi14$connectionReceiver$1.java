package coil.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import coil.network.b;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NetworkObserver.kt */
public final class NetworkObserverApi14$connectionReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ b.C0007b a;
    final /* synthetic */ NetworkObserverApi14 b;

    NetworkObserverApi14$connectionReceiver$1(b.C0007b $listener, NetworkObserverApi14 $receiver) {
        this.a = $listener;
        this.b = $receiver;
    }

    public void onReceive(@NotNull Context context, @Nullable Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        k.e(context, "context");
        if (k.a(intent == null ? null : intent.getAction(), "android.net.conn.CONNECTIVITY_CHANGE")) {
            this.a.a(this.b.a());
        }
    }
}
