package com.typesafe.config;

/* compiled from: ConfigMemorySize */
public final class c {
    private final long a;

    private c(long bytes) {
        if (bytes >= 0) {
            this.a = bytes;
            return;
        }
        throw new IllegalArgumentException("Attempt to construct ConfigMemorySize with negative number: " + bytes);
    }

    public static c a(long bytes) {
        return new c(bytes);
    }

    public String toString() {
        return "ConfigMemorySize(" + this.a + ")";
    }

    public boolean equals(Object other) {
        if (!(other instanceof c) || ((c) other).a != this.a) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Long.valueOf(this.a).hashCode();
    }
}
