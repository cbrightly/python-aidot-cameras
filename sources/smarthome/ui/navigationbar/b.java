package smarthome.ui.navigationbar;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ LDSNavigationBar c;
    public final /* synthetic */ String d;

    public /* synthetic */ b(LDSNavigationBar lDSNavigationBar, String str) {
        this.c = lDSNavigationBar;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.A(this.d, (String) obj);
    }
}
