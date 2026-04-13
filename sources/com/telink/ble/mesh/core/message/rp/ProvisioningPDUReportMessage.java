package com.telink.ble.mesh.core.message.rp;

import android.os.Parcel;
import android.os.Parcelable;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class ProvisioningPDUReportMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ProvisioningPDUReportMessage> CREATOR = new Parcelable.Creator<ProvisioningPDUReportMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12743, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12742, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ProvisioningPDUReportMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12741, new Class[]{Parcel.class}, ProvisioningPDUReportMessage.class);
            if (proxy.isSupported) {
                return (ProvisioningPDUReportMessage) proxy.result;
            }
            return new ProvisioningPDUReportMessage(in);
        }

        public ProvisioningPDUReportMessage[] b(int size) {
            return new ProvisioningPDUReportMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private byte[] d;

    public ProvisioningPDUReportMessage() {
    }

    public ProvisioningPDUReportMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.createByteArray();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12738, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0];
            if (params.length > 1) {
                int pduLen = params.length - 1;
                byte[] bArr = new byte[pduLen];
                this.d = bArr;
                System.arraycopy(params, 1, bArr, 0, pduLen);
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12739, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeByteArray(this.d);
        }
    }

    public byte c() {
        return this.c;
    }

    public byte[] d() {
        return this.d;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12740, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ProvisioningPDUReportMessage{inboundPDUNumber=" + this.c + ", provisioningPDU=" + e.a(this.d) + '}';
    }
}
