package com.telink.ble.mesh.core.networking;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.Encipher;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Priority;
import com.telink.ble.mesh.util.MeshLogger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NetworkLayerPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte a;
    private byte b;
    private byte c;
    private byte d;
    private int e;
    private int f;
    private int g;
    private byte[] h;
    private Priority i;
    private String j;
    protected NetworkEncryptionSuite k;

    public String i() {
        return this.j;
    }

    public void t(String extra) {
        this.j = extra;
    }

    public NetworkLayerPDU(NetworkEncryptionSuite encryptionSuite) {
        this.k = encryptionSuite;
    }

    public byte[] e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12805, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] encryptedPayload = d(this.h);
        byte[] header = a((byte) ((this.c << 7) | this.d), this.e, this.f, b(c(encryptedPayload)));
        return ByteBuffer.allocate(header.length + 1 + encryptedPayload.length).order(ByteOrder.BIG_ENDIAN).put((byte) ((this.a << 7) | this.b)).put(header).put(encryptedPayload).array();
    }

    private byte[] a(byte ctlTTL, int sno, int src, byte[] bArr) {
        Object[] objArr = {new Byte(ctlTTL), new Integer(sno), new Integer(src), bArr};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12806, new Class[]{Byte.TYPE, cls, cls, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] pecb = bArr;
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] seqNo = MeshUtils.l(sno, 3, byteOrder);
        ByteBuffer buffer = ByteBuffer.allocate(seqNo.length + 1 + 2).order(byteOrder);
        buffer.put(ctlTTL);
        buffer.put(seqNo);
        buffer.putShort((short) src);
        byte[] headerBuffer = buffer.array();
        ByteBuffer.allocate(6).put(pecb, 0, 6);
        byte[] obfuscated = new byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            obfuscated[i2] = (byte) (headerBuffer[i2] ^ pecb[i2]);
        }
        return obfuscated;
    }

    private byte[] b(byte[] privacyRandom) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{privacyRandom}, this, changeQuickRedirect, false, 12807, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer buffer = ByteBuffer.allocate(privacyRandom.length + 5 + 4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(new byte[]{0, 0, 0, 0, 0});
        buffer.putInt(this.k.a);
        buffer.put(privacyRandom);
        return Encipher.a(buffer.array(), this.k.c);
    }

    private byte[] c(byte[] encryptedUpperTransportPDU) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{encryptedUpperTransportPDU}, this, changeQuickRedirect, false, 12808, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] privacyRandom = new byte[7];
        System.arraycopy(encryptedUpperTransportPDU, 0, privacyRandom, 0, privacyRandom.length);
        return privacyRandom;
    }

    private byte[] d(byte[] lowerPDU) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lowerPDU}, this, changeQuickRedirect, false, 12809, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return Encipher.d(ByteBuffer.allocate(lowerPDU.length + 2).order(ByteOrder.BIG_ENDIAN).putShort((short) this.g).put(lowerPDU).array(), this.k.b, f(), j(), true);
    }

    public byte[] f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12810, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return NonceGenerator.b((byte) ((this.c << 7) | this.d), MeshUtils.l(this.e, 3, ByteOrder.BIG_ENDIAN), this.f, this.k.a);
    }

    private int j() {
        return this.c == 0 ? 4 : 8;
    }

    public byte[] q(byte[] pdu) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pdu}, this, changeQuickRedirect, false, 12811, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer obfuscatedNetworkBuffer = ByteBuffer.allocate(6);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        obfuscatedNetworkBuffer.order(byteOrder);
        obfuscatedNetworkBuffer.put(pdu, 1, 6);
        byte[] obfuscatedData = obfuscatedNetworkBuffer.array();
        ByteBuffer privacyRandomBuffer = ByteBuffer.allocate(7);
        privacyRandomBuffer.order(byteOrder);
        privacyRandomBuffer.put(pdu, 7, 7);
        byte[] pecb = b(c(privacyRandomBuffer.array()));
        byte[] deobfuscatedData = new byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            deobfuscatedData[i2] = (byte) (obfuscatedData[i2] ^ pecb[i2]);
        }
        return deobfuscatedData;
    }

    public boolean p(byte[] bArr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bArr}, this, changeQuickRedirect, false, 12812, new Class[]{byte[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] pduData = bArr;
        byte b2 = pduData[0] & 255;
        int ivi = b2 >> 7;
        byte b3 = b2 & Byte.MAX_VALUE;
        if (!B(ivi, b3)) {
            MeshLogger.c("ivi or nid invalid: ivi -- " + ivi + " nid -- " + b3 + " encryptSuit : ivi -- " + this.k.a + " nid -- " + this.k.d);
            return false;
        }
        byte[] originalHeader = q(pduData);
        byte ctlTtl = originalHeader[0];
        int ttl = ctlTtl & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        ByteBuffer allocate = ByteBuffer.allocate(3);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] sequenceNumber = allocate.order(byteOrder).put(originalHeader, 1, 3).array();
        int src = ((originalHeader[4] & 255) << 8) + (originalHeader[5] & 255);
        u((byte) ivi);
        v((byte) b3);
        r((byte) ((ctlTtl >> 7) & 1));
        A((byte) ttl);
        x(MeshUtils.c(sequenceNumber, byteOrder));
        y(src);
        byte[] networkNonce = f();
        int dstTransportLen = pduData.length - (originalHeader.length + 1);
        byte[] dstTransportPayload = new byte[dstTransportLen];
        System.arraycopy(pduData, 7, dstTransportPayload, 0, dstTransportLen);
        byte[] bArr2 = pduData;
        byte[] decDstTransportPayload = Encipher.d(dstTransportPayload, this.k.b, networkNonce, j(), false);
        if (decDstTransportPayload == null) {
            MeshLogger.c("network layer decrypt err");
            return false;
        }
        int dstAdr = ((decDstTransportPayload[0] & 255) << 8) | (decDstTransportPayload[1] & 255);
        byte b4 = b2;
        byte[] lowerTransportPdu = new byte[(decDstTransportPayload.length - 2)];
        int i2 = ivi;
        byte b5 = b3;
        System.arraycopy(decDstTransportPayload, 2, lowerTransportPdu, 0, lowerTransportPdu.length);
        this.g = dstAdr;
        z(lowerTransportPdu);
        return true;
    }

    private boolean B(int ivi, int nid) {
        NetworkEncryptionSuite networkEncryptionSuite = this.k;
        return nid == networkEncryptionSuite.d && ivi == (networkEncryptionSuite.a & 1);
    }

    public void u(byte ivi) {
        this.a = ivi;
    }

    public void v(byte nid) {
        this.b = nid;
    }

    public byte g() {
        return this.c;
    }

    public void r(byte ctl) {
        this.c = ctl;
    }

    public byte o() {
        return this.d;
    }

    public void A(byte ttl) {
        this.d = ttl;
    }

    public int l() {
        return this.e;
    }

    public void x(int seq) {
        this.e = seq;
    }

    public int m() {
        return this.f;
    }

    public void y(int src) {
        this.f = src;
    }

    public int h() {
        return this.g;
    }

    public void s(int dst) {
        this.g = dst;
    }

    public byte[] n() {
        return this.h;
    }

    public void z(byte[] transportPDU) {
        this.h = transportPDU;
    }

    public Priority k() {
        return this.i;
    }

    public void w(Priority priority) {
        this.i = priority;
    }

    public static class NetworkEncryptionSuite {
        public int a;
        protected byte[] b;
        protected byte[] c;
        protected int d;

        public NetworkEncryptionSuite(int ivIndex, byte[] encryptionKey, byte[] privacyKey, int nid) {
            this.a = ivIndex;
            this.b = encryptionKey;
            this.c = privacyKey;
            this.d = nid;
        }
    }
}
