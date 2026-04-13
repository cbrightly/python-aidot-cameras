package com.leedarson.utils;

import com.leedarson.bean.FftResult;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.math3.complex.a;
import org.apache.commons.math3.transform.b;
import org.apache.commons.math3.transform.c;

/* compiled from: VoiceUtil */
public class p {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static double e(short[] bufferRead, int readSize) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bufferRead, new Integer(readSize)}, (Object) null, changeQuickRedirect, true, 11548, new Class[]{short[].class, Integer.TYPE}, Double.TYPE);
        if (proxy.isSupported) {
            return ((Double) proxy.result).doubleValue();
        }
        long v = 0;
        for (short value : bufferRead) {
            v += (long) (value * value);
        }
        return Math.log10(((double) v) / ((double) readSize)) * 10.0d;
    }

    public static FftResult c(short[] buffer, int sampleRate) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{buffer, new Integer(sampleRate)}, (Object) null, changeQuickRedirect, true, 11550, new Class[]{short[].class, Integer.TYPE}, FftResult.class);
        if (proxy.isSupported) {
            return (FftResult) proxy.result;
        }
        a[] fftComplex = b(buffer, new b(org.apache.commons.math3.transform.a.STANDARD));
        double maxAmp = 0.0d;
        int[] ampArr = new int[(fftComplex.length / 2)];
        float resultFreq = 0.0f;
        for (int i = 0; i < fftComplex.length / 2; i++) {
            double amp = fftComplex[i].abs();
            ampArr[i] = (int) amp;
            if (amp > maxAmp) {
                maxAmp = amp;
                resultFreq = ((((float) sampleRate) * 1.0f) / ((float) fftComplex.length)) * ((float) i);
            }
        }
        return new FftResult(resultFreq, ampArr, maxAmp);
    }

    private static a[] a(double[] inputData, b fft) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inputData, fft}, (Object) null, changeQuickRedirect2, true, 11551, new Class[]{double[].class, b.class}, a[].class);
        if (proxy.isSupported) {
            return (a[]) proxy.result;
        }
        if (fft == null) {
            fft = new b(org.apache.commons.math3.transform.a.STANDARD);
        }
        return fft.transform(inputData, c.FORWARD);
    }

    public static int g(int k) {
        Object[] objArr = {new Integer(k)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11553, new Class[]{cls}, cls);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : 1 << ((int) f((double) k, 2.0d));
    }

    public static double f(double value, double base) {
        Object[] objArr = {new Double(value), new Double(base)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11554, new Class[]{cls, cls}, cls);
        return proxy.isSupported ? ((Double) proxy.result).doubleValue() : Math.log(value) / Math.log(base);
    }

    public static a[] b(short[] inputData, b fft) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inputData, fft}, (Object) null, changeQuickRedirect, true, 11555, new Class[]{short[].class, b.class}, a[].class);
        if (proxy.isSupported) {
            return (a[]) proxy.result;
        }
        if (inputData.length < 1) {
            return null;
        }
        int len = g(inputData.length);
        double[] dArr = new double[len];
        for (int i = 0; i < len; i++) {
            dArr[i] = (double) inputData[i];
        }
        return a(dArr, fft);
    }

    public static List d(double countRate, List rhythmLightList) {
        int j;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Double(countRate), rhythmLightList}, (Object) null, changeQuickRedirect, true, 11558, new Class[]{Double.TYPE, List.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        try {
            int rhythmCount = (int) ((((double) rhythmLightList.size()) * countRate) + 1.0d);
            if (rhythmCount < 1) {
                rhythmCount = 1;
            }
            if (rhythmCount > rhythmLightList.size()) {
                rhythmCount = rhythmLightList.size();
            }
            List list = new ArrayList();
            for (int r = 0; r < rhythmLightList.size(); r++) {
                list.add(rhythmLightList.get(r));
            }
            List listRandom = new ArrayList();
            for (int i = rhythmCount; i >= 1; i--) {
                Random random = new Random();
                Math.random();
                int bound = list.size();
                if (bound == 0) {
                    j = 0;
                } else {
                    j = random.nextInt(bound);
                }
                listRandom.add(list.get(j));
                list.remove(j);
            }
            return listRandom;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
