package com.telink.ble.mesh.core.message.firmwaredistribution;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class FDUploadStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FDUploadStatusMessage> CREATOR = new Parcelable.Creator<FDUploadStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12585, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12584, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FDUploadStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12583, new Class[]{Parcel.class}, FDUploadStatusMessage.class);
            if (proxy.isSupported) {
                return (FDUploadStatusMessage) proxy.result;
            }
            return new FDUploadStatusMessage(in);
        }

        public FDUploadStatusMessage[] b(int size) {
            return new FDUploadStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    public int d;
    public int f;
    public byte[] q;

    public FDUploadStatusMessage() {
    }

    public FDUploadStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.createByteArray();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12581, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0] & 255;
            int index2 = index + 1;
            this.d = params[index] & 255;
            if (params.length != 2) {
                int index3 = index2 + 1;
                this.f = params[index2] & 255;
                byte[] bArr = new byte[(params.length - index3)];
                this.q = bArr;
                System.arraycopy(params, index3, bArr, 0, bArr.length);
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12582, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeByteArray(this.q);
        }
    }
}
