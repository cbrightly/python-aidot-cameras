package com.telink.ble.mesh.core;

import android.content.Context;
import android.os.ParcelUuid;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.MeshScanRecord;
import com.telink.ble.mesh.core.ble.UUIDInfo;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.util.SharedPreferenceHelper;

public final class MeshUtils {
    private static SecureRandom a;
    public static ChangeQuickRedirect changeQuickRedirect;

    private MeshUtils() {
    }

    public static byte[] g(int length) {
        Object[] objArr = {new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12112, new Class[]{Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] data = new byte[length];
        synchronized (MeshUtils.class) {
            if (a == null) {
                a = new SecureRandom();
            }
        }
        a.nextBytes(data);
        return data;
    }

    public static long j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 12113, new Class[0], Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : (Calendar.getInstance().getTimeInMillis() / 1000) - 946684800;
    }

    public static int a(int n) {
        return 1 << n;
    }

    public static int c(byte[] buffer, ByteOrder order) {
        int re = 0;
        int i = 4;
        if (buffer.length <= 4) {
            i = buffer.length;
        }
        int valLen = i;
        for (int i2 = 0; i2 < valLen; i2++) {
            if (order == ByteOrder.LITTLE_ENDIAN) {
                re |= (buffer[i2] & 255) << (i2 * 8);
            } else if (order == ByteOrder.BIG_ENDIAN) {
                re |= (buffer[i2] & 255) << (((valLen - i2) - 1) * 8);
            }
        }
        return re;
    }

    public static int b(byte[] buffer, int offset, int size, ByteOrder order) {
        Object[] objArr = {buffer, new Integer(offset), new Integer(size), order};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12115, new Class[]{byte[].class, cls, cls, ByteOrder.class}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int re = 0;
        int valLen = Math.min(4, size);
        for (int i = 0; i < valLen; i++) {
            if (order == ByteOrder.LITTLE_ENDIAN) {
                re |= (buffer[i + offset] & 255) << (i * 8);
            } else if (order == ByteOrder.BIG_ENDIAN) {
                re |= (buffer[i + offset] & 255) << (((valLen - i) - 1) * 8);
            }
        }
        return re;
    }

    public static long d(byte[] buffer, int offset, int size, ByteOrder order) {
        Object[] objArr = {buffer, new Integer(offset), new Integer(size), order};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12116, new Class[]{byte[].class, cls, cls, ByteOrder.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        long re = 0;
        int valLen = Math.min(8, size);
        for (int i = 0; i < valLen; i++) {
            if (order == ByteOrder.LITTLE_ENDIAN) {
                re |= (long) ((buffer[i + offset] & 255) << (i * 8));
            } else if (order == ByteOrder.BIG_ENDIAN) {
                re |= (long) ((buffer[i + offset] & 255) << (((valLen - i) - 1) * 8));
            }
        }
        return re;
    }

    public static byte[] l(int i, int size, ByteOrder order) {
        if (size > 4) {
            size = 4;
        }
        byte[] re = new byte[size];
        for (int j = 0; j < size; j++) {
            if (order == ByteOrder.LITTLE_ENDIAN) {
                re[j] = (byte) (i >> (j * 8));
            } else {
                re[(size - j) - 1] = (byte) (i >> (j * 8));
            }
        }
        return re;
    }

    public static String e(int color) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(color)}, (Object) null, changeQuickRedirect, true, 12117, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return String.format("#%06X", new Object[]{Integer.valueOf(color & -1)});
    }

    public static int k(String hex, ByteOrder order) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{hex, order}, (Object) null, changeQuickRedirect2, true, 12118, new Class[]{String.class, ByteOrder.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return c(e.g(hex), order);
    }

    public static byte[] q(int sequenceNumber) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(sequenceNumber)}, (Object) null, changeQuickRedirect, true, 12119, new Class[]{Integer.TYPE}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : l(sequenceNumber, 3, ByteOrder.BIG_ENDIAN);
    }

    public static byte f(byte[] key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, (Object) null, changeQuickRedirect, true, 12120, new Class[]{byte[].class}, Byte.TYPE);
        return proxy.isSupported ? ((Byte) proxy.result).byteValue() : Encipher.p(key);
    }

    public static int i(byte szmic) {
        return szmic == 0 ? 4 : 8;
    }

    public static boolean r(int address) {
        return (address & 65535) <= 32767 && (65535 & address) >= 1;
    }

    public static byte[] h(byte[] scanRecord, boolean unprovisioned) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{scanRecord, new Byte(unprovisioned ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 12123, new Class[]{byte[].class, Boolean.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        MeshScanRecord meshScanRecord = MeshScanRecord.c(scanRecord);
        if (meshScanRecord == null) {
            return null;
        }
        return meshScanRecord.b(ParcelUuid.fromString((unprovisioned ? UUIDInfo.d : UUIDInfo.g).toString()));
    }

    public static List<Integer> o(@NonNull byte[] params, int offset) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{params, new Integer(offset)}, (Object) null, changeQuickRedirect, true, 12124, new Class[]{byte[].class, Integer.TYPE}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<Integer> missingChunks = new ArrayList<>();
        int basePosition = 0;
        while (offset < params.length) {
            byte val = params[offset];
            for (int i = 0; i < 8; i++) {
                if (((val >> i) & 1) == 1) {
                    missingChunks.add(Integer.valueOf(basePosition + i));
                }
            }
            offset++;
            basePosition += 8;
        }
        return missingChunks;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v1, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.Byte, com.telink.ble.mesh.core.MeshUtils.AdvertiseDataUnit> n(byte[] r11) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r2 = byte[].class
            r6[r8] = r2
            java.lang.Class<java.util.Map> r7 = java.util.Map.class
            r2 = 0
            r4 = 1
            r5 = 12127(0x2f5f, float:1.6994E-41)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0021
            java.lang.Object r11 = r1.result
            java.util.Map r11 = (java.util.Map) r11
            return r11
        L_0x0021:
            java.util.concurrent.ConcurrentHashMap r1 = new java.util.concurrent.ConcurrentHashMap
            r1.<init>()
            r2 = 0
            byte r3 = r11[r2]
        L_0x0029:
            if (r3 <= 0) goto L_0x005c
            int r4 = r3 + 1
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0057 }
            int r5 = r4.length     // Catch:{ Exception -> 0x0057 }
            java.lang.System.arraycopy(r11, r2, r4, r8, r5)     // Catch:{ Exception -> 0x0057 }
            com.telink.ble.mesh.core.MeshUtils$AdvertiseDataUnit r5 = new com.telink.ble.mesh.core.MeshUtils$AdvertiseDataUnit     // Catch:{ Exception -> 0x0057 }
            byte r6 = r4[r8]     // Catch:{ Exception -> 0x0057 }
            byte r7 = r4[r0]     // Catch:{ Exception -> 0x0057 }
            r9 = 2
            int r10 = r4.length     // Catch:{ Exception -> 0x0057 }
            byte[] r9 = java.util.Arrays.copyOfRange(r4, r9, r10)     // Catch:{ Exception -> 0x0057 }
            r5.<init>(r6, r7, r9)     // Catch:{ Exception -> 0x0057 }
            byte r6 = r5.b     // Catch:{ Exception -> 0x0057 }
            java.lang.Byte r6 = java.lang.Byte.valueOf(r6)     // Catch:{ Exception -> 0x0057 }
            r1.put(r6, r5)     // Catch:{ Exception -> 0x0057 }
            int r6 = r2 + r3
            int r2 = r6 + 1
            int r6 = r11.length     // Catch:{ Exception -> 0x0057 }
            if (r2 < r6) goto L_0x0053
            goto L_0x005c
        L_0x0053:
            byte r6 = r11[r2]     // Catch:{ Exception -> 0x0057 }
            r3 = r6
            goto L_0x0029
        L_0x0057:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x005d
        L_0x005c:
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.MeshUtils.n(byte[]):java.util.Map");
    }

    public static class AdvertiseDataUnit {
        public byte a;
        public byte b;
        public byte[] c;

        public AdvertiseDataUnit(byte dataUnitSize, byte dataUnitType, byte[] adData) {
            this.a = dataUnitSize;
            this.b = dataUnitType;
            this.c = adData;
        }
    }

    public static byte[] p(String oobHex) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{oobHex}, (Object) null, changeQuickRedirect, true, 12128, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (TextUtils.isEmpty(oobHex) || oobHex.length() != 32) {
            return null;
        }
        byte[] oob = new byte[16];
        for (int i = 0; i < oob.length; i++) {
            oob[i] = (byte) Integer.parseInt(oobHex.substring(i * 2, (i * 2) + 2), 16);
        }
        return oob;
    }

    public static boolean m(Context context, String houseId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, houseId}, (Object) null, changeQuickRedirect, true, 12129, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (houseId == null) {
            return false;
        }
        String currentHouseId = SharedPreferenceHelper.getHouseId(context);
        boolean same = houseId.equals(currentHouseId);
        if (!same) {
            MeshLog.e("业务处理的houseid与当前的houseid不一致,request houseid:" + houseId + ",currentHouseId:" + currentHouseId);
        } else {
            MeshLog.e("业务处理的houseid与当前的houseid一致");
        }
        return same;
    }
}
