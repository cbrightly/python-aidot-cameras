package com.telink.bluetooth.light;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class DeviceInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceInfo> CREATOR = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a1;
    public String c;
    public String d;
    public String f;
    public byte[] p0 = new byte[16];
    public String p1;
    public int q;
    public int x;
    public int y;
    public int z;

    public static final class a implements Parcelable.Creator<DeviceInfo> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13521, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13520, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public DeviceInfo a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13519, new Class[]{Parcel.class}, DeviceInfo.class);
            if (proxy.isSupported) {
                return (DeviceInfo) proxy.result;
            }
            return new DeviceInfo(in);
        }

        public DeviceInfo[] b(int size) {
            return new DeviceInfo[size];
        }
    }

    public DeviceInfo() {
    }

    public DeviceInfo(Parcel in) {
        this.c = in.readString();
        this.d = in.readString();
        this.f = in.readString();
        this.a1 = in.readString();
        this.p1 = in.readString();
        this.q = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
        in.readByteArray(this.p0);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13518, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.c);
            dest.writeString(this.d);
            dest.writeString(this.f);
            dest.writeString(this.a1);
            dest.writeString(this.p1);
            dest.writeInt(this.q);
            dest.writeInt(this.x);
            dest.writeInt(this.y);
            dest.writeInt(this.z);
            dest.writeByteArray(this.p0);
        }
    }
}
