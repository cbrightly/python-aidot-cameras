package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;

public class RemoteProvisioningDevice extends ProvisioningDevice {
    public static final Parcelable.Creator<RemoteProvisioningDevice> CREATOR = new Parcelable.Creator<RemoteProvisioningDevice>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13046, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13045, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public RemoteProvisioningDevice a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13044, new Class[]{Parcel.class}, RemoteProvisioningDevice.class);
            if (proxy.isSupported) {
                return (RemoteProvisioningDevice) proxy.result;
            }
            return new RemoteProvisioningDevice(in);
        }

        public RemoteProvisioningDevice[] b(int size) {
            return new RemoteProvisioningDevice[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int B4;
    private byte[] C4 = null;
    private int D4;

    public RemoteProvisioningDevice(Parcel in) {
        super(in);
        this.B4 = in.readInt();
        this.C4 = in.createByteArray();
        this.D4 = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13041, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.B4);
            dest.writeByteArray(this.C4);
            dest.writeInt(this.D4);
        }
    }

    public int describeContents() {
        return 0;
    }

    public int h() {
        return this.B4;
    }

    public int x() {
        return this.D4;
    }

    public boolean equals(Object o) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o}, this, changeQuickRedirect, false, 13042, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.C4, ((RemoteProvisioningDevice) o).C4);
    }

    public int hashCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13043, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : Arrays.hashCode(this.C4);
    }
}
