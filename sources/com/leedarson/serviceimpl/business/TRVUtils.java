package com.leedarson.serviceimpl.business;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class TRVUtils {
    private static final int[] POLY = {0, 40961};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] int2ByteArr(long u, int length) {
        byte[] arr = new byte[length];
        if (length == 2) {
            arr[0] = (byte) ((int) ((u >> 8) & 255));
            arr[1] = (byte) ((int) (u & 255));
        } else if (length == 4) {
            arr[0] = (byte) ((int) ((u >> 24) & 255));
            arr[1] = (byte) ((int) ((u >> 16) & 255));
            arr[2] = (byte) ((int) ((u >> 8) & 255));
            arr[3] = (byte) ((int) (u & 255));
        }
        return arr;
    }

    public static byte[] getVersionBytes(String newVersion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{newVersion}, (Object) null, changeQuickRedirect, true, 7101, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] res = new byte[3];
        try {
            String[] substring = newVersion.split("\\.");
            res[0] = Byte.parseByte(substring[0]);
            res[1] = Byte.parseByte(substring[1]);
            res[2] = Byte.parseByte(substring[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static byte createPkgFlag(int total, int index) {
        return (byte) (((byte) ((total & 15) << 4)) | ((byte) (index & 15)));
    }

    public static byte[] parsePkgFlag(byte flag) {
        return new byte[]{(byte) ((flag & 240) >> 4), (byte) (flag & 15)};
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getCRC16(byte[] r8) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r8
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r2] = r0
            java.lang.Class<byte[]> r7 = byte[].class
            r2 = 0
            r4 = 1
            r5 = 7102(0x1bbe, float:9.952E-42)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0021
            java.lang.Object r8 = r0.result
            byte[] r8 = (byte[]) r8
            return r8
        L_0x0021:
            r0 = 65535(0xffff, float:9.1834E-41)
            r1 = 40961(0xa001, float:5.7399E-41)
            r2 = 0
        L_0x0028:
            int r3 = r8.length
            if (r2 >= r3) goto L_0x0045
            byte r3 = r8[r2]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r0 = r0 ^ r3
            r3 = 0
        L_0x0031:
            r4 = 8
            if (r3 >= r4) goto L_0x0042
            r4 = r0 & 1
            if (r4 == 0) goto L_0x003d
            int r0 = r0 >> 1
            r0 = r0 ^ r1
            goto L_0x003f
        L_0x003d:
            int r0 = r0 >> 1
        L_0x003f:
            int r3 = r3 + 1
            goto L_0x0031
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x0028
        L_0x0045:
            java.lang.String r3 = java.lang.Integer.toHexString(r0)
            byte[] r3 = toBytes(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.TRVUtils.getCRC16(byte[]):byte[]");
    }

    public static byte[] getCRC16V2(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 7103, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int crc = toLittleHex(data);
        return new byte[]{(byte) (crc & 255), (byte) ((crc >> 8) & 255)};
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int toLittleHex(byte[] r8) {
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
            int[] r6 = POLY
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.TRVUtils.toLittleHex(byte[]):int");
    }

    public static byte[] toBytes(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 7104, new Class[]{String.class}, byte[].class);
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

    public static class CRC {
        private static final int BITS_OF_BYTE = 8;
        private static final int INITIAL_VALUE = 65535;
        private static final int POLYNOMIAL = 40961;
        public static ChangeQuickRedirect changeQuickRedirect;

        public static int crc16(int[] bytes) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 7105, new Class[]{int[].class}, Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            int res = 65535;
            for (int data : bytes) {
                res ^= data;
                for (int i = 0; i < 8; i++) {
                    res = (res & 1) == 1 ? (res >> 1) ^ POLYNOMIAL : res >> 1;
                }
            }
            return revert(res);
        }

        public static int revert(int src) {
            return ((65280 & src) >> 8) | ((src & 255) << 8);
        }
    }
}
