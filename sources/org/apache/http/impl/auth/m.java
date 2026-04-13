package org.apache.http.impl.auth;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.l;
import org.apache.http.auth.n;
import org.apache.http.message.q;
import org.apache.http.o;
import org.apache.http.util.d;

/* compiled from: NTLMScheme */
public class m extends a {
    private final k c;
    private a d;
    private String f;

    /* compiled from: NTLMScheme */
    public enum a {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        MSG_TYPE1_GENERATED,
        MSG_TYPE2_RECEVIED,
        MSG_TYPE3_GENERATED,
        FAILED
    }

    public m(k engine) {
        org.apache.http.util.a.i(engine, "NTLM engine");
        this.c = engine;
        this.d = a.UNINITIATED;
        this.f = null;
    }

    public m() {
        this(new l());
    }

    public String getSchemeName() {
        return "ntlm";
    }

    public String getRealm() {
        return null;
    }

    public boolean isConnectionBased() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(d buffer, int beginIndex, int endIndex) {
        String substringTrimmed = buffer.substringTrimmed(beginIndex, endIndex);
        this.f = substringTrimmed;
        if (!substringTrimmed.isEmpty()) {
            a aVar = this.d;
            a aVar2 = a.MSG_TYPE1_GENERATED;
            if (aVar.compareTo(aVar2) < 0) {
                this.d = a.FAILED;
                throw new MalformedChallengeException("Out of sequence NTLM response message");
            } else if (this.d == aVar2) {
                this.d = a.MSG_TYPE2_RECEVIED;
            }
        } else if (this.d == a.UNINITIATED) {
            this.d = a.CHALLENGE_RECEIVED;
        } else {
            this.d = a.FAILED;
        }
    }

    public org.apache.http.d authenticate(l credentials, o request) {
        String response;
        try {
            n ntcredentials = (n) credentials;
            a aVar = this.d;
            if (aVar != a.FAILED) {
                if (aVar == a.CHALLENGE_RECEIVED) {
                    response = this.c.b(ntcredentials.getDomain(), ntcredentials.getWorkstation());
                    this.d = a.MSG_TYPE1_GENERATED;
                } else if (aVar == a.MSG_TYPE2_RECEVIED) {
                    response = this.c.a(ntcredentials.getUserName(), ntcredentials.getPassword(), ntcredentials.getDomain(), ntcredentials.getWorkstation(), this.f);
                    this.d = a.MSG_TYPE3_GENERATED;
                } else {
                    throw new AuthenticationException("Unexpected state: " + this.d);
                }
                d buffer = new d(32);
                if (isProxy()) {
                    buffer.append("Proxy-Authorization");
                } else {
                    buffer.append("Authorization");
                }
                buffer.append(": NTLM ");
                buffer.append(response);
                return new q(buffer);
            }
            throw new AuthenticationException("NTLM authentication failed");
        } catch (ClassCastException e) {
            throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + credentials.getClass().getName());
        }
    }

    public boolean isComplete() {
        a aVar = this.d;
        return aVar == a.MSG_TYPE3_GENERATED || aVar == a.FAILED;
    }
}
