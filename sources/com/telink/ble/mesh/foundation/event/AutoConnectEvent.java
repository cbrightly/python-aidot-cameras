package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;

public class AutoConnectEvent extends Event<String> {
    public static final Parcelable.Creator<AutoConnectEvent> CREATOR = new Parcelable.Creator<AutoConnectEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13309, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13308, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public AutoConnectEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13307, new Class[]{Parcel.class}, AutoConnectEvent.class);
            if (proxy.isSupported) {
                return (AutoConnectEvent) proxy.result;
            }
            return new AutoConnectEvent(in);
        }

        public AutoConnectEvent[] b(int size) {
            return new AutoConnectEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;

    public AutoConnectEvent(Object sender, String type, int connectedAddress) {
        super(sender, type);
        this.c = connectedAddress;
    }

    public AutoConnectEvent(Parcel in) {
        this.c = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13306, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
        }
    }
}
