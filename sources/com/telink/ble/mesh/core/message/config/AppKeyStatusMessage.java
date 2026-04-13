package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class AppKeyStatusMessage extends StatusMessage {
    public static final Parcelable.Creator<AppKeyStatusMessage> CREATOR = new Parcelable.Creator<AppKeyStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12450, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12449, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public AppKeyStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12448, new Class[]{Parcel.class}, AppKeyStatusMessage.class);
            if (proxy.isSupported) {
                return (AppKeyStatusMessage) proxy.result;
            }
            return new AppKeyStatusMessage(in);
        }

        public AppKeyStatusMessage[] b(int size) {
            return new AppKeyStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private int d;
    private int f;

    public AppKeyStatusMessage() {
    }

    public AppKeyStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.readInt();
        this.f = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12446, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0];
            int netAppKeyIndex = MeshUtils.c(new byte[]{params[1], params[2], params[3]}, ByteOrder.LITTLE_ENDIAN);
            this.d = netAppKeyIndex & 4095;
            this.f = (netAppKeyIndex >> 12) & 4095;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12447, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
        }
    }

    public byte c() {
        return this.c;
    }
}
