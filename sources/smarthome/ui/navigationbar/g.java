package smarthome.ui.navigationbar;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class g implements e {
    public final /* synthetic */ NavigationItem c;

    public /* synthetic */ g(NavigationItem navigationItem) {
        this.c = navigationItem;
    }

    public final void accept(Object obj) {
        LDSNavigationBar.v(this.c, (String) obj);
    }
}
