package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.StatusMessage;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class BlobPartialBlockReportMessage extends StatusMessage implements Parcelable {
    public static final Parcelable.Creator<BlobPartialBlockReportMessage> CREATOR = new Parcelable.Creator<BlobPartialBlockReportMessage>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 12649, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 12648, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public BlobPartialBlockReportMessage a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 12647, new Class[]{Parcel.class}, BlobPartialBlockReportMessage.class);
            if (proxy.isSupported) {
                return (BlobPartialBlockReportMessage) proxy.result;
            }
            return new BlobPartialBlockReportMessage(in);
        }

        public BlobPartialBlockReportMessage[] b(int size) {
            return new BlobPartialBlockReportMessage[size];
        }
    };
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<Integer> c;

    public BlobPartialBlockReportMessage() {
    }

    public BlobPartialBlockReportMessage(Parcel in) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        this.c = arrayList;
        in.readList(arrayList, (ClassLoader) null);
    }

    public void b(byte[] params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 12645, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.c = new ArrayList<>();
            for (char c2 : new String(params, Charset.forName("UTF-8")).toCharArray()) {
                this.c.add(Integer.valueOf(65535 & c2));
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 12646, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeList(this.c);
        }
    }
}
