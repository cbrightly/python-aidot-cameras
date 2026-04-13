package com.didichuxing.doraemonkit.kit.performance.widget;

import androidx.core.util.Pools;

public class LineData {
    private static Pools.SimplePool<LineData> mPool = new Pools.SimplePool<>(50);
    public String label;
    public float value;

    public LineData(float value2, String label2) {
        this.value = value2;
        this.label = label2;
    }

    public static LineData obtain(float value2, String label2) {
        LineData lineData = mPool.acquire();
        if (lineData == null) {
            return new LineData(value2, label2);
        }
        lineData.value = value2;
        lineData.label = label2;
        return lineData;
    }

    public void release() {
        mPool.release(this);
    }
}
