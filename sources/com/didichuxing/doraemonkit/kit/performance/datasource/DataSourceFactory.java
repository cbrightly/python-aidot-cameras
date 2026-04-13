package com.didichuxing.doraemonkit.kit.performance.datasource;

import androidx.annotation.NonNull;

public class DataSourceFactory {
    public static final int TYPE_CPU = 2;
    public static final int TYPE_FPS = 4;
    public static final int TYPE_NETWORK = 1;
    public static final int TYPE_RAM = 3;

    @NonNull
    public static IDataSource createDataSource(int type) {
        switch (type) {
            case 1:
                return new NetworkDataSource();
            case 2:
                return new CpuDataSource();
            case 3:
                return new RamDataSource();
            case 4:
                return new FpsDataSource();
            default:
                return new DefaultDataSource();
        }
    }
}
