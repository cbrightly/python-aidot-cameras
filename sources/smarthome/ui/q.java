package smarthome.ui;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class q implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;
    public final /* synthetic */ String x;

    public /* synthetic */ q(CoreActivity coreActivity, String str, String str2, String str3, String str4) {
        this.c = coreActivity;
        this.d = str;
        this.f = str2;
        this.q = str3;
        this.x = str4;
    }

    public final void onClick(View view) {
        this.c.E1(this.d, this.f, this.q, this.x, view);
    }
}
