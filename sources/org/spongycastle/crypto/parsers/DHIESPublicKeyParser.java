package org.spongycastle.crypto.parsers;

import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.KeyParser;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.util.io.Streams;

public class DHIESPublicKeyParser implements KeyParser {
    private DHParameters a;

    public DHIESPublicKeyParser(DHParameters dhParams) {
        this.a = dhParams;
    }

    public AsymmetricKeyParameter a(InputStream stream) {
        byte[] V = new byte[((this.a.e().bitLength() + 7) / 8)];
        Streams.d(stream, V, 0, V.length);
        return new DHPublicKeyParameters(new BigInteger(1, V), this.a);
    }
}
