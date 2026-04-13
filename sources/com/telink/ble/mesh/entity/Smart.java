package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;

public class Smart implements Serializable, Parcelable {
    public static final Parcelable.Creator<Smart> CREATOR = new Parcelable.Creator<Smart>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13061, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13060, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public Smart a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13059, new Class[]{Parcel.class}, Smart.class);
            if (proxy.isSupported) {
                return (Smart) proxy.result;
            }
            return new Smart(in);
        }

        public Smart[] b(int size) {
            return new Smart[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public int smartAddress;
    public int smartId;

    public Smart(int smartId2, int smartAddress2) {
        this.smartId = smartId2;
        this.smartAddress = smartAddress2;
    }

    public Smart(Parcel in) {
        this.smartId = in.readInt();
        this.smartAddress = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13058, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.smartId);
            dest.writeInt(this.smartAddress);
        }
    }
}
