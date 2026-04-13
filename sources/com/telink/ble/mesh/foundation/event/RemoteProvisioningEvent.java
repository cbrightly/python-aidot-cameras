package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.RemoteProvisioningDevice;
import com.telink.ble.mesh.foundation.Event;

public class RemoteProvisioningEvent extends Event<String> {
    public static final Parcelable.Creator<RemoteProvisioningEvent> CREATOR = new Parcelable.Creator<RemoteProvisioningEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13363, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13362, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public RemoteProvisioningEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13361, new Class[]{Parcel.class}, RemoteProvisioningEvent.class);
            if (proxy.isSupported) {
                return (RemoteProvisioningEvent) proxy.result;
            }
            return new RemoteProvisioningEvent(in);
        }

        public RemoteProvisioningEvent[] b(int size) {
            return new RemoteProvisioningEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private RemoteProvisioningDevice c;
    private String d;

    public RemoteProvisioningEvent(Object sender, String type) {
        super(sender, type);
    }

    public RemoteProvisioningEvent(Parcel in) {
        this.c = (RemoteProvisioningDevice) in.readParcelable(RemoteProvisioningDevice.class.getClassLoader());
        this.d = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13360, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
            dest.writeString(this.d);
        }
    }

    public void b(RemoteProvisioningDevice remoteProvisioningDevice) {
        this.c = remoteProvisioningDevice;
    }

    public void a(String desc) {
        this.d = desc;
    }
}
