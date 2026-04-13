package com.telink.ble.mesh.core.message.rp;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class ScanStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ScanStatusMessage> CREATOR = new Parcelable.Creator<ScanStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12757, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12756, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ScanStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12755, new Class[]{Parcel.class}, ScanStatusMessage.class);
            if (proxy.isSupported) {
                return (ScanStatusMessage) proxy.result;
            }
            return new ScanStatusMessage(in);
        }

        public ScanStatusMessage[] b(int size) {
            return new ScanStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private byte d;
    private byte f;
    private byte q;

    public ScanStatusMessage() {
    }

    public ScanStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.readByte();
        this.f = in.readByte();
        this.q = in.readByte();
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12754, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeByte(this.d);
            dest.writeByte(this.f);
            dest.writeByte(this.q);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void b(byte[] params) {
        int index = 0 + 1;
        this.c = params[0];
        int index2 = index + 1;
        this.d = params[index];
        this.f = params[index2];
        this.q = params[index2 + 1];
    }
}
