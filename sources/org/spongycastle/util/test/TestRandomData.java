package org.spongycastle.util.test;

import org.spongycastle.util.encoders.Hex;
import org.spongycastle.util.test.FixedSecureRandom;

public class TestRandomData extends FixedSecureRandom {
    public TestRandomData(String encoding) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.Data(Hex.a(encoding))});
    }

    public TestRandomData(byte[] encoding) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.Data(encoding)});
    }
}
