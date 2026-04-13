package com.leedarson.bean;

import com.leedarson.utils.d;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.e;
import timber.log.a;

public class SchemeBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public List<FrequencyStage> frequencyStageList = new ArrayList();

    public static class FrequencyStage {
        public int frequencyLowerLimit;
        public int frequencyUpperLimit;
        public List<AmpMode> modes = new ArrayList();
    }

    public FrequencyStage findFreqStage(double freq) {
        Object[] objArr = {new Double(freq)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 1108, new Class[]{Double.TYPE}, FrequencyStage.class);
        if (proxy.isSupported) {
            return (FrequencyStage) proxy.result;
        }
        for (FrequencyStage stage : this.frequencyStageList) {
            if (freq <= ((double) stage.frequencyUpperLimit) && freq > ((double) stage.frequencyLowerLimit)) {
                return stage;
            }
        }
        return null;
    }

    public AmpMode findAmpMode(double freq, double amp) {
        Object[] objArr = {new Double(freq), new Double(amp)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 1109, new Class[]{cls, cls}, AmpMode.class);
        if (proxy.isSupported) {
            return (AmpMode) proxy.result;
        }
        FrequencyStage freqStage = findFreqStage(freq);
        if (freqStage == null) {
            return null;
        }
        for (AmpMode mode : freqStage.modes) {
            if (amp <= ((double) mode.amplitudeUpperLimit) && amp > ((double) mode.amplitudeLowerLimit)) {
                return mode;
            }
        }
        return null;
    }

    public static class AmpMode {
        public static ChangeQuickRedirect changeQuickRedirect;
        public float amplitudeLowerLimit;
        public float amplitudeUpperLimit;
        private float b;
        private String calcMaxBrightness;
        private float k;
        public List<CommandItem> lightingEffect = new ArrayList();
        private String modeKey;
        public int speed;

        public void setCalcMaxBrightness(String calcMaxBrightness2) {
            if (!PatchProxy.proxy(new Object[]{calcMaxBrightness2}, this, changeQuickRedirect, false, 1110, new Class[]{String.class}, Void.TYPE).isSupported) {
                this.calcMaxBrightness = calcMaxBrightness2;
                int mul = calcMaxBrightness2.indexOf(e.ANY_MARKER);
                int add = calcMaxBrightness2.indexOf(e.ANY_NON_NULL_MARKER);
                this.b = Float.parseFloat(calcMaxBrightness2.substring(add + 1));
                this.k = Float.parseFloat(calcMaxBrightness2.substring(mul + 1, add));
            }
        }

        public String getModeKey() {
            return this.modeKey;
        }

        public void setModeKey(String modeKey2) {
            this.modeKey = modeKey2;
        }

        public float getAmplitudeUpperLimit() {
            return this.amplitudeUpperLimit;
        }

        public float getK() {
            return this.k;
        }

        public float getB() {
            return this.b;
        }

        public int nextColorIndex(int lastColor) {
            Object[] objArr = {new Integer(lastColor)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1111, new Class[]{cls}, cls);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            a.b g = a.g("Rhythm");
            g.a("#nextColorIndex  modekey=" + this.modeKey + ",lastColor" + d.b(lastColor), new Object[0]);
            int sameColorIndex = -1;
            for (int i = 0; i < this.lightingEffect.size(); i++) {
                if (this.lightingEffect.get(i).color == lastColor) {
                    if (sameColorIndex == -1) {
                        sameColorIndex = i;
                    }
                } else if (sameColorIndex != -1) {
                    return i;
                }
            }
            return 0;
        }

        public int[] sameColorNextIndex(int lastColor, int lastColorIndex) {
            Object[] objArr = {new Integer(lastColor), new Integer(lastColorIndex)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, cls};
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1112, clsArr, int[].class);
            if (proxy.isSupported) {
                return (int[]) proxy.result;
            }
            a.b g = a.g("Rhythm");
            g.a("#sameColorNextIndex  modekey=" + this.modeKey + ",lastColor" + d.b(lastColor) + "当前颜色角标:" + lastColorIndex, new Object[0]);
            for (int i = 0; i < this.lightingEffect.size(); i++) {
                if (this.lightingEffect.get(i).color == lastColor) {
                    int resColorSubIndex = lastColorIndex + 1;
                    int resSubIndex = i + resColorSubIndex;
                    if (resSubIndex > this.lightingEffect.size() - 1) {
                        return new int[]{0, 0};
                    }
                    if (this.lightingEffect.get(resSubIndex).color != lastColor) {
                        resColorSubIndex = 0;
                    }
                    return new int[]{resSubIndex, resColorSubIndex};
                }
            }
            return new int[]{0, 0};
        }
    }
}
