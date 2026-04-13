package com.bumptech.glide.manager;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(26)
/* compiled from: FirstFrameAndAfterTrimMemoryWaiter */
public final class h implements j, ComponentCallbacks2 {
    h() {
    }

    public void a(Activity activity) {
    }

    public void onTrimMemory(int level) {
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
    }

    public void onLowMemory() {
        onTrimMemory(20);
    }
}
