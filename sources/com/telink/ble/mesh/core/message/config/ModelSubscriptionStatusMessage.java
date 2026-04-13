package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class ModelSubscriptionStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ModelSubscriptionStatusMessage> CREATOR = new Parcelable.Creator<ModelSubscriptionStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12494, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12493, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ModelSubscriptionStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12492, new Class[]{Parcel.class}, ModelSubscriptionStatusMessage.class);
            if (proxy.isSupported) {
                return (ModelSubscriptionStatusMessage) proxy.result;
            }
            return new ModelSubscriptionStatusMessage(in);
        }

        public ModelSubscriptionStatusMessage[] b(int size) {
            return new ModelSubscriptionStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private int d;
    private int f;
    private int q;
    private boolean x = true;

    public ModelSubscriptionStatusMessage() {
    }

    public ModelSubscriptionStatusMessage(Parcel in) {
        boolean z = true;
        this.c = in.readByte();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.readByte() == 0 ? false : z;
    }

    public void b(byte[] params) {
        boolean z = true;
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12489, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (params.length != 7) {
                z = false;
            }
            this.x = z;
            int index = 0 + 1;
            this.c = params[0];
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            int i = 2;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.f = MeshUtils.b(params, index2, 2, byteOrder);
            int index3 = index2 + 2;
            if (!this.x) {
                i = 4;
            }
            this.q = MeshUtils.b(params, index3, i, byteOrder);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12490, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeByte(this.x ? (byte) 1 : 0);
        }
    }

    public byte c() {
        return this.c;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12491, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ModelSubscriptionStatusMessage{status=" + this.c + ", elementAddress=" + this.d + ", address=" + this.f + ", modelIdentifier=" + this.q + ", isSig=" + this.x + '}';
    }
}
