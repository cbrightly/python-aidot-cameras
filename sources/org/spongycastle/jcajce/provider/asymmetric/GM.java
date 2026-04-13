package org.spongycastle.jcajce.provider.asymmetric;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.gm.GMObjectIdentifiers;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class GM {
    private static final Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("SupportedKeyClasses", "java.security.interfaces.ECPublicKey|java.security.interfaces.ECPrivateKey");
        hashMap.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("Signature.SM3WITHSM2", "org.spongycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi$sm3WithSM2");
            provider.addAlgorithm("Alg.Alias.Signature." + GMObjectIdentifiers.d0, "SM3WITHSM2");
        }
    }
}
