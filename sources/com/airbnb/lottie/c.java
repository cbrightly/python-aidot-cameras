package com.airbnb.lottie;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class c implements Callable {
    public final /* synthetic */ LottieAnimationView c;
    public final /* synthetic */ int d;

    public /* synthetic */ c(LottieAnimationView lottieAnimationView, int i) {
        this.c = lottieAnimationView;
        this.d = i;
    }

    public final Object call() {
        return this.c.r(this.d);
    }
}
