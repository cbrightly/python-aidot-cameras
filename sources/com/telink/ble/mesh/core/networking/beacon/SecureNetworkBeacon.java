package com.telink.ble.mesh.core.networking.beacon;

import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.Encipher;
import com.telink.ble.mesh.core.MeshUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;

public class SecureNetworkBeacon extends MeshBeaconPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final byte a = 1;
    private byte b;
    private byte[] c;
    private int d;
    private byte[] e;

    public boolean i() {
        return (this.b & 2) != 0;
    }

    public static SecureNetworkBeacon c(byte[] payload) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{payload}, (Object) null, changeQuickRedirect, true, 12908, new Class[]{byte[].class}, SecureNetworkBeacon.class);
        if (proxy.isSupported) {
            return (SecureNetworkBeacon) proxy.result;
        }
        if (payload.length != 22) {
            return null;
        }
        int index = 0 + 1;
        if (payload[0] != 1) {
            return null;
        }
        SecureNetworkBeacon beacon = new SecureNetworkBeacon();
        int index2 = index + 1;
        beacon.b = payload[index];
        byte[] bArr = new byte[8];
        beacon.c = bArr;
        System.arraycopy(payload, index2, bArr, 0, bArr.length);
        int index3 = index2 + beacon.c.length;
        beacon.d = MeshUtils.b(payload, index3, 4, ByteOrder.BIG_ENDIAN);
        byte[] bArr2 = new byte[8];
        beacon.e = bArr2;
        System.arraycopy(payload, index3 + 4, bArr2, 0, bArr2.length);
        return beacon;
    }

    public static SecureNetworkBeacon b(int curIvIndex, byte[] networkId, byte[] beaconKey, boolean updating) {
        Class<byte[]> cls = byte[].class;
        int i = 2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(curIvIndex), networkId, beaconKey, new Byte(updating ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 12909, new Class[]{Integer.TYPE, cls, cls, Boolean.TYPE}, SecureNetworkBeacon.class);
        if (proxy.isSupported) {
            return (SecureNetworkBeacon) proxy.result;
        }
        SecureNetworkBeacon networkBeacon = new SecureNetworkBeacon();
        if (!updating) {
            i = 0;
        }
        networkBeacon.b = (byte) i;
        networkBeacon.c = networkId;
        networkBeacon.d = updating ? curIvIndex + 1 : curIvIndex;
        ByteBuffer buffer = ByteBuffer.allocate(13).order(ByteOrder.BIG_ENDIAN);
        buffer.put(networkBeacon.b);
        buffer.put(networkBeacon.c);
        buffer.putInt(networkBeacon.d);
        byte[] authCal = new byte[8];
        System.arraycopy(Encipher.b(buffer.array(), beaconKey), 0, authCal, 0, authCal.length);
        networkBeacon.e = authCal;
        return networkBeacon;
    }

    public boolean j(byte[] networkID, byte[] beaconKey) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkID, beaconKey}, this, changeQuickRedirect, false, 12910, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!e.d(this.c, networkID) || this.e == null) {
            return false;
        }
        ByteBuffer buffer = ByteBuffer.allocate(13).order(ByteOrder.BIG_ENDIAN);
        buffer.put(this.b);
        buffer.put(networkID);
        buffer.putInt(this.d);
        byte[] authCal = new byte[8];
        System.arraycopy(Encipher.b(buffer.array(), beaconKey), 0, authCal, 0, authCal.length);
        return e.d(authCal, this.e);
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12911, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "SecureNetworkBeacon{beaconType=1, flags=" + this.b + ", networkID=" + e.b(this.c, "") + ", ivIndex=0x" + String.format(Locale.US, "%08X", new Object[]{Integer.valueOf(this.d)}) + ", authenticationValue=" + e.b(this.e, "") + '}';
    }

    public byte e() {
        return 1;
    }

    public byte f() {
        return this.b;
    }

    public byte[] h() {
        return this.c;
    }

    public int g() {
        return this.d;
    }

    public byte[] d() {
        return this.e;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12912, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer buffer1 = ByteBuffer.allocate(22).order(ByteOrder.BIG_ENDIAN);
        buffer1.put((byte) 1).put(this.b).put(this.c).putInt(this.d).put(this.e);
        return buffer1.array();
    }
}
