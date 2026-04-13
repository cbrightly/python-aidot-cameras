package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class d implements Callable {
    public final /* synthetic */ Context c;
    public final /* synthetic */ Intent d;

    public /* synthetic */ d(Context context, Intent intent) {
        this.c = context;
        this.d = intent;
    }

    public final Object call() {
        return Integer.valueOf(ServiceStarter.getInstance().startMessagingService(this.c, this.d));
    }
}
