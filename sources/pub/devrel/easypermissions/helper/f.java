package pub.devrel.easypermissions.helper;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/* compiled from: SupportFragmentPermissionHelper */
public class f extends c<Fragment> {
    public f(@NonNull Fragment host) {
        super(host);
    }

    public FragmentManager m() {
        return ((Fragment) c()).getChildFragmentManager();
    }

    public void a(int requestCode, @NonNull String... perms) {
        ((Fragment) c()).requestPermissions(perms, requestCode);
    }

    public boolean i(@NonNull String perm) {
        return ((Fragment) c()).shouldShowRequestPermissionRationale(perm);
    }

    public Context b() {
        return ((Fragment) c()).getActivity();
    }
}
