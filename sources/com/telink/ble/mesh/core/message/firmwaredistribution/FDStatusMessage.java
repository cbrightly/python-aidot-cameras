package com.telink.ble.mesh.core.message.firmwaredistribution;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class FDStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FDStatusMessage> CREATOR = new Parcelable.Creator<FDStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12576, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12575, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FDStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12574, new Class[]{Parcel.class}, FDStatusMessage.class);
            if (proxy.isSupported) {
                return (FDStatusMessage) proxy.result;
            }
            return new FDStatusMessage(in);
        }

        public FDStatusMessage[] b(int size) {
            return new FDStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a1;
    public int c;
    public int d;
    public int f;
    public int p0;
    public int p1;
    public int q;
    public int x;
    public int y;
    public int z;

    public FDStatusMessage() {
    }

    public FDStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
        this.p0 = in.readInt();
        this.a1 = in.readInt();
        this.p1 = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12572, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0] & 255;
            int index2 = index + 1;
            this.d = params[index] & 255;
            if (params.length != 2) {
                ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                this.f = MeshUtils.b(params, index2, 2, byteOrder);
                int index3 = index2 + 2;
                this.q = MeshUtils.b(params, index3, 2, byteOrder);
                int index4 = index3 + 2;
                int index5 = index4 + 1;
                this.x = params[index4] & 255;
                this.y = MeshUtils.b(params, index5, 2, byteOrder);
                int index6 = index5 + 2;
                this.z = params[index6] & 3;
                this.p0 = 1 & (params[index6] >> 2);
                this.a1 = (params[index6] >> 3) & 31;
                this.p1 = MeshUtils.b(params, index6, 2, byteOrder);
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12573, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeInt(this.x);
            dest.writeInt(this.y);
            dest.writeInt(this.z);
            dest.writeInt(this.p0);
            dest.writeInt(this.a1);
            dest.writeInt(this.p1);
        }
    }
}
