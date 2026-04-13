package smarthome.ui.navigationbar;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ LDSNavigationBar c;
    public final /* synthetic */ String d;

    public /* synthetic */ c(LDSNavigationBar lDSNavigationBar, String str) {
        this.c = lDSNavigationBar;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.C(this.d, (Throwable) obj);
    }
}
