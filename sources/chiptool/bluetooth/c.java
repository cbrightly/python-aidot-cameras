package chiptool.bluetooth;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ BLEProxy c;

    public /* synthetic */ c(BLEProxy bLEProxy) {
        this.c = bLEProxy;
    }

    public final void run() {
        BLEProxy.m7serviceDiscoverTimeoutTask$lambda3(this.c);
    }
}
