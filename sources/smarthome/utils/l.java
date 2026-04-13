package smarthome.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import smarthome.bean.VersionInfo;
import timber.log.a;

/* compiled from: Utils */
public class l {
    public static String c(Context context, String assetName) {
        byte[] buffer = new byte[1024];
        try {
            InputStream in = context.getAssets().open(assetName);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            while (true) {
                int read = in.read(buffer, 0, 1024);
                int len = read;
                if (read != -1) {
                    digest.update(buffer, 0, len);
                } else {
                    in.close();
                    return a(digest.digest());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String f(Context context) {
        String uuid = SharePreferenceUtils.getPrefString(context, "uuid", "");
        if (TextUtils.isEmpty(uuid)) {
            StringBuilder key = new StringBuilder();
            String androidId = Settings.System.getString(context.getContentResolver(), "android_id");
            if (!TextUtils.isEmpty(androidId)) {
                key.append(androidId);
            }
            String serialNumber = Build.SERIAL;
            a.b g = a.g("getDeviceId");
            g.h("getUUID_SerialNumber: " + serialNumber, new Object[0]);
            if (!TextUtils.isEmpty(serialNumber) && !"unknown".equals(serialNumber)) {
                key.append(serialNumber);
            }
            if (key.length() > 0) {
                try {
                    uuid = UUID.nameUUIDFromBytes(key.toString().getBytes("UTF-8")).toString();
                    a.b g2 = a.g("getDeviceId");
                    g2.h("getUUID_uuid: " + uuid, new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                uuid = UUID.randomUUID().toString();
            }
            SharePreferenceUtils.setPrefString(context, "uuid", uuid);
        }
        return uuid;
    }

    public static String g(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        String result = null;
        try {
            InputStream is2 = new FileInputStream(path);
            byte[] data = new byte[is2.available()];
            is2.read(data);
            result = Base64.encodeToString(data, 0);
            try {
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        return "data:image/png;base64," + result;
    }

    public static String b() {
        return new SimpleDateFormat(TimeUtils.YYYY_MM_DD).format(new Date());
    }

    public static void i(Context context, String andVersion, String way) {
        SharePreferenceUtils.setPrefString(context, "skipAndroidVersion", andVersion);
        SharePreferenceUtils.setPrefString(context, "skipAndroidWay", way);
    }

    public static void j(Context context, String webVersion, String way) {
        SharePreferenceUtils.setPrefString(context, "skipWebVersion", webVersion);
        SharePreferenceUtils.setPrefString(context, "skipWebWay", way);
    }

    public static VersionInfo d(Context context) {
        return new VersionInfo(SharePreferenceUtils.getPrefString(context, "skipAndroidVersion", ""), SharePreferenceUtils.getPrefString(context, "skipAndroidWay", ""));
    }

    public static VersionInfo e(Context context) {
        return new VersionInfo(SharePreferenceUtils.getPrefString(context, "skipWebVersion", ""), SharePreferenceUtils.getPrefString(context, "skipWebWay", ""));
    }

    public static boolean h(Context context, String filename) {
        try {
            String[] names = context.getAssets().list("");
            for (String equals : names) {
                if (equals.equals(filename.trim())) {
                    System.out.println(filename + "存在");
                    return true;
                }
            }
            System.out.println(filename + "不存在");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(filename + "不存在");
            return false;
        }
    }
}
