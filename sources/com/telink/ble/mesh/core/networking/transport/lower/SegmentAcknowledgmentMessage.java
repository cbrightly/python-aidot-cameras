package com.telink.ble.mesh.core.networking.transport.lower;

import com.github.druk.dnssd.NSType;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SegmentAcknowledgmentMessage extends UnsegmentedControlMessagePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int j = 0;
    private final int k = 0;
    private final int l = 0;
    private int m;
    private final int n = 0;
    private int o = 0;

    public boolean d(byte[] lowerTransportData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lowerTransportData}, this, changeQuickRedirect, false, 12913, new Class[]{byte[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (lowerTransportData.length != 7) {
            return false;
        }
        byte[] bArr = {lowerTransportData[1], lowerTransportData[2]};
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        this.m = (MeshUtils.c(bArr, byteOrder) & 32767) >> 2;
        this.o = MeshUtils.c(new byte[]{lowerTransportData[3], lowerTransportData[4], lowerTransportData[5], lowerTransportData[6]}, byteOrder);
        return true;
    }

    public SegmentAcknowledgmentMessage() {
    }

    public SegmentAcknowledgmentMessage(int seqZero, int blockAck) {
        this.m = seqZero;
        this.o = blockAck;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12914, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN).put((byte) 0).put((byte) (((this.m >> 6) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 0)).put((byte) (0 | ((this.m << 2) & NSType.AXFR))).putInt(this.o).array();
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12915, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "SegmentAcknowledgmentMessage{seg=0, opcode=0, obo=0, seqZero=" + this.m + ", rfu=" + 0 + ", blockAck=" + this.o + '}';
    }

    public int c() {
        return this.m;
    }

    public int b() {
        return this.o;
    }
}
