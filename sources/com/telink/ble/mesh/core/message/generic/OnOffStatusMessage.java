package com.telink.ble.mesh.core.message.generic;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class OnOffStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<OnOffStatusMessage> CREATOR = new Parcelable.Creator<OnOffStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12684, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12683, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public OnOffStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12682, new Class[]{Parcel.class}, OnOffStatusMessage.class);
            if (proxy.isSupported) {
                return (OnOffStatusMessage) proxy.result;
            }
            return new OnOffStatusMessage(in);
        }

        public OnOffStatusMessage[] b(int size) {
            return new OnOffStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private byte d;
    private byte f;
    private boolean q = false;

    public OnOffStatusMessage() {
    }

    public OnOffStatusMessage(Parcel in) {
        boolean z = false;
        this.c = in.readByte();
        this.d = in.readByte();
        this.f = in.readByte();
        this.q = in.readByte() != 0 ? true : z;
    }

    public void b(byte[] params) {
        this.c = params[0];
        if (params.length == 3) {
            this.q = true;
            this.d = params[1];
            this.f = params[2];
        }
    }

    public byte c() {
        return this.c;
    }

    public byte d() {
        return this.d;
    }

    public boolean e() {
        return this.q;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12681, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeByte(this.d);
            dest.writeByte(this.f);
            dest.writeByte(this.q ? (byte) 1 : 0);
        }
    }
}
