package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;

public class GattOtaEvent extends Event<String> {
    public static final Parcelable.Creator<GattOtaEvent> CREATOR = new Parcelable.Creator<GattOtaEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13337, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13336, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public GattOtaEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13335, new Class[]{Parcel.class}, GattOtaEvent.class);
            if (proxy.isSupported) {
                return (GattOtaEvent) proxy.result;
            }
            return new GattOtaEvent(in);
        }

        public GattOtaEvent[] b(int size) {
            return new GattOtaEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private String d;

    public GattOtaEvent(Object sender, String type, int progress, String desc) {
        super(sender, type);
        this.c = progress;
        this.d = desc;
    }

    public GattOtaEvent(Parcel in) {
        this.c = in.readInt();
        this.d = in.readString();
    }

    public String a() {
        return this.d;
    }

    public int b() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13334, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeString(this.d);
        }
    }
}
