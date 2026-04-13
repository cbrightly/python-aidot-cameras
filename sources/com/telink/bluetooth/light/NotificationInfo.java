package com.telink.bluetooth.light;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public final class NotificationInfo implements Parcelable {
    public static final Parcelable.Creator<NotificationInfo> CREATOR = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    public int c;
    public int d;
    public byte[] f = new byte[10];
    public byte[] q;
    public DeviceInfo x;

    public static final class a implements Parcelable.Creator<NotificationInfo> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13824, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13823, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public NotificationInfo a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13822, new Class[]{Parcel.class}, NotificationInfo.class);
            if (proxy.isSupported) {
                return (NotificationInfo) proxy.result;
            }
            return new NotificationInfo(in);
        }

        public NotificationInfo[] b(int size) {
            return new NotificationInfo[size];
        }
    }

    public NotificationInfo() {
    }

    public NotificationInfo(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        in.readByteArray(this.f);
        in.readByteArray(this.q);
        Object ret = in.readValue(DeviceInfo.class.getClassLoader());
        if (ret != null) {
            this.x = (DeviceInfo) ret;
        }
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13821, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeByteArray(this.f);
            dest.writeByteArray(this.q);
            DeviceInfo deviceInfo = this.x;
            if (deviceInfo != null) {
                dest.writeValue(deviceInfo);
            }
        }
    }

    public int describeContents() {
        return 0;
    }
}
