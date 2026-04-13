package org.apache.http;

import java.io.Serializable;
import org.apache.http.util.a;

/* compiled from: ProtocolVersion */
public class v implements Serializable, Cloneable {
    private static final long serialVersionUID = 8950662842175091068L;
    protected final int major;
    protected final int minor;
    protected final String protocol;

    public v(String protocol2, int major2, int minor2) {
        this.protocol = (String) a.i(protocol2, "Protocol name");
        this.major = a.g(major2, "Protocol minor version");
        this.minor = a.g(minor2, "Protocol minor version");
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public v forVersion(int major2, int minor2) {
        if (major2 == this.major && minor2 == this.minor) {
            return this;
        }
        return new v(this.protocol, major2, minor2);
    }

    public final int hashCode() {
        return (this.protocol.hashCode() ^ (this.major * 100000)) ^ this.minor;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof v)) {
            return false;
        }
        v that = (v) obj;
        if (this.protocol.equals(that.protocol) && this.major == that.major && this.minor == that.minor) {
            return true;
        }
        return false;
    }

    public boolean isComparable(v that) {
        return that != null && this.protocol.equals(that.protocol);
    }

    public int compareToVersion(v that) {
        a.i(that, "Protocol version");
        a.b(this.protocol.equals(that.protocol), "Versions for different protocols cannot be compared: %s %s", this, that);
        int delta = getMajor() - that.getMajor();
        if (delta == 0) {
            return getMinor() - that.getMinor();
        }
        return delta;
    }

    public final boolean greaterEquals(v version) {
        return isComparable(version) && compareToVersion(version) >= 0;
    }

    public final boolean lessEquals(v version) {
        return isComparable(version) && compareToVersion(version) <= 0;
    }

    public String toString() {
        return this.protocol + '/' + Integer.toString(this.major) + '.' + Integer.toString(this.minor);
    }

    public Object clone() {
        return super.clone();
    }
}
