package com.leedarson.bean;

import com.leedarson.utils.d;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONArray;

public class CommandItem {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String actionMode;
    private float b;
    public int color;
    public int color2;
    public int[] colorArray;
    public int colorIndex;
    public double db;
    public JSONArray effectArray;
    public int fading;
    public int[] fadingArray;
    public boolean isUpdateEffect;
    private float k;
    public int light;
    public double[] lightArray;
    public String mac;
    public int maxDimming;
    public double rate;
    public int sensitivity;
    public int soundWaveType;

    public CommandItem() {
    }

    public CommandItem(double db2, int light2, int sensitivity2) {
        this.db = db2;
        this.light = light2;
        this.sensitivity = sensitivity2;
    }

    public CommandItem(int soundWaveType2, int color3, int light2) {
        this.soundWaveType = soundWaveType2;
        this.color = color3;
        this.light = light2;
    }

    public CommandItem(int soundWaveType2, int color3, int light2, int color22, int maxDimming2, int fading2, int rate2) {
        this.soundWaveType = soundWaveType2;
        this.color = color3;
        this.light = light2;
        this.color2 = color22;
        this.maxDimming = maxDimming2;
        this.fading = fading2;
        this.rate = (double) rate2;
    }

    public void setCalcParams(float k2, float b2) {
        this.k = k2;
        this.b = b2;
    }

    public int getCalLight(double A) {
        int i = this.light;
        if (i < 0) {
            return (int) ((((double) this.k) * A) + ((double) this.b));
        }
        return i;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1000, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "CommandItem color =" + d.b(this.color) + ",dim=" + this.light + ",maxDimming=" + this.maxDimming + ",rate=" + this.rate + ",fading=" + this.fading;
    }
}
