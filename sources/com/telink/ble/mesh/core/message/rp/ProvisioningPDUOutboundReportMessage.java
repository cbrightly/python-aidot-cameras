package com.telink.ble.mesh.core.message.rp;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;

public class ProvisioningPDUOutboundReportMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<ProvisioningPDUOutboundReportMessage> CREATOR = new Parcelable.Creator<ProvisioningPDUOutboundReportMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12737, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12736, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ProvisioningPDUOutboundReportMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12735, new Class[]{Parcel.class}, ProvisioningPDUOutboundReportMessage.class);
            if (proxy.isSupported) {
                return (ProvisioningPDUOutboundReportMessage) proxy.result;
            }
            return new ProvisioningPDUOutboundReportMessage(in);
        }

        public ProvisioningPDUOutboundReportMessage[] b(int size) {
            return new ProvisioningPDUOutboundReportMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;

    public ProvisioningPDUOutboundReportMessage() {
    }

    public ProvisioningPDUOutboundReportMessage(Parcel in) {
        this.c = in.readByte();
    }

    public void b(byte[] params) {
        this.c = params[0];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12734, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
        }
    }

    public byte c() {
        return this.c;
    }
}
