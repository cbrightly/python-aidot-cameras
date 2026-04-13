package com.telink.ble.mesh.core.message.rp;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class LinkStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<LinkStatusMessage> CREATOR = new Parcelable.Creator<LinkStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12733, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12732, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public LinkStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12731, new Class[]{Parcel.class}, LinkStatusMessage.class);
            if (proxy.isSupported) {
                return (LinkStatusMessage) proxy.result;
            }
            return new LinkStatusMessage(in);
        }

        public LinkStatusMessage[] b(int size) {
            return new LinkStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private byte d;

    public LinkStatusMessage() {
    }

    public LinkStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.readByte();
    }

    public void b(byte[] params) {
        int index = 0 + 1;
        this.c = params[0];
        int i = index + 1;
        this.d = params[index];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12729, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeByte(this.d);
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12730, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "LinkStatusMessage{status=" + this.c + ", rpState=" + this.d + '}';
    }
}
