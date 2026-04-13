package com.sensorsdata.analytics.android.sdk.encrypt;

public class SecreteKey {
    public String asymmetricEncryptType;
    public String key;
    public String symmetricEncryptType;
    public int version;

    public SecreteKey(String secretKey, int secretVersion, String symmetricEncryptType2, String asymmetricEncryptType2) {
        this.key = secretKey;
        this.version = secretVersion;
        this.symmetricEncryptType = symmetricEncryptType2;
        this.asymmetricEncryptType = asymmetricEncryptType2;
    }

    public String toString() {
        return "{\"key\":\"" + this.key + "\",\"version\":\"" + this.version + "\",\"symmetricEncryptType\":\"" + this.symmetricEncryptType + "\",\"asymmetricEncryptType\":\"" + this.asymmetricEncryptType + "\"}";
    }
}
