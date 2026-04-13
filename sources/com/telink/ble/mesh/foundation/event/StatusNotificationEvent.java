package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.foundation.Event;

public class StatusNotificationEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<StatusNotificationEvent> CREATOR = new Parcelable.Creator<StatusNotificationEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13371, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13370, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public StatusNotificationEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13369, new Class[]{Parcel.class}, StatusNotificationEvent.class);
            if (proxy.isSupported) {
                return (StatusNotificationEvent) proxy.result;
            }
            return new StatusNotificationEvent(in);
        }

        public StatusNotificationEvent[] b(int size) {
            return new StatusNotificationEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private NotificationMessage c;

    public StatusNotificationEvent(Object sender, String type, NotificationMessage notificationMessage) {
        super(sender, type);
        this.c = notificationMessage;
    }

    public StatusNotificationEvent(Parcel in) {
        this.c = (NotificationMessage) in.readParcelable(NotificationMessage.class.getClassLoader());
    }

    public NotificationMessage a() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13368, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
        }
    }
}
