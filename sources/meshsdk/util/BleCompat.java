package meshsdk.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.core.content.ContextCompat;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.tbruyelle.rxpermissions2.b;
import pub.devrel.easypermissions.EasyPermissions;

public class BleCompat {
    b rxPermissions;

    public BleCompat(Activity activity) {
        this.rxPermissions = new b(activity);
    }

    public static boolean checkNeededPermission(Context mContext) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (w.R()) {
            if (ContextCompat.checkSelfPermission(mContext, "android.permission.BLUETOOTH_SCAN") == 0 && ContextCompat.checkSelfPermission(mContext, "android.permission.BLUETOOTH_CONNECT") == 0) {
                return true;
            }
            return false;
        } else if (ContextCompat.checkSelfPermission(mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean neededPermissionPermanentlyDenied(Activity activity) {
        if (w.R()) {
            return EasyPermissions.e(activity, "android.permission.BLUETOOTH_SCAN");
        }
        if (!SharePreferenceUtils.getPrefBoolean(activity, "location_permission_denied", false) || !EasyPermissions.e(activity, "android.permission.ACCESS_COARSE_LOCATION")) {
            return false;
        }
        return true;
    }
}
