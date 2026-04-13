package com.clj.fastble.data;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Parcel;
import android.os.Parcelable;
import com.leedarson.base.utils.e;

public class BleDevice implements Parcelable {
    public static final Parcelable.Creator<BleDevice> CREATOR = new a();
    private BluetoothDevice c;
    private byte[] d;
    private int f;
    public BluetoothGatt p0;
    private long q;
    private String x = "";
    private String y;
    public boolean z = false;

    public BleDevice(BluetoothDevice device) {
        this.c = device;
    }

    public BleDevice(BluetoothDevice device, int rssi, byte[] scanRecord, long timestampNanos) {
        this.c = device;
        this.d = scanRecord;
        this.f = rssi;
        this.q = timestampNanos;
        if (scanRecord != null) {
            this.y = e.a(scanRecord);
        }
    }

    protected BleDevice(Parcel in) {
        this.c = (BluetoothDevice) in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.d = in.createByteArray();
        this.f = in.readInt();
        this.q = in.readLong();
        this.x = in.readString();
        this.y = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.c, flags);
        dest.writeByteArray(this.d);
        dest.writeInt(this.f);
        dest.writeLong(this.q);
        dest.writeString(this.x);
        dest.writeString(this.y);
    }

    public int describeContents() {
        return 0;
    }

    public class a implements Parcelable.Creator<BleDevice> {
        a() {
        }

        /* renamed from: a */
        public BleDevice createFromParcel(Parcel in) {
            return new BleDevice(in);
        }

        /* renamed from: b */
        public BleDevice[] newArray(int size) {
            return new BleDevice[size];
        }
    }

    public String d() {
        BluetoothDevice bluetoothDevice = this.c;
        if (bluetoothDevice != null) {
            return bluetoothDevice.getName();
        }
        return "";
    }

    public String c() {
        BluetoothDevice bluetoothDevice = this.c;
        if (bluetoothDevice != null) {
            return bluetoothDevice.getAddress();
        }
        return "";
    }

    public String b() {
        BluetoothDevice bluetoothDevice = this.c;
        if (bluetoothDevice == null) {
            return "";
        }
        if (bluetoothDevice.getName() == null) {
            return this.c.getAddress();
        }
        return this.c.getName() + this.c.getAddress();
    }

    public BluetoothDevice a() {
        return this.c;
    }

    public byte[] f() {
        return this.d;
    }

    public void k(byte[] scanRecord) {
        this.d = scanRecord;
    }

    public int e() {
        return this.f;
    }

    public void j(int rssi) {
        this.f = rssi;
    }

    public long g() {
        return this.q;
    }

    public void l(long timestampNanos) {
        this.q = timestampNanos;
    }

    public String h() {
        return this.x;
    }

    public void m(String mBleAdvertisementData) {
        this.x = mBleAdvertisementData;
    }

    public String i() {
        return this.y;
    }
}
