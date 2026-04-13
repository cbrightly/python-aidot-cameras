package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;

public class BluetoothEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<BluetoothEvent> CREATOR = new Parcelable.Creator<BluetoothEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13317, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13316, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BluetoothEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13315, new Class[]{Parcel.class}, BluetoothEvent.class);
            if (proxy.isSupported) {
                return (BluetoothEvent) proxy.result;
            }
            return new BluetoothEvent(in);
        }

        public BluetoothEvent[] b(int size) {
            return new BluetoothEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private String d;

    public BluetoothEvent(Object sender, String type) {
        super(sender, type);
    }

    public int a() {
        return this.c;
    }

    public void c(int state) {
        this.c = state;
    }

    public void b(String desc) {
        this.d = desc;
    }

    public BluetoothEvent(Parcel in) {
        this.c = in.readInt();
        this.d = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13314, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeString(this.d);
        }
    }
}
