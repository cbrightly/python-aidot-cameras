package com.leedarson.hummandetect;

import com.meituan.robust.ChangeQuickRedirect;

public class HumanDetectSDK {
    public static ChangeQuickRedirect changeQuickRedirect;

    public native int create(String str);

    public native DetectRoi detect(byte[] bArr, int i, int i2);

    public native int release();

    static {
        System.loadLibrary("hummandetect");
    }
}
