package chiptool.bluetooth;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ BLEProxy c;

    public /* synthetic */ a(BLEProxy bLEProxy) {
        this.c = bLEProxy;
    }

    public final void run() {
        BLEProxy.m4connectTask$lambda0(this.c);
    }
}
