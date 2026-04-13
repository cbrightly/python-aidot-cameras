package smarthome.ui;

import com.leedarson.serviceinterface.event.ShowToastEvent;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ ShowToastEvent d;

    public /* synthetic */ o(CoreActivity coreActivity, ShowToastEvent showToastEvent) {
        this.c = coreActivity;
        this.d = showToastEvent;
    }

    public final void run() {
        this.c.r1(this.d);
    }
}
