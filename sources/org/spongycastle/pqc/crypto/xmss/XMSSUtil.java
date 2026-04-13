package org.spongycastle.pqc.crypto.xmss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.spongycastle.crypto.Digest;

public class XMSSUtil {
    public static int o(int n) {
        int log = 0;
        while (true) {
            int i = n >> 1;
            n = i;
            if (i == 0) {
                return log;
            }
            log++;
        }
    }

    public static byte[] q(long value, int sizeInByte) {
        byte[] out = new byte[sizeInByte];
        for (int i = sizeInByte - 1; i >= 0; i--) {
            out[i] = (byte) ((int) value);
            value >>>= 8;
        }
        return out;
    }

    public static long a(byte[] in, int offset, int size) {
        if (in != null) {
            long res = 0;
            for (int i = offset; i < offset + size; i++) {
                res = (res << 8) | ((long) (in[i] & 255));
            }
            return res;
        }
        throw new NullPointerException("in == null");
    }

    public static byte[] c(byte[] in) {
        if (in != null) {
            byte[] out = new byte[in.length];
            for (int i = 0; i < in.length; i++) {
                out[i] = in[i];
            }
            return out;
        }
        throw new NullPointerException("in == null");
    }

    public static byte[][] d(byte[][] in) {
        if (!k(in)) {
            byte[][] out = new byte[in.length][];
            for (int i = 0; i < in.length; i++) {
                out[i] = new byte[in[i].length];
                for (int j = 0; j < in[i].length; j++) {
                    out[i][j] = in[i][j];
                }
            }
            return out;
        }
        throw new NullPointerException("in has null pointers");
    }

    public static boolean k(byte[][] in) {
        if (in == null) {
            return true;
        }
        for (byte[] bArr : in) {
            if (bArr == null) {
                return true;
            }
        }
        return false;
    }

    public static void e(byte[] dst, byte[] src, int offset) {
        if (dst == null) {
            throw new NullPointerException("dst == null");
        } else if (src == null) {
            throw new NullPointerException("src == null");
        } else if (offset < 0) {
            throw new IllegalArgumentException("offset hast to be >= 0");
        } else if (src.length + offset <= dst.length) {
            for (int i = 0; i < src.length; i++) {
                dst[offset + i] = src[i];
            }
        } else {
            throw new IllegalArgumentException("src length + offset must not be greater than size of destination");
        }
    }

    public static byte[] g(byte[] src, int offset, int length) {
        if (src == null) {
            throw new NullPointerException("src == null");
        } else if (offset < 0) {
            throw new IllegalArgumentException("offset hast to be >= 0");
        } else if (length < 0) {
            throw new IllegalArgumentException("length hast to be >= 0");
        } else if (offset + length <= src.length) {
            byte[] out = new byte[length];
            for (int i = 0; i < out.length; i++) {
                out[i] = src[offset + i];
            }
            return out;
        } else {
            throw new IllegalArgumentException("offset + length must not be greater then size of source array");
        }
    }

    public static boolean l(int height, long index) {
        if (index >= 0) {
            return index < (1 << height);
        }
        throw new IllegalStateException("index must not be negative");
    }

    public static int h(Digest digest) {
        if (digest != null) {
            String algorithmName = digest.b();
            if (algorithmName.equals("SHAKE128")) {
                return 32;
            }
            if (algorithmName.equals("SHAKE256")) {
                return 64;
            }
            return digest.e();
        }
        throw new NullPointerException("digest == null");
    }

    public static long j(long index, int xmssTreeHeight) {
        return index >> xmssTreeHeight;
    }

    public static int i(long index, int xmssTreeHeight) {
        return (int) (index & ((1 << xmssTreeHeight) - 1));
    }

    public static byte[] p(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(obj);
        oos.flush();
        return out.toByteArray();
    }

    public static Object f(byte[] data) {
        return new ObjectInputStream(new ByteArrayInputStream(data)).readObject();
    }

    public static int b(int index, int height) {
        for (int i = 0; i < height; i++) {
            if (((index >> i) & 1) == 0) {
                return i;
            }
        }
        return 0;
    }

    public static boolean n(long globalIndex, int xmssHeight, int layer) {
        if (globalIndex != 0 && globalIndex % ((long) Math.pow((double) (1 << xmssHeight), (double) (layer + 1))) == 0) {
            return true;
        }
        return false;
    }

    public static boolean m(long globalIndex, int xmssHeight, int layer) {
        if (globalIndex != 0 && (1 + globalIndex) % ((long) Math.pow((double) (1 << xmssHeight), (double) layer)) == 0) {
            return true;
        }
        return false;
    }
}
