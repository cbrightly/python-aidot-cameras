package smarthome.ui;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class t implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;

    public /* synthetic */ t(CoreActivity coreActivity, String str, String str2) {
        this.c = coreActivity;
        this.d = str;
        this.f = str2;
    }

    public final void onClick(View view) {
        this.c.y1(this.d, this.f, view);
    }
}
