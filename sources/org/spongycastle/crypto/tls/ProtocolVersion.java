package org.spongycastle.crypto.tls;

import com.github.druk.dnssd.NSType;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.spongycastle.util.Strings;

public final class ProtocolVersion {
    public static final ProtocolVersion a = new ProtocolVersion(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART, "SSL 3.0");
    public static final ProtocolVersion b = new ProtocolVersion(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP, "TLS 1.0");
    public static final ProtocolVersion c = new ProtocolVersion(770, "TLS 1.1");
    public static final ProtocolVersion d = new ProtocolVersion(771, "TLS 1.2");
    public static final ProtocolVersion e = new ProtocolVersion(65279, "DTLS 1.0");
    public static final ProtocolVersion f = new ProtocolVersion(65277, "DTLS 1.2");
    private int g;
    private String h;

    private ProtocolVersion(int v, String name) {
        this.g = 65535 & v;
        this.h = name;
    }

    public int d() {
        return this.g >> 8;
    }

    public int e() {
        return this.g & 255;
    }

    public boolean g() {
        return d() == 254;
    }

    public boolean j() {
        return this == a;
    }

    public ProtocolVersion c() {
        if (!g()) {
            return this;
        }
        if (this == e) {
            return c;
        }
        return d;
    }

    public boolean h(ProtocolVersion version) {
        if (d() != version.d()) {
            return false;
        }
        int diffMinorVersion = version.e() - e();
        if (g()) {
            if (diffMinorVersion > 0) {
                return false;
            }
        } else if (diffMinorVersion < 0) {
            return false;
        }
        return true;
    }

    public boolean i(ProtocolVersion version) {
        if (d() != version.d()) {
            return false;
        }
        int diffMinorVersion = version.e() - e();
        if (g()) {
            if (diffMinorVersion <= 0) {
                return false;
            }
        } else if (diffMinorVersion >= 0) {
            return false;
        }
        return true;
    }

    public boolean equals(Object other) {
        return this == other || ((other instanceof ProtocolVersion) && a((ProtocolVersion) other));
    }

    public boolean a(ProtocolVersion other) {
        return other != null && this.g == other.g;
    }

    public int hashCode() {
        return this.g;
    }

    public static ProtocolVersion b(int major, int minor) {
        switch (major) {
            case 3:
                switch (minor) {
                    case 0:
                        return a;
                    case 1:
                        return b;
                    case 2:
                        return c;
                    case 3:
                        return d;
                    default:
                        return f(major, minor, "TLS");
                }
            case 254:
                switch (minor) {
                    case NSType.MAILB:
                        return f;
                    case 254:
                        throw new TlsFatalAlert(47);
                    case 255:
                        return e;
                    default:
                        return f(major, minor, "DTLS");
                }
            default:
                throw new TlsFatalAlert(47);
        }
    }

    public String toString() {
        return this.h;
    }

    private static ProtocolVersion f(int major, int minor, String prefix) {
        TlsUtils.j(major);
        TlsUtils.j(minor);
        int v = (major << 8) | minor;
        String hex = Strings.l(Integer.toHexString(65536 | v).substring(1));
        return new ProtocolVersion(v, prefix + " 0x" + hex);
    }
}
