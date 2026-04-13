package com.telink.ble.mesh.core.ble;

import android.os.ParcelUuid;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import java.util.Map;

public class MeshScanRecord {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a;
    @Nullable
    private final List<ParcelUuid> b;
    private final SparseArray<byte[]> c;
    private final Map<ParcelUuid, byte[]> d;
    private final int e;
    private final String f;
    private final byte[] g;

    @Nullable
    public byte[] b(ParcelUuid serviceDataUuid) {
        Map<ParcelUuid, byte[]> map;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serviceDataUuid}, this, changeQuickRedirect, false, 12416, new Class[]{ParcelUuid.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (serviceDataUuid == null || (map = this.d) == null) {
            return null;
        }
        return map.get(serviceDataUuid);
    }

    private MeshScanRecord(List<ParcelUuid> serviceUuids, SparseArray<byte[]> manufacturerData, Map<ParcelUuid, byte[]> serviceData, int advertiseFlags, int txPowerLevel, String localName, byte[] bytes) {
        this.b = serviceUuids;
        this.c = manufacturerData;
        this.d = serviceData;
        this.f = localName;
        this.a = advertiseFlags;
        this.e = txPowerLevel;
        this.g = bytes;
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x00eb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.telink.ble.mesh.core.ble.MeshScanRecord c(byte[] r18) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r18
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r2] = r0
            java.lang.Class<com.telink.ble.mesh.core.ble.MeshScanRecord> r7 = com.telink.ble.mesh.core.ble.MeshScanRecord.class
            r2 = 0
            r4 = 1
            r5 = 12417(0x3081, float:1.74E-41)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0021
            java.lang.Object r0 = r0.result
            com.telink.ble.mesh.core.ble.MeshScanRecord r0 = (com.telink.ble.mesh.core.ble.MeshScanRecord) r0
            return r0
        L_0x0021:
            r9 = r18
            if (r9 != 0) goto L_0x0027
            r0 = 0
            return r0
        L_0x0027:
            r0 = 0
            r1 = -1
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = 0
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            android.util.SparseArray r5 = new android.util.SparseArray
            r5.<init>()
            r10 = r5
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r11 = r5
            r12 = r1
            r13 = r3
            r14 = r4
            r1 = r0
        L_0x0041:
            int r0 = r9.length     // Catch:{ Exception -> 0x00db }
            if (r1 >= r0) goto L_0x00b9
            int r3 = r1 + 1
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x00b4 }
            r0 = r0 & 255(0xff, float:3.57E-43)
            if (r0 != 0) goto L_0x004f
            r15 = r3
            goto L_0x00ba
        L_0x004f:
            int r1 = r0 + -1
            int r4 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x00af }
            r3 = r3 & 255(0xff, float:3.57E-43)
            switch(r3) {
                case 1: goto L_0x00a8;
                case 2: goto L_0x00a3;
                case 3: goto L_0x00a3;
                case 4: goto L_0x009e;
                case 5: goto L_0x009e;
                case 6: goto L_0x0098;
                case 7: goto L_0x0098;
                case 8: goto L_0x008d;
                case 9: goto L_0x008d;
                case 10: goto L_0x0089;
                case 22: goto L_0x0074;
                case 255: goto L_0x005b;
                default: goto L_0x005a;
            }     // Catch:{ Exception -> 0x00af }
        L_0x005a:
            goto L_0x00ad
        L_0x005b:
            int r5 = r4 + 1
            byte r5 = r9[r5]     // Catch:{ Exception -> 0x00af }
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << 8
            byte r6 = r9[r4]     // Catch:{ Exception -> 0x00af }
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r5 = r5 + r6
            int r6 = r4 + 2
            int r7 = r1 + -2
            byte[] r6 = a(r9, r6, r7)     // Catch:{ Exception -> 0x00af }
            r10.put(r5, r6)     // Catch:{ Exception -> 0x00af }
            goto L_0x00ad
        L_0x0074:
            r5 = 2
            byte[] r6 = a(r9, r4, r5)     // Catch:{ Exception -> 0x00af }
            android.os.ParcelUuid r7 = com.telink.ble.mesh.core.ble.MeshBluetoothUUID.a(r6)     // Catch:{ Exception -> 0x00af }
            int r8 = r4 + r5
            int r15 = r1 - r5
            byte[] r8 = a(r9, r8, r15)     // Catch:{ Exception -> 0x00af }
            r11.put(r7, r8)     // Catch:{ Exception -> 0x00af }
            goto L_0x00ad
        L_0x0089:
            byte r5 = r9[r4]     // Catch:{ Exception -> 0x00af }
            r14 = r5
            goto L_0x00ad
        L_0x008d:
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x00af }
            byte[] r6 = a(r9, r4, r1)     // Catch:{ Exception -> 0x00af }
            r5.<init>(r6)     // Catch:{ Exception -> 0x00af }
            r13 = r5
            goto L_0x00ad
        L_0x0098:
            r5 = 16
            d(r9, r4, r1, r5, r2)     // Catch:{ Exception -> 0x00af }
            goto L_0x00ad
        L_0x009e:
            r5 = 4
            d(r9, r4, r1, r5, r2)     // Catch:{ Exception -> 0x00af }
            goto L_0x00ad
        L_0x00a3:
            r5 = 2
            d(r9, r4, r1, r5, r2)     // Catch:{ Exception -> 0x00af }
            goto L_0x00ad
        L_0x00a8:
            byte r5 = r9[r4]     // Catch:{ Exception -> 0x00af }
            r5 = r5 & 255(0xff, float:3.57E-43)
            r12 = r5
        L_0x00ad:
            int r1 = r1 + r4
            goto L_0x0041
        L_0x00af:
            r0 = move-exception
            r16 = r2
            r15 = r4
            goto L_0x00df
        L_0x00b4:
            r0 = move-exception
            r16 = r2
            r15 = r3
            goto L_0x00df
        L_0x00b9:
            r15 = r1
        L_0x00ba:
            boolean r0 = r2.isEmpty()     // Catch:{ Exception -> 0x00d7 }
            if (r0 == 0) goto L_0x00c4
            r2 = 0
            r16 = r2
            goto L_0x00c6
        L_0x00c4:
            r16 = r2
        L_0x00c6:
            com.telink.ble.mesh.core.ble.MeshScanRecord r0 = new com.telink.ble.mesh.core.ble.MeshScanRecord     // Catch:{ Exception -> 0x00d5 }
            r1 = r0
            r2 = r16
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r14
            r7 = r13
            r8 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x00d5 }
            return r0
        L_0x00d5:
            r0 = move-exception
            goto L_0x00df
        L_0x00d7:
            r0 = move-exception
            r16 = r2
            goto L_0x00df
        L_0x00db:
            r0 = move-exception
            r15 = r1
            r16 = r2
        L_0x00df:
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()
            com.telink.ble.mesh.foundation.MeshController$Mode r1 = r1.f()
            com.telink.ble.mesh.foundation.MeshController$Mode r2 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_AUTO_CONNECT
            if (r1 != r2) goto L_0x0109
            com.leedarson.serviceimpl.reporters.AutoConnectDeviceStepBean r1 = new com.leedarson.serviceimpl.reporters.AutoConnectDeviceStepBean
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "扫描到设备，解析异常:"
            r2.append(r3)
            java.lang.String r3 = r0.getMessage()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            com.leedarson.serviceimpl.reporters.c.c(r1)
        L_0x0109:
            com.telink.ble.mesh.core.ble.MeshScanRecord r17 = new com.telink.ble.mesh.core.ble.MeshScanRecord
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = -1
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = 0
            r1 = r17
            r8 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.ble.MeshScanRecord.c(byte[]):com.telink.ble.mesh.core.ble.MeshScanRecord");
    }

    private static int d(byte[] scanRecord, int currentPos, int dataLength, int uuidLength, List<ParcelUuid> serviceUuids) {
        Object[] objArr = {scanRecord, new Integer(currentPos), new Integer(dataLength), new Integer(uuidLength), serviceUuids};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12418, new Class[]{byte[].class, cls, cls, cls, List.class}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        while (dataLength > 0) {
            serviceUuids.add(MeshBluetoothUUID.a(a(scanRecord, currentPos, uuidLength)));
            dataLength -= uuidLength;
            currentPos += uuidLength;
        }
        return currentPos;
    }

    private static byte[] a(byte[] scanRecord, int start, int length) {
        Object[] objArr = {scanRecord, new Integer(start), new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12419, new Class[]{byte[].class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bytes = new byte[length];
        System.arraycopy(scanRecord, start, bytes, 0, length);
        return bytes;
    }
}
