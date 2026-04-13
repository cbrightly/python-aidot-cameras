package com.sensorsdata.analytics.android.sdk.plugin.encrypt;

import android.content.Context;
import android.content.SharedPreferences;
import com.sensorsdata.analytics.android.sdk.util.SASpUtils;
import java.util.List;

public abstract class DefaultStorePlugin implements StorePlugin {
    private final String mFileName;
    private final SharedPreferences mStoreSp;

    public abstract List<String> storeKeys();

    public DefaultStorePlugin(Context context, String fileName) {
        this.mStoreSp = SASpUtils.getSharedPreferences(context, fileName, 0);
        this.mFileName = fileName;
    }

    public boolean isExists(String key) {
        return this.mStoreSp.contains(getKey(key));
    }

    public String type() {
        return this.mFileName;
    }

    public void upgrade(StorePlugin oldPlugin) {
    }

    public void setString(String key, String value) {
        this.mStoreSp.edit().putString(getKey(key), value).apply();
    }

    public void setBool(String key, boolean value) {
        this.mStoreSp.edit().putBoolean(getKey(key), value).apply();
    }

    public void setInteger(String key, int value) {
        this.mStoreSp.edit().putInt(getKey(key), value).apply();
    }

    public void setFloat(String key, float value) {
        this.mStoreSp.edit().putFloat(getKey(key), value).apply();
    }

    public void setLong(String key, long value) {
        this.mStoreSp.edit().putLong(getKey(key), value).apply();
    }

    public String getString(String key) {
        if (isExists(key)) {
            return this.mStoreSp.getString(getKey(key), (String) null);
        }
        return null;
    }

    public Boolean getBool(String key) {
        if (isExists(key)) {
            return Boolean.valueOf(this.mStoreSp.getBoolean(getKey(key), false));
        }
        return null;
    }

    public Integer getInteger(String key) {
        if (isExists(key)) {
            return Integer.valueOf(this.mStoreSp.getInt(getKey(key), 0));
        }
        return null;
    }

    public Float getFloat(String key) {
        if (isExists(key)) {
            return Float.valueOf(this.mStoreSp.getFloat(getKey(key), 0.0f));
        }
        return null;
    }

    public Long getLong(String key) {
        if (isExists(key)) {
            return Long.valueOf(this.mStoreSp.getLong(getKey(key), 0));
        }
        return null;
    }

    public void remove(String key) {
        this.mStoreSp.edit().remove(getKey(key)).apply();
    }

    private String getKey(String key) {
        return key.replaceFirst(type(), "");
    }
}
