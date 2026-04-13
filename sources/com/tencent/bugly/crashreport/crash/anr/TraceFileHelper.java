package com.tencent.bugly.crashreport.crash.anr;

import com.tencent.bugly.proguard.x;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public class TraceFileHelper {

    /* compiled from: BUGLY */
    public static final class a {
        public long a;
        public String b;
        public long c;
        public Map<String, String[]> d;
    }

    /* compiled from: BUGLY */
    public interface b {
        boolean a(long j);

        boolean a(long j, long j2, String str);

        boolean a(String str, int i, String str2, String str3);
    }

    public static a readTargetDumpInfo(final String str, String str2, final boolean z) {
        if (str == null || str2 == null) {
            return null;
        }
        final a aVar = new a();
        readTraceFile(str2, new b() {
            public final boolean a(String str, int i, String str2, String str3) {
                x.c("new thread %s", str);
                a aVar = aVar;
                if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
                    return true;
                }
                if (aVar.d == null) {
                    aVar.d = new HashMap();
                }
                Map<String, String[]> map = aVar.d;
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                map.put(str, new String[]{str2, str3, sb.toString()});
                return true;
            }

            public final boolean a(long j, long j2, String str) {
                x.c("new process %s", str);
                if (!str.equals(str)) {
                    return true;
                }
                a aVar = aVar;
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                return z;
            }

            public final boolean a(long j) {
                x.c("process end %d", Long.valueOf(j));
                a aVar = aVar;
                return aVar.a <= 0 || aVar.c <= 0 || aVar.b == null;
            }
        });
        if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
            return null;
        }
        return aVar;
    }

    public static a readFirstDumpInfo(String str, final boolean z) {
        if (str == null) {
            x.e("path:%s", str);
            return null;
        }
        final a aVar = new a();
        readTraceFile(str, new b() {
            public final boolean a(String str, int i, String str2, String str3) {
                x.c("new thread %s", str);
                a aVar = aVar;
                if (aVar.d == null) {
                    aVar.d = new HashMap();
                }
                Map<String, String[]> map = aVar.d;
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                map.put(str, new String[]{str2, str3, sb.toString()});
                return true;
            }

            public final boolean a(long j, long j2, String str) {
                x.c("new process %s", str);
                a aVar = aVar;
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                return z;
            }

            public final boolean a(long j) {
                x.c("process end %d", Long.valueOf(j));
                return false;
            }
        });
        if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
            return aVar;
        }
        x.e("first dump error %s", aVar.a + " " + aVar.c + " " + aVar.b);
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:66:0x0182 A[Catch:{ all -> 0x0178 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01ab A[SYNTHETIC, Splitter:B:69:0x01ab] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01bb A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01be A[SYNTHETIC, Splitter:B:78:0x01be] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void readTraceFile(java.lang.String r18, com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b r19) {
        /*
            r0 = r18
            r6 = r19
            java.lang.String r7 = "\\s"
            if (r0 == 0) goto L_0x01ce
            if (r6 != 0) goto L_0x000c
            goto L_0x01ce
        L_0x000c:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            if (r0 != 0) goto L_0x0018
            return
        L_0x0018:
            r1.lastModified()
            r1.length()
            r2 = 0
            r8 = 2
            r9 = 0
            r10 = 1
            java.io.BufferedReader r11 = new java.io.BufferedReader     // Catch:{ Exception -> 0x017b }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Exception -> 0x017b }
            r0.<init>(r1)     // Catch:{ Exception -> 0x017b }
            r11.<init>(r0)     // Catch:{ Exception -> 0x017b }
            java.lang.String r0 = "-{5}\\spid\\s\\d+\\sat\\s\\d+-\\d+-\\d+\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}"
            java.util.regex.Pattern r12 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = "-{5}\\send\\s\\d+\\s-{5}"
            java.util.regex.Pattern r13 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = "Cmd\\sline:\\s(\\S+)"
            java.util.regex.Pattern r14 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = "\".+\"\\s(daemon\\s){0,1}prio=\\d+\\stid=\\d+\\s.*"
            java.util.regex.Pattern r15 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.text.SimpleDateFormat r5 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r5.<init>(r0, r1)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
        L_0x004f:
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r10]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0[r9] = r12     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.Object[] r0 = a(r11, r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            if (r0 == 0) goto L_0x0161
            r0 = r0[r10]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String[] r0 = r0.split(r7)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r1 = r0[r8]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            long r1 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r3.<init>()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r4 = 4
            r4 = r0[r4]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r3.append(r4)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r4 = " "
            r3.append(r4)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r4 = 5
            r0 = r0[r4]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r3.append(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = r3.toString()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.util.Date r0 = r5.parse(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            long r3 = r0.getTime()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r10]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0[r9] = r14     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.Object[] r0 = a(r11, r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            if (r0 != 0) goto L_0x00a5
            r11.close()     // Catch:{ IOException -> 0x0099 }
            return
        L_0x0099:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x00a4
            r1.printStackTrace()
        L_0x00a4:
            return
        L_0x00a5:
            r0 = r0[r10]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.util.regex.Matcher r0 = r14.matcher(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0.find()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0.group(r10)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r16 = r0.group(r10)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0 = r19
            r17 = r5
            r5 = r16
            boolean r0 = r0.a(r1, r3, r5)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            if (r0 != 0) goto L_0x00d5
            r11.close()     // Catch:{ IOException -> 0x00c9 }
            return
        L_0x00c9:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x00d4
            r1.printStackTrace()
        L_0x00d4:
            return
        L_0x00d5:
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r8]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0[r9] = r15     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0[r10] = r13     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.Object[] r0 = a(r11, r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            if (r0 == 0) goto L_0x015d
            r1 = r0[r9]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            if (r1 != r15) goto L_0x0137
            r0 = r0[r10]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r1 = "\".+\""
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r1)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.util.regex.Matcher r1 = r1.matcher(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r1.find()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r1 = r1.group()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            int r2 = r1.length()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            int r2 = r2 - r10
            java.lang.String r1 = r1.substring(r10, r2)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r2 = "NATIVE"
            r0.contains(r2)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r2 = "tid=\\d+"
            java.util.regex.Pattern r2 = java.util.regex.Pattern.compile(r2)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.util.regex.Matcher r0 = r2.matcher(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0.find()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = r0.group()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r2 = "="
            int r2 = r0.indexOf(r2)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            int r2 = r2 + r10
            java.lang.String r0 = r0.substring(r2)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r2 = a(r11)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r3 = b(r11)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r6.a(r1, r0, r2, r3)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            goto L_0x00d5
        L_0x0137:
            r0 = r0[r10]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            java.lang.String[] r0 = r0.split(r7)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            r0 = r0[r8]     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            boolean r0 = r6.a(r0)     // Catch:{ Exception -> 0x0175, all -> 0x0171 }
            if (r0 != 0) goto L_0x015d
            r11.close()     // Catch:{ IOException -> 0x0151 }
            return
        L_0x0151:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x015c
            r1.printStackTrace()
        L_0x015c:
            return
        L_0x015d:
            r5 = r17
            goto L_0x004f
        L_0x0161:
            r11.close()     // Catch:{ IOException -> 0x0165 }
            return
        L_0x0165:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x0170
            r1.printStackTrace()
        L_0x0170:
            return
        L_0x0171:
            r0 = move-exception
            r1 = r0
            r2 = r11
            goto L_0x01bc
        L_0x0175:
            r0 = move-exception
            r2 = r11
            goto L_0x017c
        L_0x0178:
            r0 = move-exception
            r1 = r0
            goto L_0x01bc
        L_0x017b:
            r0 = move-exception
        L_0x017c:
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0178 }
            if (r1 != 0) goto L_0x0185
            r0.printStackTrace()     // Catch:{ all -> 0x0178 }
        L_0x0185:
            java.lang.String r1 = "trace open fail:%s : %s"
            java.lang.Object[] r3 = new java.lang.Object[r8]     // Catch:{ all -> 0x0178 }
            java.lang.Class r4 = r0.getClass()     // Catch:{ all -> 0x0178 }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x0178 }
            r3[r9] = r4     // Catch:{ all -> 0x0178 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0178 }
            r4.<init>()     // Catch:{ all -> 0x0178 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0178 }
            r4.append(r0)     // Catch:{ all -> 0x0178 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0178 }
            r3[r10] = r0     // Catch:{ all -> 0x0178 }
            com.tencent.bugly.proguard.x.d(r1, r3)     // Catch:{ all -> 0x0178 }
            if (r2 == 0) goto L_0x01bb
            r2.close()     // Catch:{ IOException -> 0x01af }
            return
        L_0x01af:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x01ba
            r1.printStackTrace()
        L_0x01ba:
            return
        L_0x01bb:
            return
        L_0x01bc:
            if (r2 == 0) goto L_0x01cd
            r2.close()     // Catch:{ IOException -> 0x01c2 }
            goto L_0x01cd
        L_0x01c2:
            r0 = move-exception
            r2 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r2)
            if (r0 != 0) goto L_0x01cd
            r2.printStackTrace()
        L_0x01cd:
            throw r1
        L_0x01ce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readTraceFile(java.lang.String, com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$b):void");
    }

    private static Object[] a(BufferedReader bufferedReader, Pattern... patternArr) {
        if (bufferedReader == null || patternArr == null) {
            return null;
        }
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            int length = patternArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    Pattern pattern = patternArr[i];
                    if (pattern.matcher(readLine).matches()) {
                        return new Object[]{pattern, readLine};
                    }
                    i++;
                }
            }
        }
    }

    private static String a(BufferedReader bufferedReader) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            stringBuffer.append(readLine + "\n");
        }
        return stringBuffer.toString();
    }

    private static String b(BufferedReader bufferedReader) {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null && readLine.trim().length() > 0) {
                stringBuffer.append(readLine + "\n");
            }
        }
        return stringBuffer.toString();
    }
}
