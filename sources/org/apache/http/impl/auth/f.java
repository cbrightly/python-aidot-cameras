package org.apache.http.impl.auth;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.logging.h;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.l;
import org.apache.http.auth.m;
import org.apache.http.d;
import org.apache.http.message.q;
import org.apache.http.o;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

/* compiled from: GGSSchemeBase */
public abstract class f extends a {
    private final org.apache.commons.logging.a c = h.n(getClass());
    private final org.apache.commons.codec.binary.a d = new org.apache.commons.codec.binary.a(0);
    private final boolean f;
    private final boolean q;
    private b x;
    private byte[] y;

    /* compiled from: GGSSchemeBase */
    public enum b {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        TOKEN_GENERATED,
        FAILED
    }

    /* access modifiers changed from: protected */
    public abstract byte[] c(byte[] bArr, String str, l lVar);

    f(boolean stripPort, boolean useCanonicalHostname) {
        this.f = stripPort;
        this.q = useCanonicalHostname;
        this.x = b.UNINITIATED;
    }

    /* access modifiers changed from: protected */
    public GSSManager d() {
        return GSSManager.getInstance();
    }

    /* access modifiers changed from: protected */
    public byte[] b(byte[] input, Oid oid, String authServer, l credentials) {
        GSSCredential gssCredential;
        GSSManager manager = d();
        GSSName serverName = manager.createName("HTTP@" + authServer, GSSName.NT_HOSTBASED_SERVICE);
        if (credentials instanceof m) {
            gssCredential = ((m) credentials).getGSSCredential();
        } else {
            gssCredential = null;
        }
        GSSContext gssContext = a(manager, oid, serverName, gssCredential);
        if (input != null) {
            return gssContext.initSecContext(input, 0, input.length);
        }
        return gssContext.initSecContext(new byte[0], 0, 0);
    }

    /* access modifiers changed from: package-private */
    public GSSContext a(GSSManager manager, Oid oid, GSSName serverName, GSSCredential gssCredential) {
        GSSContext gssContext = manager.createContext(serverName.canonicalize(oid), oid, gssCredential, 0);
        gssContext.requestMutualAuth(true);
        return gssContext;
    }

    public boolean isComplete() {
        b bVar = this.x;
        return bVar == b.TOKEN_GENERATED || bVar == b.FAILED;
    }

    @Deprecated
    public d authenticate(l credentials, o request) {
        return authenticate(credentials, request, (org.apache.http.protocol.f) null);
    }

    /* compiled from: GGSSchemeBase */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.UNINITIATED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.CHALLENGE_RECEIVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[b.TOKEN_GENERATED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public d authenticate(l credentials, o request, org.apache.http.protocol.f context) {
        org.apache.http.l host;
        String authServer;
        org.apache.http.util.a.i(request, "HTTP request");
        switch (a.a[this.x.ordinal()]) {
            case 1:
                throw new AuthenticationException(getSchemeName() + " authentication has not been initiated");
            case 2:
                throw new AuthenticationException(getSchemeName() + " authentication has failed");
            case 3:
                try {
                    org.apache.http.conn.routing.b route = (org.apache.http.conn.routing.b) context.getAttribute("http.route");
                    if (route != null) {
                        if (isProxy()) {
                            host = route.c();
                            if (host == null) {
                                host = route.e();
                            }
                        } else {
                            host = route.e();
                        }
                        String hostname = host.getHostName();
                        if (this.q) {
                            try {
                                hostname = e(hostname);
                            } catch (UnknownHostException e) {
                            }
                        }
                        if (this.f) {
                            authServer = hostname;
                        } else {
                            authServer = hostname + ":" + host.getPort();
                        }
                        if (this.c.isDebugEnabled()) {
                            this.c.debug("init " + authServer);
                        }
                        this.y = c(this.y, authServer, credentials);
                        this.x = b.TOKEN_GENERATED;
                        break;
                    } else {
                        throw new AuthenticationException("Connection route is not available");
                    }
                } catch (GSSException gsse) {
                    this.x = b.FAILED;
                    if (gsse.getMajor() == 9 || gsse.getMajor() == 8) {
                        throw new InvalidCredentialsException(gsse.getMessage(), gsse);
                    } else if (gsse.getMajor() == 13) {
                        throw new InvalidCredentialsException(gsse.getMessage(), gsse);
                    } else if (gsse.getMajor() == 10 || gsse.getMajor() == 19 || gsse.getMajor() == 20) {
                        throw new AuthenticationException(gsse.getMessage(), gsse);
                    } else {
                        throw new AuthenticationException(gsse.getMessage());
                    }
                }
                break;
            case 4:
                break;
            default:
                throw new IllegalStateException("Illegal state: " + this.x);
        }
        String tokenstr = new String(this.d.f(this.y));
        if (this.c.isDebugEnabled()) {
            this.c.debug("Sending response '" + tokenstr + "' back to the auth server");
        }
        org.apache.http.util.d buffer = new org.apache.http.util.d(32);
        if (isProxy()) {
            buffer.append("Proxy-Authorization");
        } else {
            buffer.append("Authorization");
        }
        buffer.append(": Negotiate ");
        buffer.append(tokenstr);
        return new q(buffer);
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(org.apache.http.util.d buffer, int beginIndex, int endIndex) {
        String challenge = buffer.substringTrimmed(beginIndex, endIndex);
        if (this.c.isDebugEnabled()) {
            org.apache.commons.logging.a aVar = this.c;
            aVar.debug("Received challenge '" + challenge + "' from the auth server");
        }
        if (this.x == b.UNINITIATED) {
            this.y = org.apache.commons.codec.binary.a.m(challenge.getBytes());
            this.x = b.CHALLENGE_RECEIVED;
            return;
        }
        this.c.debug("Authentication already attempted");
        this.x = b.FAILED;
    }

    private String e(String host) {
        InetAddress in = InetAddress.getByName(host);
        String canonicalServer = in.getCanonicalHostName();
        if (in.getHostAddress().contentEquals(canonicalServer)) {
            return host;
        }
        return canonicalServer;
    }
}
