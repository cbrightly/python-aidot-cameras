package org.spongycastle.jcajce.util;

import java.security.Provider;
import java.security.Security;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class BCJcaJceHelper extends ProviderJcaJceHelper {
    private static volatile Provider b;

    private static Provider h() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) != null) {
            return Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
        if (b != null) {
            return b;
        }
        b = new BouncyCastleProvider();
        return b;
    }

    public BCJcaJceHelper() {
        super(h());
    }
}
