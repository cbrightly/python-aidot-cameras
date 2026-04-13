package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: Internal */
public class i {
    public static final byte[] a;
    public static final ByteBuffer b;

    /* compiled from: Internal */
    public interface a {
        int getNumber();
    }

    /* compiled from: Internal */
    public interface b<T extends a> {
        T a(int i);
    }

    public static boolean a(byte[] byteArray) {
        return v.e(byteArray);
    }

    public static String b(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    static {
        byte[] bArr = new byte[0];
        a = bArr;
        b = ByteBuffer.wrap(bArr);
    }
}
