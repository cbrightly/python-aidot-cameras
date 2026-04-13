package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.MeshUpdatingDevice;
import com.telink.ble.mesh.foundation.Event;

public class FirmwareUpdatingEvent extends Event<String> {
    public static final Parcelable.Creator<FirmwareUpdatingEvent> CREATOR = new Parcelable.Creator<FirmwareUpdatingEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13325, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13324, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FirmwareUpdatingEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13323, new Class[]{Parcel.class}, FirmwareUpdatingEvent.class);
            if (proxy.isSupported) {
                return (FirmwareUpdatingEvent) proxy.result;
            }
            return new FirmwareUpdatingEvent(in);
        }

        public FirmwareUpdatingEvent[] b(int size) {
            return new FirmwareUpdatingEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private MeshUpdatingDevice c;
    private int d;
    private String f;

    public FirmwareUpdatingEvent(Object sender, String type) {
        super(sender, type);
    }

    public FirmwareUpdatingEvent(Parcel in) {
        this.c = (MeshUpdatingDevice) in.readParcelable(MeshUpdatingDevice.class.getClassLoader());
        this.d = in.readInt();
        this.f = in.readString();
    }

    public void c(MeshUpdatingDevice updatingDevice) {
        this.c = updatingDevice;
    }

    public void b(int progress) {
        this.d = progress;
    }

    public void a(String desc) {
        this.f = desc;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13322, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
            dest.writeInt(this.d);
            dest.writeString(this.f);
        }
    }
}
