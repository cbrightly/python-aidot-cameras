package com.leedarson.serviceinterface.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.secret.JNIUtil;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.aes.LDSAESUtils;
import com.leedarson.serviceinterface.utils.AESUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.security.SecureRandom;
import java.util.Map;

public class SharePreferenceUtils {
    private static String ANDROID_KEY_STORE_RANDOM_KEY = "";
    public static final String KEY_AND_VERSION_CODE = "and_version_code";
    public static final String KEY_APP_VERSION = "app_version";
    public static final String KEY_AT = "AT";
    public static final String KEY_H5_HAS_COPY = "h5HasCopy";
    public static final String KEY_H5_LAST_DOWNLOAD_VERSION = "h5_last_download_version";
    public static final String KEY_H5_LAST_USE_VERSION = "h5_last_use_version";
    public static final String KEY_H5_VERSION = "h5_version";
    public static final String KEY_LEFT_PERCENT = "leftPercent";
    public static String KEY_MULTI_VIEW_DEVICE_INDEX = "multi_view_device_index";
    private static final String KEY_RANDOM_KEY_IN_SP = "KEY_RANDOM_KEY_IN_SP";
    public static final String KEY_RIGHT_PERCENT = "rightPercent";
    public static final String KEY_RT = "RT";
    private static final String TAG = "SharePreferenceUtils";
    public static final String WEB_VIEW_COOKIE = "cook";
    public static String _spSessionKey = "mqttSessionKey";
    public static String _spSimpleVersionSeq = "_spSimpleVersionSeq";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String getPrefString(Context context, String key, String defaultValue) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, key, defaultValue}, (Object) null, changeQuickRedirect, true, 9211, new Class[]{Context.class, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        checkAndroidKeyStoreRandomKey();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String innerKey = key + "_new";
        String innerKey_CBC = innerKey + "_cbc";
        if (key.equals("accessToken")) {
            innerKey_CBC = "AT_new_cbc";
        } else if (key.equals("refreshToken")) {
            innerKey_CBC = "RT_new_cbc";
        }
        String res = settings.getString(innerKey, "res_inner_default");
        String res_cbc = settings.getString(innerKey_CBC, "cbc_inner_default");
        if ("cbc_inner_default".equals(res_cbc)) {
            if ("res_inner_default".equals(res)) {
                return defaultValue;
            }
            String res_decrypt_value = LDSAESUtils.decrypt(JNIUtil.getInstance().getStr4(), res);
            settings.edit().putString(innerKey_CBC, LDSAESUtils.encrypt(ANDROID_KEY_STORE_RANDOM_KEY, res_decrypt_value)).apply();
            settings.edit().remove(innerKey).apply();
            return res_decrypt_value;
        } else if (TextUtils.isEmpty(res_cbc)) {
            return defaultValue;
        } else {
            return LDSAESUtils.decrypt(ANDROID_KEY_STORE_RANDOM_KEY, res_cbc);
        }
    }

    public static void setPrefString(Context context, String key, String value) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, key, value}, (Object) null, changeQuickRedirect, true, 9212, new Class[]{Context.class, cls, cls}, Void.TYPE).isSupported) {
            checkAndroidKeyStoreRandomKey();
            if (!TextUtils.isEmpty(key) && value != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                if (key.equals("accessToken")) {
                    if (hasKey(context, key)) {
                        deleteByKey(context, key);
                    }
                    key = KEY_AT;
                } else if (key.equals("refreshToken")) {
                    if (hasKey(context, key)) {
                        deleteByKey(context, key);
                    }
                    key = KEY_RT;
                }
                String innerKey = key + "_new";
                settings.edit().putString(innerKey + "_cbc", LDSAESUtils.encrypt(ANDROID_KEY_STORE_RANDOM_KEY, value)).apply();
                settings.edit().remove(innerKey).apply();
            }
        }
    }

    private static void checkAndroidKeyStoreRandomKey() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9213, new Class[0], Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(ANDROID_KEY_STORE_RANDOM_KEY)) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication.b());
                String _encodeKeyValue = settings.getString(KEY_RANDOM_KEY_IN_SP, "");
                if (TextUtils.isEmpty(_encodeKeyValue)) {
                    String _randomKeyStore = createRandomKeyStr(32);
                    ANDROID_KEY_STORE_RANDOM_KEY = _randomKeyStore;
                    String _encodeRandomKey = AESUtil.INSTANCE.encode(_randomKeyStore);
                    settings.edit().putString(KEY_RANDOM_KEY_IN_SP, _encodeRandomKey).apply();
                    I("随机密钥初次生成，并已将密钥的加密数据存储至SP  Key=" + ANDROID_KEY_STORE_RANDOM_KEY + ", 加密数据=" + _encodeRandomKey);
                    return;
                }
                ANDROID_KEY_STORE_RANDOM_KEY = AESUtil.INSTANCE.decode(_encodeKeyValue);
                I("随机密钥已从SP中获取： Key=" + ANDROID_KEY_STORE_RANDOM_KEY + ",原始数据=" + _encodeKeyValue);
                return;
            }
            I("随机密钥已启用缓存  Key=" + ANDROID_KEY_STORE_RANDOM_KEY);
        }
    }

    private static void I(String msg) {
    }

    private static String createRandomKeyStr(int length) {
        Object[] objArr = {new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 9214, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        SecureRandom random = new SecureRandom();
        random.setSeed(System.nanoTime());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append((char) random.nextInt(NeedPermissionEvent.PER_IPC_SPEAK_PERM));
        }
        return stringBuffer.toString();
    }

    public static boolean getPrefBoolean(Context context, String key, boolean defaultValue) {
        Object[] objArr = {context, key, new Byte(defaultValue ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9215, new Class[]{Context.class, String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key + "_new", defaultValue);
    }

    public static boolean hasKey(Context context, String key) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, key}, (Object) null, changeQuickRedirect2, true, 9216, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.contains(key + "_new");
    }

    public static void setPrefBoolean(Context context, String key, boolean value) {
        Object[] objArr = {context, key, new Byte(value ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9217, new Class[]{Context.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putBoolean(key + "_new", value).apply();
        }
    }

    public static void setPrefInt(Context context, String key, int value) {
        Object[] objArr = {context, key, new Integer(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9218, new Class[]{Context.class, String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putInt(key + "_new", value).apply();
        }
    }

    public static int getPrefInt(Context context, String key, int defaultValue) {
        Object[] objArr = {context, key, new Integer(defaultValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9219, new Class[]{Context.class, String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key + "_new", defaultValue);
    }

    public static void setPrefFloat(Context context, String key, float value) {
        Object[] objArr = {context, key, new Float(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9220, new Class[]{Context.class, String.class, Float.TYPE}, Void.TYPE).isSupported) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putFloat(key + "_new", value).apply();
        }
    }

    public static float getPrefFloat(Context context, String key, float defaultValue) {
        Object[] objArr = {context, key, new Float(defaultValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9221, new Class[]{Context.class, String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key + "_new", defaultValue);
    }

    public static void setPreLong(Context context, String key, long value) {
        Object[] objArr = {context, key, new Long(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9222, new Class[]{Context.class, String.class, Long.TYPE}, Void.TYPE).isSupported) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putLong(key + "_new", value).apply();
        }
    }

    public static long getPrefLong(Context context, String key, long defaultValue) {
        Object[] objArr = {context, key, new Long(defaultValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9223, new Class[]{Context.class, String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key + "_new", defaultValue);
    }

    public static void clearPreference(Context context, SharedPreferences p) {
        Class[] clsArr = {Context.class, SharedPreferences.class};
        if (!PatchProxy.proxy(new Object[]{context, p}, (Object) null, changeQuickRedirect, true, 9224, clsArr, Void.TYPE).isSupported) {
            SharedPreferences.Editor editor = p.edit();
            editor.clear();
            editor.apply();
        }
    }

    public static void deleteByKey(Context context, String key) {
        Class[] clsArr = {Context.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, key}, (Object) null, changeQuickRedirect, true, 9225, clsArr, Void.TYPE).isSupported) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.remove(key + "_new");
            editor.apply();
        }
    }

    public static Map<String, ?> getAll(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9226, new Class[]{Context.class}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        return PreferenceManager.getDefaultSharedPreferences(context).getAll();
    }
}
