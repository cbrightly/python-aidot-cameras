package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class OnlineStatusInfo implements Parcelable {
    public static final Parcelable.Creator<OnlineStatusInfo> CREATOR = new Parcelable.Creator<OnlineStatusInfo>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13034, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13033, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public OnlineStatusInfo a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13032, new Class[]{Parcel.class}, OnlineStatusInfo.class);
            if (proxy.isSupported) {
                return (OnlineStatusInfo) proxy.result;
            }
            return new OnlineStatusInfo(in);
        }

        public OnlineStatusInfo[] b(int size) {
            return new OnlineStatusInfo[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public int c;
    public byte d;
    public byte[] f;

    public OnlineStatusInfo() {
    }

    public OnlineStatusInfo(Parcel in) {
        this.c = in.readInt();
        this.d = in.readByte();
        this.f = in.createByteArray();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13031, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeByte(this.d);
            dest.writeByteArray(this.f);
        }
    }
}
