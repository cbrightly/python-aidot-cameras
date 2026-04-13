package com.leedarson.newui.view.radar;

import android.graphics.Color;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.view.a;
import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: RadarConstants */
public class c {
    public static final int A = a.a(BaseApplication.b(), 130.0f);
    public static final int B = a.a(BaseApplication.b(), 73.0f);
    public static final int C = a.a(BaseApplication.b(), 200.0f);
    public static final int D = a.a(BaseApplication.b(), 112.0f);
    private static final int[] a = {Color.parseColor("#B3FF8A2C"), Color.parseColor("#B32EAD9F"), Color.parseColor("#B36469F6"), Color.parseColor("#B3BDB521"), Color.parseColor("#B32992EA")};
    private static final int[] b = {Color.parseColor("#FF8A2C"), Color.parseColor("#2EAD9F"), Color.parseColor("#6469F6"), Color.parseColor("#BDB521"), Color.parseColor("#2992EA")};
    private static final int[] c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final int[] d;
    private static final int[] e;
    private static final int[] f;
    private static final int[] g;
    public static final int[][] h;
    private static final float[] i = {0.65f, 0.7f, 0.75f, 0.8f, 0.85f, 0.9f, 0.95f, 1.0f};
    public static final int j = a.a(BaseApplication.b(), 2.0f);
    public static final int k = a.a(BaseApplication.b(), 20.0f);
    public static final int l = a.a(BaseApplication.b(), 50.0f);
    public static final int m = a.a(BaseApplication.b(), 70.0f);
    public static final int n = a.a(BaseApplication.b(), 2.0f);
    public static final int o = a.a(BaseApplication.b(), 1.0f);
    public static final int p = a.a(BaseApplication.b(), 4.0f);
    public static final int q = a.a(BaseApplication.b(), 11.0f);
    public static final int r = a.a(BaseApplication.b(), 5.0f);
    public static final int s = a.a(BaseApplication.b(), 11.0f);
    public static final int t = a.a(BaseApplication.b(), 7.0f);
    public static final int u = a.a(BaseApplication.b(), 21.0f);
    public static final int v = a.a(BaseApplication.b(), 6.4f);
    public static final int w = a.a(BaseApplication.b(), 9.0f);
    public static final int x = a.a(BaseApplication.b(), 34.0f);
    public static final int y = a.a(BaseApplication.b(), 9.0f);
    public static final int z = a.a(BaseApplication.b(), 34.0f);

    static {
        int[] iArr = {Color.parseColor("#FEDDC3"), Color.parseColor("#FED2AF"), Color.parseColor("#FDC69A"), Color.parseColor("#FEBC86"), Color.parseColor("#FDB072"), Color.parseColor("#FCA45D"), Color.parseColor("#FD9A4A"), Color.parseColor("#FF8A2C")};
        c = iArr;
        int[] iArr2 = {Color.parseColor("#C0E6E2"), Color.parseColor("#ABDED9"), Color.parseColor("#96D6CF"), Color.parseColor("#82CEC5"), Color.parseColor("#6DC5BC"), Color.parseColor("#58BDB3"), Color.parseColor("#42B5A8"), Color.parseColor("#2EAD9F")};
        d = iArr2;
        int[] iArr3 = {Color.parseColor("#D0D2FC"), Color.parseColor("#C1C3FB"), Color.parseColor("#B1B3FA"), Color.parseColor("#A2A5FA"), Color.parseColor("#9295F9"), Color.parseColor("#8386F7"), Color.parseColor("#7478F7"), Color.parseColor("#6469F6")};
        e = iArr3;
        int[] iArr4 = {Color.parseColor("#EBE9BC"), Color.parseColor("#E5E1A6"), Color.parseColor("#DEDA90"), Color.parseColor("#D7D37A"), Color.parseColor("#D1CB63"), Color.parseColor("#CAC44D"), Color.parseColor("#C3BC37"), Color.parseColor("#BDB521")};
        f = iArr4;
        int[] iArr5 = {Color.parseColor("#BEDEF9"), Color.parseColor("#A9D3F7"), Color.parseColor("#94C9F5"), Color.parseColor("#7FBEF3"), Color.parseColor("#69B3F1"), Color.parseColor("#54A9EF"), Color.parseColor("#3E9EED"), Color.parseColor("#2992EA")};
        g = iArr5;
        h = new int[][]{iArr, iArr2, iArr3, iArr4, iArr5};
    }

    public static int[] a(int colorGroupIndex) {
        return h[0];
    }

    public static float b(int index) {
        if (index >= 0) {
            float[] fArr = i;
            if (index < fArr.length) {
                return fArr[index];
            }
        }
        return i[0];
    }

    public static int c(int colorGroupIndex) {
        return a[0];
    }

    public static int d(int colorGroupIndex) {
        return b[0];
    }
}
