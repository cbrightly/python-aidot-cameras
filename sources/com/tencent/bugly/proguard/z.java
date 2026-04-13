package com.tencent.bugly.proguard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.info.a;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.l;

/* compiled from: BUGLY */
public class z {
    private static Map<String, String> a = null;
    private static boolean b = false;

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.getBuffer().toString();
        } catch (Throwable th2) {
            if (x.a(th2)) {
                return "fail";
            }
            th2.printStackTrace();
            return "fail";
        }
    }

    public static String a() {
        return a(System.currentTimeMillis());
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(j));
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static String a(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    private static byte[] a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        x.c("rqdp{  enD:} %d %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            aj a2 = a.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static byte[] b(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        try {
            aj a2 = a.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.d("encrytype %d %s", Integer.valueOf(i), str);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x006d A[Catch:{ all -> 0x0080 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0072 A[SYNTHETIC, Splitter:B:28:0x0072] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.io.File r5, java.lang.String r6, java.lang.String r7) {
        /*
            java.lang.String r5 = "rqdp{  ZF end}"
            r0 = 0
            if (r6 == 0) goto L_0x0091
            int r1 = r6.length()
            if (r1 != 0) goto L_0x000e
            goto L_0x0091
        L_0x000e:
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "rqdp{  ZF start}"
            com.tencent.bugly.proguard.x.c(r3, r2)
            java.lang.String r2 = "UTF-8"
            byte[] r6 = r6.getBytes(r2)     // Catch:{ all -> 0x0065 }
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0065 }
            r2.<init>(r6)     // Catch:{ all -> 0x0065 }
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0065 }
            r6.<init>()     // Catch:{ all -> 0x0065 }
            java.util.zip.ZipOutputStream r3 = new java.util.zip.ZipOutputStream     // Catch:{ all -> 0x0065 }
            r3.<init>(r6)     // Catch:{ all -> 0x0065 }
            r4 = 8
            r3.setMethod(r4)     // Catch:{ all -> 0x0063 }
            java.util.zip.ZipEntry r4 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x0063 }
            r4.<init>(r7)     // Catch:{ all -> 0x0063 }
            r3.putNextEntry(r4)     // Catch:{ all -> 0x0063 }
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch:{ all -> 0x0063 }
        L_0x003e:
            int r4 = r2.read(r7)     // Catch:{ all -> 0x0063 }
            if (r4 <= 0) goto L_0x0048
            r3.write(r7, r1, r4)     // Catch:{ all -> 0x0063 }
            goto L_0x003e
        L_0x0048:
            r3.closeEntry()     // Catch:{ all -> 0x0063 }
            r3.flush()     // Catch:{ all -> 0x0063 }
            r3.finish()     // Catch:{ all -> 0x0063 }
            byte[] r6 = r6.toByteArray()     // Catch:{ all -> 0x0063 }
            r3.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x005d
        L_0x0059:
            r7 = move-exception
            r7.printStackTrace()
        L_0x005d:
            java.lang.Object[] r7 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.x.c(r5, r7)
            return r6
        L_0x0063:
            r6 = move-exception
            goto L_0x0067
        L_0x0065:
            r6 = move-exception
            r3 = r0
        L_0x0067:
            boolean r7 = com.tencent.bugly.proguard.x.a(r6)     // Catch:{ all -> 0x0080 }
            if (r7 != 0) goto L_0x0070
            r6.printStackTrace()     // Catch:{ all -> 0x0080 }
        L_0x0070:
            if (r3 == 0) goto L_0x007a
            r3.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x007a
        L_0x0076:
            r6 = move-exception
            r6.printStackTrace()
        L_0x007a:
            java.lang.Object[] r6 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.x.c(r5, r6)
            return r0
        L_0x0080:
            r6 = move-exception
            if (r3 == 0) goto L_0x008b
            r3.close()     // Catch:{ IOException -> 0x0087 }
            goto L_0x008b
        L_0x0087:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008b:
            java.lang.Object[] r7 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.x.c(r5, r7)
            throw r6
        L_0x0091:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(java.io.File, java.lang.String, java.lang.String):byte[]");
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        x.c("[Util] Zip %d bytes data with type %s", objArr);
        try {
            ae a2 = ad.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        x.c("[Util] Unzip %d bytes data with type %s", objArr);
        try {
            ae a2 = ad.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return a(a(bArr, 2), 1, str);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        try {
            return b(b(bArr, 1, str), 2);
        } catch (Exception e) {
            if (x.a(e)) {
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public static long b() {
        try {
            return (((System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset())) / CostTimeUtil.DAY) * CostTimeUtil.DAY) - ((long) TimeZone.getDefault().getRawOffset());
        } catch (Throwable th) {
            if (x.a(th)) {
                return -1;
            }
            th.printStackTrace();
            return -1;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(bArr);
            return a(instance.digest());
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.util.zip.ZipOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.io.FileInputStream} */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00c0 A[Catch:{ all -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c5 A[SYNTHETIC, Splitter:B:59:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00cf A[SYNTHETIC, Splitter:B:64:0x00cf] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r6, java.io.File r7, int r8) {
        /*
            java.lang.String r8 = "rqdp{  ZF end}"
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.String r2 = "rqdp{  ZF start}"
            com.tencent.bugly.proguard.x.c(r2, r1)
            if (r6 == 0) goto L_0x0102
            if (r7 == 0) goto L_0x0102
            boolean r1 = r6.equals(r7)
            if (r1 == 0) goto L_0x0018
            goto L_0x0102
        L_0x0018:
            boolean r1 = r6.exists()
            if (r1 == 0) goto L_0x00f9
            boolean r1 = r6.canRead()
            if (r1 != 0) goto L_0x0026
            goto L_0x00f9
        L_0x0026:
            java.io.File r1 = r7.getParentFile()     // Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x003d
            java.io.File r1 = r7.getParentFile()     // Catch:{ all -> 0x0047 }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x0047 }
            if (r1 != 0) goto L_0x003d
            java.io.File r1 = r7.getParentFile()     // Catch:{ all -> 0x0047 }
            r1.mkdirs()     // Catch:{ all -> 0x0047 }
        L_0x003d:
            boolean r1 = r7.exists()     // Catch:{ all -> 0x0047 }
            if (r1 != 0) goto L_0x0046
            r7.createNewFile()     // Catch:{ all -> 0x0047 }
        L_0x0046:
            goto L_0x0051
        L_0x0047:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x0051
            r1.printStackTrace()
        L_0x0051:
            boolean r1 = r7.exists()
            if (r1 == 0) goto L_0x00f8
            boolean r1 = r7.canRead()
            if (r1 != 0) goto L_0x005f
            goto L_0x00f8
        L_0x005f:
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x00b8 }
            r2.<init>(r6)     // Catch:{ all -> 0x00b8 }
            java.util.zip.ZipOutputStream r3 = new java.util.zip.ZipOutputStream     // Catch:{ all -> 0x00b4 }
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00b4 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ all -> 0x00b4 }
            r5.<init>(r7)     // Catch:{ all -> 0x00b4 }
            r4.<init>(r5)     // Catch:{ all -> 0x00b4 }
            r3.<init>(r4)     // Catch:{ all -> 0x00b4 }
            r7 = 8
            r3.setMethod(r7)     // Catch:{ all -> 0x00b2 }
            java.util.zip.ZipEntry r7 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x00b2 }
            java.lang.String r6 = r6.getName()     // Catch:{ all -> 0x00b2 }
            r7.<init>(r6)     // Catch:{ all -> 0x00b2 }
            r3.putNextEntry(r7)     // Catch:{ all -> 0x00b2 }
            r6 = 5000(0x1388, float:7.006E-42)
            byte[] r6 = new byte[r6]     // Catch:{ all -> 0x00b2 }
        L_0x008b:
            int r7 = r2.read(r6)     // Catch:{ all -> 0x00b2 }
            if (r7 <= 0) goto L_0x0095
            r3.write(r6, r0, r7)     // Catch:{ all -> 0x00b2 }
            goto L_0x008b
        L_0x0095:
            r3.flush()     // Catch:{ all -> 0x00b2 }
            r3.closeEntry()     // Catch:{ all -> 0x00b2 }
            r2.close()     // Catch:{ IOException -> 0x009f }
            goto L_0x00a3
        L_0x009f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00a3:
            r3.close()     // Catch:{ IOException -> 0x00a7 }
            goto L_0x00ab
        L_0x00a7:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00ab:
            java.lang.Object[] r6 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r8, r6)
            r6 = 1
            return r6
        L_0x00b2:
            r6 = move-exception
            goto L_0x00b6
        L_0x00b4:
            r6 = move-exception
            r3 = r1
        L_0x00b6:
            r1 = r2
            goto L_0x00ba
        L_0x00b8:
            r6 = move-exception
            r3 = r1
        L_0x00ba:
            boolean r7 = com.tencent.bugly.proguard.x.a(r6)     // Catch:{ all -> 0x00dd }
            if (r7 != 0) goto L_0x00c3
            r6.printStackTrace()     // Catch:{ all -> 0x00dd }
        L_0x00c3:
            if (r1 == 0) goto L_0x00cd
            r1.close()     // Catch:{ IOException -> 0x00c9 }
            goto L_0x00cd
        L_0x00c9:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00cd:
            if (r3 == 0) goto L_0x00d7
            r3.close()     // Catch:{ IOException -> 0x00d3 }
            goto L_0x00d7
        L_0x00d3:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00d7:
            java.lang.Object[] r6 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r8, r6)
            return r0
        L_0x00dd:
            r6 = move-exception
            if (r1 == 0) goto L_0x00e8
            r1.close()     // Catch:{ IOException -> 0x00e4 }
            goto L_0x00e8
        L_0x00e4:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00e8:
            if (r3 == 0) goto L_0x00f2
            r3.close()     // Catch:{ IOException -> 0x00ee }
            goto L_0x00f2
        L_0x00ee:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00f2:
            java.lang.Object[] r7 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.c(r8, r7)
            throw r6
        L_0x00f8:
            return r0
        L_0x00f9:
            java.lang.Object[] r6 = new java.lang.Object[r0]
            java.lang.String r7 = "rqdp{  !sFile.exists() || !sFile.canRead(),pls check ,return!}"
            com.tencent.bugly.proguard.x.d(r7, r6)
            return r0
        L_0x0102:
            java.lang.Object[] r6 = new java.lang.Object[r0]
            java.lang.String r7 = "rqdp{  err ZF 1R!}"
            com.tencent.bugly.proguard.x.d(r7, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(java.io.File, java.io.File, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b2 A[Catch:{ all -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b7 A[SYNTHETIC, Splitter:B:40:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c1 A[SYNTHETIC, Splitter:B:45:0x00c1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<java.lang.String> c(android.content.Context r5, java.lang.String r6) {
        /*
            boolean r5 = com.tencent.bugly.crashreport.common.info.AppInfo.f(r5)
            if (r5 == 0) goto L_0x0017
            java.util.ArrayList r5 = new java.util.ArrayList
            java.lang.String r6 = "unknown(low memory)"
            java.lang.String[] r6 = new java.lang.String[]{r6}
            java.util.List r6 = java.util.Arrays.asList(r6)
            r5.<init>(r6)
            return r5
        L_0x0017:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r0 = 0
            java.lang.String r1 = "/system/bin/sh"
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x00a9 }
            r2.<init>(r1)     // Catch:{ all -> 0x00a9 }
            boolean r2 = r2.exists()     // Catch:{ all -> 0x00a9 }
            if (r2 == 0) goto L_0x0037
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x00a9 }
            r2.<init>(r1)     // Catch:{ all -> 0x00a9 }
            boolean r2 = r2.canExecute()     // Catch:{ all -> 0x00a9 }
            if (r2 != 0) goto L_0x003a
        L_0x0037:
            java.lang.String r1 = "sh"
        L_0x003a:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x00a9 }
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ all -> 0x00a9 }
            r4 = 0
            r3[r4] = r1     // Catch:{ all -> 0x00a9 }
            r1 = 1
            java.lang.String r4 = "-c"
            r3[r1] = r4     // Catch:{ all -> 0x00a9 }
            java.util.List r1 = java.util.Arrays.asList(r3)     // Catch:{ all -> 0x00a9 }
            r2.<init>(r1)     // Catch:{ all -> 0x00a9 }
            r2.add(r6)     // Catch:{ all -> 0x00a9 }
            java.lang.Runtime r6 = java.lang.Runtime.getRuntime()     // Catch:{ all -> 0x00a9 }
            r1 = 3
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x00a9 }
            java.lang.Object[] r1 = r2.toArray(r1)     // Catch:{ all -> 0x00a9 }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ all -> 0x00a9 }
            java.lang.Process r6 = r6.exec(r1)     // Catch:{ all -> 0x00a9 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ all -> 0x00a9 }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ all -> 0x00a9 }
            java.io.InputStream r3 = r6.getInputStream()     // Catch:{ all -> 0x00a9 }
            r2.<init>(r3)     // Catch:{ all -> 0x00a9 }
            r1.<init>(r2)     // Catch:{ all -> 0x00a9 }
        L_0x0070:
            java.lang.String r2 = r1.readLine()     // Catch:{ all -> 0x00a6 }
            if (r2 == 0) goto L_0x007a
            r5.add(r2)     // Catch:{ all -> 0x00a6 }
            goto L_0x0070
        L_0x007a:
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x00a6 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ all -> 0x00a6 }
            java.io.InputStream r6 = r6.getErrorStream()     // Catch:{ all -> 0x00a6 }
            r3.<init>(r6)     // Catch:{ all -> 0x00a6 }
            r2.<init>(r3)     // Catch:{ all -> 0x00a6 }
        L_0x0088:
            java.lang.String r6 = r2.readLine()     // Catch:{ all -> 0x00a4 }
            if (r6 == 0) goto L_0x0092
            r5.add(r6)     // Catch:{ all -> 0x00a4 }
            goto L_0x0088
        L_0x0092:
            r1.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x009a
        L_0x0096:
            r6 = move-exception
            r6.printStackTrace()
        L_0x009a:
            r2.close()     // Catch:{ IOException -> 0x009e }
        L_0x009d:
            goto L_0x00a3
        L_0x009e:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x009d
        L_0x00a3:
            return r5
        L_0x00a4:
            r5 = move-exception
            goto L_0x00ac
        L_0x00a6:
            r5 = move-exception
            r2 = r0
            goto L_0x00ac
        L_0x00a9:
            r5 = move-exception
            r1 = r0
            r2 = r1
        L_0x00ac:
            boolean r6 = com.tencent.bugly.proguard.x.a(r5)     // Catch:{ all -> 0x00ca }
            if (r6 != 0) goto L_0x00b5
            r5.printStackTrace()     // Catch:{ all -> 0x00ca }
        L_0x00b5:
            if (r1 == 0) goto L_0x00bf
            r1.close()     // Catch:{ IOException -> 0x00bb }
            goto L_0x00bf
        L_0x00bb:
            r5 = move-exception
            r5.printStackTrace()
        L_0x00bf:
            if (r2 == 0) goto L_0x00c9
            r2.close()     // Catch:{ IOException -> 0x00c5 }
            goto L_0x00c9
        L_0x00c5:
            r5 = move-exception
            r5.printStackTrace()
        L_0x00c9:
            return r0
        L_0x00ca:
            r5 = move-exception
            if (r1 == 0) goto L_0x00d5
            r1.close()     // Catch:{ IOException -> 0x00d1 }
            goto L_0x00d5
        L_0x00d1:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00d5:
            if (r2 == 0) goto L_0x00df
            r2.close()     // Catch:{ IOException -> 0x00db }
            goto L_0x00df
        L_0x00db:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00df:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.c(android.content.Context, java.lang.String):java.util.ArrayList");
    }

    public static String a(Context context, String str) {
        Class<z> cls = z.class;
        if (str == null || str.trim().equals("")) {
            return "";
        }
        if (a == null) {
            a = new HashMap();
            ArrayList<String> c = c(context, "getprop");
            if (c != null && c.size() > 0) {
                x.b(cls, "Successfully get 'getprop' list.", new Object[0]);
                Pattern compile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
                for (String matcher : c) {
                    Matcher matcher2 = compile.matcher(matcher);
                    if (matcher2.find()) {
                        a.put(matcher2.group(1), matcher2.group(2));
                    }
                }
                x.b(cls, "System properties number: %d.", Integer.valueOf(a.size()));
            }
        }
        if (a.containsKey(str)) {
            return a.get(str);
        }
        return "fail";
    }

    public static void b(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(String str) {
        if (str == null || str.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    public static void b(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.isFile() && file.exists() && file.canWrite()) {
                file.delete();
            }
        }
    }

    public static byte[] c(long j) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(j);
            return sb.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long c(byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        try {
            return Long.parseLong(new String(bArr, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r0 = r1.getApplicationContext();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Context a(android.content.Context r1) {
        /*
            if (r1 != 0) goto L_0x0003
            return r1
        L_0x0003:
            android.content.Context r0 = r1.getApplicationContext()
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(android.content.Context):android.content.Context");
    }

    public static String b(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void a(Class<?> cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set((Object) null, obj);
        } catch (Exception e) {
        }
    }

    public static Object a(String str, String str2, Object obj, Class<?>[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke((Object) null, objArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void a(Parcel parcel, Map<String, PlugInBean> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle((Bundle) null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Map.Entry next : map.entrySet()) {
            arrayList.add(next.getKey());
            arrayList2.add(next.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putInt("pluginNum", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            bundle.putString("pluginKey" + i, (String) arrayList.get(i));
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bundle.putString("pluginVal" + i2 + "plugInId", ((PlugInBean) arrayList2.get(i2)).a);
            bundle.putString("pluginVal" + i2 + "plugInUUID", ((PlugInBean) arrayList2.get(i2)).c);
            bundle.putString("pluginVal" + i2 + "plugInVersion", ((PlugInBean) arrayList2.get(i2)).b);
        }
        parcel.writeBundle(bundle);
    }

    public static Map<String, PlugInBean> a(Parcel parcel) {
        Bundle readBundle = parcel.readBundle();
        HashMap hashMap = null;
        if (readBundle == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int intValue = ((Integer) readBundle.get("pluginNum")).intValue();
        for (int i = 0; i < intValue; i++) {
            arrayList.add(readBundle.getString("pluginKey" + i));
        }
        for (int i2 = 0; i2 < intValue; i2++) {
            arrayList2.add(new PlugInBean(readBundle.getString("pluginVal" + i2 + "plugInId"), readBundle.getString("pluginVal" + i2 + "plugInVersion"), readBundle.getString("pluginVal" + i2 + "plugInUUID")));
        }
        if (arrayList.size() == arrayList2.size()) {
            hashMap = new HashMap(arrayList.size());
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                hashMap.put(arrayList.get(i3), PlugInBean.class.cast(arrayList2.get(i3)));
            }
        } else {
            x.e("map plugin parcel error!", new Object[0]);
        }
        return hashMap;
    }

    public static void b(Parcel parcel, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle((Bundle) null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Map.Entry next : map.entrySet()) {
            arrayList.add(next.getKey());
            arrayList2.add(next.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", arrayList);
        bundle.putStringArrayList("values", arrayList2);
        parcel.writeBundle(bundle);
    }

    public static Map<String, String> b(Parcel parcel) {
        Bundle readBundle = parcel.readBundle();
        HashMap hashMap = null;
        if (readBundle == null) {
            return null;
        }
        ArrayList<String> stringArrayList = readBundle.getStringArrayList("keys");
        ArrayList<String> stringArrayList2 = readBundle.getStringArrayList("values");
        if (stringArrayList == null || stringArrayList2 == null || stringArrayList.size() != stringArrayList2.size()) {
            x.e("map parcel error!", new Object[0]);
        } else {
            hashMap = new HashMap(stringArrayList.size());
            for (int i = 0; i < stringArrayList.size(); i++) {
                hashMap.put(stringArrayList.get(i), stringArrayList2.get(i));
            }
        }
        return hashMap;
    }

    public static byte[] a(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    /* JADX INFO: finally extract failed */
    public static <T> T a(byte[] bArr, Parcelable.Creator<T> creator) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        try {
            T createFromParcel = creator.createFromParcel(obtain);
            obtain.recycle();
            return createFromParcel;
        } catch (Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.Process, java.lang.String] */
    public static String a(Context context, int i, String str) {
        String[] strArr;
        ? r0 = 0;
        if (!AppInfo.a(context, "android.permission.READ_LOGS")) {
            x.d("no read_log permission!", new Object[0]);
            return r0;
        }
        if (str == null) {
            strArr = new String[]{"logcat", "-d", "-v", "threadtime"};
        } else {
            strArr = new String[]{"logcat", "-d", "-v", "threadtime", "-s", str};
        }
        StringBuilder sb = new StringBuilder();
        try {
            Process exec = Runtime.getRuntime().exec(strArr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append("\n");
                if (i > 0 && sb.length() > i) {
                    sb.delete(0, sb.length() - i);
                }
            }
            String sb2 = sb.toString();
            try {
                exec.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                exec.getInputStream().close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                exec.getErrorStream().close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return sb2;
        } catch (Throwable th) {
            if (r0 != 0) {
                try {
                    r0.getOutputStream().close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                try {
                    r0.getInputStream().close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                try {
                    r0.getErrorStream().close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static Map<String, String> a(int i, boolean z) {
        HashMap hashMap = new HashMap(12);
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces == null) {
            return null;
        }
        Thread thread = Looper.getMainLooper().getThread();
        if (!allStackTraces.containsKey(thread)) {
            allStackTraces.put(thread, thread.getStackTrace());
        }
        Thread.currentThread().getId();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : allStackTraces.entrySet()) {
            int i2 = 0;
            sb.setLength(0);
            if (!(next.getValue() == null || ((StackTraceElement[]) next.getValue()).length == 0)) {
                StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) next.getValue();
                int length = stackTraceElementArr.length;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTraceElementArr[i2];
                    if (i > 0 && sb.length() >= i) {
                        sb.append("\n[Stack over limit size :" + i + " , has been cut!]");
                        break;
                    }
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                    i2++;
                }
                hashMap.put(((Thread) next.getKey()).getName() + "(" + ((Thread) next.getKey()).getId() + ")", sb.toString());
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0057 A[SYNTHETIC, Splitter:B:27:0x0057] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] a(int r6) {
        /*
            java.lang.Class<com.tencent.bugly.proguard.z> r6 = com.tencent.bugly.proguard.z.class
            monitor-enter(r6)
            r0 = 16
            r1 = 0
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            java.lang.String r5 = "/dev/urandom"
            r4.<init>(r5)     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0028, all -> 0x0025 }
            r2.readFully(r0)     // Catch:{ Exception -> 0x0023 }
            r2.close()     // Catch:{ Exception -> 0x005d }
            monitor-exit(r6)
            return r0
        L_0x0023:
            r0 = move-exception
            goto L_0x002a
        L_0x0025:
            r0 = move-exception
            r2 = r1
            goto L_0x0055
        L_0x0028:
            r0 = move-exception
            r2 = r1
        L_0x002a:
            java.lang.String r3 = "Failed to read from /dev/urandom : %s"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0054 }
            r5 = 0
            r4[r5] = r0     // Catch:{ all -> 0x0054 }
            com.tencent.bugly.proguard.x.e(r3, r4)     // Catch:{ all -> 0x0054 }
            if (r2 == 0) goto L_0x003a
            r2.close()     // Catch:{ Exception -> 0x005d }
        L_0x003a:
            java.lang.String r0 = "AES"
            javax.crypto.KeyGenerator r0 = javax.crypto.KeyGenerator.getInstance(r0)     // Catch:{ Exception -> 0x005d }
            r2 = 128(0x80, float:1.794E-43)
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x005d }
            r3.<init>()     // Catch:{ Exception -> 0x005d }
            r0.init(r2, r3)     // Catch:{ Exception -> 0x005d }
            javax.crypto.SecretKey r0 = r0.generateKey()     // Catch:{ Exception -> 0x005d }
            byte[] r0 = r0.getEncoded()     // Catch:{ Exception -> 0x005d }
            monitor-exit(r6)
            return r0
        L_0x0054:
            r0 = move-exception
        L_0x0055:
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ Exception -> 0x005d }
        L_0x005a:
            throw r0     // Catch:{ Exception -> 0x005d }
        L_0x005b:
            r0 = move-exception
            goto L_0x0069
        L_0x005d:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.b(r0)     // Catch:{ all -> 0x005b }
            if (r2 != 0) goto L_0x0067
            r0.printStackTrace()     // Catch:{ all -> 0x005b }
        L_0x0067:
            monitor-exit(r6)
            return r1
        L_0x0069:
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(int):byte[]");
    }

    @TargetApi(19)
    public static byte[] a(int i, byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/GCM/NoPadding");
            if (Build.VERSION.SDK_INT >= 21) {
                if (!b) {
                    instance.init(i, secretKeySpec, new GCMParameterSpec(instance.getBlockSize() << 3, bArr2));
                    return instance.doFinal(bArr);
                }
            }
            instance.init(i, secretKeySpec, new IvParameterSpec(bArr2));
            return instance.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException e) {
            b = true;
            throw e;
        } catch (Exception e2) {
            if (x.b(e2)) {
                return null;
            }
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] b(int i, byte[] bArr, byte[] bArr2) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePublic);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            if (x.b(e)) {
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        x.c("[Util] Try to lock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < j) {
                    return false;
                }
                x.c("[Util] Lock file (%s) is expired, unlock it.", str);
                b(context, str);
            }
            if (file.createNewFile()) {
                x.c("[Util] Successfully locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                return true;
            }
            x.c("[Util] Failed to locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return false;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        x.c("[Util] Try to unlock file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (!file.exists()) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            x.c("[Util] Successfully unlocked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return true;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0069 A[SYNTHETIC, Splitter:B:30:0x0069] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r5, int r6, boolean r7) {
        /*
            r0 = 0
            if (r5 == 0) goto L_0x007e
            boolean r1 = r5.exists()
            if (r1 == 0) goto L_0x007e
            boolean r1 = r5.canRead()
            if (r1 != 0) goto L_0x0011
            goto L_0x007e
        L_0x0011:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0062 }
            r1.<init>()     // Catch:{ all -> 0x0062 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x0062 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ all -> 0x0062 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ all -> 0x0062 }
            r4.<init>(r5)     // Catch:{ all -> 0x0062 }
            java.lang.String r5 = "utf-8"
            r3.<init>(r4, r5)     // Catch:{ all -> 0x0062 }
            r2.<init>(r3)     // Catch:{ all -> 0x0062 }
        L_0x0029:
            java.lang.String r5 = r2.readLine()     // Catch:{ all -> 0x0060 }
            if (r5 == 0) goto L_0x0053
            r1.append(r5)     // Catch:{ all -> 0x0060 }
            java.lang.String r5 = "\n"
            r1.append(r5)     // Catch:{ all -> 0x0060 }
            if (r6 <= 0) goto L_0x0029
            int r5 = r1.length()     // Catch:{ all -> 0x0060 }
            if (r5 <= r6) goto L_0x0029
            if (r7 == 0) goto L_0x0049
            int r5 = r1.length()     // Catch:{ all -> 0x0060 }
            r1.delete(r6, r5)     // Catch:{ all -> 0x0060 }
            goto L_0x0053
        L_0x0049:
            r5 = 0
            int r3 = r1.length()     // Catch:{ all -> 0x0060 }
            int r3 = r3 - r6
            r1.delete(r5, r3)     // Catch:{ all -> 0x0060 }
            goto L_0x0029
        L_0x0053:
            java.lang.String r5 = r1.toString()     // Catch:{ all -> 0x0060 }
            r2.close()     // Catch:{ Exception -> 0x005b }
            goto L_0x005f
        L_0x005b:
            r6 = move-exception
            com.tencent.bugly.proguard.x.a(r6)
        L_0x005f:
            return r5
        L_0x0060:
            r5 = move-exception
            goto L_0x0064
        L_0x0062:
            r5 = move-exception
            r2 = r0
        L_0x0064:
            com.tencent.bugly.proguard.x.a(r5)     // Catch:{ all -> 0x0072 }
            if (r2 == 0) goto L_0x0071
            r2.close()     // Catch:{ Exception -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r5 = move-exception
            com.tencent.bugly.proguard.x.a(r5)
        L_0x0071:
            return r0
        L_0x0072:
            r5 = move-exception
            if (r2 == 0) goto L_0x007d
            r2.close()     // Catch:{ Exception -> 0x0079 }
            goto L_0x007d
        L_0x0079:
            r6 = move-exception
            com.tencent.bugly.proguard.x.a(r6)
        L_0x007d:
            throw r5
        L_0x007e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.z.a(java.io.File, int, boolean):java.lang.String");
    }

    private static BufferedReader a(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (Throwable th) {
            x.a(th);
            return null;
        }
    }

    public static BufferedReader a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str, str2);
            if (file.exists()) {
                if (file.canRead()) {
                    return a(file);
                }
            }
            return null;
        } catch (NullPointerException e) {
            x.a(e);
            return null;
        }
    }

    public static Thread a(Runnable runnable, String str) {
        try {
            Thread thread = new Thread(runnable);
            thread.setName(str);
            thread.start();
            return thread;
        } catch (Throwable th) {
            x.e("[Util] Failed to start a thread to execute task with message: %s", th.getMessage());
            return null;
        }
    }

    public static boolean a(Runnable runnable) {
        if (runnable == null) {
            return false;
        }
        w a2 = w.a();
        if (a2 != null) {
            return a2.a(runnable);
        }
        String[] split = runnable.getClass().getName().split("\\.");
        if (a(runnable, split[split.length - 1]) != null) {
            return true;
        }
        return false;
    }

    public static boolean c(String str) {
        if (str == null || str.trim().length() <= 0) {
            return false;
        }
        if (str.length() > 255) {
            x.a("URL(%s)'s length is larger than 255.", str);
            return false;
        } else if (str.toLowerCase().startsWith(l.DEFAULT_SCHEME_NAME)) {
            return true;
        } else {
            x.a("URL(%s) is not start with \"http\".", str);
            return false;
        }
    }

    public static SharedPreferences a(String str, Context context) {
        if (context != null) {
            return context.getSharedPreferences(str, 0);
        }
        return null;
    }

    public static String b(String str, String str2) {
        if (a.b() == null || a.b().E == null) {
            return "";
        }
        return a.b().E.getString(str, str2);
    }
}
