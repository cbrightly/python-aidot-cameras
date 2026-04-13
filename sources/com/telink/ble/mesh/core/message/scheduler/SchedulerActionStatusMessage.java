package com.telink.ble.mesh.core.message.scheduler;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;
import com.telink.ble.mesh.entity.Scheduler;

public class SchedulerActionStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<SchedulerActionStatusMessage> CREATOR = new Parcelable.Creator<SchedulerActionStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12785, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12784, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public SchedulerActionStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12783, new Class[]{Parcel.class}, SchedulerActionStatusMessage.class);
            if (proxy.isSupported) {
                return (SchedulerActionStatusMessage) proxy.result;
            }
            return new SchedulerActionStatusMessage(in);
        }

        public SchedulerActionStatusMessage[] b(int size) {
            return new SchedulerActionStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private Scheduler c;

    public SchedulerActionStatusMessage() {
    }

    public SchedulerActionStatusMessage(Parcel in) {
        this.c = (Scheduler) in.readParcelable(Scheduler.class.getClassLoader());
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12781, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = Scheduler.fromBytes(params);
        }
    }

    public Scheduler c() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 12782, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeParcelable(this.c, flags);
        }
    }
}
