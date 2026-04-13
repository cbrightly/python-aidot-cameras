package com.sensorsdata.analytics.android.sdk.plugin.encrypt;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.BuildConfig;
import com.sensorsdata.analytics.android.sdk.encrypt.AESSecretManager;
import com.sensorsdata.analytics.android.sdk.util.SASpUtils;

public class SAEncryptStorePlugin implements StorePlugin {
    private final String mFileName;
    private final SharedPreferences mStoreSp;

    public SAEncryptStorePlugin(Context context) {
        this(context, BuildConfig.LIBRARY_PACKAGE_NAME);
    }

    public SAEncryptStorePlugin(Context context, String fileName) {
        this.mStoreSp = SASpUtils.getSharedPreferences(context, fileName, 0);
        this.mFileName = fileName;
    }

    public void setString(String key, String value) {
        this.mStoreSp.edit().putString(encryptValue(key), encryptValue(value)).apply();
    }

    public void setBool(String key, boolean value) {
        this.mStoreSp.edit().putString(encryptValue(key), encryptValue(String.valueOf(value))).apply();
    }

    public void setInteger(String key, int value) {
        this.mStoreSp.edit().putString(encryptValue(key), encryptValue(String.valueOf(value))).apply();
    }

    public void setFloat(String key, float value) {
        this.mStoreSp.edit().putString(encryptValue(key), encryptValue(String.valueOf(value))).apply();
    }

    public void setLong(String key, long value) {
        this.mStoreSp.edit().putString(encryptValue(key), encryptValue(String.valueOf(value))).apply();
    }

    public String getString(String key) {
        String value = this.mStoreSp.getString(encryptValue(key), (String) null);
        if (!TextUtils.isEmpty(value)) {
            return decryptValue(value);
        }
        return null;
    }

    public Boolean getBool(String key) {
        String value = getString(key);
        if (!TextUtils.isEmpty(value)) {
            return Boolean.valueOf(Boolean.parseBoolean(value));
        }
        return null;
    }

    public Integer getInteger(String key) {
        String value = getString(key);
        if (!TextUtils.isEmpty(value)) {
            return Integer.valueOf(Integer.parseInt(value));
        }
        return null;
    }

    public Float getFloat(String key) {
        String value = getString(key);
        if (!TextUtils.isEmpty(value)) {
            return Float.valueOf(Float.parseFloat(value));
        }
        return null;
    }

    public Long getLong(String key) {
        String value = getString(key);
        if (!TextUtils.isEmpty(value)) {
            return Long.valueOf(Long.parseLong(value));
        }
        return null;
    }

    public void remove(String key) {
        this.mStoreSp.edit().remove(encryptValue(key)).apply();
    }

    public boolean isExists(String key) {
        return this.mStoreSp.contains(encryptValue(key));
    }

    public String type() {
        return this.mFileName;
    }

    public void upgrade(StorePlugin oldPlugin) {
    }

    private String decryptValue(String value) {
        return AESSecretManager.getInstance().decryptAES(value);
    }

    private String encryptValue(String value) {
        return AESSecretManager.getInstance().encryptAES(value);
    }
}
