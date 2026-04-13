package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.foundation.Event;

public class ScanEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<ScanEvent> CREATOR = new Parcelable.Creator<ScanEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13367, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13366, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ScanEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13365, new Class[]{Parcel.class}, ScanEvent.class);
            if (proxy.isSupported) {
                return (ScanEvent) proxy.result;
            }
            return new ScanEvent(in);
        }

        public ScanEvent[] b(int size) {
            return new ScanEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private AdvertisingDevice c;
    private int d;

    public int b() {
        return this.d;
    }

    public void c(int errorCode) {
        this.d = errorCode;
    }

    public ScanEvent(Object sender, String type, AdvertisingDevice advertisingDevice) {
        super(sender, type);
        this.c = advertisingDevice;
    }

    public ScanEvent(Parcel in) {
        this.c = (AdvertisingDevice) in.readParcelable(AdvertisingDevice.class.getClassLoader());
    }

    public AdvertisingDevice a() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13364, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
        }
    }
}
