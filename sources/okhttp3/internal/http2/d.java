package okhttp3.internal.http2;

import kotlin.jvm.internal.k;
import kotlin.text.w;
import okhttp3.internal.b;
import okio.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: Http2.kt */
public final class d {
    @NotNull
    public static final f a = f.Companion.d("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    private static final String[] b = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
    private static final String[] c = new String[64];
    private static final String[] d;
    public static final d e = new d();

    static {
        String[] strArr = new String[256];
        for (int it = 0; it < 256; it++) {
            String binaryString = Integer.toBinaryString(it);
            k.b(binaryString, "Integer.toBinaryString(it)");
            strArr[it] = w.G(b.q("%8s", binaryString), ' ', '0', false, 4, (Object) null);
        }
        d = strArr;
        String[] strArr2 = c;
        strArr2[0] = "";
        strArr2[1] = "END_STREAM";
        int[] prefixFlags = {1};
        strArr2[8] = "PADDED";
        for (int prefixFlag : prefixFlags) {
            String[] strArr3 = c;
            strArr3[prefixFlag | 8] = k.l(strArr3[prefixFlag], "|PADDED");
        }
        String[] strArr4 = c;
        strArr4[4] = "END_HEADERS";
        strArr4[32] = "PRIORITY";
        strArr4[36] = "END_HEADERS|PRIORITY";
        for (int frameFlag : new int[]{4, 32, 36}) {
            for (int prefixFlag2 : prefixFlags) {
                String[] strArr5 = c;
                strArr5[prefixFlag2 | frameFlag] = strArr5[prefixFlag2] + "|" + strArr5[frameFlag];
                strArr5[prefixFlag2 | frameFlag | 8] = strArr5[prefixFlag2] + "|" + strArr5[frameFlag] + "|PADDED";
            }
        }
        int length = c.length;
        for (int i = 0; i < length; i++) {
            String[] strArr6 = c;
            if (strArr6[i] == null) {
                strArr6[i] = d[i];
            }
        }
    }

    private d() {
    }

    @NotNull
    public final String c(boolean inbound, int streamId, int length, int type, int flags) {
        return b.q("%s 0x%08x %5d %-13s %s", inbound ? "<<" : ">>", Integer.valueOf(streamId), Integer.valueOf(length), b(type), a(type, flags));
    }

    @NotNull
    public final String b(int type) {
        String[] strArr = b;
        if (type < strArr.length) {
            return strArr[type];
        }
        return b.q("0x%02x", Integer.valueOf(type));
    }

    @NotNull
    public final String a(int type, int flags) {
        String str;
        if (flags == 0) {
            return "";
        }
        switch (type) {
            case 2:
            case 3:
            case 7:
            case 8:
                return d[flags];
            case 4:
            case 6:
                return flags == 1 ? "ACK" : d[flags];
            default:
                String[] strArr = c;
                if (flags < strArr.length) {
                    str = strArr[flags];
                    if (str == null) {
                        k.n();
                    }
                } else {
                    str = d[flags];
                }
                String result = str;
                if (type == 5 && (flags & 4) != 0) {
                    return w.H(result, "HEADERS", "PUSH_PROMISE", false, 4, (Object) null);
                }
                if (type != 0 || (flags & 32) == 0) {
                    return result;
                }
                return w.H(result, "PRIORITY", "COMPRESSED", false, 4, (Object) null);
        }
    }
}
