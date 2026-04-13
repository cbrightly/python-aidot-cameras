package com.leedarson.serviceinterface.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Locale;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class PubUtils {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String bytesToHex(byte[] bytes) {
        int a;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 9438, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        char[] buf = new char[(bytes.length * 2)];
        int index = 0;
        for (byte b : bytes) {
            if (b < 0) {
                a = b + 256;
            } else {
                a = b;
            }
            int index2 = index + 1;
            char[] cArr = HEX_CHAR;
            buf[index] = cArr[a / 16];
            index = index2 + 1;
            buf[index2] = cArr[a % 16];
        }
        return new String(buf);
    }

    public static byte[] toBytes(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 9439, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
        }
        return bytes;
    }

    public static String getString(Context context, int resId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Integer(resId)}, (Object) null, changeQuickRedirect, true, 9440, new Class[]{Context.class, Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        setLanguage(context);
        return context != null ? context.getString(resId) : "";
    }

    public static void setLanguage(Context context) {
        Locale selectLocale;
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9441, new Class[]{Context.class}, Void.TYPE).isSupported && context != null) {
            String language = SharePreferenceUtils.getPrefString(context, IjkMediaMeta.IJKM_KEY_LANGUAGE, "");
            Resources resources = context.getResources();
            if (resources != null) {
                DisplayMetrics metrics = resources.getDisplayMetrics();
                Configuration config = resources.getConfiguration();
                String[] splits = language.split("-");
                if (splits.length > 1) {
                    selectLocale = new Locale(splits[0], splits[1]);
                } else {
                    selectLocale = new Locale(splits[0]);
                }
                if (needUpdateLocale(context, selectLocale)) {
                    config.setLocale(selectLocale);
                    resources.updateConfiguration(config, metrics);
                }
            }
        }
    }

    public static boolean needUpdateLocale(Context pContext, Locale newUserLocale) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pContext, newUserLocale}, (Object) null, changeQuickRedirect, true, 9442, new Class[]{Context.class, Locale.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return newUserLocale != null && !getCurrentLocale(pContext).equals(newUserLocale);
    }

    public static Locale getCurrentLocale(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9443, new Class[]{Context.class}, Locale.class);
        if (proxy.isSupported) {
            return (Locale) proxy.result;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getResources().getConfiguration().getLocales().get(0);
        }
        return context.getResources().getConfiguration().locale;
    }

    public static int getStatusBarHeight(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9444, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cf, code lost:
        if (r5.equals("cs") != false) goto L_0x00d3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getDateForMMMTime(long r11) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r11)
            r8 = 0
            r1[r8] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Long.TYPE
            r6[r8] = r2
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r2 = 0
            r4 = 1
            r5 = 9445(0x24e5, float:1.3235E-41)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0026
            java.lang.Object r11 = r1.result
            java.lang.String r11 = (java.lang.String) r11
            return r11
        L_0x0026:
            java.util.Date r1 = new java.util.Date
            r1.<init>(r11)
            com.leedarson.base.application.BaseApplication r2 = com.leedarson.base.application.BaseApplication.b()
            java.lang.String r3 = "language"
            java.lang.String r4 = "en-US"
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r2, r3, r4)
            java.lang.String r3 = "-"
            java.lang.String[] r3 = r2.split(r3)
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            r6 = 0
            int r7 = r3.length     // Catch:{ Exception -> 0x0052 }
            if (r7 <= r0) goto L_0x0048
            r7 = r3[r0]     // Catch:{ Exception -> 0x0052 }
            r4 = r7
        L_0x0048:
            r7 = r3[r8]     // Catch:{ Exception -> 0x0052 }
            r5 = r7
            java.util.Locale r7 = new java.util.Locale     // Catch:{ Exception -> 0x0052 }
            r7.<init>(r5, r4)     // Catch:{ Exception -> 0x0052 }
            r6 = r7
            goto L_0x0055
        L_0x0052:
            r7 = move-exception
            java.util.Locale r6 = java.util.Locale.ENGLISH
        L_0x0055:
            java.lang.String r7 = "MMM d, yyyy"
            r9 = -1
            int r10 = r5.hashCode()
            switch(r10) {
                case 3184: goto L_0x00c9;
                case 3201: goto L_0x00bf;
                case 3241: goto L_0x00b5;
                case 3276: goto L_0x00ab;
                case 3355: goto L_0x00a1;
                case 3371: goto L_0x0097;
                case 3383: goto L_0x008d;
                case 3518: goto L_0x0082;
                case 3580: goto L_0x0078;
                case 3700: goto L_0x006d;
                case 3886: goto L_0x0061;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x00d2
        L_0x0061:
            java.lang.String r0 = "zh"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 10
            goto L_0x00d3
        L_0x006d:
            java.lang.String r0 = "th"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 9
            goto L_0x00d3
        L_0x0078:
            java.lang.String r0 = "pl"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 3
            goto L_0x00d3
        L_0x0082:
            java.lang.String r0 = "nl"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 8
            goto L_0x00d3
        L_0x008d:
            java.lang.String r0 = "ja"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 7
            goto L_0x00d3
        L_0x0097:
            java.lang.String r0 = "it"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 6
            goto L_0x00d3
        L_0x00a1:
            java.lang.String r0 = "id"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 5
            goto L_0x00d3
        L_0x00ab:
            java.lang.String r0 = "fr"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 4
            goto L_0x00d3
        L_0x00b5:
            java.lang.String r0 = "en"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = r8
            goto L_0x00d3
        L_0x00bf:
            java.lang.String r0 = "de"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 2
            goto L_0x00d3
        L_0x00c9:
            java.lang.String r8 = "cs"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x005f
            goto L_0x00d3
        L_0x00d2:
            r0 = r9
        L_0x00d3:
            switch(r0) {
                case 0: goto L_0x00ec;
                case 1: goto L_0x00e9;
                case 2: goto L_0x00e6;
                case 3: goto L_0x00e6;
                case 4: goto L_0x00e3;
                case 5: goto L_0x00e3;
                case 6: goto L_0x00e3;
                case 7: goto L_0x00e0;
                case 8: goto L_0x00dd;
                case 9: goto L_0x00da;
                case 10: goto L_0x00d7;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            goto L_0x00ef
        L_0x00d7:
            java.lang.String r7 = "yyyy年M月d日"
            goto L_0x00ef
        L_0x00da:
            java.lang.String r7 = "dd/MM/yyyy"
            goto L_0x00ef
        L_0x00dd:
            java.lang.String r7 = "d-MM-yyyy"
            goto L_0x00ef
        L_0x00e0:
            java.lang.String r7 = "yyyy/MM/d"
            goto L_0x00ef
        L_0x00e3:
            java.lang.String r7 = "d/MM/yyyy"
            goto L_0x00ef
        L_0x00e6:
            java.lang.String r7 = "d.MM.yyyy"
            goto L_0x00ef
        L_0x00e9:
            java.lang.String r7 = "d. MMMM yyyy"
            goto L_0x00ef
        L_0x00ec:
            java.lang.String r7 = "MMM d, yyyy"
        L_0x00ef:
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            r0.<init>(r7, r6)
            java.lang.String r8 = r0.format(r1)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceinterface.utils.PubUtils.getDateForMMMTime(long):java.lang.String");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cf, code lost:
        if (r5.equals("cs") != false) goto L_0x00d3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getDateForCalendar(long r11) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r11)
            r8 = 0
            r1[r8] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Long.TYPE
            r6[r8] = r2
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r2 = 0
            r4 = 1
            r5 = 9446(0x24e6, float:1.3237E-41)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0026
            java.lang.Object r11 = r1.result
            java.lang.String r11 = (java.lang.String) r11
            return r11
        L_0x0026:
            java.util.Date r1 = new java.util.Date
            r1.<init>(r11)
            com.leedarson.base.application.BaseApplication r2 = com.leedarson.base.application.BaseApplication.b()
            java.lang.String r3 = "language"
            java.lang.String r4 = "en-US"
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r2, r3, r4)
            java.lang.String r3 = "-"
            java.lang.String[] r3 = r2.split(r3)
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            r6 = 0
            int r7 = r3.length     // Catch:{ Exception -> 0x0052 }
            if (r7 <= r0) goto L_0x0048
            r7 = r3[r0]     // Catch:{ Exception -> 0x0052 }
            r4 = r7
        L_0x0048:
            r7 = r3[r8]     // Catch:{ Exception -> 0x0052 }
            r5 = r7
            java.util.Locale r7 = new java.util.Locale     // Catch:{ Exception -> 0x0052 }
            r7.<init>(r5, r4)     // Catch:{ Exception -> 0x0052 }
            r6 = r7
            goto L_0x0055
        L_0x0052:
            r7 = move-exception
            java.util.Locale r6 = java.util.Locale.ENGLISH
        L_0x0055:
            java.lang.String r7 = "MMM yyyy"
            r9 = -1
            int r10 = r5.hashCode()
            switch(r10) {
                case 3184: goto L_0x00c9;
                case 3201: goto L_0x00bf;
                case 3241: goto L_0x00b5;
                case 3276: goto L_0x00ab;
                case 3355: goto L_0x00a1;
                case 3371: goto L_0x0097;
                case 3383: goto L_0x008c;
                case 3518: goto L_0x0081;
                case 3580: goto L_0x0077;
                case 3700: goto L_0x006d;
                case 3886: goto L_0x0061;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x00d2
        L_0x0061:
            java.lang.String r0 = "zh"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 10
            goto L_0x00d3
        L_0x006d:
            java.lang.String r0 = "th"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 7
            goto L_0x00d3
        L_0x0077:
            java.lang.String r0 = "pl"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 3
            goto L_0x00d3
        L_0x0081:
            java.lang.String r0 = "nl"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 9
            goto L_0x00d3
        L_0x008c:
            java.lang.String r0 = "ja"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 8
            goto L_0x00d3
        L_0x0097:
            java.lang.String r0 = "it"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 6
            goto L_0x00d3
        L_0x00a1:
            java.lang.String r0 = "id"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 5
            goto L_0x00d3
        L_0x00ab:
            java.lang.String r0 = "fr"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 4
            goto L_0x00d3
        L_0x00b5:
            java.lang.String r0 = "en"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = r8
            goto L_0x00d3
        L_0x00bf:
            java.lang.String r0 = "de"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 2
            goto L_0x00d3
        L_0x00c9:
            java.lang.String r8 = "cs"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x005f
            goto L_0x00d3
        L_0x00d2:
            r0 = r9
        L_0x00d3:
            switch(r0) {
                case 0: goto L_0x00e9;
                case 1: goto L_0x00e6;
                case 2: goto L_0x00e3;
                case 3: goto L_0x00e3;
                case 4: goto L_0x00e0;
                case 5: goto L_0x00e0;
                case 6: goto L_0x00e0;
                case 7: goto L_0x00e0;
                case 8: goto L_0x00dd;
                case 9: goto L_0x00da;
                case 10: goto L_0x00d7;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            goto L_0x00ec
        L_0x00d7:
            java.lang.String r7 = "yyyy年M月"
            goto L_0x00ec
        L_0x00da:
            java.lang.String r7 = "MM-yyyy"
            goto L_0x00ec
        L_0x00dd:
            java.lang.String r7 = "yyyy/MM"
            goto L_0x00ec
        L_0x00e0:
            java.lang.String r7 = "MM/yyyy"
            goto L_0x00ec
        L_0x00e3:
            java.lang.String r7 = "MM.yyyy"
            goto L_0x00ec
        L_0x00e6:
            java.lang.String r7 = "MMMM yyyy"
            goto L_0x00ec
        L_0x00e9:
            java.lang.String r7 = "MMM yyyy"
        L_0x00ec:
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            r0.<init>(r7, r6)
            java.lang.String r8 = r0.format(r1)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceinterface.utils.PubUtils.getDateForCalendar(long):java.lang.String");
    }

    public static Locale getCurrentSystemLocal() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9447, new Class[0], Locale.class);
        if (proxy.isSupported) {
            return (Locale) proxy.result;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return LocaleList.getDefault().get(0);
        }
        return Locale.getDefault();
    }
}
