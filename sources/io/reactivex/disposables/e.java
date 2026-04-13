package io.reactivex.disposables;

/* compiled from: RunnableDisposable */
public final class e extends d<Runnable> {
    private static final long serialVersionUID = -8219729196779211169L;

    e(Runnable value) {
        super(value);
    }

    /* access modifiers changed from: protected */
    public void onDisposed(Runnable value) {
        value.run();
    }

    public String toString() {
        return "RunnableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }
}
