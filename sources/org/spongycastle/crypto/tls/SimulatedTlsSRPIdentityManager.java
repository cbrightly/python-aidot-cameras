package org.spongycastle.crypto.tls;

import chip.platform.ConfigurationManager;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.agreement.srp.SRP6VerifierGenerator;
import org.spongycastle.crypto.params.SRP6GroupParameters;
import org.spongycastle.util.Strings;

public class SimulatedTlsSRPIdentityManager implements TlsSRPIdentityManager {
    private static final byte[] a = Strings.f("password");
    private static final byte[] b = Strings.f(ConfigurationManager.kConfigKey_Spake2pSalt);
    protected SRP6GroupParameters c;
    protected SRP6VerifierGenerator d;
    protected Mac e;

    public TlsSRPLoginParameters a(byte[] identity) {
        Mac mac = this.e;
        byte[] bArr = b;
        mac.update(bArr, 0, bArr.length);
        this.e.update(identity, 0, identity.length);
        byte[] salt = new byte[this.e.e()];
        this.e.c(salt, 0);
        Mac mac2 = this.e;
        byte[] bArr2 = a;
        mac2.update(bArr2, 0, bArr2.length);
        this.e.update(identity, 0, identity.length);
        byte[] password = new byte[this.e.e()];
        this.e.c(password, 0);
        return new TlsSRPLoginParameters(this.c, this.d.a(salt, identity, password), salt);
    }
}
