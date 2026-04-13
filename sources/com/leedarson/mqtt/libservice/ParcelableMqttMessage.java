package com.leedarson.mqtt.libservice;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.eclipse.paho.client.mqttv3.l;

public class ParcelableMqttMessage extends l implements Parcelable {
    public static final Parcelable.Creator<ParcelableMqttMessage> CREATOR = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    String z = null;

    ParcelableMqttMessage(l original) {
        super(original.c());
        k(original.d());
        l(original.f());
        g(original.e());
    }

    ParcelableMqttMessage(Parcel parcel) {
        super(parcel.createByteArray());
        k(parcel.readInt());
        boolean[] flags = parcel.createBooleanArray();
        l(flags[0]);
        g(flags[1]);
        this.z = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (!PatchProxy.proxy(new Object[]{parcel, new Integer(i)}, this, changeQuickRedirect, false, 1692, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            parcel.writeByteArray(c());
            parcel.writeInt(d());
            parcel.writeBooleanArray(new boolean[]{f(), e()});
            parcel.writeString(this.z);
        }
    }

    public class a implements Parcelable.Creator<ParcelableMqttMessage> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 1695, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 1694, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ParcelableMqttMessage a(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 1693, new Class[]{Parcel.class}, ParcelableMqttMessage.class);
            if (proxy.isSupported) {
                return (ParcelableMqttMessage) proxy.result;
            }
            return new ParcelableMqttMessage(parcel);
        }

        public ParcelableMqttMessage[] b(int size) {
            return new ParcelableMqttMessage[size];
        }
    }
}
