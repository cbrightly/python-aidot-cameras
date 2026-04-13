package org.spongycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.Digest;

public class NullDigest implements Digest {
    private ByteArrayOutputStream a = new ByteArrayOutputStream();

    public String b() {
        return "NULL";
    }

    public int e() {
        return this.a.size();
    }

    public void d(byte in) {
        this.a.write(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.write(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        byte[] res = this.a.toByteArray();
        System.arraycopy(res, 0, out, outOff, res.length);
        reset();
        return res.length;
    }

    public void reset() {
        this.a.reset();
    }
}
