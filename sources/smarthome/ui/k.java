package smarthome.ui;

import android.view.View;
import smarthome.bean.VersionInfo;

/* compiled from: lambda */
public final /* synthetic */ class k implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;
    public final /* synthetic */ String x;
    public final /* synthetic */ VersionInfo y;
    public final /* synthetic */ VersionInfo z;

    public /* synthetic */ k(CoreActivity coreActivity, String str, String str2, String str3, String str4, VersionInfo versionInfo, VersionInfo versionInfo2) {
        this.c = coreActivity;
        this.d = str;
        this.f = str2;
        this.q = str3;
        this.x = str4;
        this.y = versionInfo;
        this.z = versionInfo2;
    }

    public final void onClick(View view) {
        this.c.I1(this.d, this.f, this.q, this.x, this.y, this.z, view);
    }
}
