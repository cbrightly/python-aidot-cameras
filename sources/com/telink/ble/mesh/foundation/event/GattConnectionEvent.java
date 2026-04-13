package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;

public class GattConnectionEvent extends Event<String> {
    public static final Parcelable.Creator<GattConnectionEvent> CREATOR = new Parcelable.Creator<GattConnectionEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13329, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13328, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public GattConnectionEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13327, new Class[]{Parcel.class}, GattConnectionEvent.class);
            if (proxy.isSupported) {
                return (GattConnectionEvent) proxy.result;
            }
            return new GattConnectionEvent(in);
        }

        public GattConnectionEvent[] b(int size) {
            return new GattConnectionEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private String c;

    public GattConnectionEvent(Object sender, String type, String desc) {
        super(sender, type);
        this.c = desc;
    }

    public GattConnectionEvent(Parcel in) {
        this.c = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13326, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.c);
        }
    }
}
