package org.spongycastle.crypto.tls;

import java.io.OutputStream;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;

public class TlsRSAUtils {
    public static byte[] a(TlsContext context, RSAKeyParameters rsaServerPublicKey, OutputStream output) {
        byte[] premasterSecret = new byte[48];
        context.d().nextBytes(premasterSecret);
        TlsUtils.S0(context.c(), premasterSecret, 0);
        PKCS1Encoding encoding = new PKCS1Encoding(new RSABlindedEngine());
        encoding.a(true, new ParametersWithRandom(rsaServerPublicKey, context.d()));
        try {
            byte[] encryptedPreMasterSecret = encoding.d(premasterSecret, 0, premasterSecret.length);
            if (TlsUtils.P(context)) {
                output.write(encryptedPreMasterSecret);
            } else {
                TlsUtils.A0(encryptedPreMasterSecret, output);
            }
            return premasterSecret;
        } catch (InvalidCipherTextException e) {
            throw new TlsFatalAlert(80, e);
        }
    }

    public static byte[] b(TlsContext context, RSAKeyParameters rsaServerPrivateKey, byte[] encryptedPreMasterSecret) {
        ProtocolVersion clientVersion = context.c();
        byte[] fallback = new byte[48];
        context.d().nextBytes(fallback);
        byte[] M = Arrays.h(fallback);
        try {
            PKCS1Encoding encoding = new PKCS1Encoding(new RSABlindedEngine(), fallback);
            encoding.a(false, new ParametersWithRandom(rsaServerPrivateKey, context.d()));
            M = encoding.d(encryptedPreMasterSecret, 0, encryptedPreMasterSecret.length);
        } catch (Exception e) {
        }
        if (0 == 0 || !clientVersion.h(ProtocolVersion.b)) {
            int correct = ((M[0] & 255) ^ clientVersion.d()) | (clientVersion.e() ^ (M[1] & 255));
            int correct2 = correct | (correct >> 1);
            int correct3 = correct2 | (correct2 >> 2);
            int mask = ~(((correct3 | (correct3 >> 4)) & 1) - 1);
            for (int i = 0; i < 48; i++) {
                M[i] = (byte) ((M[i] & (~mask)) | (fallback[i] & mask));
            }
        }
        return M;
    }
}
