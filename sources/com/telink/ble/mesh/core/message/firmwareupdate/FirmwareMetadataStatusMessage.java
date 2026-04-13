package com.telink.ble.mesh.core.message.firmwareupdate;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class FirmwareMetadataStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FirmwareMetadataStatusMessage> CREATOR = new Parcelable.Creator<FirmwareMetadataStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12597, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12596, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FirmwareMetadataStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12595, new Class[]{Parcel.class}, FirmwareMetadataStatusMessage.class);
            if (proxy.isSupported) {
                return (FirmwareMetadataStatusMessage) proxy.result;
            }
            return new FirmwareMetadataStatusMessage(in);
        }

        public FirmwareMetadataStatusMessage[] b(int size) {
            return new FirmwareMetadataStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;

    public FirmwareMetadataStatusMessage() {
    }

    public FirmwareMetadataStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
    }

    public void b(byte[] params) {
        this.c = params[0] & 7;
        this.d = (params[0] >> 3) & 31;
        this.f = params[0 + 1] & 255;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12594, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
        }
    }

    public int c() {
        return this.c;
    }
}
