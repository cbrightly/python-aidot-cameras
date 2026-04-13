package com.telink.ble.mesh.core.networking.transport.lower;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.networking.NetworkLayerPDU;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UnsegmentedAccessMessagePDU extends LowerTransportPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final byte g = 0;
    private byte h;
    private byte i;
    private byte[] j;

    public UnsegmentedAccessMessagePDU() {
    }

    public UnsegmentedAccessMessagePDU(byte akf, byte aid, byte[] upperTransportPDU) {
        this.h = akf;
        this.i = aid;
        this.j = upperTransportPDU;
    }

    public byte[] e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12920, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer lowerTransportBuffer = ByteBuffer.allocate(this.j.length + 1).order(ByteOrder.BIG_ENDIAN);
        lowerTransportBuffer.put((byte) (0 | (this.h << 6) | this.i));
        lowerTransportBuffer.put(this.j);
        return lowerTransportBuffer.array();
    }

    public boolean d(NetworkLayerPDU networkLayerPDU) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12921, new Class[]{NetworkLayerPDU.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] lowerTransportData = networkLayerPDU.n();
        byte header = lowerTransportData[0];
        this.h = (byte) ((header >> 6) & 1);
        this.i = (byte) (header & 63);
        byte[] upperTransportPDU = new byte[(lowerTransportData.length - 1)];
        System.arraycopy(lowerTransportData, 1, upperTransportPDU, 0, upperTransportPDU.length);
        this.j = upperTransportPDU;
        if (upperTransportPDU.length != 0) {
            return true;
        }
        return false;
    }

    public byte b() {
        return this.h;
    }

    public byte a() {
        return this.i;
    }

    public byte[] c() {
        return this.j;
    }
}
