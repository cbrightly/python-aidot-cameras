package com.didichuxing.doraemonkit.kit.loginfo.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogcatHelper {
    public static final String BUFFER_EVENTS = "events";
    public static final String BUFFER_MAIN = "main";
    public static final String BUFFER_RADIO = "radio";
    private static final String TAG = "LogcatHelper";

    public static Process getLogcatProcess(String buffer) {
        return RuntimeHelper.exec(getLogcatArgs(buffer));
    }

    private static List<String> getLogcatArgs(String buffer) {
        List<String> args = new ArrayList<>(Arrays.asList(new String[]{"logcat", "-v", "time"}));
        if (!buffer.equals(BUFFER_MAIN)) {
            args.add("-b");
            args.add(buffer);
        }
        return args;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0036, code lost:
        if (0 == 0) goto L_0x0039;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getLastLogLine(java.lang.String r7) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            java.util.List r3 = getLogcatArgs(r7)     // Catch:{ IOException -> 0x0032 }
            java.lang.String r4 = "-d"
            r3.add(r4)     // Catch:{ IOException -> 0x0032 }
            java.lang.Process r4 = com.didichuxing.doraemonkit.kit.loginfo.helper.RuntimeHelper.exec(r3)     // Catch:{ IOException -> 0x0032 }
            r0 = r4
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0032 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0032 }
            java.io.InputStream r6 = r0.getInputStream()     // Catch:{ IOException -> 0x0032 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x0032 }
            r6 = 8192(0x2000, float:1.14794E-41)
            r4.<init>(r5, r6)     // Catch:{ IOException -> 0x0032 }
            r1 = r4
        L_0x0022:
            java.lang.String r4 = r1.readLine()     // Catch:{ IOException -> 0x0032 }
            r5 = r4
            if (r4 == 0) goto L_0x002b
            r2 = r5
            goto L_0x0022
        L_0x002b:
        L_0x002c:
            com.didichuxing.doraemonkit.kit.loginfo.helper.RuntimeHelper.destroy(r0)
            goto L_0x0039
        L_0x0030:
            r3 = move-exception
            goto L_0x003a
        L_0x0032:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x0039
            goto L_0x002c
        L_0x0039:
            return r2
        L_0x003a:
            if (r0 == 0) goto L_0x003f
            com.didichuxing.doraemonkit.kit.loginfo.helper.RuntimeHelper.destroy(r0)
        L_0x003f:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper.getLastLogLine(java.lang.String):java.lang.String");
    }
}
