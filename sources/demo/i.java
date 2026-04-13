package demo;

import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import demo.ProvisionActivity;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    public final /* synthetic */ ProvisionActivity c;
    public final /* synthetic */ CHIPDeviceInfo d;

    public /* synthetic */ i(ProvisionActivity provisionActivity, CHIPDeviceInfo cHIPDeviceInfo) {
        this.c = provisionActivity;
        this.d = cHIPDeviceInfo;
    }

    public final void run() {
        ProvisionActivity.c.i(this.c, this.d);
    }
}
