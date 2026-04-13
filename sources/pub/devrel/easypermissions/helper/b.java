package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

/* compiled from: AppCompatActivityPermissionsHelper */
public class b extends c<AppCompatActivity> {
    public b(AppCompatActivity host) {
        super(host);
    }

    public FragmentManager m() {
        return ((AppCompatActivity) c()).getSupportFragmentManager();
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
}
