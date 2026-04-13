package com.sensorsdata.analytics.android.sdk.encrypt;

public interface IPersistentSecretKey {
    SecreteKey loadSecretKey();

    void saveSecretKey(SecreteKey secreteKey);
}
