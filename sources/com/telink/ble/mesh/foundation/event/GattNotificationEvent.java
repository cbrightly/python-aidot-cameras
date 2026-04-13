package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;
import java.util.UUID;

public class GattNotificationEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<GattNotificationEvent> CREATOR = new Parcelable.Creator<GattNotificationEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13333, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13332, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public GattNotificationEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13331, new Class[]{Parcel.class}, GattNotificationEvent.class);
            if (proxy.isSupported) {
                return (GattNotificationEvent) proxy.result;
            }
            return new GattNotificationEvent(in);
        }

        public GattNotificationEvent[] b(int size) {
            return new GattNotificationEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private UUID c;
    private UUID d;
    private byte[] f;

    public GattNotificationEvent(Object sender, String type, UUID serviceUUID, UUID characteristicUUID, byte[] data) {
        super(sender, type);
        this.c = serviceUUID;
        this.d = characteristicUUID;
        this.f = data;
    }

    public GattNotificationEvent(Parcel in) {
        this.c = UUID.fromString(in.readString());
        this.d = UUID.fromString(in.readString());
        this.f = in.createByteArray();
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13330, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.c.toString());
            dest.writeString(this.d.toString());
            dest.writeByteArray(this.f);
        }
    }

    public int describeContents() {
        return 0;
    }
}
