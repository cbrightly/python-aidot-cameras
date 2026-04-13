package org.spongycastle.crypto.prng.drbg;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.Hashtable;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.util.Integers;

public class Utils {
    static final Hashtable a;

    Utils() {
    }

    static {
        Hashtable hashtable = new Hashtable();
        a = hashtable;
        hashtable.put("SHA-1", Integers.b(128));
        hashtable.put("SHA-224", Integers.b(Opcodes.CHECKCAST));
        hashtable.put("SHA-256", Integers.b(256));
        hashtable.put("SHA-384", Integers.b(256));
        hashtable.put("SHA-512", Integers.b(256));
        hashtable.put("SHA-512/224", Integers.b(Opcodes.CHECKCAST));
        hashtable.put("SHA-512/256", Integers.b(256));
    }

    static int a(Digest d) {
        return ((Integer) a.get(d.b())).intValue();
    }

    static int b(Mac m) {
        String name = m.b();
        return ((Integer) a.get(name.substring(0, name.indexOf("/")))).intValue();
    }

    static byte[] c(Digest digest, byte[] seedMaterial, int seedLength) {
        byte[] temp = new byte[((seedLength + 7) / 8)];
        int len = temp.length / digest.e();
        int counter = 1;
        byte[] dig = new byte[digest.e()];
        for (int i = 0; i <= len; i++) {
            digest.d((byte) counter);
            digest.d((byte) (seedLength >> 24));
            digest.d((byte) (seedLength >> 16));
            digest.d((byte) (seedLength >> 8));
            digest.d((byte) seedLength);
            digest.update(seedMaterial, 0, seedMaterial.length);
            digest.c(dig, 0);
            System.arraycopy(dig, 0, temp, dig.length * i, temp.length - (dig.length * i) > dig.length ? dig.length : temp.length - (dig.length * i));
            counter++;
        }
        if (seedLength % 8 != 0) {
            int shift = 8 - (seedLength % 8);
            int carry = 0;
            for (int i2 = 0; i2 != temp.length; i2++) {
                int b = temp[i2] & 255;
                temp[i2] = (byte) ((b >>> shift) | (carry << (8 - shift)));
                carry = b;
            }
        }
        return temp;
    }

    static boolean d(byte[] bytes, int maxBytes) {
        return bytes != null && bytes.length > maxBytes;
    }
}
