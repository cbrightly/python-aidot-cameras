package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class NetworkTransmitStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<NetworkTransmitStatusMessage> CREATOR = new Parcelable.Creator<NetworkTransmitStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12505, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12504, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public NetworkTransmitStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12503, new Class[]{Parcel.class}, NetworkTransmitStatusMessage.class);
            if (proxy.isSupported) {
                return (NetworkTransmitStatusMessage) proxy.result;
            }
            return new NetworkTransmitStatusMessage(in);
        }

        public NetworkTransmitStatusMessage[] b(int size) {
            return new NetworkTransmitStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;

    public NetworkTransmitStatusMessage() {
    }

    public NetworkTransmitStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
    }

    public void b(byte[] data) {
        this.c = data[0] & 7;
        this.d = (data[0] & 255) >> 3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12502, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
        }
    }
}
