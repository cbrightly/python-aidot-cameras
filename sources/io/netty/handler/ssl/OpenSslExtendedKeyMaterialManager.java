package io.netty.handler.ssl;

import java.security.Principal;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.security.auth.x500.X500Principal;

public final class OpenSslExtendedKeyMaterialManager extends OpenSslKeyMaterialManager {
    private final X509ExtendedKeyManager keyManager;

    OpenSslExtendedKeyMaterialManager(X509ExtendedKeyManager keyManager2, String password) {
        super(keyManager2, password);
        this.keyManager = keyManager2;
    }

    /* access modifiers changed from: protected */
    public String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
        return this.keyManager.chooseEngineClientAlias(keyTypes, issuer, engine);
    }

    /* access modifiers changed from: protected */
    public String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type) {
        return this.keyManager.chooseEngineServerAlias(type, (Principal[]) null, engine);
    }
}
