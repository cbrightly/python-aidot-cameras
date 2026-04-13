package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public abstract class SymmetricAlgorithmProvider extends AlgorithmProvider {
    SymmetricAlgorithmProvider() {
    }

    /* access modifiers changed from: protected */
    public void b(ConfigurableProvider provider, String algorithm, String algorithmClassName, String keyGeneratorClassName) {
        provider.addAlgorithm("Mac." + algorithm + "-CMAC", algorithmClassName);
        provider.addAlgorithm("Alg.Alias.Mac." + algorithm + "CMAC", algorithm + "-CMAC");
        provider.addAlgorithm("KeyGenerator." + algorithm + "-CMAC", keyGeneratorClassName);
        provider.addAlgorithm("Alg.Alias.KeyGenerator." + algorithm + "CMAC", algorithm + "-CMAC");
    }

    /* access modifiers changed from: protected */
    public void c(ConfigurableProvider provider, String algorithm, String algorithmClassName, String keyGeneratorClassName) {
        provider.addAlgorithm("Mac." + algorithm + "-GMAC", algorithmClassName);
        provider.addAlgorithm("Alg.Alias.Mac." + algorithm + "GMAC", algorithm + "-GMAC");
        provider.addAlgorithm("KeyGenerator." + algorithm + "-GMAC", keyGeneratorClassName);
        provider.addAlgorithm("Alg.Alias.KeyGenerator." + algorithm + "GMAC", algorithm + "-GMAC");
    }

    /* access modifiers changed from: protected */
    public void d(ConfigurableProvider provider, String algorithm, String algorithmClassName, String keyGeneratorClassName) {
        provider.addAlgorithm("Mac.POLY1305-" + algorithm, algorithmClassName);
        provider.addAlgorithm("Alg.Alias.Mac.POLY1305" + algorithm, "POLY1305-" + algorithm);
        provider.addAlgorithm("KeyGenerator.POLY1305-" + algorithm, keyGeneratorClassName);
        provider.addAlgorithm("Alg.Alias.KeyGenerator.POLY1305" + algorithm, "POLY1305-" + algorithm);
    }
}
