package org.spongycastle.crypto.tls;

import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.crypto.agreement.srp.SRP6StandardGroups;
import org.spongycastle.crypto.params.SRP6GroupParameters;

public class DefaultTlsSRPGroupVerifier implements TlsSRPGroupVerifier {
    protected static final Vector a;
    protected Vector b;

    static {
        Vector vector = new Vector();
        a = vector;
        vector.addElement(SRP6StandardGroups.a);
        vector.addElement(SRP6StandardGroups.b);
        vector.addElement(SRP6StandardGroups.c);
        vector.addElement(SRP6StandardGroups.d);
        vector.addElement(SRP6StandardGroups.e);
        vector.addElement(SRP6StandardGroups.f);
        vector.addElement(SRP6StandardGroups.g);
    }

    public DefaultTlsSRPGroupVerifier() {
        this(a);
    }

    public DefaultTlsSRPGroupVerifier(Vector groups) {
        this.b = groups;
    }

    public boolean a(SRP6GroupParameters group) {
        for (int i = 0; i < this.b.size(); i++) {
            if (b(group, (SRP6GroupParameters) this.b.elementAt(i))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean b(SRP6GroupParameters a2, SRP6GroupParameters b2) {
        return a2 == b2 || (c(a2.b(), b2.b()) && c(a2.a(), b2.a()));
    }

    /* access modifiers changed from: protected */
    public boolean c(BigInteger a2, BigInteger b2) {
        return a2 == b2 || a2.equals(b2);
    }
}
