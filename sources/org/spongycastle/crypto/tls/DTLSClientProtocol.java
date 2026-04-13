package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import org.spongycastle.crypto.tls.SessionParameters;

public class DTLSClientProtocol extends DTLSProtocol {

    public static class ClientHandshakeState {
        TlsClient a = null;
        TlsClientContextImpl b = null;
        TlsSession c = null;
        SessionParameters d = null;
        SessionParameters.Builder e = null;
        int[] f = null;
        short[] g = null;
        Hashtable h = null;
        Hashtable i = null;
        byte[] j = null;
        boolean k = false;
        boolean l = false;
        boolean m = false;
        boolean n = false;
        TlsKeyExchange o = null;
        TlsAuthentication p = null;
        CertificateStatus q = null;
        CertificateRequest r = null;
        TlsCredentials s = null;

        protected ClientHandshakeState() {
        }
    }
}
