package org.spongycastle.jce.provider;

import java.io.OutputStream;
import java.security.KeyStore;

public class JDKPKCS12StoreParameter implements KeyStore.LoadStoreParameter {
    private OutputStream a;
    private KeyStore.ProtectionParameter b;
    private boolean c;

    public OutputStream a() {
        return this.a;
    }

    public KeyStore.ProtectionParameter getProtectionParameter() {
        return this.b;
    }

    public boolean b() {
        return this.c;
    }
}
