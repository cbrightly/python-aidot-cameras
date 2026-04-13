package androidx.constraintlayout.core.state;

/* compiled from: lambda */
public final /* synthetic */ class f implements Interpolator {
    public final /* synthetic */ String a;

    public /* synthetic */ f(String str) {
        this.a = str;
    }

    public final float getInterpolation(float f) {
        return Transition.lambda$getInterpolator$0(this.a, f);
    }
}
