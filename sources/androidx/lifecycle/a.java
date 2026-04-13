package androidx.lifecycle;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ DispatchQueue c;
    public final /* synthetic */ Runnable d;

    public /* synthetic */ a(DispatchQueue dispatchQueue, Runnable runnable) {
        this.c = dispatchQueue;
        this.d = runnable;
    }

    public final void run() {
        DispatchQueue.m0dispatchAndEnqueue$lambda2$lambda1(this.c, this.d);
    }
}
