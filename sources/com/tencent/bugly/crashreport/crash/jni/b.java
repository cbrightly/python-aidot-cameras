package com.tencent.bugly.crashreport.crash.jni;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class b {
    private static List<File> a = new ArrayList();

    private static Map<String, Integer> d(String str) {
        if (str == null) {
            return null;
        }
        try {
            HashMap hashMap = new HashMap();
            for (String str2 : str.split(",")) {
                String[] split = str2.split(":");
                if (split.length != 2) {
                    x.e("error format at %s", str2);
                    return null;
                }
                hashMap.put(split[0], Integer.valueOf(Integer.parseInt(split[1])));
            }
            return hashMap;
        } catch (Exception e) {
            x.e("error format intStateStr %s", str);
            e.printStackTrace();
            return null;
        }
    }

    protected static String a(String str) {
        if (str == null) {
            return "";
        }
        String[] split = str.split("\n");
        if (split == null || split.length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : split) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                sb.append(str2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x011c A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0125 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0132 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x013e A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0155 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0161 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x016c A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0183 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x018f A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0190 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x019b A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01b2 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01be A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01eb A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0235 A[Catch:{ NumberFormatException -> 0x02ab, all -> 0x02bc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.tencent.bugly.crashreport.crash.CrashDetailBean a(android.content.Context r24, java.util.Map<java.lang.String, java.lang.String> r25, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler r26) {
        /*
            r0 = r25
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            com.tencent.bugly.crashreport.common.info.a r2 = com.tencent.bugly.crashreport.common.info.a.a((android.content.Context) r24)
            r3 = 0
            if (r2 != 0) goto L_0x0015
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.String r2 = "abnormal com info not created"
            com.tencent.bugly.proguard.x.e(r2, r0)
            return r1
        L_0x0015:
            java.lang.String r2 = "intStateStr"
            java.lang.Object r2 = r0.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 == 0) goto L_0x02ca
            java.lang.String r4 = r2.trim()
            int r4 = r4.length()
            if (r4 > 0) goto L_0x002d
            r2 = r1
            r1 = r3
            goto L_0x02cc
        L_0x002d:
            java.util.Map r2 = d(r2)
            r4 = 1
            if (r2 != 0) goto L_0x0047
            java.lang.Object[] r2 = new java.lang.Object[r4]
            int r0 = r25.size()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2[r3] = r0
            java.lang.String r0 = "parse intSateMap fail"
            com.tencent.bugly.proguard.x.e(r0, r2)
            return r1
        L_0x0047:
            java.lang.String r5 = "sino"
            java.lang.Object r5 = r2.get(r5)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch:{ all -> 0x02bc }
            r5.intValue()     // Catch:{ all -> 0x02bc }
            java.lang.String r5 = "sud"
            java.lang.Object r5 = r2.get(r5)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch:{ all -> 0x02bc }
            r5.intValue()     // Catch:{ all -> 0x02bc }
            java.lang.String r5 = "soVersion"
            java.lang.Object r5 = r0.get(r5)     // Catch:{ all -> 0x02bc }
            r19 = r5
            java.lang.String r19 = (java.lang.String) r19     // Catch:{ all -> 0x02bc }
            if (r19 != 0) goto L_0x0074
            java.lang.String r0 = "error format at version"
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ all -> 0x02bc }
            com.tencent.bugly.proguard.x.e(r0, r2)     // Catch:{ all -> 0x02bc }
            return r1
        L_0x0074:
            java.lang.String r5 = "errorAddr"
            java.lang.Object r5 = r0.get(r5)     // Catch:{ all -> 0x02bc }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x02bc }
            java.lang.String r6 = "unknown"
            if (r5 != 0) goto L_0x0083
            r12 = r6
            goto L_0x0084
        L_0x0083:
            r12 = r5
        L_0x0084:
            java.lang.String r5 = "codeMsg"
            java.lang.Object r5 = r0.get(r5)     // Catch:{ all -> 0x02bc }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x02bc }
            if (r5 != 0) goto L_0x008f
            r5 = r6
        L_0x008f:
            java.lang.String r7 = "tombPath"
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x02bc }
            if (r7 != 0) goto L_0x009d
            r16 = r6
            goto L_0x009f
        L_0x009d:
            r16 = r7
        L_0x009f:
            java.lang.String r7 = "signalName"
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x02bc }
            if (r7 != 0) goto L_0x00ab
            r7 = r6
        L_0x00ab:
            java.lang.String r8 = "errnoMsg"
            r0.get(r8)     // Catch:{ all -> 0x02bc }
            java.lang.String r8 = "stack"
            java.lang.Object r8 = r0.get(r8)     // Catch:{ all -> 0x02bc }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x02bc }
            if (r8 != 0) goto L_0x00bc
            r8 = r6
        L_0x00bc:
            java.lang.String r9 = "jstack"
            java.lang.Object r9 = r0.get(r9)     // Catch:{ all -> 0x02bc }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x02bc }
            if (r9 == 0) goto L_0x00da
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x02bc }
            r10.<init>()     // Catch:{ all -> 0x02bc }
            r10.append(r8)     // Catch:{ all -> 0x02bc }
            java.lang.String r8 = "java:\n"
            r10.append(r8)     // Catch:{ all -> 0x02bc }
            r10.append(r9)     // Catch:{ all -> 0x02bc }
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x02bc }
        L_0x00da:
            java.lang.String r9 = "sico"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ all -> 0x02bc }
            java.lang.String r10 = ")"
            java.lang.String r11 = "("
            if (r9 == 0) goto L_0x0109
            int r9 = r9.intValue()     // Catch:{ all -> 0x02bc }
            if (r9 <= 0) goto L_0x0109
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x02bc }
            r9.<init>()     // Catch:{ all -> 0x02bc }
            r9.append(r7)     // Catch:{ all -> 0x02bc }
            r9.append(r11)     // Catch:{ all -> 0x02bc }
            r9.append(r5)     // Catch:{ all -> 0x02bc }
            r9.append(r10)     // Catch:{ all -> 0x02bc }
            java.lang.String r5 = r9.toString()     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = "KERNEL"
            r14 = r7
            goto L_0x010b
        L_0x0109:
            r14 = r5
            r5 = r7
        L_0x010b:
            java.lang.String r7 = "nativeLog"
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x02bc }
            if (r7 == 0) goto L_0x0125
            boolean r9 = r7.isEmpty()     // Catch:{ all -> 0x02bc }
            if (r9 != 0) goto L_0x0125
            java.lang.String r9 = "BuglyNativeLog.txt"
            byte[] r7 = com.tencent.bugly.proguard.z.a((java.io.File) r1, (java.lang.String) r7, (java.lang.String) r9)     // Catch:{ all -> 0x02bc }
            r20 = r7
            goto L_0x0127
        L_0x0125:
            r20 = r1
        L_0x0127:
            java.lang.String r7 = "sendingProcess"
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x02bc }
            if (r7 != 0) goto L_0x0133
            r7 = r6
        L_0x0133:
            java.lang.String r9 = "spd"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ all -> 0x02bc }
            if (r9 == 0) goto L_0x0155
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x02bc }
            r13.<init>()     // Catch:{ all -> 0x02bc }
            r13.append(r7)     // Catch:{ all -> 0x02bc }
            r13.append(r11)     // Catch:{ all -> 0x02bc }
            r13.append(r9)     // Catch:{ all -> 0x02bc }
            r13.append(r10)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = r13.toString()     // Catch:{ all -> 0x02bc }
            r15 = r7
            goto L_0x0156
        L_0x0155:
            r15 = r7
        L_0x0156:
            java.lang.String r7 = "threadName"
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x02bc }
            if (r7 != 0) goto L_0x0162
            r7 = r6
        L_0x0162:
            java.lang.String r9 = "et"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ all -> 0x02bc }
            if (r9 == 0) goto L_0x0183
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x02bc }
            r13.<init>()     // Catch:{ all -> 0x02bc }
            r13.append(r7)     // Catch:{ all -> 0x02bc }
            r13.append(r11)     // Catch:{ all -> 0x02bc }
            r13.append(r9)     // Catch:{ all -> 0x02bc }
            r13.append(r10)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = r13.toString()     // Catch:{ all -> 0x02bc }
            r9 = r7
            goto L_0x0184
        L_0x0183:
            r9 = r7
        L_0x0184:
            java.lang.String r7 = "processName"
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x02bc }
            if (r7 != 0) goto L_0x0190
            goto L_0x0191
        L_0x0190:
            r6 = r7
        L_0x0191:
            java.lang.String r7 = "ep"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ all -> 0x02bc }
            if (r7 == 0) goto L_0x01b2
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x02bc }
            r13.<init>()     // Catch:{ all -> 0x02bc }
            r13.append(r6)     // Catch:{ all -> 0x02bc }
            r13.append(r11)     // Catch:{ all -> 0x02bc }
            r13.append(r7)     // Catch:{ all -> 0x02bc }
            r13.append(r10)     // Catch:{ all -> 0x02bc }
            java.lang.String r6 = r13.toString()     // Catch:{ all -> 0x02bc }
            r7 = r6
            goto L_0x01b3
        L_0x01b2:
            r7 = r6
        L_0x01b3:
            java.lang.String r6 = "key-value"
            java.lang.Object r6 = r0.get(r6)     // Catch:{ all -> 0x02bc }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x02bc }
            if (r6 == 0) goto L_0x01eb
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ all -> 0x02bc }
            r10.<init>()     // Catch:{ all -> 0x02bc }
            java.lang.String r11 = "\n"
            java.lang.String[] r6 = r6.split(r11)     // Catch:{ all -> 0x02bc }
            int r11 = r6.length     // Catch:{ all -> 0x02bc }
            r13 = r3
        L_0x01cb:
            if (r13 >= r11) goto L_0x01e8
            r1 = r6[r13]     // Catch:{ all -> 0x02bc }
            java.lang.String r4 = "="
            java.lang.String[] r1 = r1.split(r4)     // Catch:{ all -> 0x02bc }
            int r4 = r1.length     // Catch:{ all -> 0x02bc }
            r3 = 2
            if (r4 != r3) goto L_0x01e2
            r3 = 0
            r4 = r1[r3]     // Catch:{ all -> 0x02bc }
            r3 = 1
            r1 = r1[r3]     // Catch:{ all -> 0x02bc }
            r10.put(r4, r1)     // Catch:{ all -> 0x02bc }
        L_0x01e2:
            int r13 = r13 + 1
            r1 = 0
            r3 = 0
            r4 = 1
            goto L_0x01cb
        L_0x01e8:
            r21 = r10
            goto L_0x01ed
        L_0x01eb:
            r21 = 0
        L_0x01ed:
            java.lang.String r1 = "ets"
            java.lang.Object r1 = r2.get(r1)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ all -> 0x02bc }
            int r1 = r1.intValue()     // Catch:{ all -> 0x02bc }
            long r3 = (long) r1     // Catch:{ all -> 0x02bc }
            java.lang.String r1 = "etms"
            java.lang.Object r1 = r2.get(r1)     // Catch:{ all -> 0x02bc }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ all -> 0x02bc }
            int r1 = r1.intValue()     // Catch:{ all -> 0x02bc }
            long r1 = (long) r1     // Catch:{ all -> 0x02bc }
            r10 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 * r10
            long r1 = r1 / r10
            long r1 = r1 + r3
            java.lang.String r13 = a((java.lang.String) r8)     // Catch:{ all -> 0x02bc }
            java.lang.String r3 = "sysLogPath"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x02bc }
            r17 = r3
            java.lang.String r17 = (java.lang.String) r17     // Catch:{ all -> 0x02bc }
            java.lang.String r3 = "jniLogPath"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x02bc }
            r18 = r3
            java.lang.String r18 = (java.lang.String) r18     // Catch:{ all -> 0x02bc }
            r22 = 0
            r23 = 0
            r6 = r26
            r8 = r9
            r9 = r1
            r11 = r5
            com.tencent.bugly.crashreport.crash.CrashDetailBean r1 = r6.packageCrashDatas(r7, r8, r9, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)     // Catch:{ all -> 0x02bc }
            if (r1 == 0) goto L_0x02bb
            java.lang.String r2 = "userId"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x02bc }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02bc }
            if (r2 == 0) goto L_0x024d
            java.lang.String r3 = "[Native record info] userId: %s"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x02bc }
            r4 = 0
            r5[r4] = r2     // Catch:{ all -> 0x02bc }
            com.tencent.bugly.proguard.x.c(r3, r5)     // Catch:{ all -> 0x02bc }
            r1.m = r2     // Catch:{ all -> 0x02bc }
        L_0x024d:
            java.lang.String r2 = "sysLog"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x02bc }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02bc }
            if (r2 == 0) goto L_0x025a
            r1.w = r2     // Catch:{ all -> 0x02bc }
        L_0x025a:
            java.lang.String r2 = "appVersion"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x02bc }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02bc }
            if (r2 == 0) goto L_0x0271
            java.lang.String r3 = "[Native record info] appVersion: %s"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x02bc }
            r4 = 0
            r5[r4] = r2     // Catch:{ all -> 0x02bc }
            com.tencent.bugly.proguard.x.c(r3, r5)     // Catch:{ all -> 0x02bc }
            r1.f = r2     // Catch:{ all -> 0x02bc }
        L_0x0271:
            java.lang.String r2 = "isAppForeground"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x02bc }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02bc }
            if (r2 == 0) goto L_0x028f
            java.lang.String r3 = "[Native record info] isAppForeground: %s"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x02bc }
            r4 = 0
            r5[r4] = r2     // Catch:{ all -> 0x02bc }
            com.tencent.bugly.proguard.x.c(r3, r5)     // Catch:{ all -> 0x02bc }
            java.lang.String r3 = "true"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ all -> 0x02bc }
            r1.N = r2     // Catch:{ all -> 0x02bc }
        L_0x028f:
            java.lang.String r2 = "launchTime"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x02bc }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x02bc }
            if (r0 == 0) goto L_0x02b5
            java.lang.String r2 = "[Native record info] launchTime: %s"
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x02bc }
            r3 = 0
            r4[r3] = r0     // Catch:{ all -> 0x02bc }
            com.tencent.bugly.proguard.x.c(r2, r4)     // Catch:{ all -> 0x02bc }
            long r2 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x02ab }
            r1.M = r2     // Catch:{ NumberFormatException -> 0x02ab }
            goto L_0x02b5
        L_0x02ab:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x02bc }
            if (r2 != 0) goto L_0x02b5
            r0.printStackTrace()     // Catch:{ all -> 0x02bc }
        L_0x02b5:
            r2 = 0
            r1.z = r2     // Catch:{ all -> 0x02bc }
            r2 = 1
            r1.k = r2     // Catch:{ all -> 0x02bc }
        L_0x02bb:
            return r1
        L_0x02bc:
            r0 = move-exception
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "error format"
            com.tencent.bugly.proguard.x.e(r2, r1)
            r0.printStackTrace()
            r2 = 0
            return r2
        L_0x02ca:
            r2 = r1
            r1 = r3
        L_0x02cc:
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r1 = "no intStateStr"
            com.tencent.bugly.proguard.x.e(r1, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.b.a(android.content.Context, java.util.Map, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    private static String a(BufferedInputStream bufferedInputStream) {
        ByteArrayOutputStream byteArrayOutputStream;
        if (bufferedInputStream == null) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            while (true) {
                try {
                    int read = bufferedInputStream.read();
                    if (read == -1) {
                        byteArrayOutputStream.close();
                        break;
                    } else if (read == 0) {
                        String str = new String(byteArrayOutputStream.toByteArray(), "UTf-8");
                        byteArrayOutputStream.close();
                        return str;
                    } else {
                        byteArrayOutputStream.write(read);
                    }
                } catch (Throwable th) {
                    th = th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
            try {
                x.a(th);
                return null;
            } finally {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x0091 A[SYNTHETIC, Splitter:B:51:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x009e A[SYNTHETIC, Splitter:B:59:0x009e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.bugly.crashreport.crash.CrashDetailBean a(android.content.Context r6, java.lang.String r7, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler r8) {
        /*
            r0 = 0
            r1 = 0
            if (r6 == 0) goto L_0x00a8
            if (r7 == 0) goto L_0x00a8
            if (r8 != 0) goto L_0x000a
            goto L_0x00a8
        L_0x000a:
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "rqd_record.eup"
            r2.<init>(r7, r3)
            boolean r7 = r2.exists()
            if (r7 == 0) goto L_0x00a7
            boolean r7 = r2.canRead()
            if (r7 != 0) goto L_0x0020
            goto L_0x00a7
        L_0x0020:
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x008a, all -> 0x0088 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x008a, all -> 0x0088 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x008a, all -> 0x0088 }
            r7.<init>(r3)     // Catch:{ IOException -> 0x008a, all -> 0x0088 }
            java.lang.String r2 = a((java.io.BufferedInputStream) r7)     // Catch:{ IOException -> 0x0086 }
            r3 = 1
            if (r2 == 0) goto L_0x0073
            java.lang.String r4 = "NATIVE_RQD_REPORT"
            boolean r4 = r2.equals(r4)     // Catch:{ IOException -> 0x0086 }
            if (r4 != 0) goto L_0x003b
            goto L_0x0073
        L_0x003b:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ IOException -> 0x0086 }
            r2.<init>()     // Catch:{ IOException -> 0x0086 }
            r4 = r1
        L_0x0042:
            java.lang.String r5 = a((java.io.BufferedInputStream) r7)     // Catch:{ IOException -> 0x0086 }
            if (r5 == 0) goto L_0x0051
            if (r4 != 0) goto L_0x004c
            r4 = r5
            goto L_0x0042
        L_0x004c:
            r2.put(r4, r5)     // Catch:{ IOException -> 0x0086 }
            r4 = r1
            goto L_0x0042
        L_0x0051:
            if (r4 == 0) goto L_0x0066
            java.lang.String r6 = "record not pair! drop! %s"
            java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x0086 }
            r8[r0] = r4     // Catch:{ IOException -> 0x0086 }
            com.tencent.bugly.proguard.x.e(r6, r8)     // Catch:{ IOException -> 0x0086 }
            r7.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0065:
            return r1
        L_0x0066:
            com.tencent.bugly.crashreport.crash.CrashDetailBean r6 = a((android.content.Context) r6, (java.util.Map<java.lang.String, java.lang.String>) r2, (com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler) r8)     // Catch:{ IOException -> 0x0086 }
            r7.close()     // Catch:{ IOException -> 0x006e }
            goto L_0x0072
        L_0x006e:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0072:
            return r6
        L_0x0073:
            java.lang.String r6 = "record read fail! %s"
            java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x0086 }
            r8[r0] = r2     // Catch:{ IOException -> 0x0086 }
            com.tencent.bugly.proguard.x.e(r6, r8)     // Catch:{ IOException -> 0x0086 }
            r7.close()     // Catch:{ IOException -> 0x0081 }
            goto L_0x0085
        L_0x0081:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0085:
            return r1
        L_0x0086:
            r6 = move-exception
            goto L_0x008c
        L_0x0088:
            r6 = move-exception
            goto L_0x009c
        L_0x008a:
            r6 = move-exception
            r7 = r1
        L_0x008c:
            r6.printStackTrace()     // Catch:{ all -> 0x009a }
            if (r7 == 0) goto L_0x0099
            r7.close()     // Catch:{ IOException -> 0x0095 }
            goto L_0x0099
        L_0x0095:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0099:
            return r1
        L_0x009a:
            r6 = move-exception
            r1 = r7
        L_0x009c:
            if (r1 == 0) goto L_0x00a6
            r1.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x00a6
        L_0x00a2:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00a6:
            throw r6
        L_0x00a7:
            return r1
        L_0x00a8:
            java.lang.Object[] r6 = new java.lang.Object[r0]
            java.lang.String r7 = "get eup record file args error"
            com.tencent.bugly.proguard.x.e(r7, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.b.a(android.content.Context, java.lang.String, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    private static String b(String str, String str2) {
        BufferedReader a2 = z.a(str, "reg_record.txt");
        if (a2 == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String readLine = a2.readLine();
            if (readLine != null) {
                if (readLine.startsWith(str2)) {
                    int i = 18;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        String readLine2 = a2.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        if (i2 % 4 == 0) {
                            if (i2 > 0) {
                                sb.append("\n");
                            }
                            sb.append(JustifyTextView.TWO_CHINESE_BLANK);
                        } else {
                            if (readLine2.length() > 16) {
                                i = 28;
                            }
                            sb.append("                ".substring(0, i - i3));
                        }
                        i3 = readLine2.length();
                        sb.append(readLine2);
                        i2++;
                    }
                    sb.append("\n");
                    String sb2 = sb.toString();
                    try {
                        a2.close();
                    } catch (Exception e) {
                        x.a(e);
                    }
                    return sb2;
                }
            }
            try {
                a2.close();
            } catch (Exception e2) {
                x.a(e2);
            }
            return null;
        } catch (Throwable th) {
            try {
                a2.close();
            } catch (Exception e3) {
                x.a(e3);
            }
            throw th;
        }
    }

    private static String c(String str, String str2) {
        BufferedReader a2 = z.a(str, "map_record.txt");
        if (a2 == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String readLine = a2.readLine();
            if (readLine != null) {
                if (readLine.startsWith(str2)) {
                    while (true) {
                        String readLine2 = a2.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        sb.append(JustifyTextView.TWO_CHINESE_BLANK);
                        sb.append(readLine2);
                        sb.append("\n");
                    }
                    String sb2 = sb.toString();
                    try {
                        a2.close();
                    } catch (Exception e) {
                        x.a(e);
                    }
                    return sb2;
                }
            }
            try {
                a2.close();
            } catch (Exception e2) {
                x.a(e2);
            }
            return null;
        } catch (Throwable th) {
            try {
                a2.close();
            } catch (Exception e3) {
                x.a(e3);
            }
            throw th;
        }
    }

    public static String a(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String b = b(str, str2);
        if (b != null && !b.isEmpty()) {
            sb.append("Register infos:\n");
            sb.append(b);
        }
        String c = c(str, str2);
        if (c != null && !c.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("System SO infos:\n");
            sb.append(c);
        }
        return sb.toString();
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str, "backup_record.txt");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static void c(String str) {
        File[] listFiles;
        if (str != null) {
            try {
                File file = new File(str);
                if (file.canRead() && file.isDirectory() && (listFiles = file.listFiles()) != null) {
                    for (File file2 : listFiles) {
                        if (file2.canRead() && file2.canWrite() && file2.length() == 0) {
                            file2.delete();
                            x.c("Delete empty record file %s", file2.getAbsoluteFile());
                        }
                    }
                }
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    public static void a(boolean z, String str) {
        if (str != null) {
            a.add(new File(str, "rqd_record.eup"));
            a.add(new File(str, "reg_record.txt"));
            a.add(new File(str, "map_record.txt"));
            a.add(new File(str, "backup_record.txt"));
            if (z) {
                c(str);
            }
        }
        List<File> list = a;
        if (list != null && list.size() > 0) {
            for (File next : a) {
                if (next.exists() && next.canWrite()) {
                    next.delete();
                    x.c("Delete record file %s", next.getAbsoluteFile());
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00e1 A[SYNTHETIC, Splitter:B:37:0x00e1] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r6, int r7, java.lang.String r8, boolean r9) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x00f3
            if (r7 > 0) goto L_0x0007
            goto L_0x00f3
        L_0x0007:
            java.io.File r1 = new java.io.File
            r1.<init>(r6)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x00f2
            boolean r2 = r1.canRead()
            if (r2 != 0) goto L_0x001a
            goto L_0x00f2
        L_0x001a:
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]
            long r3 = r1.length()
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r4 = 0
            r2[r4] = r3
            r3 = 1
            java.lang.String r5 = r1.getAbsolutePath()
            r2[r3] = r5
            java.lang.String r3 = "Read system log from native record file(length: %s bytes): %s"
            com.tencent.bugly.proguard.x.a(r3, r2)
            java.util.List<java.io.File> r2 = a
            r2.add(r1)
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r3 = "Add this record file to list for cleaning lastly."
            com.tencent.bugly.proguard.x.c(r3, r2)
            if (r8 != 0) goto L_0x004d
            java.io.File r8 = new java.io.File
            r8.<init>(r6)
            java.lang.String r6 = com.tencent.bugly.proguard.z.a((java.io.File) r8, (int) r7, (boolean) r9)
            goto L_0x00e5
        L_0x004d:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x00bd }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ all -> 0x00bd }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x00bd }
            r5.<init>(r1)     // Catch:{ all -> 0x00bd }
            java.lang.String r1 = "utf-8"
            r3.<init>(r5, r1)     // Catch:{ all -> 0x00bd }
            r2.<init>(r3)     // Catch:{ all -> 0x00bd }
        L_0x0065:
            java.lang.String r0 = r2.readLine()     // Catch:{ all -> 0x00ba }
            if (r0 == 0) goto L_0x00ad
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ba }
            r1.<init>()     // Catch:{ all -> 0x00ba }
            r1.append(r8)     // Catch:{ all -> 0x00ba }
            java.lang.String r3 = "[ ]*:"
            r1.append(r3)     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ba }
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r1)     // Catch:{ all -> 0x00ba }
            java.util.regex.Matcher r1 = r1.matcher(r0)     // Catch:{ all -> 0x00ba }
            boolean r1 = r1.find()     // Catch:{ all -> 0x00ba }
            if (r1 == 0) goto L_0x0092
            r6.append(r0)     // Catch:{ all -> 0x00ba }
            java.lang.String r0 = "\n"
            r6.append(r0)     // Catch:{ all -> 0x00ba }
        L_0x0092:
            if (r7 <= 0) goto L_0x00ac
            int r0 = r6.length()     // Catch:{ all -> 0x00ba }
            if (r0 <= r7) goto L_0x00ac
            if (r9 == 0) goto L_0x00a4
            int r8 = r6.length()     // Catch:{ all -> 0x00ba }
            r6.delete(r7, r8)     // Catch:{ all -> 0x00ba }
            goto L_0x00ad
        L_0x00a4:
            int r0 = r6.length()     // Catch:{ all -> 0x00ba }
            int r0 = r0 - r7
            r6.delete(r4, r0)     // Catch:{ all -> 0x00ba }
        L_0x00ac:
            goto L_0x0065
        L_0x00ad:
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00ba }
            r2.close()     // Catch:{ Exception -> 0x00b5 }
        L_0x00b4:
            goto L_0x00e5
        L_0x00b5:
            r7 = move-exception
            com.tencent.bugly.proguard.x.a(r7)
            goto L_0x00b4
        L_0x00ba:
            r7 = move-exception
            r0 = r2
            goto L_0x00be
        L_0x00bd:
            r7 = move-exception
        L_0x00be:
            com.tencent.bugly.proguard.x.a(r7)     // Catch:{ all -> 0x00e6 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e6 }
            java.lang.String r9 = "\n[error:"
            r8.<init>(r9)     // Catch:{ all -> 0x00e6 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00e6 }
            r8.append(r7)     // Catch:{ all -> 0x00e6 }
            java.lang.String r7 = "]"
            r8.append(r7)     // Catch:{ all -> 0x00e6 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x00e6 }
            r6.append(r7)     // Catch:{ all -> 0x00e6 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00e6 }
            if (r0 == 0) goto L_0x00e5
            r0.close()     // Catch:{ Exception -> 0x00b5 }
            goto L_0x00b4
        L_0x00e5:
            return r6
        L_0x00e6:
            r6 = move-exception
            if (r0 == 0) goto L_0x00f1
            r0.close()     // Catch:{ Exception -> 0x00ed }
            goto L_0x00f1
        L_0x00ed:
            r7 = move-exception
            com.tencent.bugly.proguard.x.a(r7)
        L_0x00f1:
            throw r6
        L_0x00f2:
            return r0
        L_0x00f3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.b.a(java.lang.String, int, java.lang.String, boolean):java.lang.String");
    }
}
