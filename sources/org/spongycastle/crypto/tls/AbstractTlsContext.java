package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.RandomGenerator;
import org.spongycastle.util.Times;

public abstract class AbstractTlsContext implements TlsContext {
    private static long a = Times.a();
    private RandomGenerator b;
    private SecureRandom c;
    private SecurityParameters d;
    private ProtocolVersion e;
    private ProtocolVersion f;
    private TlsSession g;

    public RandomGenerator e() {
        return this.b;
    }

    public SecureRandom d() {
        return this.c;
    }

    public SecurityParameters f() {
        return this.d;
    }

    public ProtocolVersion c() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public void a(ProtocolVersion clientVersion) {
        this.e = clientVersion;
    }

    public ProtocolVersion b() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public void i(ProtocolVersion serverVersion) {
        this.f = serverVersion;
    }

    /* access modifiers changed from: package-private */
    public void h(TlsSession session) {
        this.g = session;
    }
}
