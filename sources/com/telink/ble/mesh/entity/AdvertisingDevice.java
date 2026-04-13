package com.telink.ble.mesh.entity;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;

public class AdvertisingDevice implements Parcelable {
    public static final Parcelable.Creator<AdvertisingDevice> CREATOR = new Parcelable.Creator<AdvertisingDevice>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12991, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12990, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public AdvertisingDevice a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12989, new Class[]{Parcel.class}, AdvertisingDevice.class);
            if (proxy.isSupported) {
                return (AdvertisingDevice) proxy.result;
            }
            return new AdvertisingDevice(in);
        }

        public AdvertisingDevice[] b(int size) {
            return new AdvertisingDevice[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public BluetoothDevice c;
    public int d;
    public byte[] f;

    public AdvertisingDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
        this.c = device;
        this.d = rssi;
        this.f = scanRecord;
    }

    public AdvertisingDevice(Parcel in) {
        this.c = (BluetoothDevice) in.readParcelable(getClass().getClassLoader());
        this.d = in.readInt();
        byte[] bArr = new byte[in.readInt()];
        this.f = bArr;
        in.readByteArray(bArr);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        Object[] objArr = {dest, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12986, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, 0);
            dest.writeInt(this.d);
            dest.writeInt(this.f.length);
            dest.writeByteArray(this.f);
        }
    }

    public boolean equals(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 12987, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!(obj instanceof AdvertisingDevice) || !((AdvertisingDevice) obj).c.equals(this.c) || !Arrays.equals(((AdvertisingDevice) obj).f, this.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12988, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.c.hashCode();
    }
}
