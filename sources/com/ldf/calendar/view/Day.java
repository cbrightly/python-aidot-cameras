package com.ldf.calendar.view;

import android.os.Parcel;
import android.os.Parcelable;
import com.ldf.calendar.component.c;

public class Day implements Parcelable {
    public static final Parcelable.Creator<Day> CREATOR = new a();
    private c c;
    private com.ldf.calendar.model.a d;
    private int f;
    private int q;

    public Day(c state, com.ldf.calendar.model.a date, int posRow, int posCol) {
        this.c = state;
        this.d = date;
        this.f = posRow;
        this.q = posCol;
    }

    public c d() {
        return this.c;
    }

    public void f(c state) {
        this.c = state;
    }

    public com.ldf.calendar.model.a a() {
        return this.d;
    }

    public void e(com.ldf.calendar.model.a date) {
        this.d = date;
    }

    public int c() {
        return this.f;
    }

    public int b() {
        return this.q;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        c cVar = this.c;
        dest.writeInt(cVar == null ? -1 : cVar.ordinal());
        dest.writeSerializable(this.d);
        dest.writeInt(this.f);
        dest.writeInt(this.q);
    }

    protected Day(Parcel in) {
        int tmpState = in.readInt();
        this.c = tmpState == -1 ? null : c.values()[tmpState];
        this.d = (com.ldf.calendar.model.a) in.readSerializable();
        this.f = in.readInt();
        this.q = in.readInt();
    }

    public static final class a implements Parcelable.Creator<Day> {
        a() {
        }

        /* renamed from: a */
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        /* renamed from: b */
        public Day[] newArray(int size) {
            return new Day[size];
        }
    }
}
