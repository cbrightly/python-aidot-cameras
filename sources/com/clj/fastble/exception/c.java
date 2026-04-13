package com.clj.fastble.exception;

/* compiled from: GattException */
public class c extends a {
    private int gattStatus;

    public c(int gattStatus2) {
        super(101, "GattException Gatt Exception Occurred! gattStatus=" + gattStatus2);
        this.gattStatus = gattStatus2;
    }

    public int getGattStatus() {
        return this.gattStatus;
    }

    public c setGattStatus(int gattStatus2) {
        this.gattStatus = gattStatus2;
        return this;
    }

    public String toString() {
        return "GattException{gattStatus=" + this.gattStatus + "} " + super.toString();
    }
}
