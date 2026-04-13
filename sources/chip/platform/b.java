package chip.platform;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ NsdManagerServiceBrowser c;
    public final /* synthetic */ long d;
    public final /* synthetic */ ChipMdnsCallback f;

    public /* synthetic */ b(NsdManagerServiceBrowser nsdManagerServiceBrowser, long j, ChipMdnsCallback chipMdnsCallback) {
        this.c = nsdManagerServiceBrowser;
        this.d = j;
        this.f = chipMdnsCallback;
    }

    public final void run() {
        this.c.a(this.d, this.f);
    }
}
