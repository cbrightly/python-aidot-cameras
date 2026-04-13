package com.telink.ble.mesh.core.message.scene;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;

public class SceneRegisterStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<SceneRegisterStatusMessage> CREATOR = new Parcelable.Creator<SceneRegisterStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12769, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12768, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public SceneRegisterStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12767, new Class[]{Parcel.class}, SceneRegisterStatusMessage.class);
            if (proxy.isSupported) {
                return (SceneRegisterStatusMessage) proxy.result;
            }
            return new SceneRegisterStatusMessage(in);
        }

        public SceneRegisterStatusMessage[] b(int size) {
            return new SceneRegisterStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte c;
    private int d;
    private int[] f;

    public SceneRegisterStatusMessage() {
    }

    public SceneRegisterStatusMessage(Parcel in) {
        this.c = in.readByte();
        this.d = in.readInt();
        this.f = in.createIntArray();
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12765, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int index = 0 + 1;
            this.c = params[0];
            this.d = MeshUtils.b(params, index, 2, ByteOrder.LITTLE_ENDIAN);
            int index2 = index + 2;
            int rst = params.length - index2;
            if (rst > 0 && rst % 2 == 0) {
                this.f = new int[(rst / 2)];
                int i = 0;
                while (true) {
                    int[] iArr = this.f;
                    if (i < iArr.length) {
                        iArr[i] = MeshUtils.b(params, index2, 2, ByteOrder.LITTLE_ENDIAN);
                        index2 += 2;
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12766, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.c);
            dest.writeInt(this.d);
            dest.writeIntArray(this.f);
        }
    }

    public byte c() {
        return this.c;
    }
}
