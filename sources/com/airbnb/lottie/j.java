package com.airbnb.lottie;

import android.content.Context;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class j implements Callable {
    public final /* synthetic */ Context c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;

    public /* synthetic */ j(Context context, String str, String str2) {
        this.c = context;
        this.d = str;
        this.f = str2;
    }

    public final Object call() {
        return d0.B(this.c, this.d, this.f);
    }
}
