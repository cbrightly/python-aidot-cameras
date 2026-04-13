package com.typesafe.config;

import java.time.Duration;
import java.util.List;

/* compiled from: Config */
public interface a extends d {
    Object getAnyRef(String str);

    List<? extends Object> getAnyRefList(String str);

    boolean getBoolean(String str);

    List<Boolean> getBooleanList(String str);

    a getConfig(String str);

    List<? extends a> getConfigList(String str);

    double getDouble(String str);

    List<Double> getDoubleList(String str);

    Duration getDuration(String str);

    List<Duration> getDurationList(String str);

    <T extends Enum<T>> T getEnum(Class<T> cls, String str);

    <T extends Enum<T>> List<T> getEnumList(Class<T> cls, String str);

    int getInt(String str);

    List<Integer> getIntList(String str);

    b getList(String str);

    long getLong(String str);

    List<Long> getLongList(String str);

    c getMemorySize(String str);

    List<c> getMemorySizeList(String str);

    e getObject(String str);

    List<? extends e> getObjectList(String str);

    String getString(String str);

    List<String> getStringList(String str);

    j getValue(String str);

    f origin();

    e root();
}
