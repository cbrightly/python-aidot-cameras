package com.leedarson.base.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Crc16Util */
public class h {
    private static final int[] a = {0, 40961};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] b(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 466, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int crc = a(data);
        return new byte[]{(byte) (crc & 255), (byte) ((crc >> 8) & 255)};
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(byte[] r8) {
        /*
            r0 = 0
            int r1 = r8.length
            r2 = 0
        L_0x0003:
            if (r2 >= r1) goto L_0x0020
            byte r3 = r8[r2]
            r4 = 0
        L_0x0008:
            r5 = 8
            if (r4 >= r5) goto L_0x001d
            int r5 = r0 >> 1
            int[] r6 = a
            r7 = r0 ^ r3
            r7 = r7 & 1
            r6 = r6[r7]
            r0 = r5 ^ r6
            int r3 = r3 >> 1
            int r4 = r4 + 1
            goto L_0x0008
        L_0x001d:
            int r2 = r2 + 1
            goto L_0x0003
        L_0x0020:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.utils.h.a(byte[]):int");
    }
}
