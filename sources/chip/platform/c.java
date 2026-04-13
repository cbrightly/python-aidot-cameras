package chip.platform;

import chip.platform.NsdManagerServiceResolver;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ long a1;
    public final /* synthetic */ Runnable a2;
    public final /* synthetic */ NsdManagerServiceResolver.AnonymousClass4.AnonymousClass1 c;
    public final /* synthetic */ Map d;
    public final /* synthetic */ String f;
    public final /* synthetic */ int p0;
    public final /* synthetic */ long p1;
    public final /* synthetic */ byte[] q;
    public final /* synthetic */ String x;
    public final /* synthetic */ ChipMdnsCallback y;
    public final /* synthetic */ String z;

    public /* synthetic */ c(NsdManagerServiceResolver.AnonymousClass4.AnonymousClass1 r1, Map map, String str, byte[] bArr, String str2, ChipMdnsCallback chipMdnsCallback, String str3, int i, long j, long j2, Runnable runnable) {
        this.c = r1;
        this.d = map;
        this.f = str;
        this.q = bArr;
        this.x = str2;
        this.y = chipMdnsCallback;
        this.z = str3;
        this.p0 = i;
        this.a1 = j;
        this.p1 = j2;
        this.a2 = runnable;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q, this.x, this.y, this.z, this.p0, this.a1, this.p1, this.a2);
    }
}
