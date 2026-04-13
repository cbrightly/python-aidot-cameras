package org.spongycastle.util.test;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.crypto.prng.EntropySourceProvider;

public class TestRandomEntropySourceProvider implements EntropySourceProvider {
    /* access modifiers changed from: private */
    public final SecureRandom a;

    public EntropySource get(final int bitsRequired) {
        return new EntropySource() {
            public byte[] a() {
                byte[] rv = new byte[((bitsRequired + 7) / 8)];
                TestRandomEntropySourceProvider.this.a.nextBytes(rv);
                return rv;
            }

            public int b() {
                return bitsRequired;
            }
        };
    }
}
