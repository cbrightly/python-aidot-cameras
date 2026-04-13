package com.telink.ble.mesh.entity;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningCapabilityPDU;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshsdk.model.NetworkingDevice;

public class ProvisioningDevice implements Parcelable {
    public static final Parcelable.Creator<ProvisioningDevice> CREATOR = new Parcelable.Creator<ProvisioningDevice>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13040, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13039, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ProvisioningDevice a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13038, new Class[]{Parcel.class}, ProvisioningDevice.class);
            if (proxy.isSupported) {
                return (ProvisioningDevice) proxy.result;
            }
            return new ProvisioningDevice(in);
        }

        public ProvisioningDevice[] b(int size) {
            return new ProvisioningDevice[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    protected int A4;
    protected int a1;
    protected byte[] a2 = null;
    private BluetoothDevice c;
    protected byte[] d;
    protected NetworkingDevice f;
    protected byte p0;
    protected int p1;
    protected boolean p2 = false;
    protected int p3;
    protected byte[] p4 = null;
    protected int q;
    protected byte[] x;
    protected int y;
    protected byte z;
    protected ProvisioningCapabilityPDU z4 = null;

    public ProvisioningDevice(BluetoothDevice bluetoothDevice, byte[] deviceUUID, int unicastAddress) {
        this.c = bluetoothDevice;
        this.d = deviceUUID;
        this.p1 = unicastAddress;
    }

    public ProvisioningDevice() {
    }

    public ProvisioningDevice(Parcel in) {
        this.c = (BluetoothDevice) in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.d = in.createByteArray();
        this.q = in.readInt();
        this.x = in.createByteArray();
        this.y = in.readInt();
        this.z = in.readByte();
        this.p0 = in.readByte();
        this.a1 = in.readInt();
        this.p1 = in.readInt();
        this.a2 = in.createByteArray();
        this.p3 = in.readInt();
        this.p4 = in.createByteArray();
        this.A4 = in.readInt();
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13035, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer buffer = ByteBuffer.allocate(25).order(ByteOrder.BIG_ENDIAN);
        buffer.put(this.x).putShort((short) this.y).put((byte) ((this.z & 1) | (this.p0 & 2))).putInt(this.a1).putShort((short) this.p1);
        return buffer.array();
    }

    public void v(int oobInfo) {
        this.q = oobInfo;
    }

    public BluetoothDevice c() {
        return this.c;
    }

    public void u(NetworkingDevice networkingDevice) {
        this.f = networkingDevice;
    }

    public NetworkingDevice g() {
        return this.f;
    }

    public void m(BluetoothDevice _newDeviceHandler) {
        this.c = _newDeviceHandler;
    }

    public byte[] f() {
        return this.d;
    }

    public void s(byte[] networkKey) {
        this.x = networkKey;
    }

    public void t(int networkKeyIndex) {
        this.y = networkKeyIndex;
    }

    public void r(byte keyRefreshFlag) {
        this.z = keyRefreshFlag;
    }

    public void q(byte ivUpdateFlag) {
        this.p0 = ivUpdateFlag;
    }

    public void p(int ivIndex) {
        this.a1 = ivIndex;
    }

    public int i() {
        return this.p1;
    }

    public byte[] b() {
        return this.a2;
    }

    public void k(byte[] authValue) {
        this.a2 = authValue;
    }

    public int h() {
        return this.A4;
    }

    public void w(int rssi) {
        this.A4 = rssi;
    }

    public byte[] e() {
        return this.p4;
    }

    public void o(byte[] deviceKey) {
        this.p4 = deviceKey;
    }

    public ProvisioningCapabilityPDU d() {
        return this.z4;
    }

    public void n(ProvisioningCapabilityPDU deviceCapability) {
        this.z4 = deviceCapability;
    }

    public boolean j() {
        return this.p2;
    }

    public void l(boolean autoUseNoOOB) {
        this.p2 = autoUseNoOOB;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13036, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
            dest.writeByteArray(this.d);
            dest.writeInt(this.q);
            dest.writeByteArray(this.x);
            dest.writeInt(this.y);
            dest.writeByte(this.z);
            dest.writeByte(this.p0);
            dest.writeInt(this.a1);
            dest.writeInt(this.p1);
            dest.writeByteArray(this.a2);
            dest.writeInt(this.p3);
            dest.writeByteArray(this.p4);
            dest.writeInt(this.A4);
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13037, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ProvisioningDevice{deviceUUID=" + e.a(this.d) + ", oobInfo=0b" + Integer.toBinaryString(this.q) + ", networkKey=" + e.a(this.x) + ", networkKeyIndex=" + this.y + ", keyRefreshFlag=" + this.z + ", ivUpdateFlag=" + this.p0 + ", ivIndex=0x" + Long.toHexString((long) this.a1) + ", unicastAddress=0x" + Integer.toHexString(this.p1) + ", authValue=" + e.a(this.a2) + ", autoUseNoOOB=" + this.p2 + ", provisioningState=" + this.p3 + ", deviceKey=" + e.a(this.p4) + '}';
    }
}
