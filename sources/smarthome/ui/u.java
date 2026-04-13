package smarthome.ui;

import android.view.View;
import smarthome.bean.VersionInfo;

/* compiled from: lambda */
public final /* synthetic */ class u implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ VersionInfo d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;

    public /* synthetic */ u(CoreActivity coreActivity, VersionInfo versionInfo, String str, String str2) {
        this.c = coreActivity;
        this.d = versionInfo;
        this.f = str;
        this.q = str2;
    }

    public final void onClick(View view) {
        this.c.w1(this.d, this.f, this.q, view);
    }
}
