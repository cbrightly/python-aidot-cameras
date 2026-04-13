package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class BlobInfoStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<BlobInfoStatusMessage> CREATOR = new Parcelable.Creator<BlobInfoStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12644, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12643, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BlobInfoStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12642, new Class[]{Parcel.class}, BlobInfoStatusMessage.class);
            if (proxy.isSupported) {
                return (BlobInfoStatusMessage) proxy.result;
            }
            return new BlobInfoStatusMessage(in);
        }

        public BlobInfoStatusMessage[] b(int size) {
            return new BlobInfoStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;
    private int q;
    private int x;
    private int y;
    private int z;

    public BlobInfoStatusMessage() {
    }

    public BlobInfoStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12640, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0] & 255;
            int index2 = index + 1;
            this.d = params[index] & 255;
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.f = MeshUtils.b(params, index2, 2, byteOrder);
            int index3 = index2 + 2;
            this.q = MeshUtils.b(params, index3, 2, byteOrder);
            int index4 = index3 + 2;
            this.x = MeshUtils.b(params, index4, 4, byteOrder);
            int index5 = index4 + 4;
            this.y = MeshUtils.b(params, index5, 2, byteOrder);
            this.z = params[index5 + 2];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12641, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeInt(this.x);
            dest.writeInt(this.y);
            dest.writeInt(this.z);
        }
    }

    public int c() {
        return this.d;
    }

    public int d() {
        return this.q;
    }
}
