package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;

public class NetworkInfoUpdateEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<NetworkInfoUpdateEvent> CREATOR = new Parcelable.Creator<NetworkInfoUpdateEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13345, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13344, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public NetworkInfoUpdateEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13343, new Class[]{Parcel.class}, NetworkInfoUpdateEvent.class);
            if (proxy.isSupported) {
                return (NetworkInfoUpdateEvent) proxy.result;
            }
            return new NetworkInfoUpdateEvent(in);
        }

        public NetworkInfoUpdateEvent[] b(int size) {
            return new NetworkInfoUpdateEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;

    public NetworkInfoUpdateEvent(Object sender, String type, int sequenceNumber, int ivIndex) {
        super(sender, type);
        this.c = sequenceNumber;
        this.d = ivIndex;
    }

    public NetworkInfoUpdateEvent(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
    }

    public int b() {
        return this.c;
    }

    public int a() {
        return this.d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13342, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
        }
    }
}
