package org.spongycastle.pqc.crypto.sphincs;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.sphincs.Tree;

public class SPHINCS256KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private SecureRandom g;
    private Digest h;

    public void b(KeyGenerationParameters param) {
        this.g = param.a();
        this.h = ((SPHINCS256KeyGenerationParameters) param).c();
    }

    public AsymmetricCipherKeyPair a() {
        Tree.leafaddr a = new Tree.leafaddr();
        byte[] sk = new byte[AVIOCTRLDEFs.IOTYPE_PRESET_SETPRESET_REQ];
        this.g.nextBytes(sk);
        byte[] pk = new byte[AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_REQ];
        System.arraycopy(sk, 32, pk, 0, 1024);
        a.a = 11;
        a.b = 0;
        a.c = 0;
        Tree.c(new HashFunctions(this.h), pk, 1024, 5, sk, a, pk, 0);
        return new AsymmetricCipherKeyPair(new SPHINCSPublicKeyParameters(pk), new SPHINCSPrivateKeyParameters(sk));
    }
}
