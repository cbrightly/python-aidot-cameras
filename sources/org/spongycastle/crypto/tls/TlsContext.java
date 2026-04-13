package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.RandomGenerator;

public interface TlsContext {
    ProtocolVersion b();

    ProtocolVersion c();

    SecureRandom d();

    RandomGenerator e();

    SecurityParameters f();

    boolean g();
}
