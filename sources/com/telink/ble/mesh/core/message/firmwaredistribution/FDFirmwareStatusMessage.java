package com.telink.ble.mesh.core.message.firmwaredistribution;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class FDFirmwareStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FDFirmwareStatusMessage> CREATOR = new Parcelable.Creator<FDFirmwareStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12556, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12555, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FDFirmwareStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12554, new Class[]{Parcel.class}, FDFirmwareStatusMessage.class);
            if (proxy.isSupported) {
                return (FDFirmwareStatusMessage) proxy.result;
            }
            return new FDFirmwareStatusMessage(in);
        }

        public FDFirmwareStatusMessage[] b(int size) {
            return new FDFirmwareStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public int c;
    public int d;
    public int f;
    public byte[] q;

    public FDFirmwareStatusMessage() {
    }

    public FDFirmwareStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.createByteArray();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12552, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0] & 255;
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.f = MeshUtils.b(params, index2, 2, byteOrder);
            int index3 = index2 + 2;
            if (params.length != 5) {
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
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12553, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeByteArray(this.q);
        }
    }
}
