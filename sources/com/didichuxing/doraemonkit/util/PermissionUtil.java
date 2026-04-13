package com.didichuxing.doraemonkit.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioRecord;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.content.ContextCompat;
import com.didichuxing.doraemonkit.constant.CachesKey;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import net.sqlcipher.database.SQLiteDatabase;

public class PermissionUtil {
    private static final int AUDIO_CHANNEL = 16;
    private static final int AUDIO_ENCODING = 2;
    private static final int AUDIO_INPUT = 1;
    private static final int AUDIO_SAMPLE_RATE = 16000;
    private static final int OP_SYSTEM_ALERT_WINDOW = 24;
    private static final String TAG = "PermissionUtil";

    private PermissionUtil() {
    }

    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(context);
        }
        return checkOp(context, 24);
    }

    private static boolean checkOp(Context context, int op) {
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService("appops");
            Class clazz = AppOpsManager.class;
            try {
                Class cls = Integer.TYPE;
                if (((Integer) clazz.getDeclaredMethod("checkOp", new Class[]{cls, cls, String.class}).invoke(manager, new Object[]{Integer.valueOf(op), Integer.valueOf(Process.myUid()), context.getPackageName()})).intValue() == 0) {
                    return true;
                }
                return false;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return true;
    }

    public static void requestDrawOverlays(Context context) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
        if (!(context instanceof Activity)) {
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            LogHelper.e(TAG, "No activity to handle intent");
        }
    }

    public static boolean isMockLocationEnabled(Context context) {
        boolean isMockLocation = false;
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT < 23) {
                return !Settings.Secure.getString(context.getContentResolver(), CachesKey.MOCK_LOCATION).equals("0");
            }
            AppOpsManager opsManager = (AppOpsManager) context.getSystemService("appops");
            if (opsManager != null) {
                if (opsManager.checkOp("android:mock_location", Process.myUid(), context.getPackageName()) == 0) {
                    z = true;
                }
                isMockLocation = z;
            }
            return isMockLocation;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasPermissions(@NonNull Context context, @Size(min = 1) @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < 23) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        } else if (context != null) {
            for (String perm : perms) {
                if (ContextCompat.checkSelfPermission(context, perm) != 0) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }
    }

    public static boolean checkStorageUnreliable() {
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + ".DoraemonkitTest.kit");
            FileOutputStream outputStream2 = new FileOutputStream(file);
            outputStream2.write(1);
            outputStream2.flush();
            outputStream2.close();
            outputStream = null;
            inputStream = new FileInputStream(file);
            inputStream.read();
            inputStream.close();
            FileInputStream inputStream2 = null;
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream2 != null) {
                inputStream2.close();
            }
            return true;
        } catch (Exception e2) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return false;
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return false;
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    throw th;
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    @SuppressLint({"MissingPermission"})
    public static boolean checkLocationUnreliable(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
            if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                return false;
            }
            Location location = locationManager.getLastKnownLocation("gps");
            Location location2 = locationManager.getLastKnownLocation("network");
            if (location == null && location2 == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkCameraUnreliable() {
        Camera camera = null;
        boolean z = false;
        try {
            Camera camera2 = Camera.open();
            if (camera2 != null) {
                z = true;
            }
            if (camera2 != null) {
                try {
                    camera2.release();
                } catch (Exception e) {
                }
            }
            return z;
        } catch (Exception e2) {
            if (camera != null) {
                try {
                    camera.release();
                } catch (Exception e3) {
                }
            }
            return false;
        } catch (Throwable th) {
            if (camera != null) {
                try {
                    camera.release();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static boolean checkRecordUnreliable() {
        AudioRecord audioRecord = null;
        boolean z = false;
        try {
            AudioRecord audioRecord2 = new AudioRecord(1, AUDIO_SAMPLE_RATE, 16, 2, AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, 16, 2));
            if (audioRecord2.getState() == 1) {
                z = true;
            }
            try {
                audioRecord2.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } catch (Exception e2) {
            e2.printStackTrace();
            if (audioRecord != null) {
                try {
                    audioRecord.release();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (audioRecord != null) {
                try {
                    audioRecord.release();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean checkReadPhoneUnreliable(Context context) {
        try {
            return !TextUtils.isEmpty(((TelephonyManager) context.getSystemService("phone")).getDeviceId());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkReadContactUnreliable(Context context) {
        Cursor cursor = null;
        try {
            Cursor cursor2 = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, (String[]) null, (String) null, (String[]) null, (String) null);
            if (cursor2 == null || !cursor2.moveToNext()) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return false;
            }
            cursor2.close();
            return true;
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
