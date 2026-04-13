package org.spongycastle.x509;

import java.util.Collection;

public class X509CollectionStoreParameters implements X509StoreParameters {
    private Collection c;

    public X509CollectionStoreParameters(Collection collection) {
        if (collection != null) {
            this.c = collection;
            return;
        }
        throw new NullPointerException("collection cannot be null");
    }

    public Object clone() {
        return new X509CollectionStoreParameters(this.c);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("X509CollectionStoreParameters: [\n");
        sb.append("  collection: " + this.c + "\n");
        sb.append("]");
        return sb.toString();
    }
}
