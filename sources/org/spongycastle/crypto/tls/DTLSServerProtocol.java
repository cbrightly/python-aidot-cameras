package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import org.spongycastle.crypto.tls.SessionParameters;

public class DTLSServerProtocol extends DTLSProtocol {

    public static class ServerHandshakeState {
        TlsServer a = null;
        TlsServerContextImpl b = null;
        TlsSession c = null;
        SessionParameters d = null;
        SessionParameters.Builder e = null;
        int[] f = null;
        short[] g = null;
        Hashtable h = null;
        Hashtable i = null;
        boolean j = false;
        boolean k = false;
        boolean l = false;
        boolean m = false;
        TlsKeyExchange n = null;
        TlsCredentials o = null;
        CertificateRequest p = null;
        short q = -1;
        Certificate r = null;

        protected ServerHandshakeState() {
        }
    }
}
