package pub.devrel.easypermissions.helper;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.FragmentManager;
import pub.devrel.easypermissions.RationaleDialogFragmentCompat;
import pub.devrel.easypermissions.b;

/* compiled from: BaseSupportPermissionsHelper */
public abstract class c<T> extends e<T> {
    public abstract FragmentManager m();

    public c(@NonNull T host) {
        super(host);
    }

    public void j(@NonNull String rationale, @NonNull String positiveButton, @NonNull String negativeButton, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String... perms) {
        FragmentManager fm = m();
        if (fm.findFragmentByTag("RationaleDialogFragmentCompat") instanceof RationaleDialogFragmentCompat) {
            Log.d("BSPermissionsHelper", "Found existing fragment, not showing rationale.");
        } else {
            RationaleDialogFragmentCompat.l1(rationale, positiveButton, negativeButton, theme, requestCode, dialogStrategy, perms).m1(fm, "RationaleDialogFragmentCompat");
        }
    }
}
