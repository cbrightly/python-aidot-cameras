package com.sensorsdata.analytics.android.sdk.encrypt;

public interface SAEncryptListener {
    String asymmetricEncryptType();

    String encryptEvent(byte[] bArr);

    String encryptSymmetricKeyWithPublicKey(String str);

    String symmetricEncryptType();
}
