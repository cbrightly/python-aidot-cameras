package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class ModelAppStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ModelAppStatusMessage> CREATOR = new Parcelable.Creator<ModelAppStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12472, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12471, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ModelAppStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12470, new Class[]{Parcel.class}, ModelAppStatusMessage.class);
            if (proxy.isSupported) {
                return (ModelAppStatusMessage) proxy.result;
            }
            return new ModelAppStatusMessage(in);
        }

        public ModelAppStatusMessage[] b(int size) {
            return new ModelAppStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private int d;
    private int f;
    private int q;

    public ModelAppStatusMessage() {
    }

    public ModelAppStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
    }

    public void b(byte[] params) {
        int modelIdLen;
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12468, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0];
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.f = MeshUtils.b(params, index2, 2, byteOrder);
            int index3 = index2 + 2;
            if (params.length == 7) {
                modelIdLen = 2;
            } else {
                modelIdLen = 4;
            }
            this.q = MeshUtils.b(params, index3, modelIdLen, byteOrder);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12469, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
        }
    }

    public byte d() {
        return this.c;
    }

    public int c() {
        return this.q;
    }
}
