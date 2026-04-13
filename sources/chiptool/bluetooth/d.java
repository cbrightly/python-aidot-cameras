package chiptool.bluetooth;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ BLEProxy c;

    public /* synthetic */ d(BLEProxy bLEProxy) {
        this.c = bLEProxy;
    }

    public final void run() {
        BLEProxy.m5connectTimeoutTask$lambda1(this.c);
    }
}
