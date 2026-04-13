package com.telink.ble.mesh.core.message.time;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class TimeStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<TimeStatusMessage> CREATOR = new Parcelable.Creator<TimeStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12799, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12798, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public TimeStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12797, new Class[]{Parcel.class}, TimeStatusMessage.class);
            if (proxy.isSupported) {
                return (TimeStatusMessage) proxy.result;
            }
            return new TimeStatusMessage(in);
        }

        public TimeStatusMessage[] b(int size) {
            return new TimeStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private long c;
    private byte d;
    private byte f;
    private byte q;
    private int x;
    private int y;
    private boolean z = false;

    public TimeStatusMessage() {
    }

    public TimeStatusMessage(Parcel in) {
        boolean z2 = false;
        this.c = in.readLong();
        this.d = in.readByte();
        this.f = in.readByte();
        this.q = in.readByte();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readByte() != 0 ? true : z2;
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12795, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = MeshUtils.d(params, 0, 5, ByteOrder.LITTLE_ENDIAN);
            int index = 0 + 5;
            if (params.length == 10) {
                this.z = true;
                int index2 = index + 1;
                this.d = params[index];
                int index3 = index2 + 1;
                this.f = params[index2];
                this.q = (byte) (1 & params[index3]);
                int index4 = index3 + 1;
                int index5 = index4 + 1;
                this.x = ((params[index4] & 255) << 8) | (params[index3] & 239);
                this.y = params[index5] & 255;
                int i = index5;
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12796, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeLong(this.c);
            dest.writeByte(this.d);
            dest.writeByte(this.f);
            dest.writeByte(this.q);
            dest.writeInt(this.x);
            dest.writeInt(this.y);
            dest.writeByte(this.z ? (byte) 1 : 0);
        }
    }

    public long c() {
        return this.c;
    }
}
