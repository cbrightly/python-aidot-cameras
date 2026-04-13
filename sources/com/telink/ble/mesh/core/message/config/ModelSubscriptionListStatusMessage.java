package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class ModelSubscriptionListStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ModelSubscriptionListStatusMessage> CREATOR = new Parcelable.Creator<ModelSubscriptionListStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12486, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12485, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ModelSubscriptionListStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12484, new Class[]{Parcel.class}, ModelSubscriptionListStatusMessage.class);
            if (proxy.isSupported) {
                return (ModelSubscriptionListStatusMessage) proxy.result;
            }
            return new ModelSubscriptionListStatusMessage(in);
        }

        public ModelSubscriptionListStatusMessage[] b(int size) {
            return new ModelSubscriptionListStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private int d;
    private int f;
    private List<Integer> q;
    private boolean x;

    public ModelSubscriptionListStatusMessage() {
        this.q = new ArrayList();
        this.x = true;
    }

    public ModelSubscriptionListStatusMessage(Parcel in) {
        this.q = new ArrayList();
        boolean z = true;
        this.x = true;
        this.c = in.readByte();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readArrayList(getClass().getClassLoader());
        this.x = in.readByte() == 0 ? false : z;
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12481, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0];
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.f = MeshUtils.b(params, index2, this.x ? 2 : 4, byteOrder);
            for (int i = index2 + 2; i < params.length; i += 2) {
                this.q.add(Integer.valueOf(MeshUtils.b(params, i, 2, ByteOrder.LITTLE_ENDIAN)));
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12482, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeList(this.q);
            dest.writeByte(this.x ? (byte) 1 : 0);
        }
    }

    public byte e() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public List<Integer> c() {
        return this.q;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12483, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ModelSubscriptionStatusMessage{status=" + this.c + ", elementAddress=" + this.d + ", modelIdentifier=" + this.f + ", isSig=" + this.x + ", bindedAddresses:" + this.q + '}';
    }
}
