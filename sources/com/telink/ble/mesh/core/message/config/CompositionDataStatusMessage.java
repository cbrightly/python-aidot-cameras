package com.telink.ble.mesh.core.message.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;
import com.telink.ble.mesh.entity.CompositionData;

public class CompositionDataStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<CompositionDataStatusMessage> CREATOR = new Parcelable.Creator<CompositionDataStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12462, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12461, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public CompositionDataStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12460, new Class[]{Parcel.class}, CompositionDataStatusMessage.class);
            if (proxy.isSupported) {
                return (CompositionDataStatusMessage) proxy.result;
            }
            return new CompositionDataStatusMessage(in);
        }

        public CompositionDataStatusMessage[] b(int size) {
            return new CompositionDataStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private CompositionData d;

    public CompositionDataStatusMessage() {
    }

    public CompositionDataStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = (CompositionData) in.readParcelable(CompositionData.class.getClassLoader());
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12458, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0];
            byte[] cpsData = new byte[(params.length - 1)];
            System.arraycopy(params, 1, cpsData, 0, cpsData.length);
            this.d = CompositionData.from(cpsData);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 12459, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeParcelable(this.d, flags);
        }
    }

    public CompositionData c() {
        return this.d;
    }
}
