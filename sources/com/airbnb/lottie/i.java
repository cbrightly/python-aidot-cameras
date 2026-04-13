package com.airbnb.lottie;

import java.io.InputStream;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class i implements Callable {
    public final /* synthetic */ InputStream c;
    public final /* synthetic */ String d;

    public /* synthetic */ i(InputStream inputStream, String str) {
        this.c = inputStream;
        this.d = str;
    }

    public final Object call() {
        return d0.h(this.c, this.d);
    }
}
