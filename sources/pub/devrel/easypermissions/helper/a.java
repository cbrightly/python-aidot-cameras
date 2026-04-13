package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.core.app.ActivityCompat;
import pub.devrel.easypermissions.b;
import pub.devrel.easypermissions.f;

/* compiled from: ActivityPermissionHelper */
public class a extends e<Activity> {
    public a(Activity host) {
        super(host);
    }

    public void a(int requestCode, @NonNull String... perms) {
        ActivityCompat.requestPermissions((Activity) c(), perms, requestCode);
    }

    public boolean i(@NonNull String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale((Activity) c(), perm);
    }

    public Context b() {
        return (Context) c();
    }

    public void j(@NonNull String rationale, @NonNull String positiveButton, @NonNull String negativeButton, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String... perms) {
        FragmentManager fm = ((Activity) c()).getFragmentManager();
        if (fm.findFragmentByTag("RationaleDialogFragment") instanceof f) {
            Log.d("ActPermissionHelper", "Found existing fragment, not showing rationale.");
        } else {
            f.a(positiveButton, negativeButton, rationale, theme, requestCode, dialogStrategy, perms).b(fm, "RationaleDialogFragment");
        }
    }
}
