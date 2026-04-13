package org.spongycastle.crypto.parsers;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.KeyParser;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.util.io.Streams;

public class ECIESPublicKeyParser implements KeyParser {
    private ECDomainParameters a;

    public ECIESPublicKeyParser(ECDomainParameters ecParams) {
        this.a = ecParams;
    }

    public AsymmetricKeyParameter a(InputStream stream) {
        byte[] V;
        int first = stream.read();
        switch (first) {
            case 0:
                throw new IOException("Sender's public key invalid.");
            case 2:
            case 3:
                V = new byte[(((this.a.a().t() + 7) / 8) + 1)];
                break;
            case 4:
            case 6:
            case 7:
                V = new byte[((((this.a.a().t() + 7) / 8) * 2) + 1)];
                break;
            default:
                throw new IOException("Sender's public key has invalid point encoding 0x" + Integer.toString(first, 16));
        }
        V[0] = (byte) first;
        Streams.d(stream, V, 1, V.length - 1);
        return new ECPublicKeyParameters(this.a.a().j(V), this.a);
    }
}
