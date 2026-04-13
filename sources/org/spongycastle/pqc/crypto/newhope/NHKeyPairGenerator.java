package org.spongycastle.pqc.crypto.newhope;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;

public class NHKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private SecureRandom g;

    public void b(KeyGenerationParameters param) {
        this.g = param.a();
    }

    public AsymmetricCipherKeyPair a() {
        byte[] pubData = new byte[AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVICESLEEP_REQ];
        short[] secData = new short[1024];
        NewHope.f(this.g, pubData, secData);
        return new AsymmetricCipherKeyPair(new NHPublicKeyParameters(pubData), new NHPrivateKeyParameters(secData));
    }
}
