package com.telink.ble.mesh.foundation.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.MeshController;

public class MeshEvent extends Event<String> {
    public static final Parcelable.Creator<MeshEvent> CREATOR = new Parcelable.Creator<MeshEvent>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13341, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13340, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public MeshEvent a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13339, new Class[]{Parcel.class}, MeshEvent.class);
            if (proxy.isSupported) {
                return (MeshEvent) proxy.result;
            }
            return new MeshEvent(in);
        }

        public MeshEvent[] b(int size) {
            return new MeshEvent[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private String c;
    private boolean d = false;

    public MeshEvent(Object sender, String type, String desc, MeshController.Mode mode) {
        super(sender, type);
        boolean z = false;
        this.c = desc;
        this.d = mode == MeshController.Mode.MODE_AUTO_CONNECT ? true : z;
    }

    public MeshEvent(Parcel in) {
        this.c = in.readString();
        this.d = in.readBoolean();
    }

    public boolean a() {
        return this.d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13338, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeString(this.c);
            dest.writeBoolean(this.d);
        }
    }
}
