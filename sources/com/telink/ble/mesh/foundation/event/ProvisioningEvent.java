package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.ProvisioningDevice;
import com.telink.ble.mesh.foundation.Event;

public class ProvisioningEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<ProvisioningEvent> CREATOR = new Parcelable.Creator<ProvisioningEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13355, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13354, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ProvisioningEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13353, new Class[]{Parcel.class}, ProvisioningEvent.class);
            if (proxy.isSupported) {
                return (ProvisioningEvent) proxy.result;
            }
            return new ProvisioningEvent(in);
        }

        public ProvisioningEvent[] b(int size) {
            return new ProvisioningEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private ProvisioningDevice c;
    private String d;

    public ProvisioningEvent(Object sender, String type, ProvisioningDevice provisioningDevice, String desc) {
        super(sender, type);
        this.c = provisioningDevice;
        this.d = desc;
    }

    public ProvisioningEvent(Parcel in) {
        this.c = (ProvisioningDevice) in.readParcelable(ProvisioningDevice.class.getClassLoader());
        this.d = in.readString();
    }

    public ProvisioningDevice b() {
        return this.c;
    }

    public String a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13351, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return this.d + ",rssi=" + this.c.h();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13352, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
            dest.writeString(this.d);
        }
    }
}
