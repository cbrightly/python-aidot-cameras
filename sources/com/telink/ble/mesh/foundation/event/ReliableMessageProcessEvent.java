package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.foundation.Event;

public class ReliableMessageProcessEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<ReliableMessageProcessEvent> CREATOR = new Parcelable.Creator<ReliableMessageProcessEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13359, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13358, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ReliableMessageProcessEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13357, new Class[]{Parcel.class}, ReliableMessageProcessEvent.class);
            if (proxy.isSupported) {
                return (ReliableMessageProcessEvent) proxy.result;
            }
            return new ReliableMessageProcessEvent(in);
        }

        public ReliableMessageProcessEvent[] b(int size) {
            return new ReliableMessageProcessEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean c;
    private int d;
    private int f;
    private int q;
    private String x;
    private MeshMessage y;

    public ReliableMessageProcessEvent(Object sender, String type, boolean success, int opcode, int rspMax, int rspCount, String desc, MeshMessage meshMessage) {
        super(sender, type);
        this.c = success;
        this.d = opcode;
        this.f = rspMax;
        this.q = rspCount;
        this.x = desc;
        this.y = meshMessage;
    }

    public ReliableMessageProcessEvent(Parcel in) {
        this.c = in.readByte() != 0;
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        this.x = in.readString();
    }

    public MeshMessage a() {
        return this.y;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13356, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c ? (byte) 1 : 0);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeString(this.x);
        }
    }
}
