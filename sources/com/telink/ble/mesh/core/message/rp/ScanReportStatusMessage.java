package com.telink.ble.mesh.core.message.rp;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class ScanReportStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ScanReportStatusMessage> CREATOR = new Parcelable.Creator<ScanReportStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12750, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12749, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ScanReportStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12748, new Class[]{Parcel.class}, ScanReportStatusMessage.class);
            if (proxy.isSupported) {
                return (ScanReportStatusMessage) proxy.result;
            }
            return new ScanReportStatusMessage(in);
        }

        public ScanReportStatusMessage[] b(int size) {
            return new ScanReportStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private byte[] d;
    private int f;

    public ScanReportStatusMessage() {
    }

    public ScanReportStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.createByteArray();
        this.f = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12746, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0];
            byte[] bArr = new byte[16];
            this.d = bArr;
            System.arraycopy(params, index, bArr, 0, bArr.length);
            this.f = MeshUtils.b(params, index + this.d.length, 2, ByteOrder.LITTLE_ENDIAN);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12747, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeByteArray(this.d);
            dest.writeInt(this.f);
        }
    }
}
