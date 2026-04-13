package pub.devrel.easypermissions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

/* compiled from: DialogStrategy */
public abstract class b {
    public DialogInterface.OnClickListener a;

    public abstract Dialog a(Activity activity, String str, String str2, String str3);

    public DialogInterface.OnClickListener b() {
        return this.a;
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.a = onClickListener;
    }

    public boolean c() {
        return true;
    }
}
