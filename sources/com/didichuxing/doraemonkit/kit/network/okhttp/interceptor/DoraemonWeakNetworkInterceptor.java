package com.didichuxing.doraemonkit.kit.network.okhttp.interceptor;

import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import okhttp3.d0;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;

public class DoraemonWeakNetworkInterceptor implements w {
    private static final String TAG = "DoraemonWeakNetworkInterceptor";

    @NotNull
    public d0 intercept(w.a chain) {
        if (!WeakNetworkManager.get().isActive()) {
            return chain.a(chain.g());
        }
        switch (WeakNetworkManager.get().getType()) {
            case 1:
                return WeakNetworkManager.get().simulateTimeOut(chain);
            case 2:
                return WeakNetworkManager.get().simulateSpeedLimit(chain);
            default:
                return WeakNetworkManager.get().simulateOffNetwork(chain);
        }
    }
}
