package com.telink.ble.mesh.core.networking;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NonceGenerator {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] b(byte ctlTTL, byte[] sequenceNumber, int src, int ivIndex) {
        Object[] objArr = {new Byte(ctlTTL), sequenceNumber, new Integer(src), new Integer(ivIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12905, new Class[]{Byte.TYPE, byte[].class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer networkNonce = ByteBuffer.allocate(13).order(ByteOrder.BIG_ENDIAN);
        networkNonce.put((byte) 0);
        networkNonce.put(ctlTTL);
        networkNonce.put(sequenceNumber);
        networkNonce.putShort((short) src);
        networkNonce.put(new byte[]{0, 0});
        networkNonce.putInt(ivIndex);
        return networkNonce.array();
    }

    public static byte[] a(byte b, byte[] bArr, int i, int i2, int i3, AccessType accessType) {
        byte b2 = b;
        byte type = 1;
        Object[] objArr = {new Byte(b), bArr, new Integer(i), new Integer(i2), new Integer(i3), accessType};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12906, new Class[]{Byte.TYPE, byte[].class, cls, cls, cls, AccessType.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte aszmic = b;
        int src = i;
        int ivIndex = i3;
        byte[] sequenceNumber = bArr;
        int dst = i2;
        AccessType accessType2 = accessType;
        ByteBuffer accessNonceBuf = ByteBuffer.allocate(13).order(ByteOrder.BIG_ENDIAN);
        if (accessType2 != AccessType.APPLICATION) {
            type = 2;
        }
        accessNonceBuf.put(type);
        accessNonceBuf.put((byte) ((aszmic << 7) | 0));
        accessNonceBuf.put(sequenceNumber);
        accessNonceBuf.putShort((short) src);
        accessNonceBuf.putShort((short) dst);
        accessNonceBuf.putInt(ivIndex);
        return accessNonceBuf.array();
    }

    public static byte[] c(byte[] sequenceNumber, int src, int ivIndex) {
        Object[] objArr = {sequenceNumber, new Integer(src), new Integer(ivIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12907, new Class[]{byte[].class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer applicationNonceBuffer = ByteBuffer.allocate(13);
        applicationNonceBuffer.put((byte) 3);
        applicationNonceBuffer.put((byte) 0);
        applicationNonceBuffer.put(sequenceNumber);
        applicationNonceBuffer.putShort((short) src);
        applicationNonceBuffer.put(new byte[]{0, 0});
        applicationNonceBuffer.putInt(ivIndex);
        return applicationNonceBuffer.array();
    }
}
