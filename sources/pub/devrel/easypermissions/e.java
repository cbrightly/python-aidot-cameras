package pub.devrel.easypermissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/* compiled from: RationaleDialogConfig */
public class e {
    String a;
    String b;
    int c;
    int d;
    String e;
    String[] f;
    b g;

    e(@NonNull String positiveButton, @NonNull String negativeButton, @NonNull String rationaleMsg, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String[] permissions) {
        this.a = positiveButton;
        this.b = negativeButton;
        this.e = rationaleMsg;
        this.c = theme;
        this.d = requestCode;
        this.f = permissions;
        this.g = dialogStrategy;
    }

    e(Bundle bundle) {
        this.a = bundle.getString("positiveButton");
        this.b = bundle.getString("negativeButton");
        this.e = bundle.getString("rationaleMsg");
        this.c = bundle.getInt("theme");
        this.d = bundle.getInt("requestCode");
        this.f = bundle.getStringArray("permissions");
        String dialogStrategyClass = bundle.getString("dialogStrategyClass", "");
        if (!TextUtils.isEmpty(dialogStrategyClass)) {
            try {
                this.g = (b) Class.forName(dialogStrategyClass).newInstance();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            } catch (InstantiationException e4) {
                e4.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle b() {
        Bundle bundle = new Bundle();
        bundle.putString("positiveButton", this.a);
        bundle.putString("negativeButton", this.b);
        bundle.putString("rationaleMsg", this.e);
        bundle.putInt("theme", this.c);
        bundle.putInt("requestCode", this.d);
        bundle.putStringArray("permissions", this.f);
        b bVar = this.g;
        if (bVar != null) {
            bundle.putString("dialogStrategyClass", bVar.getClass().getName());
        }
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public AlertDialog a(Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder;
        if (this.c > 0) {
            builder = new AlertDialog.Builder(context, this.c);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        return builder.setCancelable(false).setPositiveButton(this.a, listener).setNegativeButton(this.b, listener).setMessage(this.e).create();
    }
}
