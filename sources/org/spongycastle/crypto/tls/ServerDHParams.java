package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;

public class ServerDHParams {
    protected DHPublicKeyParameters publicKey;

    public ServerDHParams(DHPublicKeyParameters publicKey2) {
        if (publicKey2 != null) {
            this.publicKey = publicKey2;
            return;
        }
        throw new IllegalArgumentException("'publicKey' cannot be null");
    }

    public DHPublicKeyParameters getPublicKey() {
        return this.publicKey;
    }

    public void encode(OutputStream output) {
        DHParameters dhParameters = this.publicKey.b();
        BigInteger Ys = this.publicKey.c();
        TlsDHUtils.j(dhParameters.e(), output);
        TlsDHUtils.j(dhParameters.b(), output);
        TlsDHUtils.j(Ys, output);
    }

    public static ServerDHParams parse(InputStream input) {
        return new ServerDHParams(TlsDHUtils.i(new DHPublicKeyParameters(TlsDHUtils.g(input), new DHParameters(TlsDHUtils.g(input), TlsDHUtils.g(input)))));
    }
}
