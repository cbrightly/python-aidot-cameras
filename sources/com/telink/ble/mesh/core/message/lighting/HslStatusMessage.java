package com.telink.ble.mesh.core.message.lighting;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class HslStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<HslStatusMessage> CREATOR = new Parcelable.Creator<HslStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12712, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12711, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public HslStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12710, new Class[]{Parcel.class}, HslStatusMessage.class);
            if (proxy.isSupported) {
                return (HslStatusMessage) proxy.result;
            }
            return new HslStatusMessage(in);
        }

        public HslStatusMessage[] b(int size) {
            return new HslStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;
    private byte q;
    private boolean x = false;

    public HslStatusMessage() {
    }

    public HslStatusMessage(Parcel in) {
        boolean z = false;
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readByte();
        this.x = in.readByte() != 0 ? true : z;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12708, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeByte(this.q);
            dest.writeByte(this.x ? (byte) 1 : 0);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12709, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.c = MeshUtils.b(params, 0, 2, byteOrder);
            int index = 0 + 2;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.f = MeshUtils.b(params, index2, 2, byteOrder);
            int index3 = index2 + 2;
            if (params.length == 7) {
                this.x = true;
                this.q = params[index3];
            }
        }
    }

    public int d() {
        return this.c;
    }

    public int c() {
        return this.d;
    }

    public int e() {
        return this.f;
    }
}
