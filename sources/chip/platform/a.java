package chip.platform;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ NsdManagerServiceBrowser c;
    public final /* synthetic */ String d;
    public final /* synthetic */ long f;
    public final /* synthetic */ long q;
    public final /* synthetic */ ChipMdnsCallback x;

    public /* synthetic */ a(NsdManagerServiceBrowser nsdManagerServiceBrowser, String str, long j, long j2, ChipMdnsCallback chipMdnsCallback) {
        this.c = nsdManagerServiceBrowser;
        this.d = str;
        this.f = j;
        this.q = j2;
        this.x = chipMdnsCallback;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q, this.x);
    }
}
