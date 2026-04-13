package com.airbnb.lottie;

/* compiled from: RenderMode */
public enum o0 {
    AUTOMATIC,
    HARDWARE,
    SOFTWARE;

    /* compiled from: RenderMode */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a = null;

        static {
            int[] iArr = new int[o0.values().length];
            a = iArr;
            try {
                iArr[o0.HARDWARE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[o0.SOFTWARE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[o0.AUTOMATIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public boolean useSoftwareRendering(int sdkInt, boolean hasDashPattern, int numMasksAndMattes) {
        switch (a.a[ordinal()]) {
            case 1:
                return false;
            case 2:
                return true;
            default:
                if ((hasDashPattern && sdkInt < 28) || numMasksAndMattes > 4) {
                    return true;
                }
                if (sdkInt <= 25) {
                    return true;
                }
                return false;
        }
    }
}
