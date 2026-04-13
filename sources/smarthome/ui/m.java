package smarthome.ui;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class m implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ String d;

    public /* synthetic */ m(CoreActivity coreActivity, String str) {
        this.c = coreActivity;
        this.d = str;
    }

    public final void onClick(View view) {
        this.c.A1(this.d, view);
    }
}
