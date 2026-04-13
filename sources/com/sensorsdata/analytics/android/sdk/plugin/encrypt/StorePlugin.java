package com.sensorsdata.analytics.android.sdk.plugin.encrypt;

public interface StorePlugin {
    Boolean getBool(String str);

    Float getFloat(String str);

    Integer getInteger(String str);

    Long getLong(String str);

    String getString(String str);

    boolean isExists(String str);

    void remove(String str);

    void setBool(String str, boolean z);

    void setFloat(String str, float f);

    void setInteger(String str, int i);

    void setLong(String str, long j);

    void setString(String str, String str2);

    String type();

    void upgrade(StorePlugin storePlugin);
}
