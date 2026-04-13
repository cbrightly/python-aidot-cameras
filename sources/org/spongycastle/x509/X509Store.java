package org.spongycastle.x509;

import java.util.Collection;
import org.spongycastle.util.Selector;
import org.spongycastle.util.Store;

public class X509Store implements Store {
    private X509StoreSpi c;

    public Collection a(Selector selector) {
        return this.c.a(selector);
    }
}
