package com.airbnb.lottie;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class b implements Callable {
    public final /* synthetic */ LottieAnimationView c;
    public final /* synthetic */ String d;

    public /* synthetic */ b(LottieAnimationView lottieAnimationView, String str) {
        this.c = lottieAnimationView;
        this.d = str;
    }

    public final Object call() {
        return this.c.p(this.d);
    }
}
