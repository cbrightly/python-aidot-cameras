package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import java.util.Vector;

public interface TlsServer extends TlsPeer {
    void A(ProtocolVersion protocolVersion);

    int B();

    void C(short[] sArr);

    void E(Certificate certificate);

    TlsKeyExchange a();

    ProtocolVersion b();

    void c(boolean z);

    Hashtable e();

    short g();

    TlsCredentials getCredentials();

    NewSessionTicket j();

    Vector k();

    void l(Hashtable hashtable);

    void o(int[] iArr);

    void q(Vector vector);

    CertificateRequest x();

    CertificateStatus y();
}
