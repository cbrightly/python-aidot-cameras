package com.telink.ble.mesh.core.message.firmwareupdate;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class FirmwareUpdateStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FirmwareUpdateStatusMessage> CREATOR = new Parcelable.Creator<FirmwareUpdateStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12620, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12619, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FirmwareUpdateStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12618, new Class[]{Parcel.class}, FirmwareUpdateStatusMessage.class);
            if (proxy.isSupported) {
                return (FirmwareUpdateStatusMessage) proxy.result;
            }
            return new FirmwareUpdateStatusMessage(in);
        }

        public FirmwareUpdateStatusMessage[] b(int size) {
            return new FirmwareUpdateStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private byte f;
    private boolean p0 = false;
    private int q;
    private int x;
    private long y;
    private int z;

    public FirmwareUpdateStatusMessage() {
    }

    public FirmwareUpdateStatusMessage(Parcel in) {
        boolean z2 = false;
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readByte();
        this.q = in.readInt();
        this.x = in.readInt();
        this.y = in.readLong();
        this.z = in.readInt();
        this.p0 = in.readByte() != 0 ? true : z2;
    }

    public void b(byte[] params) {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12615, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0] & 7;
            this.d = (params[0] & 255) >> 5;
            if (params.length > 1) {
                z2 = true;
            }
            this.p0 = z2;
            if (z2) {
                int index = 0 + 1;
                int index2 = index + 1;
                this.f = params[index];
                int index3 = index2 + 1;
                this.q = params[index2] & 31;
                ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                this.x = MeshUtils.b(params, index3, 2, byteOrder);
                int index4 = index3 + 2;
                this.y = (long) MeshUtils.b(params, index4, 8, byteOrder);
                this.z = params[index4 + 8] & 255;
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12616, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeByte(this.f);
            dest.writeInt(this.q);
            dest.writeInt(this.x);
            dest.writeLong(this.y);
            dest.writeInt(this.z);
            dest.writeByte(this.p0 ? (byte) 1 : 0);
        }
    }

    public int d() {
        return this.c;
    }

    public int c() {
        return this.d;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12617, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "FirmwareUpdateStatusMessage{status=" + this.c + ", phase=" + this.d + ", updateTtl=" + this.f + ", additionalInfo=" + this.q + ", updateTimeoutBase=" + this.x + ", updateBLOBID=0x" + Long.toHexString(this.y) + ", updateFirmwareImageIndex=" + this.z + ", isComplete=" + this.p0 + '}';
    }
}
