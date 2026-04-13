package com.telink.bluetooth.light;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ErrorReportInfo implements Parcelable {
    public static final Parcelable.Creator<ErrorReportInfo> CREATOR = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    public int c;
    public int d;
    public int f;

    public ErrorReportInfo() {
    }

    public static final class a implements Parcelable.Creator<ErrorReportInfo> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13525, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13524, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public ErrorReportInfo a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13523, new Class[]{Parcel.class}, ErrorReportInfo.class);
            if (proxy.isSupported) {
                return (ErrorReportInfo) proxy.result;
            }
            return new ErrorReportInfo(in);
        }

        public ErrorReportInfo[] b(int size) {
            return new ErrorReportInfo[size];
        }
    }

    public ErrorReportInfo(Parcel in) {
        this.c = in.readInt();
        this.d = in.readInt();
        this.f = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13522, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.f);
        }
    }
}
