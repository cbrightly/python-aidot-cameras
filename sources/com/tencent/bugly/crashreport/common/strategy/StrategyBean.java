package com.tencent.bugly.crashreport.common.strategy;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkRequest;
import com.tencent.bugly.proguard.z;
import java.util.Map;

/* compiled from: BUGLY */
public class StrategyBean implements Parcelable {
    public static final Parcelable.Creator<StrategyBean> CREATOR = new Parcelable.Creator<StrategyBean>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new StrategyBean(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new StrategyBean[i];
        }
    };
    public static String a = "http://rqd.uu.qq.com/rqd/sync";
    public static String b = "http://android.bugly.qq.com/rqd/async";
    public static String c = "http://android.bugly.qq.com/rqd/async";
    public static String d;
    public long e;
    public long f;
    public boolean g;
    public boolean h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public long p;
    public long q;
    public String r;
    public String s;
    public String t;
    public String u;
    public Map<String, String> v;
    public int w;
    public long x;
    public long y;

    public StrategyBean() {
        this.e = -1;
        this.f = -1;
        this.g = true;
        this.h = true;
        this.i = true;
        this.j = true;
        this.k = false;
        this.l = true;
        this.m = true;
        this.n = true;
        this.o = true;
        this.q = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
        this.r = b;
        this.s = c;
        this.t = a;
        this.w = 10;
        this.x = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
        this.y = -1;
        this.f = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("S(@L@L");
        sb.append("@)");
        d = sb.toString();
        sb.setLength(0);
        sb.append("*^@K#K");
        sb.append("@!");
        this.u = sb.toString();
    }

    public StrategyBean(Parcel parcel) {
        this.e = -1;
        this.f = -1;
        boolean z = true;
        this.g = true;
        this.h = true;
        this.i = true;
        this.j = true;
        this.k = false;
        this.l = true;
        this.m = true;
        this.n = true;
        this.o = true;
        this.q = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
        this.r = b;
        this.s = c;
        this.t = a;
        this.w = 10;
        this.x = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
        this.y = -1;
        try {
            d = "S(@L@L" + "@)";
            this.f = parcel.readLong();
            this.g = parcel.readByte() == 1;
            this.h = parcel.readByte() == 1;
            this.i = parcel.readByte() == 1;
            this.r = parcel.readString();
            this.s = parcel.readString();
            this.u = parcel.readString();
            this.v = z.b(parcel);
            this.j = parcel.readByte() == 1;
            this.k = parcel.readByte() == 1;
            this.n = parcel.readByte() == 1;
            this.o = parcel.readByte() == 1;
            this.q = parcel.readLong();
            this.l = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.m = z;
            this.p = parcel.readLong();
            this.w = parcel.readInt();
            this.x = parcel.readLong();
            this.y = parcel.readLong();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.f);
        parcel.writeByte(this.g ? (byte) 1 : 0);
        parcel.writeByte(this.h ? (byte) 1 : 0);
        parcel.writeByte(this.i ? (byte) 1 : 0);
        parcel.writeString(this.r);
        parcel.writeString(this.s);
        parcel.writeString(this.u);
        z.b(parcel, this.v);
        parcel.writeByte(this.j ? (byte) 1 : 0);
        parcel.writeByte(this.k ? (byte) 1 : 0);
        parcel.writeByte(this.n ? (byte) 1 : 0);
        parcel.writeByte(this.o ? (byte) 1 : 0);
        parcel.writeLong(this.q);
        parcel.writeByte(this.l ? (byte) 1 : 0);
        parcel.writeByte(this.m ? (byte) 1 : 0);
        parcel.writeLong(this.p);
        parcel.writeInt(this.w);
        parcel.writeLong(this.x);
        parcel.writeLong(this.y);
    }
}
