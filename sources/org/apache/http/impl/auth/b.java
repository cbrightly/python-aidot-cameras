package org.apache.http.impl.auth;

import com.google.maps.android.BuildConfig;
import java.nio.charset.Charset;
import org.apache.http.auth.j;
import org.apache.http.auth.l;
import org.apache.http.d;
import org.apache.http.message.q;
import org.apache.http.o;
import org.apache.http.protocol.a;
import org.apache.http.protocol.f;

/* compiled from: BasicScheme */
public class b extends o {
    private static final long serialVersionUID = -1931571557597830536L;
    private boolean complete;

    public b(Charset credentialsCharset) {
        super(credentialsCharset);
        this.complete = false;
    }

    @Deprecated
    public b(j challengeState) {
        super(challengeState);
    }

    public b() {
        this(org.apache.http.b.b);
    }

    public String getSchemeName() {
        return "basic";
    }

    public void processChallenge(d header) {
        super.processChallenge(header);
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public boolean isConnectionBased() {
        return false;
    }

    @Deprecated
    public d authenticate(l credentials, o request) {
        return authenticate(credentials, request, (f) new a());
    }

    public d authenticate(l credentials, o request, f context) {
        org.apache.http.util.a.i(credentials, "Credentials");
        org.apache.http.util.a.i(request, "HTTP request");
        StringBuilder tmp = new StringBuilder();
        tmp.append(credentials.getUserPrincipal().getName());
        tmp.append(":");
        tmp.append(credentials.getPassword() == null ? BuildConfig.TRAVIS : credentials.getPassword());
        byte[] base64password = new org.apache.commons.codec.binary.a(0).f(org.apache.http.util.f.d(tmp.toString(), getCredentialsCharset(request)));
        org.apache.http.util.d buffer = new org.apache.http.util.d(32);
        if (isProxy()) {
            buffer.append("Proxy-Authorization");
        } else {
            buffer.append("Authorization");
        }
        buffer.append(": Basic ");
        buffer.append(base64password, 0, base64password.length);
        return new q(buffer);
    }

    @Deprecated
    public static d authenticate(l credentials, String charset, boolean proxy) {
        org.apache.http.util.a.i(credentials, "Credentials");
        org.apache.http.util.a.i(charset, "charset");
        StringBuilder tmp = new StringBuilder();
        tmp.append(credentials.getUserPrincipal().getName());
        tmp.append(":");
        tmp.append(credentials.getPassword() == null ? BuildConfig.TRAVIS : credentials.getPassword());
        byte[] base64password = org.apache.commons.codec.binary.a.o(org.apache.http.util.f.d(tmp.toString(), charset), false);
        org.apache.http.util.d buffer = new org.apache.http.util.d(32);
        if (proxy) {
            buffer.append("Proxy-Authorization");
        } else {
            buffer.append("Authorization");
        }
        buffer.append(": Basic ");
        buffer.append(base64password, 0, base64password.length);
        return new q(buffer);
    }

    public String toString() {
        return "BASIC [complete=" + this.complete + "]";
    }
}
