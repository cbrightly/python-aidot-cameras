package org.spongycastle.jce.provider;

import java.util.Collection;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.X509StoreSpi;

public class X509StoreCRLCollection extends X509StoreSpi {
    private CollectionStore a;

    public Collection a(Selector selector) {
        return this.a.a(selector);
    }
}
