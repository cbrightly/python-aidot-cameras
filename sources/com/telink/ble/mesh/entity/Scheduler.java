package com.telink.ble.mesh.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Scheduler implements Serializable, Parcelable {
    public static final int ACTION_NO = 15;
    public static final int ACTION_OFF = 0;
    public static final int ACTION_ON = 1;
    public static final int ACTION_SCENE = 2;
    public static final Parcelable.Creator<Scheduler> CREATOR = new Parcelable.Creator<Scheduler>() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13052, new Class[]{Parcel.class}, Object.class);
            return proxy.isSupported ? proxy.result : a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13051, new Class[]{Integer.TYPE}, Object[].class);
            return proxy.isSupported ? (Object[]) proxy.result : b(i);
        }

        public Scheduler a(Parcel in) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13050, new Class[]{Parcel.class}, Scheduler.class);
            if (proxy.isSupported) {
                return (Scheduler) proxy.result;
            }
            return new Scheduler(in);
        }

        public Scheduler[] b(int size) {
            return new Scheduler[size];
        }
    };
    public static final int DAY_ANY = 0;
    public static final int HOUR_ANY = 24;
    public static final int HOUR_RANDOM = 25;
    public static final int MINUTE_ANY = 60;
    public static final int MINUTE_CYCLE_15 = 61;
    public static final int MINUTE_CYCLE_20 = 62;
    public static final int MINUTE_RANDOM = 63;
    public static final int SECOND_ANY = 60;
    public static final int SECOND_CYCLE_15 = 61;
    public static final int SECOND_CYCLE_20 = 62;
    public static final int SECOND_RANDOM = 63;
    public static final int YEAR_ANY = 100;
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte index;
    private Register register;
    public int smartId;

    private Scheduler(byte index2, Register register2) {
        this.index = index2;
        this.smartId = register2.smartId;
        this.register = register2;
    }

    public Scheduler(Parcel in) {
        this.index = in.readByte();
        this.register = (Register) in.readParcelable(Register.class.getClassLoader());
    }

    public byte getIndex() {
        return this.index;
    }

    public Register getRegister() {
        return this.register;
    }

    public static Scheduler fromBytes(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 13047, new Class[]{byte[].class}, Scheduler.class);
        if (proxy.isSupported) {
            return (Scheduler) proxy.result;
        }
        if (data == null || data.length != 10) {
            return null;
        }
        Register reg = new Register();
        long unused = reg.year = (long) (((data[0] >> 4) & 15) | ((data[1] & 7) << 4));
        long unused2 = reg.month = (long) (((data[1] >> 3) & 31) | ((data[2] & Byte.MAX_VALUE) << 5));
        long unused3 = reg.day = (long) (((data[2] >> 7) & 1) | ((data[3] & 15) << 1));
        long unused4 = reg.hour = (long) (((data[3] >> 4) & 15) | ((data[4] & 1) << 4));
        long unused5 = reg.minute = (long) ((data[4] >> 1) & 63);
        long unused6 = reg.second = (long) (((data[5] & 31) << 1) | ((data[4] >> 7) & 1));
        long unused7 = reg.week = (long) (((data[5] >> 5) & 7) | ((data[6] & 15) << 3));
        long unused8 = reg.action = (long) ((data[6] >> 4) & 15);
        long unused9 = reg.transTime = (long) (data[7] & 255);
        int unused10 = reg.sceneId = ((data[9] << 8) & 255) | (data[8] & 255);
        return new Scheduler((byte) (data[0] & 15), reg);
    }

    public byte[] toBytes() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13048, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putLong(((long) this.index) | (this.register.year << 4) | (this.register.month << 11) | (this.register.day << 23) | (this.register.hour << 28) | (this.register.minute << 33) | (this.register.second << 39) | (this.register.week << 45) | (this.register.action << 52) | (this.register.transTime << 56)).putShort((short) this.register.sceneId);
        return byteBuffer.array();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (!PatchProxy.proxy(new Object[]{dest, new Integer(flags)}, this, changeQuickRedirect, false, 13049, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
            dest.writeByte(this.index);
            dest.writeParcelable(this.register, flags);
        }
    }

    public static final class Builder {
        public static ChangeQuickRedirect changeQuickRedirect;
        private byte a = 1;
        private byte b = 0;
        private short c = 0;
        private byte d = 0;
        private byte e = 0;
        private byte f = 0;
        private byte g = 0;
        private byte h = 0;
        private byte i;
        private byte j;
        private short k;
        public int l;

        public Builder j(int smartId) {
            this.l = smartId;
            return this;
        }

        public Builder e(byte index) {
            this.a = index;
            return this;
        }

        public Builder m(byte year) {
            this.b = year;
            return this;
        }

        public Builder g(short month) {
            this.c = month;
            return this;
        }

        public Builder c(byte day) {
            this.d = day;
            return this;
        }

        public Builder d(byte hour) {
            this.e = hour;
            return this;
        }

        public Builder f(byte minute) {
            this.f = minute;
            return this;
        }

        public Builder i(byte second) {
            this.g = second;
            return this;
        }

        public Builder l(byte week) {
            this.h = week;
            return this;
        }

        public Builder b(byte action) {
            this.i = action;
            return this;
        }

        public Builder k(byte transTime) {
            this.j = transTime;
            return this;
        }

        public Builder h(short sceneId) {
            this.k = sceneId;
            return this;
        }

        public Scheduler a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13053, new Class[0], Scheduler.class);
            if (proxy.isSupported) {
                return (Scheduler) proxy.result;
            }
            return new Scheduler(this.a, new Register(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l));
        }
    }

    public static class Register implements Serializable, Parcelable {
        public static final Parcelable.Creator<Register> CREATOR = new Parcelable.Creator<Register>() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 13057, new Class[]{Parcel.class}, Object.class);
                return proxy.isSupported ? proxy.result : a(parcel);
            }

            public /* bridge */ /* synthetic */ Object[] newArray(int i) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 13056, new Class[]{Integer.TYPE}, Object[].class);
                return proxy.isSupported ? (Object[]) proxy.result : b(i);
            }

            public Register a(Parcel in) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 13055, new Class[]{Parcel.class}, Register.class);
                if (proxy.isSupported) {
                    return (Register) proxy.result;
                }
                return new Register(in);
            }

            public Register[] b(int size) {
                return new Register[size];
            }
        };
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public long action;
        /* access modifiers changed from: private */
        public long day;
        /* access modifiers changed from: private */
        public long hour;
        /* access modifiers changed from: private */
        public long minute;
        /* access modifiers changed from: private */
        public long month;
        /* access modifiers changed from: private */
        public int sceneId;
        /* access modifiers changed from: private */
        public long second;
        /* access modifiers changed from: private */
        public int smartId;
        /* access modifiers changed from: private */
        public long transTime;
        /* access modifiers changed from: private */
        public long week;
        /* access modifiers changed from: private */
        public long year;

        private Register() {
        }

        private Register(byte year2, short month2, byte day2, byte hour2, byte minute2, byte second2, byte week2, byte action2, byte transTime2, int sceneId2, int smartId2) {
            this.year = (long) (year2 & 255);
            this.month = (long) (month2 & 65535);
            this.day = (long) (day2 & 255);
            this.hour = (long) (hour2 & 255);
            this.minute = (long) (minute2 & 255);
            this.second = (long) (second2 & 255);
            this.week = (long) (week2 & 255);
            this.action = (long) (action2 & 255);
            this.transTime = (long) (transTime2 & 255);
            this.sceneId = 65535 & sceneId2;
            this.smartId = smartId2;
        }

        public Register(Parcel in) {
            this.year = in.readLong();
            this.month = in.readLong();
            this.day = in.readLong();
            this.hour = in.readLong();
            this.minute = in.readLong();
            this.second = in.readLong();
            this.week = in.readLong();
            this.action = in.readLong();
            this.transTime = in.readLong();
            this.sceneId = in.readInt();
            this.smartId = in.readInt();
        }

        public long getYear() {
            return this.year;
        }

        public long getMonth() {
            return this.month;
        }

        public long getDay() {
            return this.day;
        }

        public long getHour() {
            return this.hour;
        }

        public long getMinute() {
            return this.minute;
        }

        public long getSecond() {
            return this.second;
        }

        public long getWeek() {
            return this.week;
        }

        public long getAction() {
            return this.action;
        }

        public long getTransTime() {
            return this.transTime;
        }

        public int getSceneId() {
            return this.sceneId;
        }

        public int getSmartId() {
            return this.smartId;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int i) {
            if (!PatchProxy.proxy(new Object[]{dest, new Integer(i)}, this, changeQuickRedirect, false, 13054, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
                dest.writeLong(this.year);
                dest.writeLong(this.month);
                dest.writeLong(this.day);
                dest.writeLong(this.hour);
                dest.writeLong(this.minute);
                dest.writeLong(this.second);
                dest.writeLong(this.week);
                dest.writeLong(this.action);
                dest.writeLong(this.transTime);
                dest.writeInt(this.sceneId);
                dest.writeInt(this.smartId);
            }
        }
    }
}
