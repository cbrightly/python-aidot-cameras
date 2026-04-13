package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.DialogInterface;
import androidx.fragment.app.Fragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Arrays;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.helper.e;

/* compiled from: RationaleDialogClickListener */
public class d implements DialogInterface.OnClickListener {
    private Object c;
    private e d;
    private EasyPermissions.PermissionCallbacks f;
    private EasyPermissions.a q;

    d(RationaleDialogFragmentCompat compatDialogFragment, e config, EasyPermissions.PermissionCallbacks callbacks, EasyPermissions.a rationaleCallbacks) {
        Object obj;
        if (compatDialogFragment.getParentFragment() != null) {
            obj = compatDialogFragment.getParentFragment();
        } else {
            obj = compatDialogFragment.getActivity();
        }
        this.c = obj;
        this.d = config;
        this.f = callbacks;
        this.q = rationaleCallbacks;
    }

    d(f dialogFragment, e config, EasyPermissions.PermissionCallbacks callbacks, EasyPermissions.a dialogCallback) {
        this.c = dialogFragment.getActivity();
        this.d = config;
        this.f = callbacks;
        this.q = dialogCallback;
    }

    @SensorsDataInstrumented
    public void onClick(DialogInterface dialogInterface, int i) {
        int which = i;
        DialogInterface dialogInterface2 = dialogInterface;
        e eVar = this.d;
        int requestCode = eVar.d;
        if (which == -1) {
            String[] permissions = eVar.f;
            EasyPermissions.a aVar = this.q;
            if (aVar != null) {
                aVar.b(requestCode);
            }
            Object obj = this.c;
            if (obj instanceof Fragment) {
                e.e((Fragment) obj).a(requestCode, permissions);
            } else if (obj instanceof Activity) {
                e.d((Activity) obj).a(requestCode, permissions);
            } else {
                RuntimeException runtimeException = new RuntimeException("Host must be an Activity or Fragment!");
                SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                throw runtimeException;
            }
        } else {
            EasyPermissions.a aVar2 = this.q;
            if (aVar2 != null) {
                aVar2.a(requestCode);
            }
            a();
        }
        SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
    }

    private void a() {
        EasyPermissions.PermissionCallbacks permissionCallbacks = this.f;
        if (permissionCallbacks != null) {
            e eVar = this.d;
            permissionCallbacks.Q(eVar.d, Arrays.asList(eVar.f));
        }
    }
}
