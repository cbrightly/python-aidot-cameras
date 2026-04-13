package org.spongycastle.crypto.signers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Integers;

public class ISOTrailers {
    private static final Map<String, Integer> a;

    static {
        Map<String, Integer> trailers = new HashMap<>();
        trailers.put("RIPEMD128", Integers.b(13004));
        trailers.put("RIPEMD160", Integers.b(12748));
        trailers.put("SHA-1", Integers.b(13260));
        trailers.put("SHA-224", Integers.b(14540));
        trailers.put("SHA-256", Integers.b(13516));
        trailers.put("SHA-384", Integers.b(14028));
        trailers.put("SHA-512", Integers.b(13772));
        trailers.put("SHA-512/224", Integers.b(14796));
        trailers.put("SHA-512/256", Integers.b(16588));
        trailers.put("Whirlpool", Integers.b(14284));
        a = Collections.unmodifiableMap(trailers);
    }

    public static Integer a(Digest digest) {
        return a.get(digest.b());
    }
}
