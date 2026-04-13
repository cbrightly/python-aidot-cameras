package com.telink.ble.mesh.core.message;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class NotificationMessage implements Parcelable {
    public static final Parcelable.Creator<NotificationMessage> CREATOR = new Parcelable.Creator<NotificationMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12434, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12433, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public NotificationMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12432, new Class[]{Parcel.class}, NotificationMessage.class);
            if (proxy.isSupported) {
                return (NotificationMessage) proxy.result;
            }
            return new NotificationMessage(in);
        }

        public NotificationMessage[] b(int size) {
            return new NotificationMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;
    private byte[] q;
    private StatusMessage x;

    public NotificationMessage(int src, int dst, int opcode, byte[] params) {
        this.c = src;
        this.d = dst;
        this.f = opcode;
        this.q = params;
        e();
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12430, new Class[0], Void.TYPE).isSupported) {
            this.x = StatusMessage.a(this.f, this.q);
        }
    }

    public NotificationMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.createByteArray();
        this.x = (StatusMessage) in.readParcelable(StatusMessage.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 12431, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeByteArray(this.q);
            dest.writeParcelable(this.x, flags);
        }
    }

    public int c() {
        return this.c;
    }

    public int a() {
        return this.f;
    }

    public byte[] b() {
        return this.q;
    }

    public StatusMessage d() {
        return this.x;
    }
}
