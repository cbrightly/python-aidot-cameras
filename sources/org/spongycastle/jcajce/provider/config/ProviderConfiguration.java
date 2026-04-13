package org.spongycastle.jcajce.provider.config;

import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jce.spec.ECParameterSpec;

public interface ProviderConfiguration {
    Map a();

    ECParameterSpec b();

    Set c();

    DHParameterSpec d(int i);
}
