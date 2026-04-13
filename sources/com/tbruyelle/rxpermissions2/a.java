package com.tbruyelle.rxpermissions2;

/* compiled from: Permission */
public class a {
    public final String a;
    public final boolean b;
    public final boolean c;

    public a(String name, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.a = name;
        this.b = granted;
        this.c = shouldShowRequestPermissionRationale;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        a that = (a) o;
        if (this.b == that.b && this.c == that.c) {
            return this.a.equals(that.a);
        }
        return false;
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + (this.b ? 1 : 0)) * 31) + (this.c ? 1 : 0);
    }

    public String toString() {
        return "Permission{name='" + this.a + '\'' + ", granted=" + this.b + ", shouldShowRequestPermissionRationale=" + this.c + '}';
    }
}
