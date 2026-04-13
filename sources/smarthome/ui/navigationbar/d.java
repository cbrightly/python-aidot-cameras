package smarthome.ui.navigationbar;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ NavigationItem c;

    public /* synthetic */ d(NavigationItem navigationItem) {
        this.c = navigationItem;
    }

    public final void accept(Object obj) {
        LDSNavigationBar.w(this.c, (Throwable) obj);
    }
}
