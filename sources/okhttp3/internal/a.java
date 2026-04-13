package okhttp3.internal;

import com.google.android.libraries.places.api.model.PlaceTypes;
import com.meituan.robust.Constants;
import java.net.IDN;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import okio.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: hostnames.kt */
public final class a {
    @Nullable
    public static final String e(@NotNull String $this$toCanonicalHost) {
        InetAddress inetAddress;
        k.f($this$toCanonicalHost, "$this$toCanonicalHost");
        String host = $this$toCanonicalHost;
        boolean z = false;
        if (x.S(host, ":", false, 2, (Object) null)) {
            if (!w.N(host, Constants.ARRAY_TYPE, false, 2, (Object) null) || !w.x(host, "]", false, 2, (Object) null)) {
                inetAddress = c(host, 0, host.length());
            } else {
                inetAddress = c(host, 1, host.length() - 1);
            }
            if (inetAddress == null) {
                return null;
            }
            byte[] address = inetAddress.getAddress();
            if (address.length == 16) {
                k.b(address, PlaceTypes.ADDRESS);
                return d(address);
            } else if (address.length == 4) {
                return inetAddress.getHostAddress();
            } else {
                throw new AssertionError("Invalid IPv6 address: '" + host + '\'');
            }
        } else {
            try {
                String ascii = IDN.toASCII(host);
                k.b(ascii, "IDN.toASCII(host)");
                Locale locale = Locale.US;
                k.b(locale, "Locale.US");
                if (ascii != null) {
                    String result = ascii.toLowerCase(locale);
                    k.b(result, "(this as java.lang.String).toLowerCase(locale)");
                    if (result.length() == 0) {
                        z = true;
                    }
                    if (z) {
                        return null;
                    }
                    if (a(result)) {
                        return null;
                    }
                    return result;
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    private static final boolean a(@NotNull String $this$containsInvalidHostnameAsciiCodes) {
        int length = $this$containsInvalidHostnameAsciiCodes.length();
        for (int i = 0; i < length; i++) {
            char c = $this$containsInvalidHostnameAsciiCodes.charAt(i);
            if (c <= 31 || c >= 127 || x.e0(" #%/:?@[\\]", c, 0, false, 6, (Object) null) != -1) {
                return true;
            }
        }
        return false;
    }

    private static final InetAddress c(String input, int pos, int limit) {
        byte[] address = new byte[16];
        int b = 0;
        int compress = -1;
        int groupOffset = -1;
        int i = pos;
        while (true) {
            if (i >= limit) {
                break;
            } else if (b == address.length) {
                return null;
            } else {
                if (i + 2 <= limit && w.M(input, "::", i, false, 4, (Object) null)) {
                    if (compress == -1) {
                        i += 2;
                        b += 2;
                        compress = b;
                        if (i == limit) {
                            break;
                        }
                    } else {
                        return null;
                    }
                } else if (b != 0) {
                    if (w.M(input, ":", i, false, 4, (Object) null)) {
                        i++;
                    } else if (!w.M(input, ".", i, false, 4, (Object) null) || !b(input, groupOffset, limit, address, b - 2)) {
                        return null;
                    } else {
                        b += 2;
                    }
                }
                int value = 0;
                groupOffset = i;
                while (i < limit) {
                    int hexDigit = b.E(input.charAt(i));
                    if (hexDigit == -1) {
                        break;
                    }
                    value = (value << 4) + hexDigit;
                    i++;
                }
                int groupLength = i - groupOffset;
                if (groupLength == 0 || groupLength > 4) {
                    return null;
                }
                int b2 = b + 1;
                address[b] = (byte) ((value >>> 8) & 255);
                b = b2 + 1;
                address[b2] = (byte) (value & 255);
            }
        }
        if (b != address.length) {
            if (compress == -1) {
                return null;
            }
            System.arraycopy(address, compress, address, address.length - (b - compress), b - compress);
            Arrays.fill(address, compress, (address.length - b) + compress, (byte) 0);
        }
        return InetAddress.getByAddress(address);
    }

    private static final boolean b(String input, int pos, int limit, byte[] address, int addressOffset) {
        int b = addressOffset;
        int i = pos;
        while (i < limit) {
            if (b == address.length) {
                return false;
            }
            if (b != addressOffset) {
                if (input.charAt(i) != '.') {
                    return false;
                }
                i++;
            }
            int value = 0;
            int groupOffset = i;
            while (i < limit) {
                char c = input.charAt(i);
                if (c < '0' || c > '9') {
                    break;
                } else if ((value == 0 && groupOffset != i) || ((value * 10) + c) - 48 > 255) {
                    return false;
                } else {
                    i++;
                }
            }
            if (i - groupOffset == 0) {
                return false;
            }
            address[b] = (byte) value;
            b++;
        }
        if (b == addressOffset + 4) {
            return true;
        }
        return false;
    }

    private static final String d(byte[] address) {
        int longestRunOffset = -1;
        int longestRunLength = 0;
        int i = 0;
        while (i < address.length) {
            int currentRunOffset = i;
            while (i < 16 && address[i] == 0 && address[i + 1] == 0) {
                i += 2;
            }
            int currentRunLength = i - currentRunOffset;
            if (currentRunLength > longestRunLength && currentRunLength >= 4) {
                longestRunOffset = currentRunOffset;
                longestRunLength = currentRunLength;
            }
            i += 2;
        }
        c result = new c();
        int i2 = 0;
        while (i2 < address.length) {
            if (i2 == longestRunOffset) {
                result.writeByte(58);
                i2 += longestRunLength;
                if (i2 == 16) {
                    result.writeByte(58);
                }
            } else {
                if (i2 > 0) {
                    result.writeByte(58);
                }
                result.writeHexadecimalUnsignedLong((long) ((b.b(address[i2], 255) << 8) | b.b(address[i2 + 1], 255)));
                i2 += 2;
            }
        }
        return result.a1();
    }
}
