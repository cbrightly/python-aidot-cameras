package com.blankj.utilcode.util;

/* compiled from: ShellUtils */
public final class y {
    private static final String a = System.getProperty("line.separator");

    public static a a(String command, boolean isRooted) {
        return b(new String[]{command}, isRooted, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0117, code lost:
        if (r3 == null) goto L_0x011a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d2 A[SYNTHETIC, Splitter:B:36:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00de A[SYNTHETIC, Splitter:B:41:0x00de] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x011e  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0120  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0127  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.blankj.utilcode.util.y.a b(java.lang.String[] r13, boolean r14, boolean r15) {
        /*
            java.lang.String r0 = "UTF-8"
            r1 = -1
            java.lang.String r2 = ""
            if (r13 == 0) goto L_0x0159
            int r3 = r13.length
            if (r3 != 0) goto L_0x000c
            goto L_0x0159
        L_0x000c:
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.Runtime r9 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x00ef }
            if (r14 == 0) goto L_0x001c
            java.lang.String r10 = "su"
            goto L_0x001f
        L_0x001c:
            java.lang.String r10 = "sh"
        L_0x001f:
            java.lang.Process r9 = r9.exec(r10)     // Catch:{ Exception -> 0x00ef }
            r3 = r9
            java.io.DataOutputStream r9 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00ef }
            java.io.OutputStream r10 = r3.getOutputStream()     // Catch:{ Exception -> 0x00ef }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00ef }
            r8 = r9
            int r9 = r13.length     // Catch:{ Exception -> 0x00ef }
            r10 = 0
        L_0x0030:
            if (r10 >= r9) goto L_0x0049
            r11 = r13[r10]     // Catch:{ Exception -> 0x00ef }
            if (r11 != 0) goto L_0x0037
            goto L_0x0046
        L_0x0037:
            byte[] r12 = r11.getBytes()     // Catch:{ Exception -> 0x00ef }
            r8.write(r12)     // Catch:{ Exception -> 0x00ef }
            java.lang.String r12 = a     // Catch:{ Exception -> 0x00ef }
            r8.writeBytes(r12)     // Catch:{ Exception -> 0x00ef }
            r8.flush()     // Catch:{ Exception -> 0x00ef }
        L_0x0046:
            int r10 = r10 + 1
            goto L_0x0030
        L_0x0049:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ef }
            r9.<init>()     // Catch:{ Exception -> 0x00ef }
            java.lang.String r10 = "exit"
            r9.append(r10)     // Catch:{ Exception -> 0x00ef }
            java.lang.String r10 = a     // Catch:{ Exception -> 0x00ef }
            r9.append(r10)     // Catch:{ Exception -> 0x00ef }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00ef }
            r8.writeBytes(r9)     // Catch:{ Exception -> 0x00ef }
            r8.flush()     // Catch:{ Exception -> 0x00ef }
            int r9 = r3.waitFor()     // Catch:{ Exception -> 0x00ef }
            r1 = r9
            if (r15 == 0) goto L_0x00c7
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ef }
            r9.<init>()     // Catch:{ Exception -> 0x00ef }
            r6 = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ef }
            r9.<init>()     // Catch:{ Exception -> 0x00ef }
            r7 = r9
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ef }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00ef }
            java.io.InputStream r11 = r3.getInputStream()     // Catch:{ Exception -> 0x00ef }
            r10.<init>(r11, r0)     // Catch:{ Exception -> 0x00ef }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00ef }
            r4 = r9
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ef }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00ef }
            java.io.InputStream r11 = r3.getErrorStream()     // Catch:{ Exception -> 0x00ef }
            r10.<init>(r11, r0)     // Catch:{ Exception -> 0x00ef }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00ef }
            r5 = r9
            java.lang.String r0 = r4.readLine()     // Catch:{ Exception -> 0x00ef }
            r9 = r0
            if (r0 == 0) goto L_0x00ad
            r6.append(r9)     // Catch:{ Exception -> 0x00ef }
        L_0x009d:
            java.lang.String r0 = r4.readLine()     // Catch:{ Exception -> 0x00ef }
            r9 = r0
            if (r0 == 0) goto L_0x00ad
            java.lang.String r0 = a     // Catch:{ Exception -> 0x00ef }
            r6.append(r0)     // Catch:{ Exception -> 0x00ef }
            r6.append(r9)     // Catch:{ Exception -> 0x00ef }
            goto L_0x009d
        L_0x00ad:
            java.lang.String r0 = r5.readLine()     // Catch:{ Exception -> 0x00ef }
            r9 = r0
            if (r0 == 0) goto L_0x00c7
            r7.append(r9)     // Catch:{ Exception -> 0x00ef }
        L_0x00b7:
            java.lang.String r0 = r5.readLine()     // Catch:{ Exception -> 0x00ef }
            r9 = r0
            if (r0 == 0) goto L_0x00c7
            java.lang.String r0 = a     // Catch:{ Exception -> 0x00ef }
            r7.append(r0)     // Catch:{ Exception -> 0x00ef }
            r7.append(r9)     // Catch:{ Exception -> 0x00ef }
            goto L_0x00b7
        L_0x00c7:
            r8.close()     // Catch:{ IOException -> 0x00cc }
            goto L_0x00d0
        L_0x00cc:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00d0:
            if (r4 == 0) goto L_0x00db
            r4.close()     // Catch:{ IOException -> 0x00d6 }
            goto L_0x00db
        L_0x00d6:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00dc
        L_0x00db:
        L_0x00dc:
            if (r5 == 0) goto L_0x00e7
            r5.close()     // Catch:{ IOException -> 0x00e2 }
            goto L_0x00e7
        L_0x00e2:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00e8
        L_0x00e7:
        L_0x00e8:
        L_0x00e9:
            r3.destroy()
            goto L_0x011a
        L_0x00ed:
            r0 = move-exception
            goto L_0x012f
        L_0x00ef:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x00ed }
            if (r8 == 0) goto L_0x00fe
            r8.close()     // Catch:{ IOException -> 0x00f9 }
            goto L_0x00fe
        L_0x00f9:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00ff
        L_0x00fe:
        L_0x00ff:
            if (r4 == 0) goto L_0x010a
            r4.close()     // Catch:{ IOException -> 0x0105 }
            goto L_0x010a
        L_0x0105:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x010b
        L_0x010a:
        L_0x010b:
            if (r5 == 0) goto L_0x0116
            r5.close()     // Catch:{ IOException -> 0x0111 }
            goto L_0x0116
        L_0x0111:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0117
        L_0x0116:
        L_0x0117:
            if (r3 == 0) goto L_0x011a
            goto L_0x00e9
        L_0x011a:
            com.blankj.utilcode.util.y$a r0 = new com.blankj.utilcode.util.y$a
            if (r6 != 0) goto L_0x0120
            r9 = r2
            goto L_0x0124
        L_0x0120:
            java.lang.String r9 = r6.toString()
        L_0x0124:
            if (r7 != 0) goto L_0x0127
            goto L_0x012b
        L_0x0127:
            java.lang.String r2 = r7.toString()
        L_0x012b:
            r0.<init>(r1, r9, r2)
            return r0
        L_0x012f:
            if (r8 == 0) goto L_0x013a
            r8.close()     // Catch:{ IOException -> 0x0135 }
            goto L_0x013a
        L_0x0135:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x013b
        L_0x013a:
        L_0x013b:
            if (r4 == 0) goto L_0x0146
            r4.close()     // Catch:{ IOException -> 0x0141 }
            goto L_0x0146
        L_0x0141:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0147
        L_0x0146:
        L_0x0147:
            if (r5 == 0) goto L_0x0152
            r5.close()     // Catch:{ IOException -> 0x014d }
            goto L_0x0152
        L_0x014d:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0153
        L_0x0152:
        L_0x0153:
            if (r3 == 0) goto L_0x0158
            r3.destroy()
        L_0x0158:
            throw r0
        L_0x0159:
            com.blankj.utilcode.util.y$a r0 = new com.blankj.utilcode.util.y$a
            r0.<init>(r1, r2, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.y.b(java.lang.String[], boolean, boolean):com.blankj.utilcode.util.y$a");
    }

    /* compiled from: ShellUtils */
    public static class a {
        public int a;
        public String b;
        public String c;

        public a(int result, String successMsg, String errorMsg) {
            this.a = result;
            this.b = successMsg;
            this.c = errorMsg;
        }

        public String toString() {
            return "result: " + this.a + "\nsuccessMsg: " + this.b + "\nerrorMsg: " + this.c;
        }
    }
}
