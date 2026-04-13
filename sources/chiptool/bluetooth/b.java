package chiptool.bluetooth;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ BLEProxy c;

    public /* synthetic */ b(BLEProxy bLEProxy) {
        this.c = bLEProxy;
    }

    public final void run() {
        BLEProxy.m6serviceDiscoverTask$lambda2(this.c);
    }
}
