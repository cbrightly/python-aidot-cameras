package com.telink.ble.mesh.core.message.lighting;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class CtlTemperatureStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<CtlTemperatureStatusMessage> CREATOR = new Parcelable.Creator<CtlTemperatureStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12703, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12702, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public CtlTemperatureStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12701, new Class[]{Parcel.class}, CtlTemperatureStatusMessage.class);
            if (proxy.isSupported) {
                return (CtlTemperatureStatusMessage) proxy.result;
            }
            return new CtlTemperatureStatusMessage(in);
        }

        public CtlTemperatureStatusMessage[] b(int size) {
            return new CtlTemperatureStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;
    private int q;
    private byte x;
    private boolean y = false;

    public CtlTemperatureStatusMessage() {
    }

    public CtlTemperatureStatusMessage(Parcel in) {
        boolean z = false;
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.readByte();
        this.y = in.readByte() != 0 ? true : z;
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12699, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.c = MeshUtils.b(params, 0, 2, byteOrder);
            int index = 0 + 2;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            if (params.length == 9) {
                this.y = true;
                this.f = MeshUtils.b(params, index2, 2, byteOrder);
                int index3 = index2 + 2;
                this.q = MeshUtils.b(params, index3, 2, byteOrder);
                this.x = params[index3 + 2];
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12700, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeByte(this.x);
            dest.writeByte(this.y ? (byte) 1 : 0);
        }
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.f;
    }

    public boolean e() {
        return this.y;
    }
}
