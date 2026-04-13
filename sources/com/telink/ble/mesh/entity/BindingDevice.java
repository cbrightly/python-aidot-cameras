package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.access.BindingBearer;

public class BindingDevice implements Parcelable {
    public static final Parcelable.Creator<BindingDevice> CREATOR = new Parcelable.Creator<BindingDevice>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12995, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12994, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BindingDevice a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12993, new Class[]{Parcel.class}, BindingDevice.class);
            if (proxy.isSupported) {
                return (BindingDevice) proxy.result;
            }
            return new BindingDevice(in);
        }

        public BindingDevice[] b(int size) {
            return new BindingDevice[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a1;
    private int c;
    private int d;
    private byte[] f;
    private CompositionData p0;
    private int q;
    private int[] x;
    private BindingBearer y;
    private boolean z;

    public int g() {
        return this.a1;
    }

    public BindingDevice() {
        this.c = -1;
        this.y = BindingBearer.GattOnly;
        this.z = false;
        this.a1 = 0;
    }

    public BindingDevice(int meshAddress, byte[] deviceUUID, int appKeyIndex, int protocolVersion) {
        this.c = -1;
        BindingBearer bindingBearer = BindingBearer.GattOnly;
        this.y = bindingBearer;
        this.z = false;
        this.a1 = 0;
        this.d = meshAddress;
        this.f = deviceUUID;
        this.q = appKeyIndex;
        this.x = null;
        this.y = bindingBearer;
        this.a1 = protocolVersion;
    }

    public BindingDevice(Parcel in) {
        this.c = -1;
        this.y = BindingBearer.GattOnly;
        boolean z2 = false;
        this.z = false;
        this.a1 = 0;
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.createByteArray();
        this.q = in.readInt();
        this.x = in.createIntArray();
        this.z = in.readByte() != 0 ? true : z2;
        this.p0 = (CompositionData) in.readParcelable(CompositionData.class.getClassLoader());
        this.a1 = in.readInt();
    }

    public int f() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public int a() {
        return this.q;
    }

    public int[] e() {
        return this.x;
    }

    public BindingBearer b() {
        return this.y;
    }

    public void i(BindingBearer bearer) {
        this.y = bearer;
    }

    public boolean h() {
        return this.z;
    }

    public void k(boolean defaultBound) {
        this.z = defaultBound;
    }

    public CompositionData c() {
        return this.p0;
    }

    public void j(CompositionData compositionData) {
        this.p0 = compositionData;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 12992, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeByteArray(this.f);
            dest.writeInt(this.q);
            dest.writeIntArray(this.x);
            dest.writeByte(this.z ? (byte) 1 : 0);
            dest.writeParcelable(this.p0, flags);
            dest.writeInt(this.a1);
        }
    }
}
