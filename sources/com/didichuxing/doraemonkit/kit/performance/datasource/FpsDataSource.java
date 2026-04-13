package com.didichuxing.doraemonkit.kit.performance.datasource;

import com.didichuxing.doraemonkit.kit.performance.PerformanceDataManager;
import com.didichuxing.doraemonkit.kit.performance.widget.LineData;

public class FpsDataSource implements IDataSource {
    public LineData createData() {
        float rate = (float) PerformanceDataManager.getInstance().getLastFrameRate();
        return LineData.obtain(rate, Math.round(rate) + "");
    }
}
