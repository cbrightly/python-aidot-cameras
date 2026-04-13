package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import pub.devrel.easypermissions.b;

/* compiled from: LowApiPermissionsHelper */
public class d<T> extends e<T> {
    public d(@NonNull T host) {
        super(host);
    }

    public void a(int requestCode, @NonNull String... perms) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23!");
    }

    public boolean i(@NonNull String perm) {
        return false;
    }

    public void j(@NonNull String rationale, @NonNull String positiveButton, @NonNull String negativeButton, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String... perms) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23!");
    }

    public Context b() {
        if (c() instanceof Activity) {
            return (Context) c();
        }
        if (c() instanceof Fragment) {
            return ((Fragment) c()).getContext();
        }
        throw new IllegalStateException("Unknown host: " + c());
    }
}
