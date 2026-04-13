package chip.platform;

import chip.platform.NsdManagerServiceResolver;
import com.github.druk.dnssd.QueryListener;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ String c;
    public final /* synthetic */ int d;
    public final /* synthetic */ QueryListener f;

    public /* synthetic */ d(String str, int i, QueryListener queryListener) {
        this.c = str;
        this.d = i;
        this.f = queryListener;
    }

    public final void run() {
        NsdManagerServiceResolver.AnonymousClass4.lambda$serviceResolved$0(this.c, this.d, this.f);
    }
}
