package com.telink.ble.mesh.core.message.fastpv;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;
import java.util.Arrays;

public class MeshAddressStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<MeshAddressStatusMessage> CREATOR = new Parcelable.Creator<MeshAddressStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12527, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12526, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public MeshAddressStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12525, new Class[]{Parcel.class}, MeshAddressStatusMessage.class);
            if (proxy.isSupported) {
                return (MeshAddressStatusMessage) proxy.result;
            }
            return new MeshAddressStatusMessage(in);
        }

        public MeshAddressStatusMessage[] b(int size) {
            return new MeshAddressStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] c;
    private int d;

    public MeshAddressStatusMessage() {
    }

    public MeshAddressStatusMessage(Parcel in) {
        this.c = in.createByteArray();
        this.d = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12522, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            byte[] bArr = new byte[6];
            this.c = bArr;
            System.arraycopy(params, 0, bArr, 0, 6);
            this.d = MeshUtils.b(params, 0 + 6, 2, ByteOrder.LITTLE_ENDIAN);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12523, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByteArray(this.c);
            dest.writeInt(this.d);
        }
    }

    public byte[] c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12524, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "MeshAddressStatusMessage{mac=" + Arrays.toString(this.c) + ", pid=" + this.d + '}';
    }
}
