package com.telink.ble.mesh.core.message.scene;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class SceneStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<SceneStatusMessage> CREATOR = new Parcelable.Creator<SceneStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12774, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12773, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public SceneStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12772, new Class[]{Parcel.class}, SceneStatusMessage.class);
            if (proxy.isSupported) {
                return (SceneStatusMessage) proxy.result;
            }
            return new SceneStatusMessage(in);
        }

        public SceneStatusMessage[] b(int size) {
            return new SceneStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private int d;
    private int f;
    private byte q;
    private boolean x = false;

    public SceneStatusMessage() {
    }

    public SceneStatusMessage(Parcel in) {
        boolean z = false;
        this.c = in.readByte();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readByte();
        this.x = in.readByte() != 0 ? true : z;
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12770, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0];
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.d = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            if (params.length == 6) {
                this.x = true;
                this.f = MeshUtils.b(params, index2, 2, byteOrder);
                this.q = params[index2 + 2];
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12771, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeByte(this.q);
            dest.writeByte(this.x ? (byte) 1 : 0);
        }
    }
}
