package meshsdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.leedarson.base.utils.e;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.telink.ble.mesh.core.MeshUtils;
import java.util.Map;
import meshsdk.SIGMesh;

public class SharedPreferenceHelper {
    private static final String DEFAULT_NAME = "telink_shared";
    private static final String KEY_AUTO_PV = "com.telink.bluetooth.light.KEY_AUTO_PV";
    private static final String KEY_DLE_ENABLE = "com.telink.bluetooth.light.KEY_DLE_ENABLE";
    private static final String KEY_FAST_PROVISION = "com.telink.bluetooth.light.KEY_FAST_PROVISION";
    private static final String KEY_FIRST_LOAD = "com.telink.bluetooth.light.KEY_FIRST_LOAD";
    private static final String KEY_LOCAL_Address = "com.telink.bluetooth.light.KEY_LOCAL_Address";
    private static final String KEY_LOCAL_UUID = "com.telink.bluetooth.light.KEY_LOCAL_UUID";
    private static final String KEY_LOCATION_IGNORE = "com.telink.bluetooth.light.KEY_LOCATION_IGNORE";
    private static final String KEY_LOG_ENABLE = "com.telink.bluetooth.light.KEY_LOG_ENABLE";
    private static final String KEY_MESH_DATA_TIMESTAMP = "com.telink.bluetooth.light.KEY_MESH_DATA_TIMESTAMP";
    private static final String KEY_MESH_DATA_VERSION = "com.telink.bluetooth.light.KEY_MESH_DATA_VERSION";
    private static final String KEY_MESH_HOUSE_ID = "com.telink.bluetooth.light.KEY_MESH_HOUSE_ID";
    private static final String KEY_MESH_HOUSE_ID_AND_VERSION = "com.telink.bluetooth.light.KEY_MESH_HOUSE_ID_AND_VERSION";
    private static final String KEY_MESH_INFO_UUID = "com.telink.bluetooth.light.KEY_MESH_INFO_UUID";
    private static final String KEY_MESH_NEED_UPLOAD = "com.telink.bluetooth.light.KEY_MESH_NEED_UPLOAD";
    private static final String KEY_NO_OOB = "com.telink.bluetooth.light.KEY_NO_OOB";
    private static final String KEY_PRIVATE_MODE = "com.telink.bluetooth.light.KEY_PRIVATE_MODE";
    private static final String KEY_REMOTE_PROVISION = "com.telink.bluetooth.light.KEY_REMOTE_PROVISION";

    public static void setMeshHWVersion(Context context, String key, String val) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putString(key, val).apply();
    }

    public static String getMeshHWVersion(Context context, String key) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getString(key, "");
    }

    public static String getValue(Context context, String key) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getString(key, "");
    }

    public static void setValue(Context context, String key, String value) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putString(key, value).apply();
    }

    public static void setMeshVersionHouse(Context context, String houseId, String version) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_NAME, 0);
        sharedPreferences.edit().putString(KEY_MESH_HOUSE_ID_AND_VERSION, houseId + "#" + version).apply();
    }

    public static String getMeshVersionHouse(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getString(KEY_MESH_HOUSE_ID_AND_VERSION, "");
    }

    public static String getMeshDataTimeStamp(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getString(KEY_MESH_DATA_TIMESTAMP, "");
    }

    public static void setMeshDataTimeStamp(Context context, String timestamp) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putString(KEY_MESH_DATA_TIMESTAMP, timestamp).apply();
    }

    public static String getMeshDataVersion(Context context) {
        String[] split;
        String meshVersionHouse = getMeshVersionHouse(context);
        if (TextUtils.isEmpty(meshVersionHouse) || (split = meshVersionHouse.split("#")) == null || split.length != 2) {
            return "";
        }
        return split[1];
    }

    public static String getLastHouseId(Context context) {
        return SIGMesh.getInstance().lastHouseId;
    }

    public static void setLastHouseId(Context context, String houseId) {
        SIGMesh.getInstance().lastHouseId = houseId;
    }

    public static String getHouseId(Context context) {
        String hid = context.getSharedPreferences(DEFAULT_NAME, 0).getString(KEY_MESH_HOUSE_ID, "");
        if (TextUtils.isEmpty(hid)) {
            return SharePreferenceUtils.getPrefString(context, "houseId", "");
        }
        return hid;
    }

    public static void setHouseId(Context context, String houseId) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putString(KEY_MESH_HOUSE_ID, houseId).apply();
    }

    public static boolean isNeedUpload(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_MESH_NEED_UPLOAD, false);
    }

    public static void setNeedUpload(Context context, boolean need) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_MESH_NEED_UPLOAD, need).apply();
    }

    public static boolean isFirstLoad(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_FIRST_LOAD, true);
    }

    public static void setFirst(Context context, boolean isFirst) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_FIRST_LOAD, isFirst).apply();
    }

    public static boolean isLocationIgnore(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_LOCATION_IGNORE, false);
    }

    public static void setLocationIgnore(Context context, boolean ignore) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_LOCATION_IGNORE, ignore).apply();
    }

    public static boolean isLogEnable(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_LOG_ENABLE, false);
    }

    public static void setLogEnable(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_LOG_ENABLE, enable).apply();
    }

    public static boolean isPrivateMode(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_PRIVATE_MODE, false);
    }

    public static void setPrivateMode(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_PRIVATE_MODE, enable).apply();
    }

    public static String getLocalUUID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_NAME, 0);
        String uuid = sharedPreferences.getString(KEY_LOCAL_UUID, "");
        if (uuid != null) {
            return uuid;
        }
        String uuid2 = e.b(MeshUtils.g(16), "").toUpperCase();
        sharedPreferences.edit().putString(KEY_LOCAL_UUID, uuid2).apply();
        return uuid2;
    }

    public static void setLocalUUID(Context context, String provisionerUUID) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putString(KEY_LOCAL_UUID, provisionerUUID).apply();
    }

    public static String getMeshInfoUUID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_NAME, 0);
        String uuid = sharedPreferences.getString(KEY_MESH_INFO_UUID, "");
        if (uuid != null) {
            return uuid;
        }
        String uuid2 = e.b(MeshUtils.g(16), "").toUpperCase();
        sharedPreferences.edit().putString(KEY_MESH_INFO_UUID, uuid2).apply();
        return uuid2;
    }

    public static void setMeshInfoUUID(Context context, String provisionerUUID) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putString(KEY_MESH_INFO_UUID, provisionerUUID).apply();
    }

    public static int getLocalAddress(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getInt(KEY_LOCAL_Address, 1);
    }

    public static void setLocalAddress(Context context, int localAddr) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putInt(KEY_LOCAL_Address, localAddr).apply();
    }

    public static boolean isRemoteProvisionEnable(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_REMOTE_PROVISION, false);
    }

    public static void setRemoteProvisionEnable(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_REMOTE_PROVISION, enable).apply();
    }

    public static boolean isFastProvisionEnable(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_FAST_PROVISION, false);
    }

    public static void setFastProvisionEnable(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_FAST_PROVISION, enable).apply();
    }

    public static boolean isNoOOBEnable(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_NO_OOB, true);
    }

    public static void setNoOOBEnable(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_NO_OOB, enable).apply();
    }

    public static boolean isDleEnable(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_DLE_ENABLE, false);
    }

    public static void setDleEnable(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_DLE_ENABLE, enable).apply();
    }

    public static boolean isAutoPvEnable(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getBoolean(KEY_AUTO_PV, false);
    }

    public static void setAutoPvEnable(Context context, boolean enable) {
        context.getSharedPreferences(DEFAULT_NAME, 0).edit().putBoolean(KEY_AUTO_PV, enable).apply();
    }

    public static Map<String, ?> getAll(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, 0).getAll();
    }
}
