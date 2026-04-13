package com.telink.ble.mesh.core.message;

import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;

public abstract class StatusMessage implements Parcelable {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void b(byte[] bArr);

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.telink.ble.mesh.core.message.StatusMessage} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: com.telink.ble.mesh.core.message.StatusMessage} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: com.telink.ble.mesh.core.message.StatusMessage} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.telink.ble.mesh.core.message.StatusMessage a(int r8, byte[] r9) {
        /*
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r8)
            r3 = 0
            r1[r3] = r2
            r2 = 1
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r3] = r0
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r2] = r0
            java.lang.Class<com.telink.ble.mesh.core.message.StatusMessage> r7 = com.telink.ble.mesh.core.message.StatusMessage.class
            r2 = 0
            r0 = 1
            r5 = 12444(0x309c, float:1.7438E-41)
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x002f
            java.lang.Object r8 = r0.result
            com.telink.ble.mesh.core.message.StatusMessage r8 = (com.telink.ble.mesh.core.message.StatusMessage) r8
            return r8
        L_0x002f:
            java.lang.Class r0 = com.telink.ble.mesh.core.message.MeshStatus.Container.a(r8)
            if (r0 == 0) goto L_0x004d
            r1 = 0
            java.lang.Object r2 = r0.newInstance()     // Catch:{ InstantiationException -> 0x003e, IllegalAccessException -> 0x003c }
            r1 = r2
            goto L_0x0042
        L_0x003c:
            r2 = move-exception
            goto L_0x003f
        L_0x003e:
            r2 = move-exception
        L_0x003f:
            r2.printStackTrace()
        L_0x0042:
            boolean r2 = r1 instanceof com.telink.ble.mesh.core.message.StatusMessage
            if (r2 == 0) goto L_0x004d
            r2 = r1
            com.telink.ble.mesh.core.message.StatusMessage r2 = (com.telink.ble.mesh.core.message.StatusMessage) r2
            r2.b(r9)
            return r2
        L_0x004d:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.message.StatusMessage.a(int, byte[]):com.telink.ble.mesh.core.message.StatusMessage");
    }
}
