package com.telink.bluetooth.light;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class OtaDeviceInfo extends DeviceInfo {
    public static final Parcelable.Creator<OtaDeviceInfo> CREATOR = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a2;

    public static final class a implements Parcelable.Creator<OtaDeviceInfo> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13838, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13837, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public OtaDeviceInfo a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13836, new Class[]{Parcel.class}, OtaDeviceInfo.class);
            if (proxy.isSupported) {
                return (OtaDeviceInfo) proxy.result;
            }
            return new OtaDeviceInfo(in);
        }

        public OtaDeviceInfo[] b(int size) {
            return new OtaDeviceInfo[size];
        }
    }

    public OtaDeviceInfo() {
    }

    public OtaDeviceInfo(Parcel in) {
        super(in);
        this.a2 = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13835, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.a2);
        }
    }
}
