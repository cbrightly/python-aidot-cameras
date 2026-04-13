package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.BindingDevice;
import com.telink.ble.mesh.foundation.Event;

public class BindingEvent extends Event<String> {
    public static final Parcelable.Creator<BindingEvent> CREATOR = new Parcelable.Creator<BindingEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13313, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13312, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BindingEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13311, new Class[]{Parcel.class}, BindingEvent.class);
            if (proxy.isSupported) {
                return (BindingEvent) proxy.result;
            }
            return new BindingEvent(in);
        }

        public BindingEvent[] b(int size) {
            return new BindingEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private BindingDevice c;
    private String d;

    public BindingEvent(Object sender, String type, BindingDevice bindingDevice, String desc) {
        super(sender, type);
        this.c = bindingDevice;
        this.d = desc;
    }

    public BindingEvent(Parcel in) {
        this.c = (BindingDevice) in.readParcelable(BindingDevice.class.getClassLoader());
        this.d = in.readString();
    }

    public String b() {
        return this.d;
    }

    public BindingDevice a() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13310, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
            dest.writeString(this.d);
        }
    }
}
