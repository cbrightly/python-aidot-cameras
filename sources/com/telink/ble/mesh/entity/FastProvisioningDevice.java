package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;

public class FastProvisioningDevice implements Parcelable {
    public static final Parcelable.Creator<FastProvisioningDevice> CREATOR = new Parcelable.Creator<FastProvisioningDevice>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13017, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13016, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FastProvisioningDevice a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13015, new Class[]{Parcel.class}, FastProvisioningDevice.class);
            if (proxy.isSupported) {
                return (FastProvisioningDevice) proxy.result;
            }
            return new FastProvisioningDevice(in);
        }

        public FastProvisioningDevice[] b(int size) {
            return new FastProvisioningDevice[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;
    private int q;
    private byte[] x;
    private byte[] y;

    public FastProvisioningDevice() {
    }

    public FastProvisioningDevice(int originAddress, int newAddress, int pid, int elementCount, byte[] mac) {
        this.c = originAddress;
        this.d = newAddress;
        this.f = pid;
        this.q = elementCount;
        this.x = e.h(mac);
        byte[] bArr = new byte[16];
        this.y = bArr;
        System.arraycopy(mac, 0, bArr, 0, 6);
    }

    public FastProvisioningDevice(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.createByteArray();
        this.y = in.createByteArray();
    }

    public byte[] b() {
        return this.x;
    }

    public boolean equals(Object o) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o}, this, changeQuickRedirect, false, 13011, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.x, ((FastProvisioningDevice) o).x);
    }

    public int hashCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13012, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : Arrays.hashCode(this.x);
    }

    public int d() {
        return this.c;
    }

    public int c() {
        return this.d;
    }

    public byte[] a() {
        return this.y;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13013, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeByteArray(this.x);
            dest.writeByteArray(this.y);
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13014, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "FastProvisioningDevice{originAddress=" + this.c + ", newAddress=" + this.d + ", pid=" + this.f + ", elementCount=" + this.q + ", mac=" + Arrays.toString(this.x) + ", deviceKey=" + Arrays.toString(this.y) + '}';
    }
}
