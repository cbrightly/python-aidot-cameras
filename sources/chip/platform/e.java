package chip.platform;

import chip.platform.NsdManagerServiceResolver;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    public final /* synthetic */ NsdManagerServiceResolver.AnonymousClass4 c;
    public final /* synthetic */ ChipMdnsCallback d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;
    public final /* synthetic */ long x;
    public final /* synthetic */ long y;

    public /* synthetic */ e(NsdManagerServiceResolver.AnonymousClass4 r1, ChipMdnsCallback chipMdnsCallback, String str, String str2, long j, long j2) {
        this.c = r1;
        this.d = chipMdnsCallback;
        this.f = str;
        this.q = str2;
        this.x = j;
        this.y = j2;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q, this.x, this.y);
    }
}
