package com.google.firebase.heartbeatinfo;

import android.content.Context;
import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class c implements Provider {
    public final /* synthetic */ Context a;
    public final /* synthetic */ String b;

    public /* synthetic */ c(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public final Object get() {
        return DefaultHeartBeatController.lambda$new$2(this.a, this.b);
    }
}
