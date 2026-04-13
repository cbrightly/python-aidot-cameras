package org.glassfish.grizzly.http.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Cookies;
import org.glassfish.grizzly.http.util.DataChunk;

public class CookieParserUtils {
    private static final Logger LOGGER = Grizzly.logger(CookieParserUtils.class);

    public static void parseClientCookies(Cookies cookies, Buffer buffer, int off, int len) {
        parseClientCookies(cookies, buffer, off, len, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x008f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e3 A[LOOP:5: B:56:0x00d7->B:59:0x00e3, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00f4 A[LOOP:6: B:60:0x00e6->B:65:0x00f4, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0149  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parseClientCookies(org.glassfish.grizzly.http.Cookies r17, org.glassfish.grizzly.Buffer r18, int r19, int r20, boolean r21, boolean r22) {
        /*
            r0 = r18
            r7 = r21
            if (r17 == 0) goto L_0x018c
            if (r0 == 0) goto L_0x0184
            if (r20 > 0) goto L_0x000b
            return
        L_0x000b:
            boolean r1 = r18.hasArray()
            if (r1 == 0) goto L_0x0028
            byte[] r2 = r18.array()
            int r1 = r18.arrayOffset()
            int r3 = r19 + r1
            r1 = r17
            r4 = r20
            r5 = r21
            r6 = r22
            parseClientCookies((org.glassfish.grizzly.http.Cookies) r1, (byte[]) r2, (int) r3, (int) r4, (boolean) r5, (boolean) r6)
            return
        L_0x0028:
            int r1 = r19 + r20
            r2 = r19
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x002f:
            if (r2 >= r1) goto L_0x0183
            r6 = 0
            r8 = 0
        L_0x0033:
            if (r2 >= r1) goto L_0x004c
            byte r9 = r0.get((int) r2)
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r9)
            if (r9 != 0) goto L_0x0049
            byte r9 = r0.get((int) r2)
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r9)
            if (r9 == 0) goto L_0x004c
        L_0x0049:
            int r2 = r2 + 1
            goto L_0x0033
        L_0x004c:
            if (r2 < r1) goto L_0x004f
            return
        L_0x004f:
            byte r9 = r0.get((int) r2)
            r10 = 36
            if (r9 != r10) goto L_0x005a
            r6 = 1
            int r2 = r2 + 1
        L_0x005a:
            r9 = r2
            int r10 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((org.glassfish.grizzly.Buffer) r0, (int) r2, (int) r1)
            r11 = r10
            r2 = r10
        L_0x0061:
            if (r2 >= r1) goto L_0x0070
            byte r10 = r0.get((int) r2)
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r10)
            if (r10 == 0) goto L_0x0070
            int r2 = r2 + 1
            goto L_0x0061
        L_0x0070:
            r10 = 44
            r12 = 59
            r13 = -1
            if (r2 >= r1) goto L_0x00d4
            byte r14 = r0.get((int) r2)
            r15 = 61
            if (r14 != r15) goto L_0x00d4
        L_0x007f:
            int r2 = r2 + 1
            if (r2 >= r1) goto L_0x008d
            byte r14 = r0.get((int) r2)
            boolean r14 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r14)
            if (r14 != 0) goto L_0x007f
        L_0x008d:
            if (r2 < r1) goto L_0x0090
            return
        L_0x0090:
            byte r14 = r0.get((int) r2)
            switch(r14) {
                case 34: goto L_0x00ab;
                case 44: goto L_0x00a8;
                case 59: goto L_0x00a8;
                default: goto L_0x0097;
            }
        L_0x0097:
            byte r14 = r0.get((int) r2)
            boolean r14 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r14, r7)
            if (r14 != 0) goto L_0x00b6
            r15 = r2
            int r14 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((org.glassfish.grizzly.Buffer) r0, (int) r15, (int) r1, (boolean) r7)
            r2 = r14
            goto L_0x00d7
        L_0x00a8:
            r14 = r13
            r15 = r13
            goto L_0x00d7
        L_0x00ab:
            r8 = 1
            int r15 = r2 + 1
            int r14 = org.glassfish.grizzly.http.util.CookieUtils.getQuotedValueEndPosition((org.glassfish.grizzly.Buffer) r0, (int) r15, (int) r1)
            r2 = r14
            if (r2 < r1) goto L_0x00d7
            return
        L_0x00b6:
            java.util.logging.Logger r13 = LOGGER
            java.lang.String r14 = "Invalid cookie. Value not a token or quoted value"
            r13.fine(r14)
        L_0x00bd:
            if (r2 >= r1) goto L_0x00ce
            byte r13 = r0.get((int) r2)
            if (r13 == r12) goto L_0x00ce
            byte r13 = r0.get((int) r2)
            if (r13 == r10) goto L_0x00ce
            int r2 = r2 + 1
            goto L_0x00bd
        L_0x00ce:
            int r2 = r2 + 1
            r4 = 0
            r5 = 0
            goto L_0x002f
        L_0x00d4:
            r14 = r13
            r15 = r13
            r2 = r11
        L_0x00d7:
            if (r2 >= r1) goto L_0x00e6
            byte r16 = r0.get((int) r2)
            boolean r16 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r16)
            if (r16 == 0) goto L_0x00e6
            int r2 = r2 + 1
            goto L_0x00d7
        L_0x00e6:
            if (r2 >= r1) goto L_0x00f8
            byte r13 = r0.get((int) r2)
            if (r13 == r12) goto L_0x00f8
            byte r13 = r0.get((int) r2)
            if (r13 == r10) goto L_0x00f8
            int r2 = r2 + 1
            r13 = -1
            goto L_0x00e6
        L_0x00f8:
            int r2 = r2 + 1
            if (r6 == 0) goto L_0x0149
            r6 = 0
            java.lang.String r10 = "Version"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r0, (int) r9, (int) r11)
            if (r10 == 0) goto L_0x011a
            if (r4 != 0) goto L_0x011a
            if (r22 == 0) goto L_0x010b
            goto L_0x002f
        L_0x010b:
            byte r10 = r0.get((int) r15)
            r12 = 49
            if (r10 != r12) goto L_0x002f
            int r10 = r15 + 1
            if (r14 != r10) goto L_0x002f
            r3 = 1
            goto L_0x002f
        L_0x011a:
            if (r4 != 0) goto L_0x011e
            goto L_0x002f
        L_0x011e:
            java.lang.String r10 = "Domain"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r0, (int) r9, (int) r11)
            if (r10 == 0) goto L_0x012f
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getDomain()
            r10.setBuffer(r0, r15, r14)
            goto L_0x002f
        L_0x012f:
            java.lang.String r10 = "Path"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r0, (int) r9, (int) r11)
            if (r10 == 0) goto L_0x0140
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getPath()
            r10.setBuffer(r0, r15, r14)
            goto L_0x002f
        L_0x0140:
            java.util.logging.Logger r10 = LOGGER
            java.lang.String r12 = "Unknown Special Cookie"
            r10.fine(r12)
            goto L_0x002f
        L_0x0149:
            org.glassfish.grizzly.http.Cookie r4 = r17.getNextUnusedCookie()
            org.glassfish.grizzly.http.LazyCookieState r5 = r4.getLazyCookieState()
            if (r22 != 0) goto L_0x015c
            boolean r10 = r4.isVersionSet()
            if (r10 != 0) goto L_0x015c
            r4.setVersion(r3)
        L_0x015c:
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getName()
            r10.setBuffer(r0, r9, r11)
            r10 = -1
            if (r15 == r10) goto L_0x0178
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getValue()
            r10.setBuffer(r0, r15, r14)
            if (r8 == 0) goto L_0x002f
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getValue()
            unescapeDoubleQuotes((org.glassfish.grizzly.http.util.DataChunk) r10)
            goto L_0x002f
        L_0x0178:
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getValue()
            java.lang.String r12 = ""
            r10.setString(r12)
            goto L_0x002f
        L_0x0183:
            return
        L_0x0184:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "buffer cannot be null."
            r1.<init>(r2)
            throw r1
        L_0x018c:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "cookies cannot be null."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.CookieParserUtils.parseClientCookies(org.glassfish.grizzly.http.Cookies, org.glassfish.grizzly.Buffer, int, int, boolean, boolean):void");
    }

    public static void parseClientCookies(Cookies cookies, byte[] bytes, int off, int len) {
        parseClientCookies(cookies, bytes, off, len, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x0066 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0 A[LOOP:5: B:52:0x00a6->B:55:0x00b0, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00bd A[LOOP:6: B:56:0x00b3->B:61:0x00bd, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parseClientCookies(org.glassfish.grizzly.http.Cookies r17, byte[] r18, int r19, int r20, boolean r21, boolean r22) {
        /*
            r0 = r18
            r1 = r21
            if (r17 == 0) goto L_0x0153
            if (r0 == 0) goto L_0x014b
            if (r20 > 0) goto L_0x000b
            return
        L_0x000b:
            int r2 = r19 + r20
            r3 = r19
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x0012:
            if (r3 >= r2) goto L_0x014a
            r7 = 0
            r8 = 0
        L_0x0016:
            if (r3 >= r2) goto L_0x002b
            byte r9 = r0[r3]
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r9)
            if (r9 != 0) goto L_0x0028
            byte r9 = r0[r3]
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r9)
            if (r9 == 0) goto L_0x002b
        L_0x0028:
            int r3 = r3 + 1
            goto L_0x0016
        L_0x002b:
            if (r3 < r2) goto L_0x002e
            return
        L_0x002e:
            byte r9 = r0[r3]
            r10 = 36
            if (r9 != r10) goto L_0x0037
            r7 = 1
            int r3 = r3 + 1
        L_0x0037:
            r9 = r3
            int r10 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((byte[]) r0, (int) r3, (int) r2)
            r11 = r10
            r3 = r10
        L_0x003e:
            if (r3 >= r2) goto L_0x004b
            byte r10 = r0[r3]
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r10)
            if (r10 == 0) goto L_0x004b
            int r3 = r3 + 1
            goto L_0x003e
        L_0x004b:
            r10 = 44
            r12 = 59
            r13 = -1
            if (r3 >= r2) goto L_0x00a3
            byte r14 = r0[r3]
            r15 = 61
            if (r14 != r15) goto L_0x00a3
        L_0x0058:
            int r3 = r3 + 1
            if (r3 >= r2) goto L_0x0064
            byte r14 = r0[r3]
            boolean r14 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r14)
            if (r14 != 0) goto L_0x0058
        L_0x0064:
            if (r3 < r2) goto L_0x0067
            return
        L_0x0067:
            byte r14 = r0[r3]
            switch(r14) {
                case 34: goto L_0x007e;
                case 44: goto L_0x007b;
                case 59: goto L_0x007b;
                default: goto L_0x006c;
            }
        L_0x006c:
            byte r14 = r0[r3]
            boolean r14 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r14, r1)
            if (r14 != 0) goto L_0x0089
            r15 = r3
            int r14 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((byte[]) r0, (int) r15, (int) r2, (boolean) r1)
            r3 = r14
            goto L_0x00a6
        L_0x007b:
            r14 = r13
            r15 = r13
            goto L_0x00a6
        L_0x007e:
            r8 = 1
            int r15 = r3 + 1
            int r14 = org.glassfish.grizzly.http.util.CookieUtils.getQuotedValueEndPosition((byte[]) r0, (int) r15, (int) r2)
            r3 = r14
            if (r3 < r2) goto L_0x00a6
            return
        L_0x0089:
            java.util.logging.Logger r13 = LOGGER
            java.lang.String r14 = "Invalid cookie. Value not a token or quoted value"
            r13.fine(r14)
        L_0x0090:
            if (r3 >= r2) goto L_0x009d
            byte r13 = r0[r3]
            if (r13 == r12) goto L_0x009d
            byte r13 = r0[r3]
            if (r13 == r10) goto L_0x009d
            int r3 = r3 + 1
            goto L_0x0090
        L_0x009d:
            int r3 = r3 + 1
            r5 = 0
            r6 = 0
            goto L_0x0012
        L_0x00a3:
            r14 = r13
            r15 = r13
            r3 = r11
        L_0x00a6:
            if (r3 >= r2) goto L_0x00b3
            byte r16 = r0[r3]
            boolean r16 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r16)
            if (r16 == 0) goto L_0x00b3
            int r3 = r3 + 1
            goto L_0x00a6
        L_0x00b3:
            if (r3 >= r2) goto L_0x00c1
            byte r13 = r0[r3]
            if (r13 == r12) goto L_0x00c1
            byte r13 = r0[r3]
            if (r13 == r10) goto L_0x00c1
            int r3 = r3 + 1
            r13 = -1
            goto L_0x00b3
        L_0x00c1:
            int r3 = r3 + 1
            if (r7 == 0) goto L_0x0110
            r7 = 0
            java.lang.String r10 = "Version"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (byte[]) r0, (int) r9, (int) r11)
            if (r10 == 0) goto L_0x00e1
            if (r5 != 0) goto L_0x00e1
            if (r22 == 0) goto L_0x00d4
            goto L_0x0012
        L_0x00d4:
            byte r10 = r0[r15]
            r12 = 49
            if (r10 != r12) goto L_0x0012
            int r10 = r15 + 1
            if (r14 != r10) goto L_0x0012
            r4 = 1
            goto L_0x0012
        L_0x00e1:
            if (r5 != 0) goto L_0x00e5
            goto L_0x0012
        L_0x00e5:
            java.lang.String r10 = "Domain"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (byte[]) r0, (int) r9, (int) r11)
            if (r10 == 0) goto L_0x00f6
            org.glassfish.grizzly.http.util.DataChunk r10 = r6.getDomain()
            r10.setBytes(r0, r15, r14)
            goto L_0x0012
        L_0x00f6:
            java.lang.String r10 = "Path"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (byte[]) r0, (int) r9, (int) r11)
            if (r10 == 0) goto L_0x0107
            org.glassfish.grizzly.http.util.DataChunk r10 = r6.getPath()
            r10.setBytes(r0, r15, r14)
            goto L_0x0012
        L_0x0107:
            java.util.logging.Logger r10 = LOGGER
            java.lang.String r12 = "Unknown Special Cookie"
            r10.fine(r12)
            goto L_0x0012
        L_0x0110:
            org.glassfish.grizzly.http.Cookie r5 = r17.getNextUnusedCookie()
            org.glassfish.grizzly.http.LazyCookieState r6 = r5.getLazyCookieState()
            if (r22 != 0) goto L_0x0123
            boolean r10 = r5.isVersionSet()
            if (r10 != 0) goto L_0x0123
            r5.setVersion(r4)
        L_0x0123:
            org.glassfish.grizzly.http.util.DataChunk r10 = r6.getName()
            r10.setBytes(r0, r9, r11)
            r10 = -1
            if (r15 == r10) goto L_0x013f
            org.glassfish.grizzly.http.util.DataChunk r10 = r6.getValue()
            r10.setBytes(r0, r15, r14)
            if (r8 == 0) goto L_0x0012
            org.glassfish.grizzly.http.util.DataChunk r10 = r6.getValue()
            unescapeDoubleQuotes((org.glassfish.grizzly.http.util.DataChunk) r10)
            goto L_0x0012
        L_0x013f:
            org.glassfish.grizzly.http.util.DataChunk r10 = r6.getValue()
            java.lang.String r12 = ""
            r10.setString(r12)
            goto L_0x0012
        L_0x014a:
            return
        L_0x014b:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "bytes cannot be null."
            r2.<init>(r3)
            throw r2
        L_0x0153:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "cookies cannot be null."
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.CookieParserUtils.parseClientCookies(org.glassfish.grizzly.http.Cookies, byte[], int, int, boolean, boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0076 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00c9 A[LOOP:5: B:53:0x00bd->B:56:0x00c9, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00da A[LOOP:6: B:57:0x00cc->B:62:0x00da, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x012e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parseClientCookies(org.glassfish.grizzly.http.Cookies r16, java.lang.String r17, boolean r18, boolean r19) {
        /*
            r0 = r17
            r1 = r18
            if (r16 == 0) goto L_0x0164
            if (r0 == 0) goto L_0x015c
            int r2 = r17.length()
            if (r2 != 0) goto L_0x000f
            return
        L_0x000f:
            int r2 = r17.length()
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x0016:
            if (r3 >= r2) goto L_0x015b
            r6 = 0
            r7 = 0
        L_0x001a:
            if (r3 >= r2) goto L_0x0033
            char r8 = r0.charAt(r3)
            boolean r8 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r8)
            if (r8 != 0) goto L_0x0030
            char r8 = r0.charAt(r3)
            boolean r8 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r8)
            if (r8 == 0) goto L_0x0033
        L_0x0030:
            int r3 = r3 + 1
            goto L_0x001a
        L_0x0033:
            if (r3 < r2) goto L_0x0036
            return
        L_0x0036:
            char r8 = r0.charAt(r3)
            r9 = 36
            if (r8 != r9) goto L_0x0041
            r6 = 1
            int r3 = r3 + 1
        L_0x0041:
            r8 = r3
            int r9 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((java.lang.String) r0, (int) r3, (int) r2)
            r10 = r9
            r3 = r9
        L_0x0048:
            if (r3 >= r2) goto L_0x0057
            char r9 = r0.charAt(r3)
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r9)
            if (r9 == 0) goto L_0x0057
            int r3 = r3 + 1
            goto L_0x0048
        L_0x0057:
            r9 = 44
            r11 = 59
            r12 = -1
            if (r3 >= r2) goto L_0x00ba
            char r13 = r0.charAt(r3)
            r14 = 61
            if (r13 != r14) goto L_0x00ba
        L_0x0066:
            int r3 = r3 + 1
            if (r3 >= r2) goto L_0x0074
            char r13 = r0.charAt(r3)
            boolean r13 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r13)
            if (r13 != 0) goto L_0x0066
        L_0x0074:
            if (r3 < r2) goto L_0x0077
            return
        L_0x0077:
            char r13 = r0.charAt(r3)
            switch(r13) {
                case 34: goto L_0x0092;
                case 44: goto L_0x008f;
                case 59: goto L_0x008f;
                default: goto L_0x007e;
            }
        L_0x007e:
            char r13 = r0.charAt(r3)
            boolean r13 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r13, r1)
            if (r13 != 0) goto L_0x009d
            r14 = r3
            int r13 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((java.lang.String) r0, (int) r14, (int) r2, (boolean) r1)
            r3 = r13
            goto L_0x00bd
        L_0x008f:
            r13 = r12
            r14 = r12
            goto L_0x00bd
        L_0x0092:
            r7 = 1
            int r14 = r3 + 1
            int r13 = org.glassfish.grizzly.http.util.CookieUtils.getQuotedValueEndPosition((java.lang.String) r0, (int) r14, (int) r2)
            r3 = r13
            if (r3 < r2) goto L_0x00bd
            return
        L_0x009d:
            java.util.logging.Logger r12 = LOGGER
            java.lang.String r13 = "Invalid cookie. Value not a token or quoted value"
            r12.fine(r13)
        L_0x00a4:
            if (r3 >= r2) goto L_0x00b5
            char r12 = r0.charAt(r3)
            if (r12 == r11) goto L_0x00b5
            char r12 = r0.charAt(r3)
            if (r12 == r9) goto L_0x00b5
            int r3 = r3 + 1
            goto L_0x00a4
        L_0x00b5:
            int r3 = r3 + 1
            r5 = 0
            goto L_0x0016
        L_0x00ba:
            r13 = r12
            r14 = r12
            r3 = r10
        L_0x00bd:
            if (r3 >= r2) goto L_0x00cc
            char r15 = r0.charAt(r3)
            boolean r15 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r15)
            if (r15 == 0) goto L_0x00cc
            int r3 = r3 + 1
            goto L_0x00bd
        L_0x00cc:
            if (r3 >= r2) goto L_0x00dd
            char r15 = r0.charAt(r3)
            if (r15 == r11) goto L_0x00dd
            char r15 = r0.charAt(r3)
            if (r15 == r9) goto L_0x00dd
            int r3 = r3 + 1
            goto L_0x00cc
        L_0x00dd:
            int r3 = r3 + 1
            if (r6 == 0) goto L_0x012e
            r6 = 0
            java.lang.String r9 = "Version"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r0, (int) r8, (int) r10)
            if (r9 == 0) goto L_0x00ff
            if (r5 != 0) goto L_0x00ff
            if (r19 == 0) goto L_0x00f0
            goto L_0x0016
        L_0x00f0:
            char r9 = r0.charAt(r14)
            r11 = 49
            if (r9 != r11) goto L_0x0016
            int r9 = r14 + 1
            if (r13 != r9) goto L_0x0016
            r4 = 1
            goto L_0x0016
        L_0x00ff:
            if (r5 != 0) goto L_0x0103
            goto L_0x0016
        L_0x0103:
            java.lang.String r9 = "Domain"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r0, (int) r8, (int) r10)
            if (r9 == 0) goto L_0x0114
            java.lang.String r9 = r0.substring(r14, r13)
            r5.setDomain(r9)
            goto L_0x0016
        L_0x0114:
            java.lang.String r9 = "Path"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r0, (int) r8, (int) r10)
            if (r9 == 0) goto L_0x0125
            java.lang.String r9 = r0.substring(r14, r13)
            r5.setPath(r9)
            goto L_0x0016
        L_0x0125:
            java.util.logging.Logger r9 = LOGGER
            java.lang.String r11 = "Unknown Special Cookie"
            r9.fine(r11)
            goto L_0x0016
        L_0x012e:
            java.lang.String r9 = r0.substring(r8, r10)
            if (r14 == r12) goto L_0x0142
            if (r7 == 0) goto L_0x013d
            int r11 = r13 - r14
            java.lang.String r11 = unescapeDoubleQuotes((java.lang.String) r0, (int) r14, (int) r11)
            goto L_0x0144
        L_0x013d:
            java.lang.String r11 = r0.substring(r14, r13)
            goto L_0x0144
        L_0x0142:
            java.lang.String r11 = ""
        L_0x0144:
            org.glassfish.grizzly.http.Cookie r5 = r16.getNextUnusedCookie()
            r5.setName(r9)
            r5.setValue(r11)
            if (r19 != 0) goto L_0x0159
            boolean r12 = r5.isVersionSet()
            if (r12 != 0) goto L_0x0159
            r5.setVersion(r4)
        L_0x0159:
            goto L_0x0016
        L_0x015b:
            return
        L_0x015c:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "cookieStr cannot be null."
            r2.<init>(r3)
            throw r2
        L_0x0164:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "cookies cannot be null."
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.CookieParserUtils.parseClientCookies(org.glassfish.grizzly.http.Cookies, java.lang.String, boolean, boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01d3, code lost:
        if (org.glassfish.grizzly.http.util.CookieUtils.equals("Discard", r1, r7, r9) != false) goto L_0x01d6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x01f9  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x005b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00a5 A[LOOP:5: B:49:0x009b->B:52:0x00a5, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00b2 A[LOOP:6: B:53:0x00a8->B:58:0x00b2, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parseServerCookies(org.glassfish.grizzly.http.Cookies r16, byte[] r17, int r18, int r19, boolean r20, boolean r21) {
        /*
            r1 = r17
            r2 = r20
            if (r16 == 0) goto L_0x0222
            if (r1 == 0) goto L_0x021a
            if (r19 > 0) goto L_0x000b
            return
        L_0x000b:
            int r3 = r18 + r19
            r0 = r18
            r4 = 0
            r5 = 0
        L_0x0011:
            if (r0 >= r3) goto L_0x0219
            r6 = 0
        L_0x0014:
            if (r0 >= r3) goto L_0x0029
            byte r7 = r1[r0]
            boolean r7 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r7)
            if (r7 != 0) goto L_0x0026
            byte r7 = r1[r0]
            boolean r7 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r7)
            if (r7 == 0) goto L_0x0029
        L_0x0026:
            int r0 = r0 + 1
            goto L_0x0014
        L_0x0029:
            if (r0 < r3) goto L_0x002c
            return
        L_0x002c:
            r7 = r0
            int r8 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((byte[]) r1, (int) r0, (int) r3)
            r9 = r8
            r0 = r8
        L_0x0033:
            if (r0 >= r3) goto L_0x0040
            byte r8 = r1[r0]
            boolean r8 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r8)
            if (r8 == 0) goto L_0x0040
            int r0 = r0 + 1
            goto L_0x0033
        L_0x0040:
            r8 = 44
            r10 = 59
            r11 = -1
            r12 = 1
            if (r0 >= r3) goto L_0x0098
            byte r13 = r1[r0]
            r14 = 61
            if (r13 != r14) goto L_0x0098
        L_0x004e:
            int r0 = r0 + r12
            if (r0 >= r3) goto L_0x0059
            byte r13 = r1[r0]
            boolean r13 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r13)
            if (r13 != 0) goto L_0x004e
        L_0x0059:
            if (r0 < r3) goto L_0x005c
            return
        L_0x005c:
            byte r13 = r1[r0]
            switch(r13) {
                case 34: goto L_0x0073;
                case 44: goto L_0x0070;
                case 59: goto L_0x0070;
                default: goto L_0x0061;
            }
        L_0x0061:
            byte r13 = r1[r0]
            boolean r13 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r13, r2)
            if (r13 != 0) goto L_0x007e
            r14 = r0
            int r13 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((byte[]) r1, (int) r14, (int) r3, (boolean) r2)
            r0 = r13
            goto L_0x009b
        L_0x0070:
            r13 = r11
            r14 = r11
            goto L_0x009b
        L_0x0073:
            r6 = 1
            int r14 = r0 + 1
            int r13 = org.glassfish.grizzly.http.util.CookieUtils.getQuotedValueEndPosition((byte[]) r1, (int) r14, (int) r3)
            r0 = r13
            if (r0 < r3) goto L_0x009b
            return
        L_0x007e:
            java.util.logging.Logger r11 = LOGGER
            java.lang.String r12 = "Invalid cookie. Value not a token or quoted value"
            r11.fine(r12)
        L_0x0085:
            if (r0 >= r3) goto L_0x0092
            byte r11 = r1[r0]
            if (r11 == r10) goto L_0x0092
            byte r11 = r1[r0]
            if (r11 == r8) goto L_0x0092
            int r0 = r0 + 1
            goto L_0x0085
        L_0x0092:
            int r0 = r0 + 1
            r4 = 0
            r5 = 0
            goto L_0x0011
        L_0x0098:
            r13 = r11
            r14 = r11
            r0 = r9
        L_0x009b:
            if (r0 >= r3) goto L_0x00a8
            byte r15 = r1[r0]
            boolean r15 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r15)
            if (r15 == 0) goto L_0x00a8
            int r0 = r0 + 1
            goto L_0x009b
        L_0x00a8:
            if (r0 >= r3) goto L_0x00b5
            byte r15 = r1[r0]
            if (r15 == r10) goto L_0x00b5
            byte r15 = r1[r0]
            if (r15 == r8) goto L_0x00b5
            int r0 = r0 + 1
            goto L_0x00a8
        L_0x00b5:
            int r8 = r0 + 1
            r0 = 0
            if (r4 == 0) goto L_0x01dc
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getDomain()
            boolean r10 = r10.isNull()
            if (r10 == 0) goto L_0x00d6
            java.lang.String r10 = "Domain"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r10, (byte[]) r1, (int) r7, (int) r9)
            if (r10 == 0) goto L_0x00d6
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getDomain()
            r0.setBytes(r1, r14, r13)
            r15 = r3
            goto L_0x01d6
        L_0x00d6:
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getPath()
            boolean r10 = r10.isNull()
            if (r10 == 0) goto L_0x00f2
            java.lang.String r10 = "Path"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r10, (byte[]) r1, (int) r7, (int) r9)
            if (r10 == 0) goto L_0x00f2
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getPath()
            r0.setBytes(r1, r14, r13)
            r15 = r3
            goto L_0x01d6
        L_0x00f2:
            java.lang.String r10 = "Version"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (byte[]) r1, (int) r7, (int) r9)
            if (r10 == 0) goto L_0x0112
            if (r21 == 0) goto L_0x00ff
            r15 = r3
            goto L_0x01d6
        L_0x00ff:
            byte r0 = r1[r14]
            r10 = 49
            if (r0 != r10) goto L_0x010f
            int r0 = r14 + 1
            if (r13 != r0) goto L_0x010f
            r4.setVersion(r12)
            r15 = r3
            goto L_0x01d6
        L_0x010f:
            r15 = r3
            goto L_0x01d6
        L_0x0112:
            org.glassfish.grizzly.http.util.DataChunk r10 = r5.getComment()
            boolean r10 = r10.isNull()
            if (r10 == 0) goto L_0x012e
            java.lang.String r10 = "Comment"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (byte[]) r1, (int) r7, (int) r9)
            if (r10 == 0) goto L_0x012e
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getComment()
            r0.setBytes(r1, r14, r13)
            r15 = r3
            goto L_0x01d6
        L_0x012e:
            int r10 = r4.getMaxAge()
            if (r10 != r11) goto L_0x0148
            java.lang.String r10 = "Max-Age"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (byte[]) r1, (int) r7, (int) r9)
            if (r10 == 0) goto L_0x0148
            int r0 = r13 - r14
            int r0 = org.glassfish.grizzly.http.util.Ascii.parseInt((byte[]) r1, (int) r14, (int) r0)
            r4.setMaxAge(r0)
            r15 = r3
            goto L_0x01d6
        L_0x0148:
            int r10 = r4.getVersion()
            if (r10 == 0) goto L_0x0157
            boolean r10 = r4.isVersionSet()
            if (r10 != 0) goto L_0x0155
            goto L_0x0157
        L_0x0155:
            r15 = r3
            goto L_0x01a9
        L_0x0157:
            int r10 = r4.getMaxAge()
            if (r10 != r11) goto L_0x01a8
            java.lang.String r10 = "Expires"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r10, (byte[]) r1, (int) r7, (int) r9)
            if (r10 == 0) goto L_0x01a6
            int r10 = r13 + 1
            int r0 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((byte[]) r1, (int) r10, (int) r3, (boolean) r0)     // Catch:{ ParseException -> 0x019e }
            r13 = r0
            int r8 = r13 + 1
            java.lang.String r0 = new java.lang.String     // Catch:{ ParseException -> 0x019e }
            int r10 = r13 - r14
            java.nio.charset.Charset r11 = org.glassfish.grizzly.utils.Charsets.ASCII_CHARSET     // Catch:{ ParseException -> 0x019e }
            r0.<init>(r1, r14, r10, r11)     // Catch:{ ParseException -> 0x019e }
            java.lang.ThreadLocal<java.text.SimpleDateFormat> r10 = org.glassfish.grizzly.http.util.CookieUtils.OLD_COOKIE_FORMAT     // Catch:{ ParseException -> 0x019e }
            java.lang.Object r10 = r10.get()     // Catch:{ ParseException -> 0x019e }
            java.text.SimpleDateFormat r10 = (java.text.SimpleDateFormat) r10     // Catch:{ ParseException -> 0x019e }
            java.util.Date r10 = r10.parse(r0)     // Catch:{ ParseException -> 0x019e }
            long r11 = r10.getTime()     // Catch:{ ParseException -> 0x019e }
            r15 = r3
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ ParseException -> 0x019b }
            int r2 = getMaxAgeDelta(r11, r2)     // Catch:{ ParseException -> 0x019b }
            int r2 = r2 / 1000
            r4.setMaxAge(r2)     // Catch:{ ParseException -> 0x019b }
            r2 = r20
            r0 = r8
            r3 = r15
            goto L_0x0011
        L_0x019b:
            r0 = move-exception
            r0 = r8
            goto L_0x01a1
        L_0x019e:
            r0 = move-exception
            r15 = r3
            r0 = r8
        L_0x01a1:
            r2 = r20
            r3 = r15
            goto L_0x0011
        L_0x01a6:
            r15 = r3
            goto L_0x01a9
        L_0x01a8:
            r15 = r3
        L_0x01a9:
            boolean r2 = r4.isSecure()
            if (r2 != 0) goto L_0x01bb
            java.lang.String r2 = "Secure"
            boolean r2 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r2, (byte[]) r1, (int) r7, (int) r9)
            if (r2 == 0) goto L_0x01bb
            r5.setSecure(r12)
            goto L_0x01d6
        L_0x01bb:
            boolean r2 = r4.isHttpOnly()
            if (r2 != 0) goto L_0x01cd
            java.lang.String r2 = "HttpOnly"
            boolean r2 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r2, (byte[]) r1, (int) r7, (int) r9)
            if (r2 == 0) goto L_0x01cd
            r4.setHttpOnly(r12)
            goto L_0x01d6
        L_0x01cd:
            java.lang.String r2 = "Discard"
            boolean r2 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r2, (byte[]) r1, (int) r7, (int) r9)
            if (r2 == 0) goto L_0x01dd
        L_0x01d6:
            r2 = r20
            r0 = r8
            r3 = r15
            goto L_0x0011
        L_0x01dc:
            r15 = r3
        L_0x01dd:
            org.glassfish.grizzly.http.Cookie r4 = r16.getNextUnusedCookie()
            if (r21 != 0) goto L_0x01ec
            boolean r2 = r4.isVersionSet()
            if (r2 != 0) goto L_0x01ec
            r4.setVersion(r0)
        L_0x01ec:
            org.glassfish.grizzly.http.LazyCookieState r5 = r4.getLazyCookieState()
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getName()
            r0.setBytes(r1, r7, r9)
            if (r14 == r11) goto L_0x020a
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getValue()
            r0.setBytes(r1, r14, r13)
            if (r6 == 0) goto L_0x0213
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getValue()
            unescapeDoubleQuotes((org.glassfish.grizzly.http.util.DataChunk) r0)
            goto L_0x0213
        L_0x020a:
            org.glassfish.grizzly.http.util.DataChunk r0 = r5.getValue()
            java.lang.String r2 = ""
            r0.setString(r2)
        L_0x0213:
            r2 = r20
            r0 = r8
            r3 = r15
            goto L_0x0011
        L_0x0219:
            return
        L_0x021a:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "bytes cannot be null."
            r0.<init>(r2)
            throw r0
        L_0x0222:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "cookies cannot be null."
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.CookieParserUtils.parseServerCookies(org.glassfish.grizzly.http.Cookies, byte[], int, int, boolean, boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:129:0x01f9, code lost:
        if (org.glassfish.grizzly.http.util.CookieUtils.equals("Discard", r1, r6, r9) != false) goto L_0x0232;
     */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0218  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0229  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0082 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d6 A[LOOP:5: B:53:0x00ca->B:56:0x00d6, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00e7 A[LOOP:6: B:57:0x00d9->B:62:0x00e7, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00ef  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parseServerCookies(org.glassfish.grizzly.http.Cookies r16, org.glassfish.grizzly.Buffer r17, int r18, int r19, boolean r20, boolean r21) {
        /*
            r1 = r17
            r8 = r20
            if (r16 == 0) goto L_0x0240
            if (r1 == 0) goto L_0x0238
            if (r19 > 0) goto L_0x000b
            return
        L_0x000b:
            boolean r0 = r17.hasArray()
            if (r0 == 0) goto L_0x0028
            byte[] r3 = r17.array()
            int r0 = r17.arrayOffset()
            int r4 = r18 + r0
            r2 = r16
            r5 = r19
            r6 = r20
            r7 = r21
            parseServerCookies((org.glassfish.grizzly.http.Cookies) r2, (byte[]) r3, (int) r4, (int) r5, (boolean) r6, (boolean) r7)
            return
        L_0x0028:
            int r2 = r18 + r19
            r0 = r18
            r3 = 0
            r4 = 0
        L_0x002e:
            if (r0 >= r2) goto L_0x0237
            r5 = 0
        L_0x0031:
            if (r0 >= r2) goto L_0x004a
            byte r6 = r1.get((int) r0)
            boolean r6 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r6)
            if (r6 != 0) goto L_0x0047
            byte r6 = r1.get((int) r0)
            boolean r6 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r6)
            if (r6 == 0) goto L_0x004a
        L_0x0047:
            int r0 = r0 + 1
            goto L_0x0031
        L_0x004a:
            if (r0 < r2) goto L_0x004d
            return
        L_0x004d:
            r6 = r0
            int r7 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((org.glassfish.grizzly.Buffer) r1, (int) r0, (int) r2)
            r9 = r7
            r0 = r7
        L_0x0054:
            if (r0 >= r2) goto L_0x0063
            byte r7 = r1.get((int) r0)
            boolean r7 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r7)
            if (r7 == 0) goto L_0x0063
            int r0 = r0 + 1
            goto L_0x0054
        L_0x0063:
            r7 = 44
            r10 = 59
            r11 = -1
            r12 = 1
            if (r0 >= r2) goto L_0x00c7
            byte r13 = r1.get((int) r0)
            r14 = 61
            if (r13 != r14) goto L_0x00c7
        L_0x0073:
            int r0 = r0 + r12
            if (r0 >= r2) goto L_0x0080
            byte r13 = r1.get((int) r0)
            boolean r13 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r13)
            if (r13 != 0) goto L_0x0073
        L_0x0080:
            if (r0 < r2) goto L_0x0083
            return
        L_0x0083:
            byte r13 = r1.get((int) r0)
            switch(r13) {
                case 34: goto L_0x009e;
                case 44: goto L_0x009b;
                case 59: goto L_0x009b;
                default: goto L_0x008a;
            }
        L_0x008a:
            byte r13 = r1.get((int) r0)
            boolean r13 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r13, r8)
            if (r13 != 0) goto L_0x00a9
            r14 = r0
            int r13 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((org.glassfish.grizzly.Buffer) r1, (int) r14, (int) r2, (boolean) r8)
            r0 = r13
            goto L_0x00ca
        L_0x009b:
            r13 = r11
            r14 = r11
            goto L_0x00ca
        L_0x009e:
            r5 = 1
            int r14 = r0 + 1
            int r13 = org.glassfish.grizzly.http.util.CookieUtils.getQuotedValueEndPosition((org.glassfish.grizzly.Buffer) r1, (int) r14, (int) r2)
            r0 = r13
            if (r0 < r2) goto L_0x00ca
            return
        L_0x00a9:
            java.util.logging.Logger r11 = LOGGER
            java.lang.String r12 = "Invalid cookie. Value not a token or quoted value"
            r11.fine(r12)
        L_0x00b0:
            if (r0 >= r2) goto L_0x00c1
            byte r11 = r1.get((int) r0)
            if (r11 == r10) goto L_0x00c1
            byte r11 = r1.get((int) r0)
            if (r11 == r7) goto L_0x00c1
            int r0 = r0 + 1
            goto L_0x00b0
        L_0x00c1:
            int r0 = r0 + 1
            r3 = 0
            r4 = 0
            goto L_0x002e
        L_0x00c7:
            r13 = r11
            r14 = r11
            r0 = r9
        L_0x00ca:
            if (r0 >= r2) goto L_0x00d9
            byte r15 = r1.get((int) r0)
            boolean r15 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r15)
            if (r15 == 0) goto L_0x00d9
            int r0 = r0 + 1
            goto L_0x00ca
        L_0x00d9:
            if (r0 >= r2) goto L_0x00ea
            byte r15 = r1.get((int) r0)
            if (r15 == r10) goto L_0x00ea
            byte r15 = r1.get((int) r0)
            if (r15 == r7) goto L_0x00ea
            int r0 = r0 + 1
            goto L_0x00d9
        L_0x00ea:
            int r7 = r0 + 1
            r0 = 0
            if (r3 == 0) goto L_0x01fc
            org.glassfish.grizzly.http.util.DataChunk r10 = r4.getDomain()
            boolean r10 = r10.isNull()
            if (r10 == 0) goto L_0x010a
            java.lang.String r10 = "Domain"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r10 == 0) goto L_0x010a
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getDomain()
            r0.setBuffer(r1, r14, r13)
            goto L_0x0232
        L_0x010a:
            org.glassfish.grizzly.http.util.DataChunk r10 = r4.getPath()
            boolean r10 = r10.isNull()
            if (r10 == 0) goto L_0x0125
            java.lang.String r10 = "Path"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r10 == 0) goto L_0x0125
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getPath()
            r0.setBuffer(r1, r14, r13)
            goto L_0x0232
        L_0x0125:
            java.lang.String r10 = "Version"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r10 == 0) goto L_0x0142
            if (r21 == 0) goto L_0x0131
            goto L_0x0232
        L_0x0131:
            byte r0 = r1.get((int) r14)
            r10 = 49
            if (r0 != r10) goto L_0x0232
            int r0 = r14 + 1
            if (r13 != r0) goto L_0x0232
            r3.setVersion(r12)
            goto L_0x0232
        L_0x0142:
            org.glassfish.grizzly.http.util.DataChunk r10 = r4.getComment()
            boolean r10 = r10.isNull()
            if (r10 == 0) goto L_0x015d
            java.lang.String r10 = "Comment"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r10 == 0) goto L_0x015d
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getComment()
            r0.setBuffer(r1, r14, r13)
            goto L_0x0232
        L_0x015d:
            int r10 = r3.getMaxAge()
            if (r10 != r11) goto L_0x0176
            java.lang.String r10 = "Max-Age"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r10 == 0) goto L_0x0176
            int r0 = r13 - r14
            int r0 = org.glassfish.grizzly.http.util.Ascii.parseInt((org.glassfish.grizzly.Buffer) r1, (int) r14, (int) r0)
            r3.setMaxAge(r0)
            goto L_0x0232
        L_0x0176:
            int r10 = r3.getVersion()
            if (r10 == 0) goto L_0x0182
            boolean r10 = r3.isVersionSet()
            if (r10 != 0) goto L_0x01cf
        L_0x0182:
            int r10 = r3.getMaxAge()
            if (r10 != r11) goto L_0x01cf
            java.lang.String r10 = "Expires"
            boolean r10 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r10, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r10 == 0) goto L_0x01cf
            int r10 = r13 + 1
            int r0 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((org.glassfish.grizzly.Buffer) r1, (int) r10, (int) r2, (boolean) r0)     // Catch:{ ParseException -> 0x01c9 }
            r13 = r0
            int r7 = r13 + 1
            java.nio.charset.Charset r0 = org.glassfish.grizzly.utils.Charsets.ASCII_CHARSET     // Catch:{ ParseException -> 0x01c5 }
            java.lang.String r0 = r1.toStringContent(r0, r14, r13)     // Catch:{ ParseException -> 0x01c5 }
            java.lang.ThreadLocal<java.text.SimpleDateFormat> r10 = org.glassfish.grizzly.http.util.CookieUtils.OLD_COOKIE_FORMAT     // Catch:{ ParseException -> 0x01c5 }
            java.lang.Object r10 = r10.get()     // Catch:{ ParseException -> 0x01c5 }
            java.text.SimpleDateFormat r10 = (java.text.SimpleDateFormat) r10     // Catch:{ ParseException -> 0x01c5 }
            java.util.Date r10 = r10.parse(r0)     // Catch:{ ParseException -> 0x01c5 }
            long r11 = r10.getTime()     // Catch:{ ParseException -> 0x01c5 }
            r15 = r7
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ ParseException -> 0x01c2 }
            int r7 = getMaxAgeDelta(r11, r7)     // Catch:{ ParseException -> 0x01c2 }
            int r7 = r7 / 1000
            r3.setMaxAge(r7)     // Catch:{ ParseException -> 0x01c2 }
            r8 = r20
            r0 = r15
            goto L_0x002e
        L_0x01c2:
            r0 = move-exception
            r0 = r15
            goto L_0x01cb
        L_0x01c5:
            r0 = move-exception
            r15 = r7
            r0 = r15
            goto L_0x01cb
        L_0x01c9:
            r0 = move-exception
            r0 = r7
        L_0x01cb:
            r8 = r20
            goto L_0x002e
        L_0x01cf:
            boolean r8 = r3.isSecure()
            if (r8 != 0) goto L_0x01e1
            java.lang.String r8 = "Secure"
            boolean r8 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r8, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r8 == 0) goto L_0x01e1
            r4.setSecure(r12)
            goto L_0x0232
        L_0x01e1:
            boolean r8 = r3.isHttpOnly()
            if (r8 != 0) goto L_0x01f3
            java.lang.String r8 = "HttpOnly"
            boolean r8 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r8, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r8 == 0) goto L_0x01f3
            r3.setHttpOnly(r12)
            goto L_0x0232
        L_0x01f3:
            java.lang.String r8 = "Discard"
            boolean r8 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r8, (org.glassfish.grizzly.Buffer) r1, (int) r6, (int) r9)
            if (r8 == 0) goto L_0x01fc
            goto L_0x0232
        L_0x01fc:
            org.glassfish.grizzly.http.Cookie r3 = r16.getNextUnusedCookie()
            if (r21 != 0) goto L_0x020b
            boolean r8 = r3.isVersionSet()
            if (r8 != 0) goto L_0x020b
            r3.setVersion(r0)
        L_0x020b:
            org.glassfish.grizzly.http.LazyCookieState r4 = r3.getLazyCookieState()
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getName()
            r0.setBuffer(r1, r6, r9)
            if (r14 == r11) goto L_0x0229
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getValue()
            r0.setBuffer(r1, r14, r13)
            if (r5 == 0) goto L_0x0232
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getValue()
            unescapeDoubleQuotes((org.glassfish.grizzly.http.util.DataChunk) r0)
            goto L_0x0232
        L_0x0229:
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.getValue()
            java.lang.String r8 = ""
            r0.setString(r8)
        L_0x0232:
            r8 = r20
            r0 = r7
            goto L_0x002e
        L_0x0237:
            return
        L_0x0238:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "buffer cannot be null."
            r0.<init>(r2)
            throw r0
        L_0x0240:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "cookies cannot be null."
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.CookieParserUtils.parseServerCookies(org.glassfish.grizzly.http.Cookies, org.glassfish.grizzly.Buffer, int, int, boolean, boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01ce, code lost:
        if (org.glassfish.grizzly.http.util.CookieUtils.equals("Discard", r1, r6, r8) != false) goto L_0x01d1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01e8  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0069 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bc A[LOOP:5: B:50:0x00b0->B:53:0x00bc, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00cd A[LOOP:6: B:54:0x00bf->B:59:0x00cd, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00d5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parseServerCookies(org.glassfish.grizzly.http.Cookies r16, java.lang.String r17, boolean r18, boolean r19) {
        /*
            r1 = r17
            r2 = r18
            if (r16 == 0) goto L_0x0209
            if (r1 == 0) goto L_0x0203
            int r0 = r17.length()
            if (r0 != 0) goto L_0x000f
            return
        L_0x000f:
            int r3 = r17.length()
            r0 = 0
            r4 = 0
        L_0x0015:
            if (r0 >= r3) goto L_0x0202
            r5 = 0
        L_0x0018:
            if (r0 >= r3) goto L_0x0031
            char r6 = r1.charAt(r0)
            boolean r6 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r6)
            if (r6 != 0) goto L_0x002e
            char r6 = r1.charAt(r0)
            boolean r6 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r6)
            if (r6 == 0) goto L_0x0031
        L_0x002e:
            int r0 = r0 + 1
            goto L_0x0018
        L_0x0031:
            if (r0 < r3) goto L_0x0034
            return
        L_0x0034:
            r6 = r0
            int r7 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((java.lang.String) r1, (int) r0, (int) r3)
            r8 = r7
            r0 = r7
        L_0x003b:
            if (r0 >= r3) goto L_0x004a
            char r7 = r1.charAt(r0)
            boolean r7 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r7)
            if (r7 == 0) goto L_0x004a
            int r0 = r0 + 1
            goto L_0x003b
        L_0x004a:
            r7 = 44
            r9 = 59
            r10 = -1
            r11 = 1
            if (r0 >= r3) goto L_0x00ad
            char r12 = r1.charAt(r0)
            r13 = 61
            if (r12 != r13) goto L_0x00ad
        L_0x005a:
            int r0 = r0 + r11
            if (r0 >= r3) goto L_0x0067
            char r12 = r1.charAt(r0)
            boolean r12 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r12)
            if (r12 != 0) goto L_0x005a
        L_0x0067:
            if (r0 < r3) goto L_0x006a
            return
        L_0x006a:
            char r12 = r1.charAt(r0)
            switch(r12) {
                case 34: goto L_0x0085;
                case 44: goto L_0x0082;
                case 59: goto L_0x0082;
                default: goto L_0x0071;
            }
        L_0x0071:
            char r12 = r1.charAt(r0)
            boolean r12 = org.glassfish.grizzly.http.util.CookieUtils.isSeparator(r12, r2)
            if (r12 != 0) goto L_0x0090
            r13 = r0
            int r12 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((java.lang.String) r1, (int) r13, (int) r3, (boolean) r2)
            r0 = r12
            goto L_0x00b0
        L_0x0082:
            r12 = r10
            r13 = r10
            goto L_0x00b0
        L_0x0085:
            r5 = 1
            int r13 = r0 + 1
            int r12 = org.glassfish.grizzly.http.util.CookieUtils.getQuotedValueEndPosition((java.lang.String) r1, (int) r13, (int) r3)
            r0 = r12
            if (r0 < r3) goto L_0x00b0
            return
        L_0x0090:
            java.util.logging.Logger r10 = LOGGER
            java.lang.String r11 = "Invalid cookie. Value not a token or quoted value"
            r10.fine(r11)
        L_0x0097:
            if (r0 >= r3) goto L_0x00a8
            char r10 = r1.charAt(r0)
            if (r10 == r9) goto L_0x00a8
            char r10 = r1.charAt(r0)
            if (r10 == r7) goto L_0x00a8
            int r0 = r0 + 1
            goto L_0x0097
        L_0x00a8:
            int r0 = r0 + 1
            r4 = 0
            goto L_0x0015
        L_0x00ad:
            r12 = r10
            r13 = r10
            r0 = r8
        L_0x00b0:
            if (r0 >= r3) goto L_0x00bf
            char r14 = r1.charAt(r0)
            boolean r14 = org.glassfish.grizzly.http.util.CookieUtils.isWhiteSpace(r14)
            if (r14 == 0) goto L_0x00bf
            int r0 = r0 + 1
            goto L_0x00b0
        L_0x00bf:
            if (r0 >= r3) goto L_0x00d0
            char r14 = r1.charAt(r0)
            if (r14 == r9) goto L_0x00d0
            char r14 = r1.charAt(r0)
            if (r14 == r7) goto L_0x00d0
            int r0 = r0 + 1
            goto L_0x00bf
        L_0x00d0:
            int r7 = r0 + 1
            r0 = 0
            if (r4 == 0) goto L_0x01d4
            java.lang.String r9 = r4.getDomain()
            if (r9 != 0) goto L_0x00ec
            java.lang.String r9 = "Domain"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x00ec
            java.lang.String r0 = r1.substring(r13, r12)
            r4.setDomain(r0)
            goto L_0x01d1
        L_0x00ec:
            java.lang.String r9 = r4.getPath()
            if (r9 != 0) goto L_0x0103
            java.lang.String r9 = "Path"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x0103
            java.lang.String r0 = r1.substring(r13, r12)
            r4.setPath(r0)
            goto L_0x01d1
        L_0x0103:
            java.lang.String r9 = "Version"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x0127
            if (r19 == 0) goto L_0x010f
            goto L_0x01d1
        L_0x010f:
            char r9 = r1.charAt(r13)
            r10 = 49
            if (r9 != r10) goto L_0x0120
            int r9 = r13 + 1
            if (r12 != r9) goto L_0x0120
            r4.setVersion(r11)
            goto L_0x01d1
        L_0x0120:
            if (r19 != 0) goto L_0x01d1
            r4.setVersion(r0)
            goto L_0x01d1
        L_0x0127:
            java.lang.String r9 = r4.getComment()
            if (r9 != 0) goto L_0x013e
            java.lang.String r9 = "Comment"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x013e
            java.lang.String r0 = r1.substring(r13, r12)
            r4.setComment(r0)
            goto L_0x01d1
        L_0x013e:
            int r9 = r4.getMaxAge()
            if (r9 != r10) goto L_0x0159
            java.lang.String r9 = "Max-Age"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x0159
            java.lang.String r0 = r1.substring(r13, r12)
            int r0 = java.lang.Integer.parseInt(r0)
            r4.setMaxAge(r0)
            goto L_0x01d1
        L_0x0159:
            int r9 = r4.getVersion()
            if (r9 == 0) goto L_0x0165
            boolean r9 = r4.isVersionSet()
            if (r9 == 0) goto L_0x01a4
        L_0x0165:
            int r9 = r4.getMaxAge()
            if (r9 != r10) goto L_0x01a4
            java.lang.String r9 = "Expires"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x01a4
            int r9 = r12 + 1
            int r0 = org.glassfish.grizzly.http.util.CookieUtils.getTokenEndPosition((java.lang.String) r1, (int) r9, (int) r3, (boolean) r0)     // Catch:{ ParseException -> 0x01a0 }
            r12 = r0
            int r7 = r12 + 1
            java.lang.String r0 = r1.substring(r13, r12)     // Catch:{ ParseException -> 0x01a0 }
            java.lang.ThreadLocal<java.text.SimpleDateFormat> r9 = org.glassfish.grizzly.http.util.CookieUtils.OLD_COOKIE_FORMAT     // Catch:{ ParseException -> 0x01a0 }
            java.lang.Object r9 = r9.get()     // Catch:{ ParseException -> 0x01a0 }
            java.text.SimpleDateFormat r9 = (java.text.SimpleDateFormat) r9     // Catch:{ ParseException -> 0x01a0 }
            java.util.Date r9 = r9.parse(r0)     // Catch:{ ParseException -> 0x01a0 }
            long r10 = r9.getTime()     // Catch:{ ParseException -> 0x01a0 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ ParseException -> 0x01a0 }
            int r10 = getMaxAgeDelta(r10, r14)     // Catch:{ ParseException -> 0x01a0 }
            int r10 = r10 / 1000
            r4.setMaxAge(r10)     // Catch:{ ParseException -> 0x01a0 }
            r0 = r7
            goto L_0x0015
        L_0x01a0:
            r0 = move-exception
            r0 = r7
            goto L_0x0015
        L_0x01a4:
            boolean r9 = r4.isSecure()
            if (r9 != 0) goto L_0x01b6
            java.lang.String r9 = "Secure"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equalsIgnoreCase((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x01b6
            r4.setSecure(r11)
            goto L_0x01d1
        L_0x01b6:
            boolean r9 = r4.isHttpOnly()
            if (r9 != 0) goto L_0x01c8
            java.lang.String r9 = "HttpOnly"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x01c8
            r4.setHttpOnly(r11)
            goto L_0x01d1
        L_0x01c8:
            java.lang.String r9 = "Discard"
            boolean r9 = org.glassfish.grizzly.http.util.CookieUtils.equals((java.lang.String) r9, (java.lang.String) r1, (int) r6, (int) r8)
            if (r9 == 0) goto L_0x01d4
        L_0x01d1:
            r0 = r7
            goto L_0x0015
        L_0x01d4:
            java.lang.String r9 = r1.substring(r6, r8)
            if (r13 == r10) goto L_0x01e8
            if (r5 == 0) goto L_0x01e3
            int r10 = r12 - r13
            java.lang.String r10 = unescapeDoubleQuotes((java.lang.String) r1, (int) r13, (int) r10)
            goto L_0x01ea
        L_0x01e3:
            java.lang.String r10 = r1.substring(r13, r12)
            goto L_0x01ea
        L_0x01e8:
            java.lang.String r10 = ""
        L_0x01ea:
            org.glassfish.grizzly.http.Cookie r4 = r16.getNextUnusedCookie()
            if (r19 != 0) goto L_0x01f9
            boolean r11 = r4.isVersionSet()
            if (r11 != 0) goto L_0x01f9
            r4.setVersion(r0)
        L_0x01f9:
            r4.setName(r9)
            r4.setValue(r10)
            r0 = r7
            goto L_0x0015
        L_0x0202:
            return
        L_0x0203:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        L_0x0209:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "cookies cannot be null."
            r0.<init>(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.CookieParserUtils.parseServerCookies(org.glassfish.grizzly.http.Cookies, java.lang.String, boolean, boolean):void");
    }

    /* renamed from: org.glassfish.grizzly.http.util.CookieParserUtils$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type;

        static {
            int[] iArr = new int[DataChunk.Type.values().length];
            $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type = iArr;
            try {
                iArr[DataChunk.Type.Bytes.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Buffer.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.String.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Chars.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static void unescapeDoubleQuotes(DataChunk dc) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dc.getType().ordinal()]) {
            case 1:
                unescapeDoubleQuotes(dc.getByteChunk());
                return;
            case 2:
                unescapeDoubleQuotes(dc.getBufferChunk());
                return;
            case 3:
                String s = dc.toString();
                dc.setString(unescapeDoubleQuotes(s, 0, s.length()));
                return;
            default:
                throw new NullPointerException();
        }
    }

    public static void unescapeDoubleQuotes(ByteChunk bc) {
        if (bc != null && bc.getLength() != 0) {
            int src = bc.getStart();
            int end = bc.getEnd();
            int dest = src;
            byte[] buffer = bc.getBuffer();
            while (src < end) {
                if (buffer[src] == 92 && src < end && buffer[src + 1] == 34) {
                    src++;
                }
                buffer[dest] = buffer[src];
                dest++;
                src++;
            }
            bc.setEnd(dest);
        }
    }

    public static void unescapeDoubleQuotes(BufferChunk bc) {
        if (bc != null && bc.getLength() != 0) {
            int src = bc.getStart();
            int end = bc.getEnd();
            int dest = src;
            Buffer buffer = bc.getBuffer();
            while (src < end) {
                if (buffer.get(src) == 92 && src < end && buffer.get(src + 1) == 34) {
                    src++;
                }
                buffer.put(dest, buffer.get(src));
                dest++;
                src++;
            }
            bc.setEnd(dest);
        }
    }

    public static void unescapeDoubleQuotes(CharChunk cc) {
        if (cc != null && cc.getLength() != 0) {
            int src = cc.getStart();
            int end = cc.getLimit();
            int dest = src;
            char[] buffer = cc.getBuffer();
            while (src < end) {
                if (buffer[src] == '\\' && src < end && buffer[src + 1] == '\"') {
                    src++;
                }
                buffer[dest] = buffer[src];
                dest++;
                src++;
            }
            cc.setLimit(dest);
        }
    }

    public static int unescapeDoubleQuotes(Buffer buffer, int start, int length) {
        if (buffer == null || length <= 0) {
            return length;
        }
        int src = start;
        int end = src + length;
        int dest = src;
        while (src < end) {
            if (buffer.get(src) == 92 && src < end && buffer.get(src + 1) == 34) {
                src++;
            }
            buffer.put(dest, buffer.get(src));
            dest++;
            src++;
        }
        return dest - start;
    }

    public static String unescapeDoubleQuotes(String s, int start, int length) {
        if (s == null || s.length() == 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        int src = start;
        int end = src + length;
        while (src < end) {
            if (s.charAt(src) == '\\' && src < end && s.charAt(src + 1) == '\"') {
                src++;
            }
            sb.append(s.charAt(src));
            src++;
        }
        return sb.toString();
    }

    private static int getMaxAgeDelta(long date1, long date2) {
        long result = date1 - date2;
        if (result <= 2147483647L) {
            return (int) result;
        }
        Logger logger = LOGGER;
        if (!logger.isLoggable(Level.FINE)) {
            return Integer.MAX_VALUE;
        }
        logger.fine("Integer overflow when calculating max age delta.  Date: " + date1 + ", current date: " + date2 + ".  Using Integer.MAX_VALUE for further calculation.");
        return Integer.MAX_VALUE;
    }
}
