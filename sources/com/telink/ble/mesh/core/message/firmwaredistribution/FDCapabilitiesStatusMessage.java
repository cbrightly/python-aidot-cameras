package com.telink.ble.mesh.core.message.firmwaredistribution;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import com.telink.ble.mesh.core.message.firmwaredistribution.DistributorCapabilities;
import java.nio.ByteOrder;

public class FDCapabilitiesStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FDCapabilitiesStatusMessage> CREATOR = new Parcelable.Creator<FDCapabilitiesStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12547, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12546, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FDCapabilitiesStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12545, new Class[]{Parcel.class}, FDCapabilitiesStatusMessage.class);
            if (proxy.isSupported) {
                return (FDCapabilitiesStatusMessage) proxy.result;
            }
            return new FDCapabilitiesStatusMessage(in);
        }

        public FDCapabilitiesStatusMessage[] b(int size) {
            return new FDCapabilitiesStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public int c;
    private int d;
    private int f;
    private int q;
    private int x;
    private int y;
    private byte[] z;

    public FDCapabilitiesStatusMessage() {
    }

    public FDCapabilitiesStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.createByteArray();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12543, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.c = MeshUtils.b(params, 0, 2, byteOrder);
            int index = 0 + 2;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.f = MeshUtils.b(params, index2, 4, byteOrder);
            int index3 = index2 + 4;
            this.q = MeshUtils.b(params, index3, 4, byteOrder);
            int index4 = index3 + 4;
            this.x = MeshUtils.b(params, index4, 4, byteOrder);
            int index5 = index4 + 4;
            int index6 = index5 + 1;
            byte b = params[index5] & 255;
            this.y = b;
            if (b == DistributorCapabilities.OOBRetrievalSupported.SUPPORTED.value) {
                byte[] bArr = new byte[(params.length - index6)];
                this.z = bArr;
                System.arraycopy(params, index6, bArr, 0, bArr.length);
                return;
            }
            this.z = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12544, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeInt(this.x);
            dest.writeInt(this.y);
            dest.writeByteArray(this.z);
        }
    }
}
