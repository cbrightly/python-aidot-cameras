package com.telink.ble.mesh.util;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class OtaPacketParser {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a;
    private int b = -1;
    private byte[] c;
    private int d;

    public void m(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 13408, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            a();
            this.c = data;
            int length = data.length;
            if (length % 16 == 0) {
                this.a = length / 16;
            } else {
                this.a = (int) Math.floor((double) ((length / 16) + 1));
            }
        }
    }

    public void a() {
        this.d = 0;
        this.a = 0;
        this.b = -1;
        this.c = null;
    }

    public boolean j() {
        int i = this.a;
        return i > 0 && this.b + 1 < i;
    }

    public boolean l() {
        return this.b + 1 == this.a;
    }

    public int g() {
        return this.b + 1;
    }

    public byte[] f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13409, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int index = g();
        byte[] packet = h(index);
        this.b = index;
        return packet;
    }

    public byte[] h(int index) {
        int packetSize;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(index)}, this, changeQuickRedirect, false, 13410, new Class[]{Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int length = this.c.length;
        if (length <= 16) {
            packetSize = length;
        } else if (index + 1 == this.a) {
            packetSize = length - (index * 16);
        } else {
            packetSize = 16;
        }
        int packetSize2 = packetSize + 4;
        byte[] packet = new byte[20];
        for (int i = 0; i < 20; i++) {
            packet[i] = -1;
        }
        System.arraycopy(this.c, index * 16, packet, 2, packetSize2 - 4);
        d(packet, index);
        c(packet, b(packet));
        return packet;
    }

    public void d(byte[] packet, int index) {
        packet[0] = (byte) (index & 255);
        packet[0 + 1] = (byte) ((index >> 8) & 255);
    }

    public void c(byte[] packet, int crc) {
        int offset = packet.length - 2;
        packet[offset] = (byte) (crc & 255);
        packet[offset + 1] = (byte) ((crc >> 8) & 255);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int b(byte[] r10) {
        /*
            r9 = this;
            int r0 = r10.length
            r1 = 2
            int r0 = r0 - r1
            short[] r1 = new short[r1]
            r1 = {0, -24575} // fill-array
            r2 = 65535(0xffff, float:9.1834E-41)
            r3 = 0
        L_0x000c:
            if (r3 >= r0) goto L_0x002b
            byte r4 = r10[r3]
            r5 = 0
        L_0x0011:
            r6 = 8
            if (r5 >= r6) goto L_0x0028
            int r6 = r2 >> 1
            r7 = r2 ^ r4
            r7 = r7 & 1
            short r7 = r1[r7]
            r8 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r8
            r2 = r6 ^ r7
            int r4 = r4 >> 1
            int r5 = r5 + 1
            goto L_0x0011
        L_0x0028:
            int r3 = r3 + 1
            goto L_0x000c
        L_0x002b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.util.OtaPacketParser.b(byte[]):int");
    }

    public boolean k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13412, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int progress = (int) Math.floor((double) ((((float) g()) / ((float) this.a)) * 100.0f));
        if (progress == this.d) {
            return false;
        }
        this.d = progress;
        return true;
    }

    public int i() {
        return this.d;
    }

    public int e() {
        return this.b;
    }

    public void n(int index) {
        this.b = index;
    }
}
