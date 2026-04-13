package org.spongycastle.jcajce;

import java.io.OutputStream;
import java.security.KeyStore;

public class PKCS12StoreParameter implements KeyStore.LoadStoreParameter {
    private final OutputStream a;
    private final KeyStore.ProtectionParameter b;
    private final boolean c;

    public PKCS12StoreParameter(OutputStream out, KeyStore.ProtectionParameter protectionParameter, boolean forDEREncoding) {
        this.a = out;
        this.b = protectionParameter;
        this.c = forDEREncoding;
    }

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
