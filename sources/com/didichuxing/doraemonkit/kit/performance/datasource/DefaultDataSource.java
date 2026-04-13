package com.didichuxing.doraemonkit.kit.performance.datasource;

import com.didichuxing.doraemonkit.kit.performance.widget.LineData;

public class DefaultDataSource implements IDataSource {
    public LineData createData() {
        return LineData.obtain(50.0f, Math.round(50.0f) + "");
    }
}
