package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class BlobTransferStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<BlobTransferStatusMessage> CREATOR = new Parcelable.Creator<BlobTransferStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12660, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12659, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BlobTransferStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12658, new Class[]{Parcel.class}, BlobTransferStatusMessage.class);
            if (proxy.isSupported) {
                return (BlobTransferStatusMessage) proxy.result;
            }
            return new BlobTransferStatusMessage(in);
        }

        public BlobTransferStatusMessage[] b(int size) {
            return new BlobTransferStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private byte f;
    private List<Integer> p0;
    private long q;
    private int x;
    private int y;
    private int z;

    public BlobTransferStatusMessage() {
    }

    public BlobTransferStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readByte();
        this.q = in.readLong();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
        ArrayList arrayList = new ArrayList();
        this.p0 = arrayList;
        in.readList(arrayList, (ClassLoader) null);
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12655, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0] & 15;
            int index = 0 + 1;
            this.d = (params[0] >> 6) & 3;
            int index2 = index + 1;
            this.f = params[index];
            if (params.length >= 10) {
                ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                this.q = MeshUtils.d(params, index2, 8, byteOrder);
                int index3 = index2 + 8;
                if (params.length != 10) {
                    this.x = MeshUtils.b(params, index3, 4, byteOrder);
                    int index4 = index3 + 4;
                    int index5 = index4 + 1;
                    this.y = params[index4] & 255;
                    this.z = MeshUtils.b(params, index5, 2, byteOrder);
                    this.p0 = MeshUtils.o(params, index5 + 2);
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12656, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeByte(this.f);
            dest.writeLong(this.q);
            dest.writeInt(this.x);
            dest.writeInt(this.y);
            dest.writeInt(this.z);
            dest.writeList(this.p0);
        }
    }

    public int c() {
        return this.c;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12657, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "BlobTransferStatusMessage{status=" + this.c + ", transferMode=" + this.d + ", transferPhase=" + this.f + ", blobId=0x" + Long.toHexString(this.q) + ", blobSize=" + this.x + ", blockSizeLog=" + this.y + ", transferMTUSize=" + this.z + ", blocksNotReceived=" + this.p0 + '}';
    }
}
