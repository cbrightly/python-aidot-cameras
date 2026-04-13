package com.tencent.bugly.proguard;

import android.content.Context;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: BUGLY */
public final class s {
    private static s b;
    public Map<String, String> a = null;
    private Context c;

    private s(Context context) {
        this.c = context;
    }

    public static s a(Context context) {
        if (b == null) {
            b = new s(context);
        }
        return b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:95:0x0185 A[Catch:{ all -> 0x0179, all -> 0x01a0 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] a(java.lang.String r21, byte[] r22, com.tencent.bugly.proguard.v r23, java.util.Map<java.lang.String, java.lang.String> r24) {
        /*
            r20 = this;
            r1 = r20
            r2 = r22
            r3 = r23
            r4 = 0
            r5 = 0
            if (r21 != 0) goto L_0x0012
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r2 = "Failed for no URL."
            com.tencent.bugly.proguard.x.e(r2, r0)
            return r4
        L_0x0012:
            if (r2 != 0) goto L_0x0019
            r8 = 0
            goto L_0x001b
        L_0x0019:
            int r0 = r2.length
            long r8 = (long) r0
        L_0x001b:
            r0 = 4
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r5] = r21
            java.lang.Long r10 = java.lang.Long.valueOf(r8)
            r11 = 1
            r0[r11] = r10
            int r10 = android.os.Process.myPid()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r12 = 2
            r0[r12] = r10
            r10 = 3
            int r13 = android.os.Process.myTid()
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
            r0[r10] = r13
            java.lang.String r10 = "request: %s, send: %d (pid=%d | tid=%d)"
            com.tencent.bugly.proguard.x.c(r10, r0)
            r10 = r21
            r0 = r5
            r13 = r0
            r14 = r13
        L_0x0048:
            if (r0 > 0) goto L_0x01c2
            if (r13 > 0) goto L_0x01c2
            if (r14 == 0) goto L_0x0051
            r6 = r0
            r14 = r5
            goto L_0x0081
        L_0x0051:
            int r0 = r0 + 1
            if (r0 <= r11) goto L_0x0080
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            java.lang.String r6 = "try time: "
            r15.<init>(r6)
            r15.append(r0)
            java.lang.String r6 = r15.toString()
            java.lang.Object[] r7 = new java.lang.Object[r5]
            com.tencent.bugly.proguard.x.c(r6, r7)
            java.util.Random r6 = new java.util.Random
            long r11 = java.lang.System.currentTimeMillis()
            r6.<init>(r11)
            r11 = 10000(0x2710, float:1.4013E-41)
            int r6 = r6.nextInt(r11)
            long r11 = (long) r6
            r18 = 10000(0x2710, double:4.9407E-320)
            long r11 = r11 + r18
            android.os.SystemClock.sleep(r11)
        L_0x0080:
            r6 = r0
        L_0x0081:
            android.content.Context r0 = r1.c
            java.lang.String r0 = com.tencent.bugly.crashreport.common.info.b.c(r0)
            if (r0 != 0) goto L_0x0094
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r11 = "Failed to request for network not avail"
            com.tencent.bugly.proguard.x.d(r11, r0)
            r0 = r6
            r11 = 1
            r12 = 2
            goto L_0x0048
        L_0x0094:
            r3.a((long) r8)
            r11 = r24
            java.net.HttpURLConnection r12 = r1.a((java.lang.String) r10, (byte[]) r2, (java.lang.String) r0, (java.util.Map<java.lang.String, java.lang.String>) r11)
            if (r12 == 0) goto L_0x01ac
            int r0 = r12.getResponseCode()     // Catch:{ IOException -> 0x017c }
            r7 = 200(0xc8, float:2.8E-43)
            if (r0 != r7) goto L_0x00cc
            java.util.Map r0 = a((java.net.HttpURLConnection) r12)     // Catch:{ IOException -> 0x017c }
            r1.a = r0     // Catch:{ IOException -> 0x017c }
            byte[] r7 = b(r12)     // Catch:{ IOException -> 0x017c }
            if (r7 != 0) goto L_0x00b6
            r4 = 0
            goto L_0x00b8
        L_0x00b6:
            int r0 = r7.length     // Catch:{ IOException -> 0x017c }
            long r4 = (long) r0     // Catch:{ IOException -> 0x017c }
        L_0x00b8:
            r3.b(r4)     // Catch:{ IOException -> 0x017c }
            r12.disconnect()     // Catch:{ all -> 0x00c0 }
            goto L_0x00cb
        L_0x00c0:
            r0 = move-exception
            r2 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r2)
            if (r0 != 0) goto L_0x00cb
            r2.printStackTrace()
        L_0x00cb:
            return r7
        L_0x00cc:
            r4 = 301(0x12d, float:4.22E-43)
            if (r0 == r4) goto L_0x00df
            r4 = 302(0x12e, float:4.23E-43)
            if (r0 == r4) goto L_0x00df
            r4 = 303(0x12f, float:4.25E-43)
            if (r0 == r4) goto L_0x00df
            r4 = 307(0x133, float:4.3E-43)
            if (r0 != r4) goto L_0x00dd
            goto L_0x00df
        L_0x00dd:
            r4 = 0
            goto L_0x00e0
        L_0x00df:
            r4 = 1
        L_0x00e0:
            if (r4 == 0) goto L_0x0140
            java.lang.String r4 = "Location"
            java.lang.String r4 = r12.getHeaderField(r4)     // Catch:{ IOException -> 0x013b }
            if (r4 != 0) goto L_0x0115
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0110 }
            java.lang.String r5 = "Failed to redirect: %d"
            r4.<init>(r5)     // Catch:{ IOException -> 0x0110 }
            r4.append(r0)     // Catch:{ IOException -> 0x0110 }
            java.lang.String r0 = r4.toString()     // Catch:{ IOException -> 0x0110 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ IOException -> 0x0110 }
            com.tencent.bugly.proguard.x.e(r0, r5)     // Catch:{ IOException -> 0x0110 }
            r12.disconnect()     // Catch:{ all -> 0x0103 }
            goto L_0x010e
        L_0x0103:
            r0 = move-exception
            r2 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r2)
            if (r0 != 0) goto L_0x010e
            r2.printStackTrace()
        L_0x010e:
            r2 = 0
            return r2
        L_0x0110:
            r0 = move-exception
            r7 = 2
            r14 = 1
            goto L_0x017e
        L_0x0115:
            int r13 = r13 + 1
            java.lang.String r5 = "redirect code: %d ,to:%s"
            r7 = 2
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0132 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r0)     // Catch:{ IOException -> 0x0132 }
            r14 = 0
            r6[r14] = r10     // Catch:{ IOException -> 0x0132 }
            r15 = 1
            r6[r15] = r4     // Catch:{ IOException -> 0x0130 }
            com.tencent.bugly.proguard.x.c(r5, r6)     // Catch:{ IOException -> 0x0130 }
            r10 = r4
            r14 = r15
            r6 = 0
            goto L_0x0142
        L_0x0130:
            r0 = move-exception
            goto L_0x0137
        L_0x0132:
            r0 = move-exception
            goto L_0x0136
        L_0x0134:
            r0 = move-exception
            r7 = 2
        L_0x0136:
            r15 = 1
        L_0x0137:
            r10 = r4
            r14 = r15
            r6 = 0
            goto L_0x017f
        L_0x013b:
            r0 = move-exception
            r7 = 2
            r15 = 1
            r14 = r15
            goto L_0x017f
        L_0x0140:
            r7 = 2
            r15 = 1
        L_0x0142:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0177 }
            java.lang.String r5 = "response code "
            r4.<init>(r5)     // Catch:{ IOException -> 0x0177 }
            r4.append(r0)     // Catch:{ IOException -> 0x0177 }
            java.lang.String r0 = r4.toString()     // Catch:{ IOException -> 0x0177 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ IOException -> 0x0177 }
            com.tencent.bugly.proguard.x.d(r0, r5)     // Catch:{ IOException -> 0x0177 }
            int r0 = r12.getContentLength()     // Catch:{ IOException -> 0x0177 }
            long r4 = (long) r0     // Catch:{ IOException -> 0x0177 }
            r16 = 0
            int r0 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r0 >= 0) goto L_0x0164
            r4 = 0
        L_0x0164:
            r3.b(r4)     // Catch:{ IOException -> 0x0177 }
            r12.disconnect()     // Catch:{ all -> 0x016b }
            goto L_0x018b
        L_0x016b:
            r0 = move-exception
            r4 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r4)
            if (r0 != 0) goto L_0x0197
            r4.printStackTrace()
            goto L_0x0197
        L_0x0177:
            r0 = move-exception
            goto L_0x017f
        L_0x0179:
            r0 = move-exception
            r2 = r0
            goto L_0x019c
        L_0x017c:
            r0 = move-exception
            r7 = 2
        L_0x017e:
            r15 = 1
        L_0x017f:
            boolean r4 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0179 }
            if (r4 != 0) goto L_0x0188
            r0.printStackTrace()     // Catch:{ all -> 0x0179 }
        L_0x0188:
            r12.disconnect()     // Catch:{ all -> 0x018c }
        L_0x018b:
            goto L_0x0198
        L_0x018c:
            r0 = move-exception
            r4 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r4)
            if (r0 != 0) goto L_0x0197
            r4.printStackTrace()
        L_0x0197:
        L_0x0198:
            r0 = r6
            r4 = 0
            goto L_0x01bc
        L_0x019c:
            r12.disconnect()     // Catch:{ all -> 0x01a0 }
            goto L_0x01ab
        L_0x01a0:
            r0 = move-exception
            r3 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r3)
            if (r0 != 0) goto L_0x01ab
            r3.printStackTrace()
        L_0x01ab:
            throw r2
        L_0x01ac:
            r7 = 2
            r15 = 1
            r4 = 0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            java.lang.String r5 = "Failed to execute post."
            com.tencent.bugly.proguard.x.c(r5, r0)
            r4 = 0
            r3.b(r4)
            r0 = r6
        L_0x01bc:
            r12 = r7
            r11 = r15
            r4 = 0
            r5 = 0
            goto L_0x0048
        L_0x01c2:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.s.a(java.lang.String, byte[], com.tencent.bugly.proguard.v, java.util.Map):byte[]");
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap hashMap = new HashMap();
        Map headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List list = (List) headerFields.get(str);
            if (list.size() > 0) {
                hashMap.put(str, list.get(0));
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003c A[Catch:{ all -> 0x004c, all -> 0x0053 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0041 A[SYNTHETIC, Splitter:B:24:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(java.net.HttpURLConnection r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0034 }
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ all -> 0x0034 }
            r1.<init>(r5)     // Catch:{ all -> 0x0034 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0032 }
            r5.<init>()     // Catch:{ all -> 0x0032 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x0032 }
        L_0x0017:
            int r3 = r1.read(r2)     // Catch:{ all -> 0x0032 }
            if (r3 <= 0) goto L_0x0022
            r4 = 0
            r5.write(r2, r4, r3)     // Catch:{ all -> 0x0032 }
            goto L_0x0017
        L_0x0022:
            r5.flush()     // Catch:{ all -> 0x0032 }
            byte[] r5 = r5.toByteArray()     // Catch:{ all -> 0x0032 }
            r1.close()     // Catch:{ all -> 0x002d }
            goto L_0x0031
        L_0x002d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0031:
            return r5
        L_0x0032:
            r5 = move-exception
            goto L_0x0036
        L_0x0034:
            r5 = move-exception
            r1 = r0
        L_0x0036:
            boolean r2 = com.tencent.bugly.proguard.x.a(r5)     // Catch:{ all -> 0x004c }
            if (r2 != 0) goto L_0x003f
            r5.printStackTrace()     // Catch:{ all -> 0x004c }
        L_0x003f:
            if (r1 == 0) goto L_0x004a
            r1.close()     // Catch:{ all -> 0x0045 }
            goto L_0x004a
        L_0x0045:
            r5 = move-exception
            r5.printStackTrace()
            goto L_0x004b
        L_0x004a:
        L_0x004b:
            return r0
        L_0x004c:
            r5 = move-exception
            if (r1 == 0) goto L_0x0058
            r1.close()     // Catch:{ all -> 0x0053 }
            goto L_0x0058
        L_0x0053:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0059
        L_0x0058:
        L_0x0059:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.s.b(java.net.HttpURLConnection):byte[]");
    }

    private HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            x.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection a2 = a(str2, str);
        if (a2 == null) {
            x.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            a2.setRequestProperty("wup_version", "3.0");
            if (map != null) {
                if (map.size() > 0) {
                    for (Map.Entry next : map.entrySet()) {
                        a2.setRequestProperty((String) next.getKey(), URLEncoder.encode((String) next.getValue(), "utf-8"));
                    }
                }
            }
            a2.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            a2.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = a2.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return a2;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (a.b() != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(a.b());
            } else if (str == null || !str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod(Constants.POST);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
