package com.telink.ble.mesh.core.networking.transport.lower;

import com.github.druk.dnssd.NSType;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.networking.NetworkLayerPDU;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SegmentedAccessMessagePDU extends LowerTransportPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final byte g = 1;
    private byte h;
    private byte i;
    private int j;
    private int k;
    private int l;
    private int m;
    private byte[] n;

    public byte[] p() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12916, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int akfAid = (this.h << 6) | this.i;
        ByteBuffer resultBuffer = ByteBuffer.allocate(4 + this.n.length).order(ByteOrder.BIG_ENDIAN);
        resultBuffer.put((byte) (akfAid | 128));
        resultBuffer.put((byte) ((this.j << 7) | ((this.k >> 6) & NeedPermissionEvent.PER_IPC_SPEAK_PERM)));
        resultBuffer.put((byte) (((this.k << 2) & NSType.AXFR) | ((this.l >> 3) & 3)));
        resultBuffer.put((byte) (((this.l << 5) & 224) | (this.m & 31)));
        resultBuffer.put(this.n);
        return resultBuffer.array();
    }

    public boolean h(NetworkLayerPDU networkLayerPDU) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12917, new Class[]{NetworkLayerPDU.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] lowerTransportPdu = networkLayerPDU.n();
        this.h = (byte) ((lowerTransportPdu[0] >> 6) & 1);
        this.i = (byte) (lowerTransportPdu[0] & 63);
        this.j = (lowerTransportPdu[1] >> 7) & 1;
        this.k = ((lowerTransportPdu[1] & Byte.MAX_VALUE) << 6) | ((lowerTransportPdu[2] & 252) >> 2);
        this.l = ((lowerTransportPdu[2] & 3) << 3) | ((lowerTransportPdu[3] & 224) >> 5);
        this.m = lowerTransportPdu[3] & 31;
        byte[] bArr = new byte[(lowerTransportPdu.length - 4)];
        this.n = bArr;
        System.arraycopy(lowerTransportPdu, 4, bArr, 0, bArr.length);
        byte[] bArr2 = this.n;
        if (bArr2 == null || bArr2.length < 1) {
            return false;
        }
        return true;
    }

    public byte b() {
        return this.h;
    }

    public void j(byte akf) {
        this.h = akf;
    }

    public byte a() {
        return this.i;
    }

    public void i(byte aid) {
        this.i = aid;
    }

    public int g() {
        return this.j;
    }

    public void o(int szmic) {
        this.j = szmic;
    }

    public int f() {
        return this.k;
    }

    public void n(int seqZero) {
        this.k = seqZero;
    }

    public int d() {
        return this.l;
    }

    public void l(int segO) {
        this.l = segO;
    }

    public int c() {
        return this.m;
    }

    public void k(int segN) {
        this.m = segN;
    }

    public byte[] e() {
        return this.n;
    }

    public void m(byte[] segmentM) {
        this.n = segmentM;
    }
}
