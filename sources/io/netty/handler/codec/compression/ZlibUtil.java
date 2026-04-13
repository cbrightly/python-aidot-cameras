package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;

public final class ZlibUtil {
    static void fail(Inflater z, String message, int resultCode) {
        throw inflaterException(z, message, resultCode);
    }

    static void fail(Deflater z, String message, int resultCode) {
        throw deflaterException(z, message, resultCode);
    }

    static DecompressionException inflaterException(Inflater z, String message, int resultCode) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(" (");
        sb.append(resultCode);
        sb.append(')');
        if (z.msg != null) {
            str = ": " + z.msg;
        } else {
            str = "";
        }
        sb.append(str);
        return new DecompressionException(sb.toString());
    }

    static CompressionException deflaterException(Deflater z, String message, int resultCode) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(" (");
        sb.append(resultCode);
        sb.append(')');
        if (z.msg != null) {
            str = ": " + z.msg;
        } else {
            str = "";
        }
        sb.append(str);
        return new CompressionException(sb.toString());
    }

    /* renamed from: io.netty.handler.codec.compression.ZlibUtil$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper;

        static {
            int[] iArr = new int[ZlibWrapper.values().length];
            $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = iArr;
            try {
                iArr[ZlibWrapper.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.GZIP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB_OR_NONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static JZlib.WrapperType convertWrapperType(ZlibWrapper wrapper) {
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[wrapper.ordinal()]) {
            case 1:
                return JZlib.W_NONE;
            case 2:
                return JZlib.W_ZLIB;
            case 3:
                return JZlib.W_GZIP;
            case 4:
                return JZlib.W_ANY;
            default:
                throw new Error();
        }
    }

    static int wrapperOverhead(ZlibWrapper wrapper) {
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[wrapper.ordinal()]) {
            case 1:
                return 0;
            case 2:
            case 4:
                return 2;
            case 3:
                return 10;
            default:
                throw new Error();
        }
    }

    private ZlibUtil() {
    }
}
