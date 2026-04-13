package com.telink.ble.mesh.core.message.firmwaredistribution;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class FDReceiversStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<FDReceiversStatusMessage> CREATOR = new Parcelable.Creator<FDReceiversStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12570, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12569, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public FDReceiversStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12568, new Class[]{Parcel.class}, FDReceiversStatusMessage.class);
            if (proxy.isSupported) {
                return (FDReceiversStatusMessage) proxy.result;
            }
            return new FDReceiversStatusMessage(in);
        }

        public FDReceiversStatusMessage[] b(int size) {
            return new FDReceiversStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;

    public FDReceiversStatusMessage() {
    }

    public FDReceiversStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12565, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0] & 255;
            this.d = MeshUtils.b(params, 0 + 1, 2, ByteOrder.LITTLE_ENDIAN);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12566, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12567, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "FirmwareDistributionReceiversStatus{status=" + this.c + ", receiversListCount=" + this.d + '}';
    }
}
