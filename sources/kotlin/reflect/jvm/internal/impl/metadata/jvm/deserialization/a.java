package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import org.jetbrains.annotations.NotNull;

/* compiled from: BitEncoding */
public class a {
    private static final boolean a;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/BitEncoding";
                break;
            default:
                objArr[0] = "data";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "encodeBytes";
                break;
            case 3:
                objArr[1] = "encode8to7";
                break;
            case 6:
                objArr[1] = "splitBytesToStringArray";
                break;
            case 8:
                objArr[1] = "decodeBytes";
                break;
            case 10:
                objArr[1] = "dropMarker";
                break;
            case 12:
                objArr[1] = "combineStringArrayIntoBytes";
                break;
            case 14:
                objArr[1] = "decode7to8";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/BitEncoding";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                break;
            case 2:
                objArr[2] = "encode8to7";
                break;
            case 4:
                objArr[2] = "addModuloByte";
                break;
            case 5:
                objArr[2] = "splitBytesToStringArray";
                break;
            case 7:
                objArr[2] = "decodeBytes";
                break;
            case 9:
                objArr[2] = "dropMarker";
                break;
            case 11:
                objArr[2] = "combineStringArrayIntoBytes";
                break;
            case 13:
                objArr[2] = "decode7to8";
                break;
            default:
                objArr[2] = "encodeBytes";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    static {
        String use8to7;
        try {
            use8to7 = System.getProperty("kotlin.jvm.serialization.use8to7");
        } catch (SecurityException e) {
            use8to7 = null;
        }
        a = "true".equals(use8to7);
    }

    private static void b(@NotNull byte[] data, int increment) {
        if (data == null) {
            a(4);
        }
        int n = data.length;
        for (int i = 0; i < n; i++) {
            data[i] = (byte) ((data[i] + increment) & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        }
    }

    @NotNull
    public static byte[] e(@NotNull String[] data) {
        if (data == null) {
            a(7);
        }
        if (data.length > 0 && !data[0].isEmpty()) {
            char possibleMarker = data[0].charAt(0);
            if (possibleMarker == 0) {
                byte[] a2 = j.a(f(data));
                if (a2 == null) {
                    a(8);
                }
                return a2;
            } else if (possibleMarker == 65535) {
                data = f(data);
            }
        }
        byte[] bytes = c(data);
        b(bytes, NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        return d(bytes);
    }

    @NotNull
    private static String[] f(@NotNull String[] data) {
        if (data == null) {
            a(9);
        }
        String[] result = (String[]) data.clone();
        result[0] = result[0].substring(1);
        return result;
    }

    @NotNull
    private static byte[] c(@NotNull String[] data) {
        if (data == null) {
            a(11);
        }
        int resultLength = 0;
        String[] arr$ = data;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            String s = arr$[i$];
            if (s.length() <= 65535) {
                resultLength += s.length();
                i$++;
            } else {
                throw new AssertionError("String is too long: " + s.length());
            }
        }
        byte[] result = new byte[resultLength];
        int p = 0;
        for (String s2 : data) {
            int i = 0;
            int n = s2.length();
            while (i < n) {
                result[p] = (byte) s2.charAt(i);
                i++;
                p++;
            }
        }
        return result;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] d(@org.jetbrains.annotations.NotNull byte[] r9) {
        /*
            if (r9 != 0) goto L_0x0007
            r0 = 13
            a(r0)
        L_0x0007:
            int r0 = r9.length
            int r0 = r0 * 7
            int r0 = r0 / 8
            byte[] r1 = new byte[r0]
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0011:
            if (r4 >= r0) goto L_0x0037
            byte r5 = r9[r2]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 >>> r3
            int r2 = r2 + 1
            byte r6 = r9[r2]
            int r7 = r3 + 1
            r8 = 1
            int r7 = r8 << r7
            int r7 = r7 - r8
            r6 = r6 & r7
            int r7 = 7 - r3
            int r6 = r6 << r7
            int r7 = r5 + r6
            byte r7 = (byte) r7
            r1[r4] = r7
            r7 = 6
            if (r3 != r7) goto L_0x0032
            int r2 = r2 + 1
            r3 = 0
            goto L_0x0034
        L_0x0032:
            int r3 = r3 + 1
        L_0x0034:
            int r4 = r4 + 1
            goto L_0x0011
        L_0x0037:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.a.d(byte[]):byte[]");
    }
}
