package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.FastProvisioningDevice;
import com.telink.ble.mesh.foundation.Event;

public class FastProvisioningEvent extends Event<String> {
    public static final Parcelable.Creator<FastProvisioningEvent> CREATOR = new Parcelable.Creator<FastProvisioningEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13321, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13320, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FastProvisioningEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13319, new Class[]{Parcel.class}, FastProvisioningEvent.class);
            if (proxy.isSupported) {
                return (FastProvisioningEvent) proxy.result;
            }
            return new FastProvisioningEvent(in);
        }

        public FastProvisioningEvent[] b(int size) {
            return new FastProvisioningEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private FastProvisioningDevice c;
    private String d;

    public FastProvisioningEvent(Object sender, String type) {
        super(sender, type);
    }

    public FastProvisioningEvent(Parcel in) {
        this.c = (FastProvisioningDevice) in.readParcelable(FastProvisioningDevice.class.getClassLoader());
        this.d = in.readString();
    }

    public void a(String desc) {
        this.d = desc;
    }

    public void b(FastProvisioningDevice fastProvisioningDevice) {
        this.c = fastProvisioningDevice;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13318, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
            dest.writeString(this.d);
        }
    }
}
