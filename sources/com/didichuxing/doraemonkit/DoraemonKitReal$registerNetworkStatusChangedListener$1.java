package com.didichuxing.doraemonkit;

import android.util.Log;
import com.blankj.utilcode.util.NetworkUtils;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DoraemonKitReal.kt */
public final class DoraemonKitReal$registerNetworkStatusChangedListener$1 implements NetworkUtils.b {
    DoraemonKitReal$registerNetworkStatusChangedListener$1() {
    }

    public void onDisconnected() {
        Log.i("Doraemon", "当前网络已断开");
    }

    public void onConnected(@NotNull NetworkUtils.a networkType) {
        k.f(networkType, "networkType");
        Log.i("Doraemon", "当前网络类型" + networkType.name());
    }
}
