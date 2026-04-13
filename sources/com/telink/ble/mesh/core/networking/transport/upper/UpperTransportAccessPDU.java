package com.telink.ble.mesh.core.networking.transport.upper;

import android.util.SparseArray;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.Encipher;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.networking.AccessType;
import com.telink.ble.mesh.core.networking.NonceGenerator;
import com.telink.ble.mesh.core.networking.transport.lower.SegmentedAccessMessagePDU;
import com.telink.ble.mesh.core.networking.transport.lower.UnsegmentedAccessMessagePDU;
import com.telink.ble.mesh.util.MeshLogger;
import java.nio.ByteOrder;
import java.util.List;

public class UpperTransportAccessPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] a;
    private byte[] b;
    private UpperTransportEncryptionSuite c;

    public UpperTransportAccessPDU(UpperTransportEncryptionSuite mEncryptionSuite) {
        this.c = mEncryptionSuite;
    }

    public byte[] e() {
        return this.a;
    }

    public byte[] d() {
        return this.b;
    }

    public boolean f(SparseArray<SegmentedAccessMessagePDU> messageBuffer, int i, int i2, int i3) {
        Object[] objArr = {messageBuffer, new Integer(i), new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12923, new Class[]{SparseArray.class, cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int sequenceNumber = i;
        int dst = i3;
        int src = i2;
        int len = 0;
        for (int i4 = 0; i4 < messageBuffer.size(); i4++) {
            len += messageBuffer.get(i4).e().length;
        }
        byte[] upperTransportPdu = new byte[len];
        int idx = 0;
        for (int i5 = 0; i5 < messageBuffer.size(); i5++) {
            int tmpLen = messageBuffer.get(i5).e().length;
            System.arraycopy(messageBuffer.get(i5).e(), 0, upperTransportPdu, idx, tmpLen);
            idx += tmpLen;
        }
        this.a = upperTransportPdu;
        SegmentedAccessMessagePDU message0 = messageBuffer.get(0);
        byte[] a2 = a(message0.b(), message0.a(), message0.g(), sequenceNumber, src, dst);
        this.b = a2;
        if (a2 != null) {
            return true;
        }
        return false;
    }

    public boolean g(UnsegmentedAccessMessagePDU unsegmentedAccessMessagePDU, int sequenceNumber, int src, int dst) {
        Object[] objArr = {unsegmentedAccessMessagePDU, new Integer(sequenceNumber), new Integer(src), new Integer(dst)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12924, new Class[]{UnsegmentedAccessMessagePDU.class, cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        this.a = unsegmentedAccessMessagePDU.c();
        byte b2 = unsegmentedAccessMessagePDU.b();
        byte a2 = unsegmentedAccessMessagePDU.a();
        byte[] a3 = a(b2, a2, 0, sequenceNumber, src, dst);
        this.b = a3;
        if (a3 != null) {
            return true;
        }
        return false;
    }

    public boolean c(byte[] accessPduData, byte b2, AccessType accessType, int seqNo, int src, int dst) {
        byte[] key;
        Object[] objArr = {accessPduData, new Byte(b2), accessType, new Integer(seqNo), new Integer(src), new Integer(dst)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12925, new Class[]{byte[].class, Byte.TYPE, AccessType.class, cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte szmic = b2;
        AccessType accessType2 = accessType;
        this.b = accessPduData;
        byte[] l = MeshUtils.l(seqNo, 3, ByteOrder.BIG_ENDIAN);
        byte[] nonce = NonceGenerator.a(szmic, l, src, dst, this.c.c, accessType2);
        int mic = MeshUtils.i(szmic);
        if (accessType2 == AccessType.APPLICATION) {
            key = (byte[]) this.c.a.get(0);
        } else {
            key = this.c.b;
        }
        if (key == null) {
            MeshLogger.b("upper transport encryption err: key null");
            return false;
        }
        byte[] d = Encipher.d(this.b, key, nonce, mic, true);
        this.a = d;
        if (d != null) {
            return true;
        }
        return false;
    }

    private byte[] a(int i, byte b2, int i2, int sequenceNumber, int i3, int i4) {
        Object[] objArr = {new Integer(i), new Byte(b2), new Integer(i2), new Integer(sequenceNumber), new Integer(i3), new Integer(i4)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, Byte.TYPE, cls, cls, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12926, clsArr, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte aid = b2;
        int dst = i4;
        int akf = i;
        int aszmic = i2;
        int src = i3;
        byte[] nonce = null;
        byte[] seqNo = MeshUtils.q(sequenceNumber);
        AccessType accessType = AccessType.DEVICE;
        if (accessType.akf == akf) {
            byte[] nonce2 = NonceGenerator.a((byte) aszmic, seqNo, src, dst, this.c.c, accessType);
            byte[] key = this.c.b;
            if (key != null) {
                return b(this.a, key, nonce2, aszmic);
            }
            MeshLogger.b("decrypt err: device key null");
            return null;
        }
        if (this.c.a != null) {
            for (byte[] appKey : this.c.a) {
                if (MeshUtils.f(appKey) == aid) {
                    if (nonce == null) {
                        nonce = NonceGenerator.a((byte) aszmic, seqNo, src, dst, this.c.c, AccessType.APPLICATION);
                    }
                    byte[] decResult = b(this.a, appKey, nonce, aszmic);
                    if (decResult != null) {
                        return decResult;
                    }
                }
            }
        }
        return null;
    }

    private byte[] b(byte[] payload, byte[] key, byte[] nonce, int aszmic) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{payload, key, nonce, new Integer(aszmic)}, this, changeQuickRedirect, false, 12927, new Class[]{cls, cls, cls, Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (aszmic == 1) {
            return Encipher.d(payload, key, nonce, 8, false);
        }
        return Encipher.d(payload, key, nonce, 4, false);
    }

    public static class UpperTransportEncryptionSuite {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public List<byte[]> a;
        /* access modifiers changed from: private */
        public byte[] b;
        /* access modifiers changed from: private */
        public int c;

        public UpperTransportEncryptionSuite(byte[] deviceKey, int ivIndex) {
            this.b = deviceKey;
            this.c = ivIndex;
        }

        public UpperTransportEncryptionSuite(List<byte[]> appKeyList, int ivIndex) {
            this.a = appKeyList;
            this.c = ivIndex;
        }
    }
}
