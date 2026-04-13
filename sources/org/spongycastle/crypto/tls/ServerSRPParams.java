package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;

public class ServerSRPParams {
    protected BigInteger B;
    protected BigInteger N;
    protected BigInteger g;
    protected byte[] s;

    public ServerSRPParams(BigInteger N2, BigInteger g2, byte[] s2, BigInteger B2) {
        this.N = N2;
        this.g = g2;
        this.s = Arrays.h(s2);
        this.B = B2;
    }

    public BigInteger getB() {
        return this.B;
    }

    public BigInteger getG() {
        return this.g;
    }

    public BigInteger getN() {
        return this.N;
    }

    public byte[] getS() {
        return this.s;
    }

    public void encode(OutputStream output) {
        TlsSRPUtils.e(this.N, output);
        TlsSRPUtils.e(this.g, output);
        TlsUtils.C0(this.s, output);
        TlsSRPUtils.e(this.B, output);
    }

    public static ServerSRPParams parse(InputStream input) {
        return new ServerSRPParams(TlsSRPUtils.d(input), TlsSRPUtils.d(input), TlsUtils.i0(input), TlsSRPUtils.d(input));
    }
}
