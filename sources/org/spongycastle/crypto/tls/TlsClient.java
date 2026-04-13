package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import java.util.Vector;

public interface TlsClient extends TlsPeer {
    void D(NewSessionTicket newSessionTicket);

    TlsKeyExchange a();

    void d(short s);

    Vector f();

    void i(Hashtable hashtable);

    void r(ProtocolVersion protocolVersion);

    void t(Vector vector);

    void u(byte[] bArr);

    TlsAuthentication w();

    void z(int i);
}
