package smarthome.ui;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class r implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ String d;

    public /* synthetic */ r(CoreActivity coreActivity, String str) {
        this.c = coreActivity;
        this.d = str;
    }

    public final void onClick(View view) {
        this.c.G1(this.d, view);
    }
}
