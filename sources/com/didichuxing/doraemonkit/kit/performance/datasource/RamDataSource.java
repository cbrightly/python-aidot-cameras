package com.didichuxing.doraemonkit.kit.performance.datasource;

import com.didichuxing.doraemonkit.kit.performance.PerformanceDataManager;
import com.didichuxing.doraemonkit.kit.performance.widget.LineData;

public class RamDataSource implements IDataSource {
    private float mMaxRam = ((float) ((((double) Runtime.getRuntime().maxMemory()) * 1.0d) / 1048576.0d));

    public LineData createData() {
        float info = PerformanceDataManager.getInstance().getLastMemoryInfo();
        return LineData.obtain((info / this.mMaxRam) * 100.0f, Math.round(info) + "MB");
    }
}
