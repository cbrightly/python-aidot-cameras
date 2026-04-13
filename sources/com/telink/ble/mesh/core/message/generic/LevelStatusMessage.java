package com.telink.ble.mesh.core.message.generic;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class LevelStatusMessage extends StatusMessage {
    public static final Parcelable.Creator<LevelStatusMessage> CREATOR = new Parcelable.Creator<LevelStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12676, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12675, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public LevelStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12674, new Class[]{Parcel.class}, LevelStatusMessage.class);
            if (proxy.isSupported) {
                return (LevelStatusMessage) proxy.result;
            }
            return new LevelStatusMessage(in);
        }

        public LevelStatusMessage[] b(int size) {
            return new LevelStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private byte f;
    private boolean q = false;

    public LevelStatusMessage() {
    }

    public LevelStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readByte();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12672, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.c = MeshUtils.b(params, 0, 2, byteOrder);
            int index = 0 + 2;
            if (params.length == 5) {
                this.q = true;
                this.d = MeshUtils.b(params, index, 2, byteOrder);
                this.f = params[index + 2];
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12673, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeByte(this.f);
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
}
