package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BlobBlockStatusMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<BlobBlockStatusMessage> CREATOR = new Parcelable.Creator<BlobBlockStatusMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12635, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12634, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BlobBlockStatusMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12633, new Class[]{Parcel.class}, BlobBlockStatusMessage.class);
            if (proxy.isSupported) {
                return (BlobBlockStatusMessage) proxy.result;
            }
            return new BlobBlockStatusMessage(in);
        }

        public BlobBlockStatusMessage[] b(int size) {
            return new BlobBlockStatusMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f;
    private int q;
    private List<Integer> x;
    private List<Integer> y;

    public BlobBlockStatusMessage() {
    }

    public BlobBlockStatusMessage(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
        this.q = in.readInt();
        ArrayList arrayList = new ArrayList();
        this.x = arrayList;
        in.readList(arrayList, (ClassLoader) null);
        ArrayList arrayList2 = new ArrayList();
        this.y = arrayList2;
        in.readList(arrayList2, (ClassLoader) null);
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12630, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = params[0] & 15;
            int index = 0 + 1;
            this.d = (params[0] >> 6) & 3;
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            this.f = MeshUtils.b(params, index, 2, byteOrder);
            int index2 = index + 2;
            this.q = MeshUtils.b(params, index2, 2, byteOrder);
            int index3 = index2 + 2;
            int i = this.d;
            if (i == 2) {
                this.x = MeshUtils.o(params, index3);
            } else if (i == 3) {
                this.y = new ArrayList();
                byte[] missing = new byte[(params.length - index3)];
                System.arraycopy(params, index3, missing, 0, missing.length);
                for (char c2 : new String(missing, Charset.forName("UTF-8")).toCharArray()) {
                    this.y.add(Integer.valueOf(65535 & c2));
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12631, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
            dest.writeInt(this.q);
            dest.writeList(this.x);
            dest.writeList(this.y);
        }
    }

    public int f() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public List<Integer> e() {
        return this.x;
    }

    public List<Integer> c() {
        return this.y;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12632, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("BlobBlockStatusMessage{status=");
        sb.append(this.c);
        sb.append(", format=");
        sb.append(this.d);
        sb.append(", blockNumber=");
        sb.append(this.f);
        sb.append(", chunkSize=");
        sb.append(this.q);
        sb.append(", missingChunks=");
        List<Integer> list = this.x;
        Object obj = BuildConfig.TRAVIS;
        sb.append(list == null ? obj : Integer.valueOf(list.size()));
        sb.append(", encodedMissingChunks=");
        List<Integer> list2 = this.y;
        if (list2 != null) {
            obj = Integer.valueOf(list2.size());
        }
        sb.append(obj);
        sb.append('}');
        return sb.toString();
    }
}
