package com.telink.ble.mesh.core.message.lighting;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class LightnessStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<LightnessStatusMessage> CREATOR = new Parcelable.Creator<LightnessStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12726, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12725, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public LightnessStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12724, new Class[]{Parcel.class}, LightnessStatusMessage.class);
            if (proxy.isSupported) {
                return (LightnessStatusMessage) proxy.result;
            }
            return new LightnessStatusMessage(in);
        }

        public LightnessStatusMessage[] b(int size) {
            return new LightnessStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private byte f;
    private boolean q = false;

    public LightnessStatusMessage() {
    }

    public LightnessStatusMessage(Parcel in) {
        boolean z = false;
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readByte();
        this.q = in.readByte() != 0 ? true : z;
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12722, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.c = MeshUtils.b(params, 0, 2, byteOrder);
            if (params.length == 5) {
                this.q = true;
                this.d = MeshUtils.b(params, 2, 2, byteOrder);
                this.f = params[4];
            }
        }
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public boolean e() {
        return this.q;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12723, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeByte(this.f);
            dest.writeByte(this.q ? (byte) 1 : 0);
        }
    }
}
