package com.telink.ble.mesh.core.message.scheduler;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class SchedulerStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<SchedulerStatusMessage> CREATOR = new Parcelable.Creator<SchedulerStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12791, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12790, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public SchedulerStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12789, new Class[]{Parcel.class}, SchedulerStatusMessage.class);
            if (proxy.isSupported) {
                return (SchedulerStatusMessage) proxy.result;
            }
            return new SchedulerStatusMessage(in);
        }

        public SchedulerStatusMessage[] b(int size) {
            return new SchedulerStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;

    public SchedulerStatusMessage() {
    }

    public SchedulerStatusMessage(Parcel in) {
        this.c = in.readInt();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12787, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = MeshUtils.c(params, ByteOrder.LITTLE_ENDIAN);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12788, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeLong((long) this.c);
        }
    }
}
