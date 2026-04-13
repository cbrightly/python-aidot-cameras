package com.leedarson.utils;

import android.graphics.Color;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: ColorUtils */
public class d {
    public static final int[] A = {SupportMenu.CATEGORY_MASK, -62464, -59136, -56064, -52736, -49664, -46336, -43264, -39936, -36864, -33536, -30208, -26880, -23552, -20224, -16896, -13568, -10240, -6912, -3584, InputDeviceCompat.SOURCE_ANY, -1704192, -3342592, -5046528, -6684928, -8388864, -10027264, -11731200, -13369600, -15073536, -16711936, -16711911, -16711885, -16711860, -16711834, -16711809, -16711783, -16711758, -16711732, -16711707, -16711681, -16718337, -16724737, -16731393, -16737793, -16744449, -16750849, -16757505, -16763905, -16770561, -16776961, -15138561, -13434625, -11796225, -10092289, -8453889, -6749953, -5111553, -3407617, -1769217, -65281, -65307, -65332, -65358, -65383, -65409, -65434, -65460, -65485, -65511};
    public static final int[] B;
    private static int a = Color.parseColor("#FFA500");
    private static int b = Color.parseColor("#ff0000");
    private static int c = Color.parseColor("#00ff00");
    public static ChangeQuickRedirect changeQuickRedirect;
    private static int d = Color.parseColor("#0000ff");
    private static int e = Color.parseColor("#0080ff");
    private static int f = Color.parseColor("#ffff00");
    private static int g = Color.parseColor("#00ffff");
    private static int h = Color.parseColor("#7b00ff");
    private static int i = Color.parseColor("#ffffff");
    private static int j = Color.parseColor("#ff54e5");
    private static int k = Color.parseColor("#90ee90");
    private static int l = Color.parseColor("#87cefa");
    private static int m = Color.parseColor("#ffff58");
    private static int n = Color.parseColor("#dda0dd");
    private static int o = Color.parseColor("#0000ff");
    private static int p = Color.parseColor("#0d90bf");
    private static int q = Color.parseColor("#ffe5ed");
    private static int r;
    public static final int[] s;
    public static final int[] t;
    public static final int[] u;
    public static final int[] v = {Color.parseColor("#ff0000"), Color.parseColor("#ffa500"), Color.parseColor("#ffff00"), Color.parseColor("#00ff00"), Color.parseColor("#00ffff"), Color.parseColor("#0080ff"), Color.parseColor("#7b00ff"), Color.parseColor("#ff56aa"), Color.parseColor("#028bfc"), Color.parseColor("#ffff00"), Color.parseColor("#00ff00"), Color.parseColor("#00ffff"), Color.parseColor("#0080ff"), Color.parseColor("#7b00ff")};
    public static final int[] w = {b, c, Color.parseColor("#FF7B00"), b, c, Color.parseColor("#FF7B00")};
    public static final int[] x = {Color.parseColor("#FCA39D"), Color.parseColor("#FFCC99"), Color.parseColor("#66FF99"), Color.parseColor("#66FFFF"), Color.parseColor("#FFBFBF"), Color.parseColor("#F2B6E3"), Color.parseColor("#B6C5F2"), Color.parseColor("#FFFFBF")};
    public static final int[] y = {Color.parseColor("#F90911"), Color.parseColor("#F77690"), Color.parseColor("#F9F509"), Color.parseColor("#FCA39D"), Color.parseColor("#76AEF7"), Color.parseColor("#91F909"), Color.parseColor("#00ffff"), Color.parseColor("#B6C5F2"), Color.parseColor("#09F979"), Color.parseColor("#09F945"), Color.parseColor("#7b00ff"), Color.parseColor("#F96D09"), Color.parseColor("#F9094D"), Color.parseColor("#F9AD09"), Color.parseColor("#7D09F9"), Color.parseColor("#09F9F5"), Color.parseColor("#FCA39D"), Color.parseColor("#25F909"), Color.parseColor("#09F9B5"), Color.parseColor("#66FFFF")};
    public static final int[] z = {16711680, 16743680, 16776960, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 65535, 255, 16711935, 16711680};

    static {
        int parseColor = Color.parseColor("#ffffb0");
        r = parseColor;
        int i2 = b;
        int i3 = f;
        int i4 = c;
        int i5 = g;
        int i6 = e;
        int i7 = h;
        int i8 = j;
        s = new int[]{i2, i3, i4, i5, i6, i7, i8};
        t = new int[]{i2, a, i3, i4, i5, i6, i7};
        u = new int[]{o, p, i8, parseColor};
        int[] iArr = new int[NeedPermissionEvent.PER_ANDROID_S_BLE];
        // fill-array-data instruction
        iArr[0] = -65536;
        iArr[1] = -64000;
        iArr[2] = -62464;
        iArr[3] = -60928;
        iArr[4] = -59136;
        iArr[5] = -57600;
        iArr[6] = -56064;
        iArr[7] = -54528;
        iArr[8] = -52736;
        iArr[9] = -51200;
        iArr[10] = -49664;
        iArr[11] = -48128;
        iArr[12] = -46336;
        iArr[13] = -44800;
        iArr[14] = -43264;
        iArr[15] = -41728;
        iArr[16] = -39936;
        iArr[17] = -38400;
        iArr[18] = -36864;
        iArr[19] = -35328;
        iArr[20] = -33536;
        iArr[21] = -32000;
        iArr[22] = -30208;
        iArr[23] = -28672;
        iArr[24] = -26880;
        iArr[25] = -25344;
        iArr[26] = -23552;
        iArr[27] = -22016;
        iArr[28] = -20224;
        iArr[29] = -18688;
        iArr[30] = -16896;
        iArr[31] = -15360;
        iArr[32] = -13568;
        iArr[33] = -12032;
        iArr[34] = -10240;
        iArr[35] = -8704;
        iArr[36] = -6912;
        iArr[37] = -5376;
        iArr[38] = -3584;
        iArr[39] = -2048;
        iArr[40] = -256;
        iArr[41] = -852224;
        iArr[42] = -1704192;
        iArr[43] = -2556160;
        iArr[44] = -3342592;
        iArr[45] = -4194560;
        iArr[46] = -5046528;
        iArr[47] = -5898496;
        iArr[48] = -6684928;
        iArr[49] = -7536896;
        iArr[50] = -8388864;
        iArr[51] = -9240832;
        iArr[52] = -10027264;
        iArr[53] = -10879232;
        iArr[54] = -11731200;
        iArr[55] = -12583168;
        iArr[56] = -13369600;
        iArr[57] = -14221568;
        iArr[58] = -15073536;
        iArr[59] = -15925504;
        iArr[60] = -16711936;
        iArr[61] = -16711924;
        iArr[62] = -16711911;
        iArr[63] = -16711898;
        iArr[64] = -16711885;
        iArr[65] = -16711873;
        iArr[66] = -16711860;
        iArr[67] = -16711847;
        iArr[68] = -16711834;
        iArr[69] = -16711822;
        iArr[70] = -16711809;
        iArr[71] = -16711796;
        iArr[72] = -16711783;
        iArr[73] = -16711771;
        iArr[74] = -16711758;
        iArr[75] = -16711745;
        iArr[76] = -16711732;
        iArr[77] = -16711720;
        iArr[78] = -16711707;
        iArr[79] = -16711694;
        iArr[80] = -16711681;
        iArr[81] = -16715009;
        iArr[82] = -16718337;
        iArr[83] = -16721665;
        iArr[84] = -16724737;
        iArr[85] = -16728065;
        iArr[86] = -16731393;
        iArr[87] = -16734721;
        iArr[88] = -16737793;
        iArr[89] = -16741121;
        iArr[90] = -16744449;
        iArr[91] = -16747777;
        iArr[92] = -16750849;
        iArr[93] = -16754177;
        iArr[94] = -16757505;
        iArr[95] = -16760833;
        iArr[96] = -16763905;
        iArr[97] = -16767233;
        iArr[98] = -16770561;
        iArr[99] = -16773889;
        iArr[100] = -16776961;
        iArr[101] = -15990529;
        iArr[102] = -15138561;
        iArr[103] = -14286593;
        iArr[104] = -13434625;
        iArr[105] = -12648193;
        iArr[106] = -11796225;
        iArr[107] = -10944257;
        iArr[108] = -10092289;
        iArr[109] = -9305857;
        iArr[110] = -8453889;
        iArr[111] = -7601921;
        iArr[112] = -6749953;
        iArr[113] = -5963521;
        iArr[114] = -5111553;
        iArr[115] = -4259585;
        iArr[116] = -3407617;
        iArr[117] = -2621185;
        iArr[118] = -1769217;
        iArr[119] = -917249;
        iArr[120] = -65281;
        iArr[121] = -65294;
        iArr[122] = -65307;
        iArr[123] = -65320;
        iArr[124] = -65332;
        iArr[125] = -65345;
        iArr[126] = -65358;
        iArr[127] = -65371;
        iArr[128] = -65383;
        iArr[129] = -65396;
        iArr[130] = -65409;
        iArr[131] = -65422;
        iArr[132] = -65434;
        iArr[133] = -65447;
        iArr[134] = -65460;
        iArr[135] = -65473;
        iArr[136] = -65485;
        iArr[137] = -65498;
        iArr[138] = -65511;
        iArr[139] = -65524;
        B = iArr;
    }

    public static String b(int color) {
        String str;
        String str2;
        String B2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(color)}, (Object) null, changeQuickRedirect, true, 11471, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer sb = new StringBuffer();
        String R = Integer.toHexString(Color.red(color));
        String G = Integer.toHexString(Color.green(color));
        String B3 = Integer.toHexString(Color.blue(color));
        if (R.length() == 1) {
            str = "0" + R;
        } else {
            str = R;
        }
        String R2 = str;
        if (G.length() == 1) {
            str2 = "0" + G;
        } else {
            str2 = G;
        }
        String G2 = str2;
        if (B3.length() == 1) {
            B2 = "0" + B3;
        } else {
            B2 = B3;
        }
        sb.append("#");
        sb.append(R2);
        sb.append(G2);
        sb.append(B2);
        return sb.toString();
    }

    public static int a(int[] arr, int currentColor, int offset) {
        if (offset >= arr.length - 1) {
            return 0;
        }
        for (int i2 = offset; i2 < arr.length; i2++) {
            if (arr[i2] == currentColor) {
                return i2;
            }
        }
        return 0;
    }
}
