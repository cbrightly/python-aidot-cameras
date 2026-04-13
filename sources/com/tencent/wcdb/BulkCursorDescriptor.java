package com.tencent.wcdb;

import android.os.Parcel;
import android.os.Parcelable;

public final class BulkCursorDescriptor implements Parcelable {
    public static final Parcelable.Creator<BulkCursorDescriptor> CREATOR = new a();
    public i c;
    public String[] d;
    public boolean f;
    public int q;
    public CursorWindow x;

    public static final class a implements Parcelable.Creator<BulkCursorDescriptor> {
        a() {
        }

        /* renamed from: a */
        public BulkCursorDescriptor createFromParcel(Parcel in) {
            BulkCursorDescriptor d = new BulkCursorDescriptor();
            d.a(in);
            return d;
        }

        /* renamed from: b */
        public BulkCursorDescriptor[] newArray(int size) {
            return new BulkCursorDescriptor[size];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeStrongBinder(this.c.asBinder());
        out.writeStringArray(this.d);
        out.writeInt(this.f ? 1 : 0);
        out.writeInt(this.q);
        if (this.x != null) {
            out.writeInt(1);
            this.x.writeToParcel(out, flags);
            return;
        }
        out.writeInt(0);
    }

    public final String[] b(Parcel in) {
        String[] array = null;
        int length = in.readInt();
        if (length >= 0) {
            array = new String[length];
            for (int i = 0; i < length; i++) {
                array[i] = in.readString();
            }
        }
        return array;
    }

    public void a(Parcel in) {
        this.c = c.a(in.readStrongBinder());
        this.d = b(in);
        this.f = in.readInt() != 0;
        this.q = in.readInt();
        if (in.readInt() != 0) {
            this.x = CursorWindow.CREATOR.createFromParcel(in);
        }
    }
}
