package com.sensorsdata.analytics.android.sdk.encrypt;

public enum SymmetricEncryptMode {
    AES("AES", "AES/CBC/PKCS5Padding"),
    SM4("SM4", "SM4/CBC/PKCS5Padding");
    
    public String algorithm;
    public String transformation;

    private SymmetricEncryptMode(String algorithm2, String transformation2) {
        this.algorithm = algorithm2;
        this.transformation = transformation2;
    }
}
