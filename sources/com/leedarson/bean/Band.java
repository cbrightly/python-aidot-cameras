package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class Band {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int maxAmp;
    public int maxFreq;
    public int minFreq;
    public int[] tempArr;

    public int calMaxAmp() {
        this.maxAmp = 0;
        for (int t : this.tempArr) {
            if (t > this.maxAmp) {
                this.maxAmp = t;
            }
        }
        return this.maxAmp;
    }

    public int getBandWidth() {
        return this.maxFreq - this.minFreq;
    }

    public boolean include(float hz) {
        return hz > ((float) this.minFreq) && hz < ((float) this.maxFreq);
    }
}
