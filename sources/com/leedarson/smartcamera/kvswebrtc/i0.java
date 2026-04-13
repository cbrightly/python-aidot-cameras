package com.leedarson.smartcamera.kvswebrtc;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: RadarDataParse */
public class i0 {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX WARNING: Removed duplicated region for block: B:163:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(byte[] r37, com.leedarson.smartcamera.listener.g r38) {
        /*
            r36 = this;
            java.lang.String r1 = "RadarLog"
            r0 = 2
            java.lang.Object[] r2 = new java.lang.Object[r0]
            r9 = 0
            r2[r9] = r37
            r10 = 1
            r2[r10] = r38
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r3 = byte[].class
            r7[r9] = r3
            java.lang.Class<com.leedarson.smartcamera.listener.g> r3 = com.leedarson.smartcamera.listener.g.class
            r7[r10] = r3
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 9923(0x26c3, float:1.3905E-41)
            r3 = r36
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0027
            return
        L_0x0027:
            r2 = r36
            r3 = r38
            r4 = r37
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r11 = 0
            if (r3 == 0) goto L_0x02a2
        L_0x003c:
            int r8 = r4.length     // Catch:{ Exception -> 0x0269, all -> 0x0261 }
            if (r6 >= r8) goto L_0x025a
            r8 = 8
            byte[] r13 = new byte[r8]     // Catch:{ Exception -> 0x0269, all -> 0x0261 }
            r14 = 0
        L_0x0044:
            if (r14 >= r8) goto L_0x0061
            byte r15 = r4[r6]     // Catch:{ Exception -> 0x0058, all -> 0x004f }
            r13[r14] = r15     // Catch:{ Exception -> 0x0058, all -> 0x004f }
            int r6 = r6 + 1
            int r14 = r14 + 1
            goto L_0x0044
        L_0x004f:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            goto L_0x029e
        L_0x0058:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            goto L_0x0270
        L_0x0061:
            long r14 = com.tutk.IOTC.ByteUtil.byte2long(r13)     // Catch:{ Exception -> 0x0269, all -> 0x0261 }
            r11 = r14
            r14 = 4
            byte[] r15 = new byte[r14]     // Catch:{ Exception -> 0x0269, all -> 0x0261 }
            int r16 = r6 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x0250, all -> 0x0246 }
            r15[r9] = r6     // Catch:{ Exception -> 0x0250, all -> 0x0246 }
            int r6 = r16 + 1
            byte r16 = r4[r16]     // Catch:{ Exception -> 0x0269, all -> 0x0261 }
            r15[r10] = r16     // Catch:{ Exception -> 0x0269, all -> 0x0261 }
            int r16 = r6 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x0250, all -> 0x0246 }
            r15[r0] = r6     // Catch:{ Exception -> 0x0250, all -> 0x0246 }
            r6 = 3
            int r17 = r16 + 1
            byte r16 = r4[r16]     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            r15[r6] = r16     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            int r6 = com.tutk.IOTC.ByteUtil.byte2int(r15)     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            r15 = 0
            r16 = 0
            r10 = r16
        L_0x008b:
            if (r10 >= r6) goto L_0x0221
            byte[] r9 = new byte[r8]     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            r16 = 0
            r0 = r16
        L_0x0093:
            if (r0 >= r8) goto L_0x00b6
            int r16 = r17 + 1
            byte r17 = r4[r17]     // Catch:{ Exception -> 0x00ab, all -> 0x00a0 }
            r9[r0] = r17     // Catch:{ Exception -> 0x00ab, all -> 0x00a0 }
            int r0 = r0 + 1
            r17 = r16
            goto L_0x0093
        L_0x00a0:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            r6 = r16
            goto L_0x029e
        L_0x00ab:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            r6 = r16
            goto L_0x0270
        L_0x00b6:
            long r22 = com.tutk.IOTC.ByteUtil.byte2long(r9)     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            byte[] r0 = new byte[r14]     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            r16 = 0
            r8 = r16
        L_0x00c0:
            if (r8 >= r14) goto L_0x00cd
            int r16 = r17 + 1
            byte r17 = r4[r17]     // Catch:{ Exception -> 0x00ab, all -> 0x00a0 }
            r0[r8] = r17     // Catch:{ Exception -> 0x00ab, all -> 0x00a0 }
            int r8 = r8 + 1
            r17 = r16
            goto L_0x00c0
        L_0x00cd:
            int r8 = com.tutk.IOTC.ByteUtil.byte2int(r0)     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            r16 = 0
            r14 = r16
        L_0x00d5:
            if (r14 >= r8) goto L_0x020b
            int r16 = r17 + 1
            byte r24 = r4[r17]     // Catch:{ Exception -> 0x0250, all -> 0x0246 }
            int r17 = r16 + 1
            byte r16 = r4[r16]     // Catch:{ Exception -> 0x023c, all -> 0x0231 }
            r31 = r0
            r32 = r2
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            int r21 = r17 + 1
            byte r0 = r4[r17]     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            r17 = 0
            r2[r17] = r0     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            int r17 = r21 + 1
            byte r0 = r4[r21]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r18 = 1
            r2[r18] = r0     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            short r25 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            int r21 = r17 + 1
            byte r0 = r4[r17]     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            r17 = 0
            r2[r17] = r0     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            int r17 = r21 + 1
            byte r0 = r4[r21]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r18 = 1
            r2[r18] = r0     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            short r26 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            int r21 = r17 + 1
            byte r0 = r4[r17]     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            r17 = 0
            r2[r17] = r0     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            int r17 = r21 + 1
            byte r0 = r4[r21]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r18 = 1
            r2[r18] = r0     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            short r27 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            int r21 = r17 + 1
            byte r0 = r4[r17]     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            r17 = 0
            r2[r17] = r0     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            int r17 = r21 + 1
            byte r0 = r4[r21]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r18 = 1
            r2[r18] = r0     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            short r28 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            int r21 = r17 + 1
            byte r0 = r4[r17]     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            r17 = 0
            r2[r17] = r0     // Catch:{ Exception -> 0x01f0, all -> 0x01e7 }
            int r17 = r21 + 1
            byte r0 = r4[r21]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r18 = 1
            r2[r18] = r0     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            short r29 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            int r20 = r17 + 1
            byte r17 = r4[r17]     // Catch:{ Exception -> 0x01de, all -> 0x01d5 }
            r19 = 0
            r2[r19] = r17     // Catch:{ Exception -> 0x01de, all -> 0x01d5 }
            int r17 = r20 + 1
            byte r20 = r4[r20]     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r18 = 1
            r2[r18] = r20     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            short r30 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            com.leedarson.smartcamera.kvswebrtc.beans.a r2 = new com.leedarson.smartcamera.kvswebrtc.beans.a     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r21 = r2
            r21.<init>(r22, r24, r25, r26, r27, r28, r29, r30)     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            float r0 = r2.f     // Catch:{ Exception -> 0x0202, all -> 0x01f9 }
            r20 = 1157234688(0x44fa0000, float:2000.0)
            int r20 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r20 == 0) goto L_0x01bf
            r20 = -990248960(0xffffffffc4fa0000, float:-2000.0)
            int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r0 != 0) goto L_0x018a
            r33 = r4
            r34 = r5
            r35 = r8
            r0 = r9
            goto L_0x01c6
        L_0x018a:
            if (r15 == 0) goto L_0x01a9
            r33 = r4
            r34 = r5
            long r4 = r15.a     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            r35 = r8
            r0 = r9
            long r8 = r2.a     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x01b0
            timber.log.a$b r4 = timber.log.a.g(r1)     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            java.lang.String r5 = "卡存运存解析，新点时间戳小于上个点时间戳，抛弃"
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            r4.m(r5, r9)     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            goto L_0x01c6
        L_0x01a9:
            r33 = r4
            r34 = r5
            r35 = r8
            r0 = r9
        L_0x01b0:
            r15 = r2
            r7.add(r2)     // Catch:{ Exception -> 0x01ba, all -> 0x01b5 }
            goto L_0x01c6
        L_0x01b5:
            r0 = move-exception
            r6 = r17
            goto L_0x029e
        L_0x01ba:
            r0 = move-exception
            r6 = r17
            goto L_0x0270
        L_0x01bf:
            r33 = r4
            r34 = r5
            r35 = r8
            r0 = r9
        L_0x01c6:
            int r14 = r14 + 1
            r9 = r0
            r0 = r31
            r2 = r32
            r4 = r33
            r5 = r34
            r8 = r35
            goto L_0x00d5
        L_0x01d5:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r6 = r20
            goto L_0x029e
        L_0x01de:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r6 = r20
            goto L_0x0270
        L_0x01e7:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r6 = r21
            goto L_0x029e
        L_0x01f0:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r6 = r21
            goto L_0x0270
        L_0x01f9:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r6 = r17
            goto L_0x029e
        L_0x0202:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r6 = r17
            goto L_0x0270
        L_0x020b:
            r31 = r0
            r32 = r2
            r33 = r4
            r34 = r5
            r35 = r8
            r0 = r9
            r18 = 1
            int r10 = r10 + 1
            r0 = 2
            r8 = 8
            r9 = 0
            r14 = 4
            goto L_0x008b
        L_0x0221:
            r32 = r2
            r33 = r4
            r34 = r5
            r18 = 1
            r6 = r17
            r10 = r18
            r0 = 2
            r9 = 0
            goto L_0x003c
        L_0x0231:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            r6 = r17
            goto L_0x029e
        L_0x023c:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            r6 = r17
            goto L_0x0270
        L_0x0246:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            r6 = r16
            goto L_0x029e
        L_0x0250:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            r6 = r16
            goto L_0x0270
        L_0x025a:
            r32 = r2
            r33 = r4
            r34 = r5
            goto L_0x02a8
        L_0x0261:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
            goto L_0x029e
        L_0x0269:
            r0 = move-exception
            r32 = r2
            r33 = r4
            r34 = r5
        L_0x0270:
            timber.log.a$b r1 = timber.log.a.g(r1)     // Catch:{ all -> 0x029d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x029d }
            r2.<init>()     // Catch:{ all -> 0x029d }
            java.lang.String r4 = "雷达数据解析exception position:"
            r2.append(r4)     // Catch:{ all -> 0x029d }
            r2.append(r6)     // Catch:{ all -> 0x029d }
            java.lang.String r4 = ","
            r2.append(r4)     // Catch:{ all -> 0x029d }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x029d }
            r2.append(r4)     // Catch:{ all -> 0x029d }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x029d }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x029d }
            r1.m(r2, r4)     // Catch:{ all -> 0x029d }
        L_0x0299:
            r3.a(r11, r7)
            goto L_0x02ab
        L_0x029d:
            r0 = move-exception
        L_0x029e:
            r3.a(r11, r7)
            throw r0
        L_0x02a2:
            r32 = r2
            r33 = r4
            r34 = r5
        L_0x02a8:
            if (r3 == 0) goto L_0x02ab
            goto L_0x0299
        L_0x02ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.i0.b(byte[], com.leedarson.smartcamera.listener.g):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:206:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(byte[] r41, com.leedarson.smartcamera.listener.g r42) {
        /*
            r40 = this;
            java.lang.String r1 = "RadarLog"
            r0 = 2
            java.lang.Object[] r2 = new java.lang.Object[r0]
            r9 = 0
            r2[r9] = r41
            r10 = 1
            r2[r10] = r42
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r3 = byte[].class
            r7[r9] = r3
            java.lang.Class<com.leedarson.smartcamera.listener.g> r3 = com.leedarson.smartcamera.listener.g.class
            r7[r10] = r3
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 9924(0x26c4, float:1.3906E-41)
            r3 = r40
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0027
            return
        L_0x0027:
            r2 = r40
            r3 = r42
            r4 = r41
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r11 = 0
            if (r3 == 0) goto L_0x039b
        L_0x003c:
            int r8 = r4.length     // Catch:{ Exception -> 0x035c, all -> 0x0353 }
            if (r6 >= r8) goto L_0x0322
            r8 = r6
            r13 = 4
            byte[] r14 = new byte[r13]     // Catch:{ Exception -> 0x035c, all -> 0x0353 }
            r15 = 0
        L_0x0044:
            if (r15 >= r13) goto L_0x006a
            int r16 = r6 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x005d, all -> 0x0051 }
            r14[r15] = r6     // Catch:{ Exception -> 0x005d, all -> 0x0051 }
            int r15 = r15 + 1
            r6 = r16
            goto L_0x0044
        L_0x0051:
            r0 = move-exception
            r21 = r2
            r1 = r3
            r38 = r4
            r42 = r5
            r6 = r16
            goto L_0x0397
        L_0x005d:
            r0 = move-exception
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            r6 = r16
            goto L_0x0365
        L_0x006a:
            int r15 = com.tutk.IOTC.ByteUtil.byte2int(r14)     // Catch:{ Exception -> 0x035c, all -> 0x0353 }
            r0 = 8
            byte[] r10 = new byte[r0]     // Catch:{ Exception -> 0x035c, all -> 0x0353 }
            r18 = 0
            r9 = r18
        L_0x0076:
            if (r9 >= r0) goto L_0x009c
            int r19 = r6 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x008f, all -> 0x0083 }
            r10[r9] = r6     // Catch:{ Exception -> 0x008f, all -> 0x0083 }
            int r9 = r9 + 1
            r6 = r19
            goto L_0x0076
        L_0x0083:
            r0 = move-exception
            r21 = r2
            r1 = r3
            r38 = r4
            r42 = r5
            r6 = r19
            goto L_0x0397
        L_0x008f:
            r0 = move-exception
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            r6 = r19
            goto L_0x0365
        L_0x009c:
            long r19 = com.tutk.IOTC.ByteUtil.byte2long(r10)     // Catch:{ Exception -> 0x035c, all -> 0x0353 }
            r11 = r19
            byte[] r9 = new byte[r13]     // Catch:{ Exception -> 0x0316, all -> 0x030a }
            int r19 = r6 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x02fb, all -> 0x02ed }
            r18 = 0
            r9[r18] = r6     // Catch:{ Exception -> 0x02fb, all -> 0x02ed }
            int r6 = r19 + 1
            byte r19 = r4[r19]     // Catch:{ Exception -> 0x0316, all -> 0x030a }
            r17 = 1
            r9[r17] = r19     // Catch:{ Exception -> 0x0316, all -> 0x030a }
            int r19 = r6 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x02fb, all -> 0x02ed }
            r16 = 2
            r9[r16] = r6     // Catch:{ Exception -> 0x02fb, all -> 0x02ed }
            r6 = 3
            int r20 = r19 + 1
            byte r19 = r4[r19]     // Catch:{ Exception -> 0x02de, all -> 0x02d0 }
            r9[r6] = r19     // Catch:{ Exception -> 0x02de, all -> 0x02d0 }
            int r6 = com.tutk.IOTC.ByteUtil.byte2int(r9)     // Catch:{ Exception -> 0x02de, all -> 0x02d0 }
            r9 = 0
            r19 = 0
            r13 = r19
        L_0x00cc:
            if (r13 >= r6) goto L_0x02b4
            r21 = r2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x02a7, all -> 0x029b }
            r19 = 0
            r42 = r5
            r5 = r19
        L_0x00d8:
            if (r5 >= r0) goto L_0x00f6
            int r19 = r20 + 1
            byte r20 = r4[r20]     // Catch:{ Exception -> 0x00ed, all -> 0x00e5 }
            r2[r5] = r20     // Catch:{ Exception -> 0x00ed, all -> 0x00e5 }
            int r5 = r5 + 1
            r20 = r19
            goto L_0x00d8
        L_0x00e5:
            r0 = move-exception
            r1 = r3
            r38 = r4
            r6 = r19
            goto L_0x0397
        L_0x00ed:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r6 = r19
            goto L_0x0365
        L_0x00f6:
            long r23 = com.tutk.IOTC.ByteUtil.byte2long(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r5 = 4
            byte[] r0 = new byte[r5]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r19 = 0
            r39 = r19
            r19 = r2
            r2 = r39
        L_0x0105:
            if (r2 >= r5) goto L_0x0122
            int r5 = r20 + 1
            byte r20 = r4[r20]     // Catch:{ Exception -> 0x011a, all -> 0x0113 }
            r0[r2] = r20     // Catch:{ Exception -> 0x011a, all -> 0x0113 }
            int r2 = r2 + 1
            r20 = r5
            r5 = 4
            goto L_0x0105
        L_0x0113:
            r0 = move-exception
            r1 = r3
            r38 = r4
            r6 = r5
            goto L_0x0397
        L_0x011a:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r6 = r5
            goto L_0x0365
        L_0x0122:
            int r2 = com.tutk.IOTC.ByteUtil.byte2int(r0)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r5 = 0
        L_0x0127:
            if (r5 >= r2) goto L_0x026f
            int r22 = r20 + 1
            byte r25 = r4[r20]     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            int r20 = r22 + 1
            byte r22 = r4[r22]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r32 = r22
            r33 = r0
            r34 = r2
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            int r22 = r20 + 1
            byte r0 = r4[r20]     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            r18 = 0
            r2[r18] = r0     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            int r20 = r22 + 1
            byte r0 = r4[r22]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r17 = 1
            r2[r17] = r0     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            short r26 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            int r22 = r20 + 1
            byte r0 = r4[r20]     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            r18 = 0
            r2[r18] = r0     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            int r20 = r22 + 1
            byte r0 = r4[r22]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r17 = 1
            r2[r17] = r0     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            short r27 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            int r22 = r20 + 1
            byte r0 = r4[r20]     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            r18 = 0
            r2[r18] = r0     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            int r20 = r22 + 1
            byte r0 = r4[r22]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r17 = 1
            r2[r17] = r0     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            short r28 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            int r22 = r20 + 1
            byte r0 = r4[r20]     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            r18 = 0
            r2[r18] = r0     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            int r20 = r22 + 1
            byte r0 = r4[r22]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r17 = 1
            r2[r17] = r0     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            short r29 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            int r22 = r20 + 1
            byte r0 = r4[r20]     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            r18 = 0
            r2[r18] = r0     // Catch:{ Exception -> 0x0264, all -> 0x025a }
            int r20 = r22 + 1
            byte r0 = r4[r22]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r17 = 1
            r2[r17] = r0     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            short r30 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r0 = 2
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            int r16 = r20 + 1
            byte r20 = r4[r20]     // Catch:{ Exception -> 0x024f, all -> 0x0245 }
            r18 = 0
            r2[r18] = r20     // Catch:{ Exception -> 0x024f, all -> 0x0245 }
            int r20 = r16 + 1
            byte r16 = r4[r16]     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r17 = 1
            r2[r17] = r16     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            short r31 = com.tutk.IOTC.ByteUtil.byteToShort(r2)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            com.leedarson.smartcamera.kvswebrtc.beans.a r2 = new com.leedarson.smartcamera.kvswebrtc.beans.a     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r22 = r2
            r22.<init>(r23, r25, r26, r27, r28, r29, r30, r31)     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            float r0 = r2.f     // Catch:{ Exception -> 0x0290, all -> 0x0286 }
            r16 = 1157234688(0x44fa0000, float:2000.0)
            int r16 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r16 == 0) goto L_0x022f
            r16 = -990248960(0xffffffffc4fa0000, float:-2000.0)
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 != 0) goto L_0x01df
            r37 = r3
            r38 = r4
            r0 = r10
            r35 = r11
            goto L_0x0236
        L_0x01df:
            if (r9 == 0) goto L_0x0213
            r0 = r10
            r35 = r11
            long r10 = r9.a     // Catch:{ Exception -> 0x0208, all -> 0x01fe }
            r37 = r3
            r38 = r4
            long r3 = r2.a     // Catch:{ Exception -> 0x0228, all -> 0x021f }
            int r3 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x021a
            timber.log.a$b r3 = timber.log.a.g(r1)     // Catch:{ Exception -> 0x0228, all -> 0x021f }
            java.lang.String r4 = "卡存运存解析，新点时间戳小于上个点时间戳，抛弃"
            r10 = 0
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x0228, all -> 0x021f }
            r3.m(r4, r11)     // Catch:{ Exception -> 0x0228, all -> 0x021f }
            goto L_0x0236
        L_0x01fe:
            r0 = move-exception
            r38 = r4
            r1 = r3
            r6 = r20
            r11 = r35
            goto L_0x0397
        L_0x0208:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r6 = r20
            r11 = r35
            goto L_0x0365
        L_0x0213:
            r37 = r3
            r38 = r4
            r0 = r10
            r35 = r11
        L_0x021a:
            r9 = r2
            r7.add(r2)     // Catch:{ Exception -> 0x0228, all -> 0x021f }
            goto L_0x0236
        L_0x021f:
            r0 = move-exception
            r6 = r20
            r11 = r35
            r1 = r37
            goto L_0x0397
        L_0x0228:
            r0 = move-exception
            r6 = r20
            r11 = r35
            goto L_0x0365
        L_0x022f:
            r37 = r3
            r38 = r4
            r0 = r10
            r35 = r11
        L_0x0236:
            int r5 = r5 + 1
            r10 = r0
            r0 = r33
            r2 = r34
            r11 = r35
            r3 = r37
            r4 = r38
            goto L_0x0127
        L_0x0245:
            r0 = move-exception
            r38 = r4
            r35 = r11
            r1 = r3
            r6 = r16
            goto L_0x0397
        L_0x024f:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r35 = r11
            r6 = r16
            goto L_0x0365
        L_0x025a:
            r0 = move-exception
            r38 = r4
            r35 = r11
            r1 = r3
            r6 = r22
            goto L_0x0397
        L_0x0264:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r35 = r11
            r6 = r22
            goto L_0x0365
        L_0x026f:
            r33 = r0
            r34 = r2
            r37 = r3
            r38 = r4
            r0 = r10
            r35 = r11
            r17 = 1
            int r13 = r13 + 1
            r5 = r42
            r2 = r21
            r0 = 8
            goto L_0x00cc
        L_0x0286:
            r0 = move-exception
            r38 = r4
            r35 = r11
            r1 = r3
            r6 = r20
            goto L_0x0397
        L_0x0290:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r35 = r11
            r6 = r20
            goto L_0x0365
        L_0x029b:
            r0 = move-exception
            r38 = r4
            r42 = r5
            r35 = r11
            r1 = r3
            r6 = r20
            goto L_0x0397
        L_0x02a7:
            r0 = move-exception
            r37 = r3
            r38 = r4
            r42 = r5
            r35 = r11
            r6 = r20
            goto L_0x0365
        L_0x02b4:
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            r0 = r10
            r35 = r11
            r17 = 1
            int r2 = r15 + r8
            r3 = 4
            int r6 = r2 + 4
            r10 = r17
            r2 = r21
            r3 = r37
            r0 = 2
            r9 = 0
            goto L_0x003c
        L_0x02d0:
            r0 = move-exception
            r21 = r2
            r38 = r4
            r42 = r5
            r35 = r11
            r1 = r3
            r6 = r20
            goto L_0x0397
        L_0x02de:
            r0 = move-exception
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            r35 = r11
            r6 = r20
            goto L_0x0365
        L_0x02ed:
            r0 = move-exception
            r21 = r2
            r38 = r4
            r42 = r5
            r35 = r11
            r1 = r3
            r6 = r19
            goto L_0x0397
        L_0x02fb:
            r0 = move-exception
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            r35 = r11
            r6 = r19
            goto L_0x0365
        L_0x030a:
            r0 = move-exception
            r21 = r2
            r38 = r4
            r42 = r5
            r35 = r11
            r1 = r3
            goto L_0x0397
        L_0x0316:
            r0 = move-exception
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            r35 = r11
            goto L_0x0365
        L_0x0322:
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
            timber.log.a$b r0 = timber.log.a.g(r1)     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            r2.<init>()     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            java.lang.String r3 = "雷达数据解析完毕,雷达点个数: data.size:"
            r2.append(r3)     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            int r3 = r7.size()     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            r2.append(r3)     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            r0.m(r2, r4)     // Catch:{ Exception -> 0x0351, all -> 0x034d }
            r1 = r37
            goto L_0x03a2
        L_0x034d:
            r0 = move-exception
            r1 = r37
            goto L_0x0397
        L_0x0351:
            r0 = move-exception
            goto L_0x0365
        L_0x0353:
            r0 = move-exception
            r21 = r2
            r38 = r4
            r42 = r5
            r1 = r3
            goto L_0x0397
        L_0x035c:
            r0 = move-exception
            r21 = r2
            r37 = r3
            r38 = r4
            r42 = r5
        L_0x0365:
            timber.log.a$b r1 = timber.log.a.g(r1)     // Catch:{ all -> 0x0394 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0394 }
            r2.<init>()     // Catch:{ all -> 0x0394 }
            java.lang.String r3 = "雷达数据解析exception position:"
            r2.append(r3)     // Catch:{ all -> 0x0394 }
            r2.append(r6)     // Catch:{ all -> 0x0394 }
            java.lang.String r3 = ","
            r2.append(r3)     // Catch:{ all -> 0x0394 }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x0394 }
            r2.append(r3)     // Catch:{ all -> 0x0394 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0394 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0394 }
            r1.m(r2, r3)     // Catch:{ all -> 0x0394 }
            r1 = r37
        L_0x0390:
            r1.a(r11, r7)
            goto L_0x03a5
        L_0x0394:
            r0 = move-exception
            r1 = r37
        L_0x0397:
            r1.a(r11, r7)
            throw r0
        L_0x039b:
            r21 = r2
            r1 = r3
            r38 = r4
            r42 = r5
        L_0x03a2:
            if (r1 == 0) goto L_0x03a5
            goto L_0x0390
        L_0x03a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.i0.a(byte[], com.leedarson.smartcamera.listener.g):void");
    }
}
