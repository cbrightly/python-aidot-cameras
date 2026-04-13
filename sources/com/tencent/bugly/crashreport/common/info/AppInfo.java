package com.tencent.bugly.crashreport.common.info;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/* compiled from: BUGLY */
public class AppInfo {
    private static ActivityManager a;

    static {
        "@buglyAllChannel@".split(",");
        "@buglyAllChannelPriority@".split(",");
    }

    public static String a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageName();
        } catch (Throwable th) {
            if (x.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static PackageInfo b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(a(context), 0);
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static boolean a(Context context, String str) {
        if (context == null || str == null || str.trim().length() <= 0) {
            return false;
        }
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr != null) {
                for (String equals : strArr) {
                    if (str.equals(equals)) {
                        return true;
                    }
                }
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0048 A[Catch:{ all -> 0x0059, all -> 0x0060 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0051 A[SYNTHETIC, Splitter:B:24:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(int r7) {
        /*
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ all -> 0x003e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x003e }
            java.lang.String r3 = "/proc/"
            r2.<init>(r3)     // Catch:{ all -> 0x003e }
            r2.append(r7)     // Catch:{ all -> 0x003e }
            java.lang.String r3 = "/cmdline"
            r2.append(r3)     // Catch:{ all -> 0x003e }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x003e }
            r1.<init>(r2)     // Catch:{ all -> 0x003e }
            r0 = 512(0x200, float:7.175E-43)
            char[] r2 = new char[r0]     // Catch:{ all -> 0x003c }
            r1.read(r2)     // Catch:{ all -> 0x003c }
            r3 = 0
            r4 = r3
        L_0x0023:
            if (r4 >= r0) goto L_0x002c
            char r5 = r2[r4]     // Catch:{ all -> 0x003c }
            if (r5 == 0) goto L_0x002c
            int r4 = r4 + 1
            goto L_0x0023
        L_0x002c:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x003c }
            r0.<init>(r2)     // Catch:{ all -> 0x003c }
            java.lang.String r7 = r0.substring(r3, r4)     // Catch:{ all -> 0x003c }
            r1.close()     // Catch:{ all -> 0x003a }
            goto L_0x003b
        L_0x003a:
            r0 = move-exception
        L_0x003b:
            return r7
        L_0x003c:
            r0 = move-exception
            goto L_0x0042
        L_0x003e:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x0042:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0059 }
            if (r2 != 0) goto L_0x004b
            r0.printStackTrace()     // Catch:{ all -> 0x0059 }
        L_0x004b:
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0059 }
            if (r1 == 0) goto L_0x0057
            r1.close()     // Catch:{ all -> 0x0055 }
            goto L_0x0057
        L_0x0055:
            r0 = move-exception
            goto L_0x0058
        L_0x0057:
        L_0x0058:
            return r7
        L_0x0059:
            r7 = move-exception
            if (r1 == 0) goto L_0x0062
            r1.close()     // Catch:{ all -> 0x0060 }
            goto L_0x0062
        L_0x0060:
            r0 = move-exception
            goto L_0x0063
        L_0x0062:
        L_0x0063:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.AppInfo.a(int):java.lang.String");
    }

    public static String c(Context context) {
        CharSequence applicationLabel;
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (!(packageManager == null || applicationInfo == null || (applicationLabel = packageManager.getApplicationLabel(applicationInfo)) == null)) {
                return applicationLabel.toString();
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public static Map<String, String> d(Context context) {
        if (context == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                return null;
            }
            HashMap hashMap = new HashMap();
            Object obj = applicationInfo.metaData.get("BUGLY_DISABLE");
            if (obj != null) {
                hashMap.put("BUGLY_DISABLE", obj.toString());
            }
            Object obj2 = applicationInfo.metaData.get("BUGLY_APPID");
            if (obj2 != null) {
                hashMap.put("BUGLY_APPID", obj2.toString());
            }
            Object obj3 = applicationInfo.metaData.get("BUGLY_APP_CHANNEL");
            if (obj3 != null) {
                hashMap.put("BUGLY_APP_CHANNEL", obj3.toString());
            }
            Object obj4 = applicationInfo.metaData.get("BUGLY_APP_VERSION");
            if (obj4 != null) {
                hashMap.put("BUGLY_APP_VERSION", obj4.toString());
            }
            Object obj5 = applicationInfo.metaData.get("BUGLY_ENABLE_DEBUG");
            if (obj5 != null) {
                hashMap.put("BUGLY_ENABLE_DEBUG", obj5.toString());
            }
            Object obj6 = applicationInfo.metaData.get("com.tencent.rdm.uuid");
            if (obj6 != null) {
                hashMap.put("com.tencent.rdm.uuid", obj6.toString());
            }
            return hashMap;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static List<String> a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            String str = map.get("BUGLY_DISABLE");
            if (str != null) {
                if (str.length() != 0) {
                    String[] split = str.split(",");
                    for (int i = 0; i < split.length; i++) {
                        split[i] = split[i].trim();
                    }
                    return Arrays.asList(split);
                }
            }
            return null;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static String a(byte[] bArr) {
        X509Certificate x509Certificate;
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length > 0) {
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                if (instance == null || (x509Certificate = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(bArr))) == null) {
                    return null;
                }
                sb.append("Issuer|");
                Principal issuerDN = x509Certificate.getIssuerDN();
                if (issuerDN != null) {
                    sb.append(issuerDN.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("SerialNumber|");
                BigInteger serialNumber = x509Certificate.getSerialNumber();
                if (issuerDN != null) {
                    sb.append(serialNumber.toString(16));
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("NotBefore|");
                Date notBefore = x509Certificate.getNotBefore();
                if (issuerDN != null) {
                    sb.append(notBefore.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("NotAfter|");
                Date notAfter = x509Certificate.getNotAfter();
                if (issuerDN != null) {
                    sb.append(notAfter.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("SHA1|");
                String a2 = z.a(MessageDigest.getInstance("SHA1").digest(x509Certificate.getEncoded()));
                if (a2 == null || a2.length() <= 0) {
                    sb.append("unknown");
                } else {
                    sb.append(a2.toString());
                }
                sb.append("\n");
                sb.append("MD5|");
                String a3 = z.a(MessageDigest.getInstance("MD5").digest(x509Certificate.getEncoded()));
                if (a3 == null || a3.length() <= 0) {
                    sb.append("unknown");
                } else {
                    sb.append(a3.toString());
                }
            } catch (CertificateException e) {
                if (!x.a(e)) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        if (sb.length() == 0) {
            return "unknown";
        }
        return sb.toString();
    }

    public static String e(Context context) {
        Signature[] signatureArr;
        String a2 = a(context);
        if (a2 == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a2, 64);
            if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length == 0) {
                return null;
            }
            return a(signatureArr[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean f(Context context) {
        if (context == null) {
            return false;
        }
        if (a == null) {
            a = (ActivityManager) context.getSystemService("activity");
        }
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            a.getMemoryInfo(memoryInfo);
            if (!memoryInfo.lowMemory) {
                return false;
            }
            x.c("Memory is low.", new Object[0]);
            return true;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    private static String h(Context context) {
        String str = "";
        InputStream inputStream = null;
        try {
            String string = z.a("DENGTA_META", context).getString("key_channelpath", str);
            if (z.a(string)) {
                string = "channel.ini";
            }
            x.a("[AppInfo] Beacon channel file path: " + string, new Object[0]);
            if (!string.equals(str)) {
                inputStream = context.getAssets().open(string);
                Properties properties = new Properties();
                properties.load(inputStream);
                str = properties.getProperty("CHANNEL", str);
                x.a("[AppInfo] Beacon channel read from assert: " + str, new Object[0]);
                if (!z.a(str)) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            x.a(e);
                        }
                    }
                    return str;
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    x.a(e2);
                }
            }
        } catch (Exception e3) {
            x.d("[AppInfo] Failed to get get beacon channel", new Object[0]);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    x.a(e4);
                }
            }
            throw th;
        }
        return str;
    }

    private static String i(Context context) {
        try {
            Object obj = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get("CHANNEL_DENGTA");
            if (obj != null) {
                return obj.toString();
            }
            return "";
        } catch (Throwable th) {
            x.d("[AppInfo] Failed to read beacon channel from manifest.", new Object[0]);
            x.a(th);
            return "";
        }
    }

    public static String g(Context context) {
        if (context == null) {
            return "";
        }
        String h = h(context);
        if (!z.a(h)) {
            return h;
        }
        return i(context);
    }
}
